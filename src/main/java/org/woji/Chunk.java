package org.woji;

import de.articdive.jnoise.pipeline.JNoise;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Chunk {

    private TextureHandler textureHandler;

    private Body body;

    private final int CHUNK_WIDTH = 16;
    private final int CHUNK_HEIGHT = 16;
    private final float BLOCK_SIZE = 64.f;

    private final float totalChunkWidthPixels;

    // Block Map represented as Integer Array
    private final int[] blockMap;

    public Chunk(TextureHandler textureHandler, World world, JNoise noise, int chunkOffset) {
        this.textureHandler = textureHandler;

        blockMap = new int[CHUNK_WIDTH * CHUNK_HEIGHT];
        this.totalChunkWidthPixels = chunkOffset * CHUNK_WIDTH * BLOCK_SIZE;

        initializeBlockMapValues(noise, chunkOffset);
        initializeBody(world, chunkOffset);
    }

    // Initialization Methods
    private void initializeBlockMapValues(JNoise noise, int chunkOffset) {
        for (int x = 0; x < CHUNK_WIDTH; x++) {

            double noiseValue = Math.abs(noise.evaluateNoise((totalChunkWidthPixels + x * BLOCK_SIZE) / 1000.0));
            int height = (int)(noiseValue * CHUNK_HEIGHT);

            for (int y = 0; y < CHUNK_HEIGHT; y++) {
                if (y == height) {
                    blockMap[x + CHUNK_WIDTH * y] = 1;
                }
                else if (y > height && y < height + 3) {
                    blockMap[x + CHUNK_WIDTH * y] = 2;
                }
                else if (y >= height + 3) {
                    blockMap[x + CHUNK_WIDTH * y] = 3;
                }
            }
        }
    }

    private void initializeBody(World world, int chunkOffset) {
        // Create Body Definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.STATIC;
        bodyDef.position.set(new Vec2(totalChunkWidthPixels, 0));

        // Create Body in Physics World
        body = world.createBody(bodyDef);

        // Generate and Attach Shape to Body
        for (int y = 0; y < CHUNK_HEIGHT; y++) {
            for (int x = 0; x < CHUNK_WIDTH; x++) {

                // Skip iteration if block is "Empty"
                if (blockMap[x + CHUNK_WIDTH * y] == 0) {
                    continue;
                }

                PolygonShape shape = generateBlockPolygonShape(x, y);

                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.shape = shape;
                fixtureDef.friction = 0.f;

                // Attach Shape to Body
                body.createFixture(fixtureDef);
            }
        }
    }

    private PolygonShape generateBlockPolygonShape(int x, int y) {
        PolygonShape shape = new PolygonShape();
        Vec2[] vertices = {
                new Vec2(x * BLOCK_SIZE, y * BLOCK_SIZE),              // Top Left
                new Vec2(x * BLOCK_SIZE, (y + 1) * BLOCK_SIZE),        // Bottom Left
                new Vec2((x + 1) * BLOCK_SIZE, (y + 1) * BLOCK_SIZE),  // Bottom Right
                new Vec2((x + 1) * BLOCK_SIZE, y * BLOCK_SIZE)         // Top Right
        };

        shape.set(vertices, vertices.length);
        return shape;
    }

    // Public Methods
    public void render(Graphics g) {
        for (int y = 0; y < CHUNK_HEIGHT; y++) {
            for (int x = 0; x < CHUNK_WIDTH; x++) {
                if (blockMap[x + CHUNK_WIDTH * y] != 0) {
                    BufferedImage bufferedImage = switch (blockMap[x + CHUNK_WIDTH * y]) {
                        case 1 -> textureHandler.getBufferedImage("grass");
                        case 2 -> textureHandler.getBufferedImage("dirt");
                        case 3 -> textureHandler.getBufferedImage("stone");
                        default -> null;
                    };
                    if (bufferedImage != null)
                        g.drawImage(bufferedImage, (int)(x * BLOCK_SIZE + totalChunkWidthPixels + BLOCK_SIZE / 2.f), (int)(y * BLOCK_SIZE + BLOCK_SIZE / 2.f), (int)BLOCK_SIZE, (int)BLOCK_SIZE, null);
                }
            }
        }

        // Note: Possibly loop through shapes instead of integer array
    }
}
