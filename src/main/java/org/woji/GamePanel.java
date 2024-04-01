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

    public void initialize(boolean drawHitBoxes, ArrayList<GameObject> gameObjects) {
        this.drawHitBoxes = drawHitBoxes;
        this.gameObjects = gameObjects;
    }

    @Override
    public void paint(Graphics g) {
        // Create a copy of the gameObjects list
        ArrayList<GameObject> objectsToPaint = new ArrayList<>(gameObjects);

        // Paint GameObjects' BufferedImage
        objectsToPaint.stream().filter(Objects::nonNull).forEach(object -> paintBufferedImage(g, object.getBufferedImage(), object.getPosition(), object.getSize()));
    }

    private void paintBufferedImage(Graphics g, BufferedImage bufferedImage, Vec2 position, Vec2 size) {
        // Paint BufferedImage
        g.drawImage(bufferedImage, (int)position.x, (int)position.y, (int)size.x, (int)size.y, null);

        if (drawHitBoxes) {
            g.drawRect((int) position.x, (int) position.y, (int) size.x, (int) size.y);
        }
    }
}
