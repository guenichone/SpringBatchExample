package org.barrak.springbatch.monitoring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.stereotype.Component;

/**
 * Retry listener implementation.
 *
 * @author Emilien Guenichon <emilien.guenichon@cgi.com>
 */
@Component
public class RetryListenerImpl implements RetryListener {

    private static final Logger LOG = LoggerFactory.getLogger(RetryListenerImpl.class.getName());

    @Override
    public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
        LOG.info("Retry open");
        return true;
    }

    @Override
    public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        LOG.info("Retry close");
    }

    @Override
    public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        LOG.info("Retry error : cnt {}, {}", context.getRetryCount(), context.getLastThrowable());
    }

}
