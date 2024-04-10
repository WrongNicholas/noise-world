package org.woji;

import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

public class Player extends GameObject {

    private final InputHandler inputHandler;
    private final World world;

    boolean isGrounded = true;
    boolean previousJumpPressed = false;

    boolean hasControl = true;

    public Player(InputHandler inputHandler, World world, Vec2 position, TextureHandler textureHandler) {
        super(world, BodyType.DYNAMIC, position, new Vec2(60.f, 60.f), textureHandler, "player");
        this.inputHandler = inputHandler;
        this.world = world;
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        // Check isGrounded
        checkGrounded();

        handleMovement();

        // Temporary "Respawn"
//        if (getPosition().y > 1000.f) {
//            body.setTransform(new Vec2(0.f, -100.f), body.getAngle());
//            body.setLinearVelocity(new Vec2(0.f, 0.f));
//        }
    }

    private void handleMovement() {
        if (!hasControl) {
            return;
        }

        float speed = 350.f;
        float jumpForce = 660.f;

        // Calculate Movement Vector
        Vec2 inputVector = inputHandler.getInputVector();
        Vec2 movementVector = new Vec2(inputVector.x * speed, body.getLinearVelocity().y);
        body.setLinearVelocity(movementVector);

        // Jump
        boolean jumpPressed = inputVector.y == -1.f;
        if (jumpPressed && isGrounded && body.getLinearVelocity().y >= 0.f) {
            body.setLinearVelocity(new Vec2(body.getLinearVelocity().x, -jumpForce));
        }

        // Jump Cancel
        if (!jumpPressed && previousJumpPressed && body.getLinearVelocity().y < 0.f) {
            body.setLinearVelocity(new Vec2(body.getLinearVelocity().x, body.getLinearVelocity().y * 0.6f));
        }
        previousJumpPressed = jumpPressed;
    }

    private void checkGrounded() {
        isGrounded = false;
        Vec2 topLeft = body.getPosition().clone().add(new Vec2(-getSize().x / 2.f + 3.f, getSize().y));
        Vec2 bottomRight = body.getPosition().clone().add(new Vec2(getSize().x / 2.f - 3.f,  getSize().y / 2.f));
        AABB aabb = new AABB(topLeft, bottomRight);
        world.queryAABB(fixture -> {
            if (!fixture.equals(body.getFixtureList())) {
                isGrounded = true;
            }
            return false;
        }, aabb);
    }
}
