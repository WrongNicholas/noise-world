package org.woji;

import org.jbox2d.common.Vec2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {

    private boolean up, down, left, right;

    private boolean windowShouldClose = false;

    public Vec2 getInputVector() {
        Vec2 inputVector = new Vec2((right ? 1 : 0) - (left ? 1 : 0), (down ? 1 : 0) - (up ? 1 : 0));

        /*float magnitude = (float)Math.sqrt(Math.pow(inputVector.x, 2) + Math.pow(inputVector.y, 2));
        if (magnitude != 0)
        {
            inputVector = new Vec2(inputVector.x / magnitude, inputVector.y / magnitude);
        }*/

        return inputVector;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_W, KeyEvent.VK_SPACE, KeyEvent.VK_UP:
                up = true;
                break;
            case KeyEvent.VK_S, KeyEvent.VK_DOWN:
                down = true;
                break;
            case KeyEvent.VK_A, KeyEvent.VK_LEFT:
                left = true;
                break;
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT:
                right = true;
                break;

            case KeyEvent.VK_ESCAPE:
                windowShouldClose = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_W, KeyEvent.VK_SPACE, KeyEvent.VK_UP:
                up = false;
                break;
            case KeyEvent.VK_S, KeyEvent.VK_DOWN:
                down = false;
                break;
            case KeyEvent.VK_A, KeyEvent.VK_LEFT:
                left = false;
                break;
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT:
                right = false;
                break;
        }
    }

    public boolean windowShouldClose() {
        return windowShouldClose;
    }
}
