package org.barrak.springbatch;

import org.barrak.springbatch.pojo.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.barrak.springbatch.monitoring.InstanceCounter;

@Component
public class PersonItemProcessor extends InstanceCounter implements ItemProcessor<Person, Person> {

    private static final Logger LOG = LoggerFactory.getLogger(PersonItemProcessor.class.getName());

    @Override
    public Person process(final Person person) throws Exception {
        final String firstName = person.getFirstName().toUpperCase();
        final String lastName = person.getLastName().toUpperCase();

        final Person transformedPerson = new Person(firstName, lastName);

        LOG.info(super.toString() + " Converting ({}) into ({})", person, transformedPerson);

        if (person.getInstanceNumber() == 3) {
            LOG.info(" Throwing a retryable exception, id[{}]", person.getInstanceNumber());
            throw new NullPointerException("An error occured during conversion.");
        }

        return transformedPerson;
    }

}
