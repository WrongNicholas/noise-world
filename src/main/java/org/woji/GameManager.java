package org.woji;

import de.articdive.jnoise.core.api.functions.Interpolation;
import de.articdive.jnoise.generators.noise_parameters.fade_functions.FadeFunction;
import de.articdive.jnoise.pipeline.JNoise;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import javax.swing.*;
import java.util.ArrayList;

public class GameManager {

    // Private Field Variables
    private World world;
    private InputHandler inputHandler;
    private GamePanel gamePanel;
    private JFrame frame;

    // GameObjects
    private Player player;
    private final ArrayList<GameObject> gameObjects = new ArrayList<>();

    Chunk[] chunks;

    // GameManager Initialization Method
    public void initialize() {
        // JBox2D World
        world = new World(new Vec2(0f, 1600.f));

        // InputHandler
        this.inputHandler = new InputHandler();

        TextureHandler textureHandler = new TextureHandler();
        textureHandler.initializeTextures();

        // Player
        Vec2 playerPosition = new Vec2(0, -100);
        player = new Player(inputHandler, world, playerPosition, textureHandler);

        JNoise noise =JNoise.newBuilder().perlin(3301, Interpolation.COSINE, FadeFunction.QUINTIC_POLY).build();

        // Chunks
        chunks = new Chunk[10];
        for (int i = 0; i < chunks.length; i++) {
            chunks[i] = new Chunk(textureHandler, world, noise, i);
        }

        // GamePanel
        gamePanel = new GamePanel();
        gamePanel.initialize(true, gameObjects, player, chunks);

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

    float t = 0.f;
    
    public void update(float dt) {
        // Step Physics World
        world.step(dt, 6, 2);

        // Update Player
        player.update(dt);

        // Update Rest of GameObjects
        for (GameObject gameObject : gameObjects) {
            gameObject.update(dt);
        }
    }

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
