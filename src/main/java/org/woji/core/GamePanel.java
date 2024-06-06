package org.woji.core;

import org.jbox2d.common.Vec2;
import org.woji.world.Chunk;
import org.woji.world.ChunkRenderer;
import org.woji.world.old_Chunk;
import org.woji.entity.GameObject;
import org.woji.entity.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class GamePanel extends JPanel {

    private boolean drawHitBoxes;

    private ArrayList<GameObject> gameObjects;
    private Player player;

    ChunkRenderer chunkRenderer;

    Chunk chunk;

    public void initialize(boolean drawHitBoxes, TextureHandler textureHandler, ArrayList<GameObject> gameObjects, Player player, Chunk chunk) {
        this.drawHitBoxes = drawHitBoxes;
        this.gameObjects = gameObjects;
        this.player = player;
        this.chunk = chunk;

        chunkRenderer = new ChunkRenderer(textureHandler);
    }

    public void update(boolean drawHitBoxes) {
        this.drawHitBoxes = drawHitBoxes;
    }

    @Override
    public void paint(Graphics g) {
        // Paint Background
        g.setColor(new Color(50, 173, 207));
        g.fillRect(0, 0, getWidth(), getHeight());

        // Paint Player's BufferedImage
        paintPlayer(g);

        // Create a copy of the gameObjects list
        ArrayList<GameObject> objectsToPaint = new ArrayList<>(gameObjects);

        // Translate Graphics depending on Player position
        Vec2 playerPosition = player.getPosition();
        Vec2 playerSize = player.getSize();

        int translateX = (int)((getWidth() / 2) - playerPosition.x - (playerSize.x / 2));
        int translateY = (int)((getHeight() / 2) - playerPosition.y - (playerSize.y / 2));

        g.translate(translateX, translateY);

        // Paint GameObjects' BufferedImage
        objectsToPaint.stream().filter(Objects::nonNull).forEach(object -> paintBufferedImage(g, object.getBufferedImage(), object.getPosition(), object.getSize()));

        chunkRenderer.render(chunk, g);
    }

    private void paintBufferedImage(Graphics g, BufferedImage bufferedImage, Vec2 position, Vec2 size) {
        // Paint BufferedImage
        g.drawImage(bufferedImage, (int)position.x, (int)position.y, (int)size.x, (int)size.y, null);

        if (drawHitBoxes) {
            g.setColor(Color.RED);
            g.drawRect((int) position.x, (int) position.y, (int) size.x, (int) size.y);
        }
    }

    private void paintPlayer(Graphics g) {
        // Offset because the player draws weird
        int offset = 3;
        Vec2 playerSize = player.getSize();
        paintBufferedImage(g, player.getBufferedImage(), new Vec2(getWidth() / 2.f - playerSize.x / 2.f + offset, getHeight() / 2.f - playerSize.y / 2.f + offset), playerSize);
    }
}
