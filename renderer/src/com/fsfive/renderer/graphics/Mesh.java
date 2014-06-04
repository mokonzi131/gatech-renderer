package com.fsfive.renderer.graphics;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 6/3/2014.
 */
public class Mesh {
    private List<TriangleSurface> m_surfaces;

    public static Mesh createRenderObjectFromVerticesList(List<float[]> meshData) {
        ArrayList<TriangleSurface> surfaces = new ArrayList<>();

        for (float[] triangle : meshData) {
            surfaces.add(new TriangleSurface(
                    triangle[0], triangle[1], triangle[2],
                    triangle[3], triangle[4], triangle[5],
                    triangle[6], triangle[7], triangle[8]));
        }

        return new Mesh(surfaces);
    }

    public Mesh(List<TriangleSurface> surfaces) {
        m_surfaces = surfaces;
    }

    public void render(Graphics2D graphics, Pipeline pipeline) {
        for (TriangleSurface surface : m_surfaces) {
            surface.render(graphics, pipeline);
        }
    }
}
