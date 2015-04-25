package org.barrak.springbatch.monitoring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;
import org.springframework.stereotype.Component;

/**
 * Skip listener implementation.
 *
 * @author Emilien Guenichon <emilien.guenichon@cgi.com>
 */
@Component
public class SkipListenerImpl implements SkipListener<Object, Object> {

    private static final Logger LOG = LoggerFactory.getLogger(SkipListenerImpl.class);

    @Override
    public void onSkipInRead(Throwable t) {
        LOG.info("Skip in read");
    }

    @Override
    public void onSkipInWrite(Object item, Throwable t) {
        LOG.info("Skip in write");
    }

    @Override
    public void onSkipInProcess(Object item, Throwable t) {
        LOG.info("Skip in process");
    }

}
