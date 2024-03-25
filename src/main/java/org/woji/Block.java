package org.woji;

public class Block {

    private int id;

    public Block(int id) {
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
