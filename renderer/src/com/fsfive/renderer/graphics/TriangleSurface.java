package com.fsfive.renderer.graphics;

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
        // emissive = ke
        FloatMatrix Cem = pipeline.materialColor;

        // ambient = ka * ambient
        FloatMatrix Camb = pipeline.ambientColor;

        // diffuse = kd * color * max(L.N, 0)
        FloatMatrix L = pipeline.lightPosition.sub(centroid);
        FloatMatrix Cdiff = pipeline.lightColor.mul(Math.max(Geometry.normalize(L).dot(normal), 0));

        // specular = ks * color * [N.L > 0 ? 1 : 0] * max(H.N, 0)^S
        float specular = 20;
        FloatMatrix H = Geometry.normalize(E.add(L));
        FloatMatrix Cspec = pipeline.lightColor.mul((float) Math.pow(Math.max(H.dot(normal), 0), specular));

        // final shade composition
        FloatMatrix C = Cspec.add(Cdiff).add(Cem.mul(.5f)).add(Camb.mul(.5f));
        if (C.get(0) > 1f) C.put(0, 1f);
        if (C.get(1) > 1f) C.put(1, 1f);
        if (C.get(2) > 1f) C.put(2, 1f);
        if (C.get(3) > 1f) C.put(3, 1f);
        m_shade = new Color(C.get(0), C.get(1), C.get(2), C.get(3));

        // compute a distance value to use for sorting painter list
        float distance = pipeline.m_eye.squaredDistance(centroid);

        // clip near and far planes
        float near = pipeline.near * pipeline.near;
        float far = pipeline.far * pipeline.far;
        if (distance < near || distance > far)
            return;

        pipeline.painterList.put(distance, this);
    }

    public void draw(Graphics2D graphics) {
        graphics.setColor(m_shade);
        graphics.fillPolygon(m_px, m_py, 3);
    }
}
