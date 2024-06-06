package org.woji.world;

import org.jbox2d.dynamics.Body;
import org.woji.core.TextureHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ChunkRenderer {

    private final TextureHandler textureHandler;

    public ChunkRenderer(TextureHandler textureHandler) {
        this.textureHandler = textureHandler;
    }

    public void render(ChunkNode node, Graphics g) {
        render(node.chunk, g);
        render(node.prev.chunk, g);
        render(node.next.chunk, g);
    }

    // Renders the given chunk onto current graphics context
    private void render(Chunk chunk, Graphics g) {
        Body body = chunk.body();

        // Skip render if physics body is inactive
        if (!body.isActive()) {
            return;
        }

        // Constants for defining block and chunk dimensions
        final int CHUNK_WIDTH = 16;
        final int CHUNK_HEIGHT = 16;
        final float BLOCK_SIZE = 64.f;

        final int[] blockMap = chunk.blockMap();

        // Iterate through each block in chunk
        for (int y = 0; y < CHUNK_HEIGHT; y++) {
            for (int x = 0; x < CHUNK_WIDTH; x++) {

                // Retrieve ID of block at current position
                int blockID = blockMap[x + CHUNK_WIDTH * y];

                // Skip rendering if block ID is 0 (empty block)
                if (blockID == 0) {
                    continue;
                }

                // Get blockImage for corresponding blockID from TextureHandler
                BufferedImage blockImage = textureHandler.getBufferedImage(blockID);

                // Calculate draw positions of block
                float totalChunkWidthPixels = chunk.totalChunkWidthPixels();
                int drawX = (int)(x * BLOCK_SIZE + totalChunkWidthPixels + BLOCK_SIZE / 2.f);
                int drawY = (int)(y * BLOCK_SIZE + BLOCK_SIZE / 2.f);

                // Draw blockImage onto Graphics context
                g.drawImage(blockImage, drawX, drawY, (int)BLOCK_SIZE, (int)BLOCK_SIZE, null);

            }
        }
    }
}
