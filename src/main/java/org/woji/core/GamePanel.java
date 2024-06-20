package org.woji.core;

import org.jbox2d.common.Vec2;
import org.w3c.dom.Text;
import org.woji.old_world.old_ChunkNode;
import org.woji.old_world.old_ChunkRenderer;
import org.woji.entity.GameObject;
import org.woji.entity.Player;
import org.woji.world.Chunk;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

public class GamePanel extends JPanel {

    private ArrayList<GameObject> gameObjects;
    private Player player;

    private TextureHandler textureHandler;

    private Chunk mainChunk;

    public void initialize(TextureHandler textureHandler, ArrayList<GameObject> gameObjects, Player player, Chunk mainChunk) {
        this.textureHandler = textureHandler;

        this.gameObjects = gameObjects;
        this.player = player;

        this.mainChunk = mainChunk;
    }

    public void update(Chunk mainChunk) {
        this.mainChunk = mainChunk;
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

        paintChunks(g);
    }

    private void paintChunks(Graphics g) {

        float blockSize = mainChunk.blockSizePixels();

        for (int x = 0; x < mainChunk.width(); x++) {
            for (int y = 0; y < mainChunk.height(); y++) {
                // Retrieve ID of block at current position
                int blockID = mainChunk.blockMap()[x + mainChunk.width() * y];

                // Skip rendering if block ID is 0 (empty block)
                if (blockID == 0) {
                    continue;
                }

                // Get blockImage for corresponding blockID from TextureHandler
                BufferedImage blockImage = textureHandler.getBufferedImage(blockID);

                // Calculate draw positions of block
                float totalChunkWidthPixels = blockSize * mainChunk.width() * mainChunk.position();
                int drawX = (int)(x * blockSize + totalChunkWidthPixels + blockSize / 2.f);
                int drawY = (int)(y * blockSize + blockSize / 2.f);

                // Draw blockImage onto Graphics context
                g.drawImage(blockImage, drawX, drawY, (int)blockSize, (int)blockSize, null);
            }
        }
    }

    private void paintBufferedImage(Graphics g, BufferedImage bufferedImage, Vec2 position, Vec2 size) {
        // Paint BufferedImage
        g.drawImage(bufferedImage, (int)position.x, (int)position.y, (int)size.x, (int)size.y, null);
    }

    private void paintPlayer(Graphics g) {
        // Offset because the player draws weird
        int offset = 3;
        Vec2 playerSize = player.getSize();
        paintBufferedImage(g, player.getBufferedImage(), new Vec2(getWidth() / 2.f - playerSize.x / 2.f + offset, getHeight() / 2.f - playerSize.y / 2.f + offset), playerSize);
    }
}
