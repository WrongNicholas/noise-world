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

    public void initialize() {
        // Initialize JBox2D
        world = new World(new Vec2(0f, 0f));

        // Initialize InputHandler
        InputHandler inputHandler = new InputHandler();

        // Initialize Player
        player = new Player(inputHandler, world, new Vec2(100, 100));

        // Initialize GamePanel
        gamePanel = new GamePanel();
        gamePanel.initialize(true, gameObjects, player);

        // Initialize JFrame
        JFrame frame = new JFrame("Noise World");
        frame.setSize(800, 800);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add GamePanel and InputHandler to Frame before setting to Visible
        frame.add(gamePanel);
        frame.addKeyListener(inputHandler);
        frame.setVisible(true);
    }

    public void update(float dt) {
        // Step Physics World
        world.step(dt, 6, 4);

        // Update GameObjects
        for (GameObject gameObject : gameObjects) {
            gameObject.update(dt);
        }

        // Update Player
        player.update(dt);
    }

    // Call GamePanel Paint
    public void render() {
        gamePanel.repaint();
    }
}
