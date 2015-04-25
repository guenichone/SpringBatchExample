package orga.barrak.springbatch;

import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.retry.RetryListener;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.BackOffInterruptedException;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableBatchProcessing
@ComponentScan
public class BatchConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(BatchConfiguration.class.getName());

    @Autowired
    private StepExecutionListener stepExecutionListener;
    @Autowired
    private RetryListener retryListener;

    // tag::readerwriterprocessor[]
    @Bean
    public ItemWriter<Person> writer(DataSource dataSource) {
        JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<Person>() {

            @Override
            public void write(List<? extends Person> items) throws Exception {
                LOG.info("Write item.");
                super.write(items);
            }
        };
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Person>());
        writer.setSql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)");
        writer.setDataSource(dataSource);
        return writer;
    }
    // end::readerwriterprocessor[]

    // tag::jobstep[]
    @Bean
    public Job importUserJob(JobBuilderFactory jobs, @Qualifier(value = "personStep") Step s1) {
        return jobs.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .flow(s1)
                .end()
                .build();
    }

    @Bean
    @Qualifier(value = "personStep")
    public Step step1(StepBuilderFactory stepBuilderFactory,
            ItemReader<Person> reader,
            ItemProcessor<Person, Person> processor,
            ItemWriter<Person> writer,
            SkipPolicy skipPolicy,
            RetryPolicy retryPolicy,
            TaskExecutor taskExecutor) {

        FixedBackOffPolicy backOff = new FixedBackOffPolicy() {

            @Override
            protected void doBackOff() throws BackOffInterruptedException {
                LOG.info("Do back off.");
                super.doBackOff();
            }
        };
        backOff.setBackOffPeriod(2000);

        return stepBuilderFactory.get("step1")
                .<Person, Person>chunk(2)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                // Retry policy
                //                .retryPolicy(retryPolicy)
                // OR Retry manual configuration
                .retry(NullPointerException.class)
                .retryLimit(3)
                // Retry listener is not working correctly
                .listener(retryListener)
                // BackOff policy
                .backOffPolicy(backOff)
                // Skip policy
                .skipPolicy(skipPolicy)
                // Multithreading (Exclusive with step operation)
                .taskExecutor(taskExecutor)
                .throttleLimit(1)
                // Step listener
                .listener(stepExecutionListener)
                .build();
    }
    // end::jobstep[]

    @Bean
    public TaskExecutor getTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(20);
        return taskExecutor;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
