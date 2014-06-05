package com.fsfive.renderer.demo;

import com.fsfive.renderer.graphics.Mesh;
import com.fsfive.renderer.graphics.Pipeline;
import org.jblas.FloatMatrix;

import java.awt.*;

/**
 * Created by Michael on 6/3/2014.
 */
public class RenderObject {
    private Mesh m_mesh;
    private FloatMatrix m_position;
    private FloatMatrix m_size;
    private int[] m_rotation;

    public RenderObject() {
        m_mesh = null;
        m_position = FloatMatrix.zeros(3);
        m_size = FloatMatrix.ones(3);
        m_rotation = new int[] {0, 0, 0};
    }

    void attachMesh(Mesh mesh) {
        m_mesh = mesh;
    }

    void setLocation(float x, float y, float z) {
        m_position = new FloatMatrix(3, 1, x, y, z);
    }

    void setSize(float sx, float sy, float sz) {
        m_size = new FloatMatrix(3, 1, sx, sy, sz);
    }

    void setRotation(int dx, int dy, int dz) {
        m_rotation = new int[] {dx, dy, dz};
    }

    void update(double deltaTime) {
//        m_position.put(0, m_position.get(0) + 1f);
//        m_size.put(1, m_size.get(1) + 10f);
//        m_rotation[0] += 3;
        m_rotation[1] += 1;
//        m_rotation[2] += 1;
    }

    void render(Graphics2D graphics, Pipeline pipeline) {
        pipeline.push();

        pipeline.translate(m_position.get(0), m_position.get(1), m_position.get(2), true);
        pipeline.rotate(m_rotation[0], m_rotation[1], m_rotation[2], true);
        pipeline.scale(m_size.get(0), m_size.get(1), m_size.get(2), true);
        m_mesh.render(graphics, pipeline);

        pipeline.pop();
    }
}
