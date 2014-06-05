package com.fsfive.renderer.graphics;

import com.fsfive.renderer.demo.Settings;
import org.jblas.FloatMatrix;
import org.jblas.Geometry;

import java.awt.*;

/**
 * Created by Michael on 6/3/2014.
 */
public class TriangleSurface {
    private static final int NUM_VERTICES = 3;

    private FloatMatrix[] m_vertices;
    private int[] m_px;
    private int[] m_py;
    private Color m_shade;

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
        m_shade = Color.BLACK;
    }

    public void render(Graphics2D graphics, Pipeline pipeline) {
        // transform the vertices to world coordinates for lighting
        FloatMatrix w0 = m_vertices[0].mmul(pipeline.lighting());
        FloatMatrix w1 = m_vertices[1].mmul(pipeline.lighting());
        FloatMatrix w2 = m_vertices[2].mmul(pipeline.lighting());

        // compute world centroid for this surface
        FloatMatrix centroid = new FloatMatrix(new float[][] {
                {(w0.get(0) + w1.get(0) + w2.get(0)) / 3f,
                        (w0.get(1) + w1.get(1) + w2.get(1)) / 3f,
                        (w0.get(2) + w1.get(2) + w2.get(2)) / 3f}
        });

        // compute world normal for this surface
        FloatMatrix U = w1.sub(w0);
        FloatMatrix V = w2.sub(w0);
        FloatMatrix normal = Geometry.normalize(pipeline.cross(U, V, false)); // rhs winding order

        // cull back-faces (E = centroid to eye)
        FloatMatrix E = pipeline.m_eye.sub(centroid);
        if (-Geometry.normalize(E).dot(normal) > 0)
            return;

        // test world coordinates...
//        m_px[0] = (int)(w0.get(0) * -100);
//        m_px[1] = (int)(w1.get(0) * -100);
//        m_px[2] = (int)(w2.get(0) * -100);
//        m_py[0] = (int)(w0.get(1) * -100);
//        m_py[1] = (int)(w1.get(1) * -100);
//        m_py[2] = (int)(w2.get(1) * -100);
//        int cx = (int)(centroid.get(0) * -100);
//        int cy = (int)(centroid.get(1) * -100);
//        int nx = (int) (cx + normal.get(0) * -20);
//        int ny = (int) (cy + normal.get(1) * -20);
//        graphics.setColor(Color.RED);
//        graphics.drawPolygon(m_px, m_py, 3);
//        graphics.setColor(Color.GREEN);
//        graphics.fillOval(cx, cy, 3, 3);
//        graphics.setColor(Color.WHITE);
//        graphics.drawLine(cx, cy, nx, ny);

        // transform the vertices according to pipeline
        FloatMatrix v0 = m_vertices[0].mmul(pipeline.current());
        FloatMatrix v1 = m_vertices[1].mmul(pipeline.current());
        FloatMatrix v2 = m_vertices[2].mmul(pipeline.current());

        // extract the x and y coordinates
        m_px[0] = (int)(v0.get(0) / v0.get(3));
        m_px[1] = (int)(v1.get(0) / v1.get(3));
        m_px[2] = (int)(v2.get(0) / v2.get(3));
        m_py[0] = (int)(v0.get(1) / v0.get(3));
        m_py[1] = (int)(v1.get(1) / v1.get(3));
        m_py[2] = (int)(v2.get(1) / v2.get(3));

        // compute surface color (shade)
        FloatMatrix L = Geometry.normalize(pipeline.lightPosition.sub(centroid));
        FloatMatrix Cem = pipeline.materialColor.mul(0.25f); // = ke
        FloatMatrix Camb = pipeline.ambientColor.mul(0.25f); // = ka * ambient
        FloatMatrix Cdiff = pipeline.lightColor.mul(1f).mul(Math.max(L.dot(normal), 0)); // = kd * color * max(L.N, 0)
        float specular = 5;
//        E = Geometry.normalize(pipeline.lightPosition.sub(pipeline.m_eye)); // centroid - eye?
        FloatMatrix H = Geometry.normalize(E.add(L));
        FloatMatrix Cspec = pipeline.lightColor.mul(0.25f).mul((float) Math.pow(Math.max(H.dot(normal), 0), specular));
        // = ks * color * [N.L > 0 ? 1 : 0] * max(H.N, 0)^S
//        FloatMatrix C = Cspec.add(Cdiff).add(Camb).add(Cem);
        FloatMatrix C = Cdiff;
        if (C.get(0) > 1f) C.put(0, 1f);
        if (C.get(1) > 1f) C.put(1, 1f);
        if (C.get(2) > 1f) C.put(2, 1f);
        if (C.get(3) > 1f) C.put(3, 1f);
        m_shade = new Color(C.get(0), C.get(1), C.get(2), C.get(3));

        // compute a distance value to use for sorting painter list
        float distance = pipeline.m_eye.squaredDistance(centroid);

        pipeline.painterList.put(distance, this);
    }

    public void draw(Graphics2D graphics) {
        graphics.setColor(m_shade);
        graphics.fillPolygon(m_px, m_py, 3);
    }
}
