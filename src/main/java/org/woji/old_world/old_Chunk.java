package org.woji.old_world;

import org.jbox2d.dynamics.*;

/**
 * @param body                  Physics body associated with the chunk
 * @param blockMap              Block Map represented as integer array
 * @param totalChunkWidthPixels Total width of the chunk in pixels for rendering purposes
 */
public record old_Chunk(Body body, int[] blockMap, float totalChunkWidthPixels) {
    
}
