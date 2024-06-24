package org.woji;

import org.woji.core.GameManager;

public class Main {

    public static void main(String[] args) {

        GameManager game = new GameManager();
        game.initialize();

        game.start();

        //System.exit(0);
    }
}
