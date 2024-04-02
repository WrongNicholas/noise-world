package org.woji;

import de.articdive.jnoise.pipeline.JNoise;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Chunk {

    private Body body;
    BufferedImage grass, dirt, stone;

    // Chunk Size
    private final int chunkWidth = 16, chunkHeight = 16;
    private final float blockSize = 64.f;
    private final int chunkOffset;

    // Block Map represented as Integer Array
    private final int[] blockMap;

    public Chunk(World world, JNoise noise, int chunkOffset) {
        blockMap = new int[chunkWidth * chunkHeight];
        this.chunkOffset = chunkOffset;

        initializeBlockMapValues(noise, chunkOffset);
        initializeBody(world, chunkOffset);

        initializeBufferedImage();
    }

    // Temporary
    private void initializeBufferedImage() {
        try {
            grass = ImageIO.read(new File("src/main/resources/grass.png"));
            dirt = ImageIO.read(new File("src/main/resources/dirt.png"));
            stone = ImageIO.read(new File("src/main/resources/stone.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Initialization Methods
    private void initializeBlockMapValues(JNoise noise, int chunkOffset) {
        for (int x = 0; x < chunkWidth; x++) {

            double noiseValue = Math.abs(noise.evaluateNoise((chunkOffset * chunkWidth + x) / 50.0));
            int height = (int)(noiseValue * chunkHeight);

            for (int y = 0; y < chunkHeight; y++) {
                if (y == height) {
                    blockMap[x + chunkWidth * y] = 1;
                }
                else if (y > height && y < height + 3) {
                    blockMap[x + chunkWidth * y] = 2;
                }
                else if (y >= height + 3) {
                    blockMap[x + chunkWidth * y] = 3;
                }
            }
        }
    }

    private void initializeBody(World world, int chunkOffset) {
        // Create Body Definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.STATIC;
        bodyDef.position.set(new Vec2(chunkWidth * chunkOffset * blockSize, 0));

        // Create Body in Physics World
        body = world.createBody(bodyDef);

        // Generate and Attach Shape to Body
        for (int y = 0; y < chunkHeight; y++) {
            for (int x = 0; x < chunkWidth; x++) {

                // Skip iteration if block is "Empty"
                if (blockMap[x + chunkWidth * y] == 0) {
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
                new Vec2(x * blockSize, y * blockSize),              // Top Left
                new Vec2(x * blockSize, (y + 1) * blockSize),        // Bottom Left
                new Vec2((x + 1) * blockSize, (y + 1) * blockSize),  // Bottom Right
                new Vec2((x + 1) * blockSize, y * blockSize)         // Top Right
        };

        shape.set(vertices, vertices.length);
        return shape;
    }

    // Public Methods
    public void render(Graphics g) {
        for (int y = 0; y < chunkHeight; y++) {
            for (int x = 0; x < chunkWidth; x++) {
                if (blockMap[x + chunkWidth * y] != 0) {
                    BufferedImage bufferedImage = null;
                    switch (blockMap[x + chunkWidth * y]) {
                        case 1:
                            bufferedImage = grass;
                            break;
                        case 2:
                            bufferedImage = dirt;
                            break;
                        case 3:
                            bufferedImage = stone;
                            break;
                    }
                    if (bufferedImage != null)
                        g.drawImage(bufferedImage, (int)(x * blockSize + chunkWidth * chunkOffset * blockSize + blockSize / 2.f), (int)(y * blockSize + blockSize / 2.f), (int)blockSize, (int)blockSize, null);
                }
            }
        }

        // Note: Possibly loop through shapes instead of integer array
    }
}
