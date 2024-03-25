package org.woji;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameObject {

    // Field Variables
    protected Body body;
    private BufferedImage bufferedImage;

    private final Vec2 size;

    // Constructor
    public GameObject(World world, Vec2 position, Vec2 size, String imageFilePath) {
        this.size = size;

        initializeBody(world, position);
        initializeBufferedImage(imageFilePath);
    }

    // Initialization Methods
    private void initializeBody(World world, Vec2 position) {
        // Create Body Definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(position);

        // Create Body in Physics World
        body = world.createBody(bodyDef);

        // Define a Shape for the Object
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x / 2, size.y / 2);

        // Create a Fixture Definition
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = .5f;

        // Attach the Shape to Body
        body.createFixture(fixtureDef);
    }

    private void initializeBufferedImage(String imageFilePath) {
        // Try to load BufferedImage from Image FilePath
        try {
            bufferedImage = ImageIO.read(new File(imageFilePath));
        } catch (IOException e) {
            System.err.println("ERROR: Unable to load image at filepath '" + imageFilePath + "'");
        }
    }

    // Accessors
    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public Vec2 getPosition() {
        return body.getPosition();
    }

    public Vec2 getSize() {
        return size;
    }

    // Public Methods
    public void update(float dt) {

    }
}
