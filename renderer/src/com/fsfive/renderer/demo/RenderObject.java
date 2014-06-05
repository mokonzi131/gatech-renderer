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
    private float[] m_rotation;

    public RenderObject() {
        m_mesh = null;
        m_position = FloatMatrix.zeros(3);
        m_size = FloatMatrix.ones(3);
        m_rotation = new float[] {0f, 0f, 0f};
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
        m_rotation = new float[] {(float) (dx * Math.PI / 180), (float) (dy * Math.PI / 180), (float) (dz * Math.PI / 180)};
    }

    void update(double deltaTime) {
        m_position.put(0, m_position.get(0) + Settings.dx);
        m_position.put(1, m_position.get(1) + Settings.dy);
        m_position.put(2, m_position.get(2) + Settings.dz);
        m_rotation[0] += deltaTime * Settings.rotx * Math.PI / 180;
        m_rotation[1] += deltaTime * Settings.roty * Math.PI / 180;
        m_rotation[2] += deltaTime * Settings.rotz * Math.PI / 180;
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
