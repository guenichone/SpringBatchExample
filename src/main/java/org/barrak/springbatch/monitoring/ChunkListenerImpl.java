package org.barrak.springbatch.monitoring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

/**
 * Chunk listener implementation.
 *
 * @author Emilien Guenichon <emilien.guenichon@cgi.com>
 */
@Component
public class ChunkListenerImpl implements ChunkListener {

    private static final Logger LOG = LoggerFactory.getLogger(ChunkListenerImpl.class);

    @Override
    public void beforeChunk(ChunkContext context) {
        LOG.info("Before chunk");
    }

    @Override
    public void afterChunk(ChunkContext context) {
        LOG.info("After chunk");
    }

    @Override
    public void afterChunkError(ChunkContext context) {
        LOG.info("After chunk error");
    }

}
