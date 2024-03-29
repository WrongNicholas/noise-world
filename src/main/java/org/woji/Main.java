package org.woji;

public class Main {

    public static void main(String[] args) {

        GameManager game = new GameManager();
        game.initialize();

        long previousTime = System.currentTimeMillis();
        while (game.running()) {
            long currentTime = System.currentTimeMillis();
            float deltaTime = currentTime - previousTime;
            previousTime = currentTime;

            // Convert deltaTime to seconds
            deltaTime = deltaTime / 1_000;

            game.update(deltaTime);
            game.render();
        }

        game.terminate();
        System.exit(0);
    }

}
