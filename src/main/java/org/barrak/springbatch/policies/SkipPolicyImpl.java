package org.barrak.springbatch.policies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.stereotype.Component;

/**
 *
 * @author Emilien Guenichon <emilien.guenichon@cgi.com>
 */
@Component
public class SkipPolicyImpl implements SkipPolicy {

    private static final Logger LOG = LoggerFactory.getLogger(SkipPolicyImpl.class.getName());

    @Override
    public boolean shouldSkip(Throwable thrwbl, int i) throws SkipLimitExceededException {
        LOG.info("Should skip ? : {} ({})", thrwbl, i);
        return true;
    }
}
