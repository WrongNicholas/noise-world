package org.woji.world;

public class ChunkNode {

    int index;
    public Chunk chunk;
    ChunkNode prev, next;

    public ChunkNode(int index, Chunk chunk) {
        this.index = index;
        this.chunk = chunk;
    }
}
