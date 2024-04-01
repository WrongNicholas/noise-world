package org.woji;

import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

public class Player extends GameObject {

    private final InputHandler inputHandler;
    private final World world;

    boolean isGrounded = true;

    public Player(InputHandler inputHandler, World world, Vec2 position) {
        super(world, BodyType.DYNAMIC, position, new Vec2(64.f, 64.f), "src/main/resources/player.png");
        this.inputHandler = inputHandler;
        this.world = world;
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        // Check isGrounded
        checkGrounded();

        float speed = 400.f;
        float jumpForce = 700.f;

        // Calculate Movement Vector
        Vec2 inputVector = inputHandler.getInputVector();
        Vec2 movementVector = new Vec2(inputVector.x * speed, body.getLinearVelocity().y);
        body.setLinearVelocity(movementVector);

        // Jump
        if (inputVector.y < 0.f && isGrounded) {
            body.setLinearVelocity(new Vec2(body.getLinearVelocity().x, -jumpForce));
        }

        // Temporary "Respawn"
        if (getPosition().y > 1000.f) {
            body.setTransform(new Vec2(100.f, 100.f), body.getAngle());
            body.setLinearVelocity(new Vec2(0.f, 0.f));
        }
    }

    private void checkGrounded() {
        isGrounded = false;
        Vec2 pos1 = body.getPosition().clone().add(new Vec2(-getSize().x / 2, getSize().y));
        Vec2 pos2 = body.getPosition().clone().add(new Vec2(getSize().x / 2, getSize().y * 3/5));
        AABB aabb = new AABB(pos1, pos2);
        world.queryAABB(fixture -> {
            if (!fixture.equals(body.getFixtureList())) {
                isGrounded = true;
            }
            return false;
        }, aabb);
    }
}
