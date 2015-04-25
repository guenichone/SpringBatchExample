package org.barrak.springbatch.monitoring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

/**
 * Step execution listener implementation.
 *
 * @author Emilien Guenichon <emilien.guenichon@cgi.com>
 */
@Component
public class StepExecutionListenerImpl implements StepExecutionListener {

    private static final Logger LOG = LoggerFactory.getLogger(StepExecutionListenerImpl.class.getName());

    @Override
    public void beforeStep(StepExecution stepExecution) {
        LOG.info("Before step");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        LOG.info("After step : {}", stepExecution.getExitStatus());
        return stepExecution.getExitStatus();
    }

}
