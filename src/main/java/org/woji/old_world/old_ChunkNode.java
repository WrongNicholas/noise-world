package org.woji.old_world;

public class old_ChunkNode {

    int index;
    public old_Chunk chunk;
    old_ChunkNode prev, next;

    public old_ChunkNode(int index, old_Chunk chunk) {
        this.index = index;
        this.chunk = chunk;
    }
}
