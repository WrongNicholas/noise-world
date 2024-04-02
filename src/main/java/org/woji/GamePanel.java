package org.woji;

import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

public class GamePanel extends JPanel {

    private boolean drawHitBoxes;

    private ArrayList<GameObject> gameObjects;
    private Player player;

    public void initialize(boolean drawHitBoxes, ArrayList<GameObject> gameObjects, Player player) {
        this.drawHitBoxes = drawHitBoxes;
        this.gameObjects = gameObjects;
        this.player = player;
    }

    public void update(boolean drawHitBoxes) {
        this.drawHitBoxes = drawHitBoxes;
    }

    @Override
    public void paint(Graphics g) {
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
        Vec2 playerSize = player.getSize();
        paintBufferedImage(g, player.getBufferedImage(), new Vec2(getWidth() / 2.f - playerSize.x / 2.f, getHeight() / 2.f - playerSize.y / 2.f), playerSize);
    }
}
