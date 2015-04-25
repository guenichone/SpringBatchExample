package org.barrak.springbatch.policies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.BackOffContext;
import org.springframework.retry.backoff.BackOffInterruptedException;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.stereotype.Component;

/**
 * Fixed back off policy proxy with thread sleep of 2 seconds.
 *
 * @author Emilien Guenichon <emilien.guenichon@cgi.com>
 */
@Component
public class FixedBackOffPolicyProxy implements BackOffPolicy {

    private static final Logger LOG = LoggerFactory.getLogger(FixedBackOffPolicyProxy.class);

    private FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy() {

        @Override
        protected void doBackOff() throws BackOffInterruptedException {
            LOG.info("BackOff doBackOff");
            super.doBackOff();
        }
    };

    {
        fixedBackOffPolicy.setBackOffPeriod(2000);
    }

    @Override
    public BackOffContext start(RetryContext context) {
        LOG.info("BackOff start");
        return fixedBackOffPolicy.start(context);
    }

    @Override
    public void backOff(BackOffContext backOffContext) throws BackOffInterruptedException {
        LOG.info("BackOff backOff");
        fixedBackOffPolicy.backOff(backOffContext);
    }

}
