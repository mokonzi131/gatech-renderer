package com.fsfive.renderer.render;

import com.fsfive.renderer.math.Vec4f;

import java.awt.*;

/**
 * Created by Michael on 6/3/2014.
 */
public class TriangleSurface {
    private Vec4f[] m_vertices;
    private Vec4f m_normal;
    private Vec4f[] m_colors;

    public TriangleSurface(float x1, float y1, float z1,
                           float x2, float y2, float z2,
                           float x3, float y3, float z3) {
        m_vertices = new Vec4f[3];
        m_vertices[0] = new Vec4f(x1, y1, z1, 0f);
        m_vertices[1] = new Vec4f(x2, y2, z2, 0f);
        m_vertices[2] = new Vec4f(x3, y3, z3, 0f);

        m_normal = new Vec4f();

        m_colors = new Vec4f[3];
        for (int i = 0; i < 3; i++) {
            m_colors[i] = new Vec4f();
        }
    }

    public void render(Graphics2D graphics) {
        int[] xp = new int[3];
        int[] yp = new int[3];

        xp[0] = (int)m_vertices[0].vector()[0];
        xp[1] = (int)m_vertices[1].vector()[0];
        xp[2] = (int)m_vertices[2].vector()[0];
        yp[0] = (int)m_vertices[0].vector()[1];
        yp[1] = (int)m_vertices[1].vector()[1];
        yp[2] = (int)m_vertices[2].vector()[1];

        graphics.setColor(Color.RED);
        graphics.drawPolygon(xp, yp, 3);
    }
}
