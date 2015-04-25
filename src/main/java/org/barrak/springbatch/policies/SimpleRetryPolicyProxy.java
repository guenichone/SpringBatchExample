package org.barrak.springbatch.policies;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.stereotype.Component;

/**
 * Simple retry policy proxy with 3 attempts on NullPointerException.
 *
 * @author Emilien Guenichon <emilien.guenichon@cgi.com>
 */
@Component
public class SimpleRetryPolicyProxy implements RetryPolicy {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleRetryPolicyProxy.class);

    private RetryPolicy retryPolicy;
    {
        Map<Class<? extends Throwable>, Boolean> retryableExceptions
                = new HashMap<Class<? extends Throwable>, Boolean>();
        retryableExceptions.put(NullPointerException.class, true);

        retryPolicy = new SimpleRetryPolicy(3, retryableExceptions);
    }

    @Override
    public boolean canRetry(RetryContext context) {
        LOG.debug("Before retry");
        return retryPolicy.canRetry(context);
    }

    @Override
    public RetryContext open(RetryContext parent) {
        LOG.debug("First retry attempt");
        return retryPolicy.open(parent);
    }

    @Override
    public void close(RetryContext context) {
        LOG.debug("Last retry attempt");
        retryPolicy.close(context);
    }

    @Override
    public void registerThrowable(RetryContext context, Throwable throwable) {
        retryPolicy.registerThrowable(context, throwable);
    }

}
