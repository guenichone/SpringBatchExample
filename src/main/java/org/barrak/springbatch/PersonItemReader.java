package org.barrak.springbatch;

import org.barrak.springbatch.pojo.Person;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

/**
 * Person item reader implementation.
 *
 * @author Emilien Guenichon <emilien.guenichon@cgi.com>
 */
@Component
public class PersonItemReader implements ItemReader<Person> {

    private static final Logger LOG = LoggerFactory.getLogger(PersonItemReader.class.getName());

    private List<Person> toProcess = Collections.synchronizedList(new ArrayList<Person>());

    @BeforeStep
    public void initReader() throws IOException {
        InputStream fileStream = ClassLoader.getSystemResourceAsStream("sample-data.csv");
        InputStreamReader isReader = new InputStreamReader(fileStream);
        BufferedReader reader = new BufferedReader(isReader);

        String line = null;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            toProcess.add(new Person(data[0], data[1]));
        }
        LOG.info("Read {} items.", toProcess.size());
    }

    @Override
    public Person read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        synchronized (toProcess) {
            if (toProcess.size() > 0) {
                LOG.info("Read item.");
                return toProcess.remove(0);
            } else {
                LOG.info("No more item to read.");
                return null;
            }
        }
    }
}
