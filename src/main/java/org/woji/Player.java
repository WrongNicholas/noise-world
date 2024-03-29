package org.woji;

import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
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

        checkGrounded();

        // Calculate Movement Vector
        Vec2 inputVector = inputHandler.getInputVector();
        float speed = 400.f;
        Vec2 movementVector = new Vec2(inputVector.x * speed, body.getLinearVelocity().y);
        body.setLinearVelocity(movementVector);

        if (inputVector.y < 0.f && isGrounded) {
            body.setLinearVelocity(new Vec2(body.getLinearVelocity().x, -1000.f));
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
