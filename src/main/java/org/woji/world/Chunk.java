package org.woji.world;

import de.articdive.jnoise.pipeline.JNoise;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

public class Chunk {
    // Constants for defining block and chunk dimensions
    private final int CHUNK_WIDTH = 16;
    private final int CHUNK_HEIGHT = 16;
    private final float BLOCK_SIZE = 64.f;

    // Total width of the chunk in pixels
    private final float totalChunkWidthPixels;

    // Block Map represented as integer array
    private final int[] blockMap;

    // Physics body associated with the chunk
    private Body body;

    public Chunk(World world, JNoise noise, int chunkOffset) {
        blockMap = new int[CHUNK_WIDTH * CHUNK_HEIGHT];
        totalChunkWidthPixels = chunkOffset * CHUNK_WIDTH * BLOCK_SIZE;

        initializeBlockMapValues(noise);
        initializeBody(world);
    }

    // Initializes the block map values using JNoise
    private void initializeBlockMapValues(JNoise noise) {
        // Iterate over chunk width
        for (int x = 0; x < CHUNK_WIDTH; x++) {

            // Evaluate noise at current x position
            double noiseValue = Math.abs(noise.evaluateNoise((totalChunkWidthPixels + x * BLOCK_SIZE) / 1000.0));

            // Calculate height based on noise value
            int height = (int)(noiseValue * CHUNK_HEIGHT);

            // Iterate over height of chunk
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

    // Initializes physics body for the chunk
    private void initializeBody(World world) {
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

                // Generate PolygonShape for the block at current position
                PolygonShape shape = generateBlockPolygonShape(x, y);

                // Create Fixture Definition
                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.shape = shape;

                // Attach fixture to body
                body.createFixture(fixtureDef);
            }
        }
    }

    // Generates PolygonShape for a block at specific position
    private PolygonShape generateBlockPolygonShape(int x, int y) {
        // Create new PolygonShape
        PolygonShape shape = new PolygonShape();

        // Define vertices of PolygonShape, forming a rectangle
        Vec2[] vertices = {
                new Vec2(x * BLOCK_SIZE, y * BLOCK_SIZE),              // Top Left
                new Vec2(x * BLOCK_SIZE, (y + 1) * BLOCK_SIZE),        // Bottom Left
                new Vec2((x + 1) * BLOCK_SIZE, (y + 1) * BLOCK_SIZE),  // Bottom Right
                new Vec2((x + 1) * BLOCK_SIZE, y * BLOCK_SIZE)         // Top Right
        };

        // Set the vertices of the PolygonShape using custom defined vertices
        shape.set(vertices, vertices.length);

        // Return newly generated PolygonShape
        return shape;
    }

    public Body getBody() {
        return body;
    }

    public int[] getBlockMap() {
        return blockMap;
    }

    public float getTotalChunkWidthPixels() {
        return totalChunkWidthPixels;
    }
}
