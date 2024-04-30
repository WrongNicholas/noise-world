package org.woji.world;

import de.articdive.jnoise.pipeline.JNoise;
import org.woji.core.TextureHandler;

public class ChunkFactory {

    TextureHandler textureHandler;
    JNoise noise;

    public ChunkFactory(TextureHandler textureHandler, JNoise noise) {
        this.noise = noise;
    }

    public Chunk createChunk() {
         return null;
    }
}
