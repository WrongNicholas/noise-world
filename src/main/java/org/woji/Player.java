package org.woji;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public class Player extends GameObject {

    InputHandler inputHandler;
    float speed = 400.f;

    public Player(InputHandler inputHandler, World world, Vec2 position) {
        super(world, position, new Vec2(32.f, 32.f), "src/main/resources/player.png");
        this.inputHandler = inputHandler;
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        // Calculate Movement Vector
        Vec2 inputVector = inputHandler.getInputVector();
        Vec2 movementVector = new Vec2(inputVector.x * speed, inputVector.y * speed);
        body.setLinearVelocity(movementVector);
    }
}
