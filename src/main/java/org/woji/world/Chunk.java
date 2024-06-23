package org.woji.world;

import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class Chunk {

    private Body body;
    private int[] blockMap;
    private int width, height;
    private int position;
    private float blockSizePixels;
    public Chunk prev, next;

    public Chunk(Body body, int[] blockMap, int width, int height, int position, float blockSizePixels, Chunk prev, Chunk next) {
        this.body = body;
        this.blockMap = blockMap;
        this.width = width;
        this.height = height;
        this.position = position;
        this.blockSizePixels = blockSizePixels;
        this.prev = prev;
        this.next = next;
    }
    public AABB getBounds() {
        Vec2 topLeft = new Vec2(width * position * blockSizePixels, 0);
        Vec2 bottomRight = new Vec2(width * (position + 1) * blockSizePixels, height * blockSizePixels);

        return new AABB(topLeft, bottomRight);
    }

    public void load() {
        body.setActive(true);
    }

    public void unload() {
        body.setActive(false);
    }

    public int[] blockMap() {
        return blockMap;
    }

    public float blockSizePixels() {
        return blockSizePixels;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public int position() {
        return position;
    }
}