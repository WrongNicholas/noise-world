package org.woji;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import javax.swing.*;
import java.util.ArrayList;

public class GameManager {

    // Private Variables
    private World world;
    private GamePanel gamePanel;

    // GameObjects
    private final ArrayList<GameObject> gameObjects = new ArrayList<>();
    private Player player;
    private Tile[][] tileMap;

    public void initialize() {
        // JBox2D World
        world = new World(new Vec2(0f, 0f));

        // InputHandler
        InputHandler inputHandler = new InputHandler();

        // Player
        player = new Player(inputHandler, world, new Vec2(100, 100));
        gameObjects.add(player);

        // GamePanel
        gamePanel = new GamePanel();
        gamePanel.initialize(true, gameObjects);

        // JFrame
        JFrame frame = new JFrame("Noise World");
        frame.setSize(800, 800);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add GamePanel and InputHandler to JFrame before setting to Visible
        frame.add(gamePanel);
        frame.addKeyListener(inputHandler);
        frame.setVisible(true);

        // Test
        // JNoise noisePipeline=JNoise.newBuilder().perlin(3301, Interpolation.COSINE, FadeFunction.QUINTIC_POLY).build();
    }

    public void update(float dt) {
        // Step Physics World
        world.step(dt, 6, 2);

        // Update GameObjects
        for (GameObject gameObject : gameObjects) {
            gameObject.update(dt);
        }
    }

    // Call GamePanel Paint
    public void render() {
        gamePanel.repaint();
    }
}
