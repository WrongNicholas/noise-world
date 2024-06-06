package org.woji.world;

import de.articdive.jnoise.pipeline.JNoise;
import org.jbox2d.dynamics.World;
import org.woji.core.TextureHandler;

public class ChunkFactory {
    // Reference variables
    private final World world;
    private final JNoise noise;

    public ChunkFactory(World world, JNoise noise) {
        this.world = world;
        this.noise = noise;
    }

    public Chunk createChunk(int chunkNumber) {
         return new Chunk(world, noise, chunkNumber);
    }
}
