package org.woji;

public class Tile {

    private int id;

    public Tile(int id) {
        this.id = id;
    }

    // Accessors
    public int getID() {
        return id;
    }

    public void updateID(int id) {
        this.id = id;
    }
}
