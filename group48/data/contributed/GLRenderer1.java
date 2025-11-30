package com.javagame.render.opengl;

import java.util.List;

import com.javagame.map.region.Region;
import com.javagame.map.tile.Registry.TileRegistry;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

public class GLRenderer1 {
    private final TileBatchRenderer tileBatch;

    public GLRenderer(int vWidth, int vHeight) {
        tileBatch = new TileBatchRenderer();

        // === Setup orthographic projection matrix ===
        try (MemoryStack stack = MemoryStack.stackPush()) {
            Matrix4f projection = new Matrix4f().ortho2D(0, vWidth, vHeight, 0);
            tileBatch.getShader().bind();
            tileBatch.getShader().setUniform("uProjection", projection.get(stack.mallocFloat(16)));
            tileBatch.getShader().unbind();
        }
    }
    
    public void loadRegionTextures(Region region) {
        List<Integer> handles = TileRegistry.getUsedImageHandlesForRegion(region);
        tileBatch.bindTileTextures(handles);
        tileBatch.setTextureHandleList(handles);
    }

    public TileBatchRenderer getTileBatchRenderer() {
        return tileBatch;
    }

    public void cleanup() {
        tileBatch.cleanup();
    }
}