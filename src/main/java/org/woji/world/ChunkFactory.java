package org.woji.world;

import de.articdive.jnoise.pipeline.JNoise;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

public class ChunkFactory {

    public ChunkNode node;

    // Constants for defining block and chunk dimensions
    private final int CHUNK_WIDTH = 16;
    private final int CHUNK_HEIGHT = 16;
    private final float BLOCK_SIZE = 64.f;

    // Reference variables
    private final World world;
    private final JNoise noise;

    public ChunkFactory(World world, JNoise noise) {
        this.world = world;
        this.noise = noise;

        node = new ChunkNode(0, createChunk(0));
        node.next = new ChunkNode(node.index + 1, createChunk(node.index + 1));
        node.prev = new ChunkNode(node.index -1, createChunk(node.index - 1));
    }

    public Chunk createChunk(int chunkOffset) {
         final float totalChunkWidthPixels = chunkOffset * CHUNK_WIDTH * BLOCK_SIZE;
         final int[] blockMap = new int[CHUNK_WIDTH * CHUNK_HEIGHT];

         // Initialize the block map values using JNoise
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

        // Create Body Definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.STATIC;
        bodyDef.position.set(new Vec2(totalChunkWidthPixels, 0));

        // Create Body in Physics World
        Body body = world.createBody(bodyDef);

        // Generate and Attach Shape to Body
        for (int y = 0; y < CHUNK_HEIGHT; y++) {
            for (int x = 0; x < CHUNK_WIDTH; x++) {

                // Skip iteration if block is "Empty"
                if (blockMap[x + CHUNK_WIDTH * y] == 0) {
                    continue;
                }

                // Generate PolygonShape for the block at current position
                PolygonShape shape = getPolygonShape(x, y);

                // Create Fixture Definition
                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.shape = shape;

                // Attach fixture to body
                body.createFixture(fixtureDef);
            }
        }

        return new Chunk(body, blockMap, totalChunkWidthPixels);

    }

    private PolygonShape getPolygonShape(int x, int y) {
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
        return shape;
    }
}
