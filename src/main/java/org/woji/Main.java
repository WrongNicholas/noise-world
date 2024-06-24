package org.woji;

import org.woji.core.GameManager;

public class Main {

    public static void main(String[] args) {

        GameManager game = new GameManager();
        game.initialize();

        long now;
        long updateTime;
        long wait = 1;
        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
        while (game.running()) {
            now = System.nanoTime();

            float deltaTime = wait;
            game.update(deltaTime);
            game.render();

            updateTime = System.nanoTime() - now;
            wait = (OPTIMAL_TIME - updateTime) / 1000000;
            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

        game.terminate();
        System.exit(0);
    }

}
