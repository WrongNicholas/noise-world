package org.woji;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

import javax.swing.*;
import java.util.ArrayList;

public class GameManager {

    // Field Variables
    private World world;
    private InputHandler inputHandler;
    private GamePanel gamePanel;
    private JFrame frame;

    // GameObjects
    private Player player;
    private final ArrayList<GameObject> gameObjects = new ArrayList<>();

    // GameManager Initialization Method
    public void initialize() {
        // JBox2D World
        world = new World(new Vec2(0f, 1200.f));

        // InputHandler
        this.inputHandler = new InputHandler();

        // Player and Tiles
        player = new Player(inputHandler, world, new Vec2(100, 100));
        for (int i = 0; i < 6; i++) {
            gameObjects.add(new GameObject(world, BodyType.STATIC, new Vec2(100.f + i * 64.f, 600.f), new Vec2(64.f, 64.f), "src/main/resources/simple_tile.png"));
        }

        // GamePanel
        gamePanel = new GamePanel();
        gamePanel.initialize(true, gameObjects, player);

        // JFrame
        frame = new JFrame("Noise World");
        frame.setSize(800, 800);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add GamePanel and InputHandler to JFrame before setting to Visible
        frame.add(gamePanel);
        frame.addKeyListener(inputHandler);
        frame.setVisible(true);
    }

    public void update(float dt) {
        // Step Physics World
        world.step(dt, 6, 2);

        // Update Player
        player.update(dt);

        // Update GameObjects
        for (GameObject gameObject : gameObjects) {
            gameObject.update(dt);
        }
    }

    // Call GamePanel Paint
    public void render() {
        gamePanel.update(inputHandler.shouldShowHitBoxes());
        gamePanel.repaint();
    }

    public boolean running() {
        return !inputHandler.windowShouldClose();
    }

    public void terminate() {
        frame.dispose();
    }
}
