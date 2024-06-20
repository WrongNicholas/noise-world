package org.woji;

import org.woji.core.GameManager;

public class Main {

    public static void main(String[] args) {

        GameManager game = new GameManager();
        game.initialize();

        while (game.running()) {
            float deltaTime = 1f/60f;

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            game.update(deltaTime);
            game.render();
        }

        game.terminate();
        System.exit(0);
    }

}
