package org.woji.core;

import de.articdive.jnoise.core.api.functions.Interpolation;
import de.articdive.jnoise.generators.noise_parameters.fade_functions.FadeFunction;
import de.articdive.jnoise.pipeline.JNoise;
import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.woji.entity.GameObject;
import org.woji.entity.Player;
import org.woji.world.Chunk;
import org.woji.world.ChunkFactory;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class GameManager implements Runnable {

    // Private Field Variables
    private World world;
    private InputHandler inputHandler;
    private GamePanel gamePanel;
    private JFrame frame;

    // GameObjects
    private Player player;
    private final ArrayList<GameObject> gameObjects = new ArrayList<>();
    private Chunk mainChunk;

    private ChunkFactory factory;

    // GameManager Initialization Method
    public void initialize() {

        // JBox2D World
        world = new World(new Vec2(0f, 20));

        // InputHandler
        this.inputHandler = new InputHandler();

        // TextureHandler
        TextureHandler textureHandler = new TextureHandler();
        textureHandler.initializeTextures();

        // Player
        Vec2 playerPosition = new Vec2(0, -100);
        player = new Player(inputHandler, world, playerPosition, textureHandler);

        // JNoise
        Random random = new Random();
        int noiseValue = random.nextInt(9999);
        System.out.println(noiseValue);
        JNoise noise = JNoise.newBuilder().perlin(noiseValue, Interpolation.COSINE, FadeFunction.QUINTIC_POLY).build();
        factory = new ChunkFactory(noise);

        mainChunk = factory.generate(world, 0);
        initializeMainChunk();

        // GamePanel
        gamePanel = new GamePanel();
        gamePanel.initialize(textureHandler, gameObjects, player, mainChunk);

        // JFrame
        frame = new JFrame("Noise World");
        frame.setSize(1500, 800);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add GamePanel and InputHandler to JFrame before setting to Visible
        frame.add(gamePanel);
        frame.addKeyListener(inputHandler);
        frame.setVisible(true);

    }

    public void update(float dt) {
        // Step Physics World
        world.step(dt, 20, 10);

        // Update Player
        player.update(dt);

        // Update Rest of GameObjects
        for (GameObject gameObject : gameObjects) {
            gameObject.update(dt);
        }

        ensurePlayerInMainChunk();
    }

    private void ensurePlayerInMainChunk() {
        int playerChunkRelativePosition = getPlayerRelativeChunkPosition(player, mainChunk);

        if (playerChunkRelativePosition != 0) {
            if (playerChunkRelativePosition == 1) {
                mainChunk.prev.unload();
                mainChunk = mainChunk.next;
            }
            else if (playerChunkRelativePosition == -1) {
                mainChunk.next.unload();
                mainChunk = mainChunk.prev;
            }

            initializeMainChunk();
            gamePanel.lazyUpdate(mainChunk);
        }
    }

    private void initializeMainChunk() {
        if (mainChunk.next == null) {
            mainChunk.next = factory.generate(world, mainChunk.position() + 1);
            mainChunk.next.prev = mainChunk;
        } else mainChunk.next.load();

        if (mainChunk.prev == null) {
            mainChunk.prev = factory.generate(world, mainChunk.position() - 1);
            mainChunk.prev.next = mainChunk;
        } else mainChunk.prev.load();
    }

    public int getPlayerRelativeChunkPosition(Player player, Chunk chunk) {
        Vec2 point = player.getPosition();
        AABB aabb = mainChunk.getBounds();

        if (point.x >= aabb.lowerBound.x && point.x <= aabb.upperBound.x)
            return 0;

        if (point.x < aabb.lowerBound.x)
            return -1;

        return 1;
    }

    public void render() {
        gamePanel.repaint();
    }

    public boolean running() {
        return !inputHandler.windowShouldClose();
    }

    public void terminate() {
        frame.dispose();
    }

    public void start() {
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double FPS = 60;
        double drawInterval = 100000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (running()) {
            float timeStep = 1f/60f;

            update(timeStep);
            render();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000;

                if(remainingTime < 0) {
                    remainingTime = 0;
                }

                if (remainingTime != 0)
                    Thread.sleep((long)remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        terminate();
        System.exit(0);
    }


}
