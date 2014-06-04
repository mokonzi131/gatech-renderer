package com.fsfive.renderer.graphics;

import org.jblas.FloatMatrix;

import java.awt.*;

/**
 * Created by Michael on 6/3/2014.
 */
public class TriangleSurface {
    private static final int NUM_VERTICES = 3;

    private FloatMatrix[] m_vertices;
    private int[] m_px;
    private int[] m_py;

//    private Vec4f m_normal;
//    private Vec4f[] m_colors;

    public TriangleSurface(float x1, float y1, float z1,
                           float x2, float y2, float z2,
                           float x3, float y3, float z3) {
        m_vertices = new FloatMatrix[NUM_VERTICES];
        m_vertices[0] = new FloatMatrix(new float[][] {
                {x1, y1, z1, 1f}
        });
        m_vertices[1] = new FloatMatrix(new float[][] {
                {x2, y2, z2, 1f}
        });
        m_vertices[2] = new FloatMatrix(new float[][] {
                {x3, y3, z3, 1f}
        });

        m_px = new int[NUM_VERTICES];
        m_py = new int[NUM_VERTICES];

//        m_normal = new Vec4f();
//
//        m_colors = new Vec4f[3];
//        for (int i = 0; i < 3; i++) {
//            m_colors[i] = new Vec4f();
//        }
    }

    public void render(Graphics2D graphics, Pipeline pipeline) {
        // transform the vertices according to pipeline
        FloatMatrix v0 = m_vertices[0].mmul(pipeline.current());
        FloatMatrix v1 = m_vertices[1].mmul(pipeline.current());
        FloatMatrix v2 = m_vertices[2].mmul(pipeline.current());

        m_px[0] = (int)v0.get(0);
        m_px[1] = (int)v1.get(0);
        m_px[2] = (int)v2.get(0);

        m_py[0] = (int)v0.get(1);
        m_py[1] = (int)v1.get(1);
        m_py[2] = (int)v2.get(1);

        graphics.setColor(Color.RED);
        graphics.drawPolygon(m_px, m_py, 3);
    }
}
