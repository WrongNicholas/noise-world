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
    private final BufferedImage bufferedImage;
    private final Vec2 size;

    // Constructor
    public GameObject(World world, BodyType bodyType, Vec2 position, Vec2 size, TextureHandler textureHandler, String textureFileName) {
        this.size = size;

        initializeBody(world, bodyType, position);
        bufferedImage = textureHandler.getBufferedImage(textureFileName);
    }

    // Initialization Methods
    private void initializeBody(World world, BodyType bodyType, Vec2 position) {
        // Create Body Definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(position);

        // Create Body in Physics World
        body = world.createBody(bodyDef);

        // Define a Shape for the Object
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x / 2.f, size.y / 2.f);

        // Create a Fixture Definition
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0.f;

        // Attach the Shape to Body
        body.createFixture(fixtureDef);
    }

    // Public Methods
    public void update(float dt) {

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
}