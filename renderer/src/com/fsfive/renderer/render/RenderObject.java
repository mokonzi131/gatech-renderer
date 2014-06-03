package com.fsfive.renderer.render;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 6/3/2014.
 */
public class RenderObject {
    private List<TriangleSurface> m_surfaces;

    public static RenderObject createRenderObjectFromVerticesList(List<float[]> meshData) {
        ArrayList<TriangleSurface> surfaces = new ArrayList<>();

        for (float[] triangle : meshData) {
            surfaces.add(new TriangleSurface(
                    triangle[0], triangle[1], triangle[2],
                    triangle[3], triangle[4], triangle[5],
                    triangle[6], triangle[7], triangle[8]));
        }

        return new RenderObject(surfaces);
    }

    public RenderObject() {
        m_surfaces = new ArrayList<>();
        m_surfaces.add(new TriangleSurface(10f, 100f, 0f, 50f, 150f, 0f, 150f, 50f, 0f));
        m_surfaces.add(new TriangleSurface(20f, 20f, 20f, 250f, 300f, 1f, 300f, 300f, 1f));
    }

    public RenderObject(List<TriangleSurface> surfaces) {
        m_surfaces = surfaces;
    }

    public void render(Graphics2D graphics) {
        for (TriangleSurface surface : m_surfaces) {
            surface.render(graphics);
        }
    }
}
