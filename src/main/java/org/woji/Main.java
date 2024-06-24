package org.woji;

import org.woji.core.GameManager;

public class Main {

    public static void main(String[] args) {

        GameManager game = new GameManager();
        game.initialize();

        while (game.running()) {

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            float deltaTime = .001f;
            game.update(deltaTime);
            game.render();
        }

        game.terminate();
        System.exit(0);
    }

}
