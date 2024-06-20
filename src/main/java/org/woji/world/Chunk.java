package org.woji.world;

import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

/**
 * @param width    Width of chunk
 * @param height   Height of chunk
 * @param position Position of chunk in relation to other chunks
 *
 * @param blockMap Block Map represented as integer array
 */

public record Chunk(Body body, int[] blockMap, int width, int height, int position, float blockSizePixels) {
    public AABB getBounds() {
        Vec2 topLeft = new Vec2(width * position * blockSizePixels, 0);
        Vec2 bottomRight = new Vec2(width * (position + 1) * blockSizePixels, height * blockSizePixels);

        return new AABB(topLeft, bottomRight);
    }

    public int[] blockMap() {
        return blockMap;
    }
}