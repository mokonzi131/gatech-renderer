package com.fsfive.renderer.render;

import com.fsfive.renderer.graphics.Mesh;
import com.fsfive.renderer.graphics.Pipeline;
import com.fsfive.renderer.graphics.Vec4f;
import org.jblas.FloatMatrix;

import java.awt.*;

/**
 * Created by Michael on 6/3/2014.
 */
public class RenderObject {
    private Mesh m_mesh;
    private FloatMatrix m_worldLocation;
    private FloatMatrix m_worldSize;

    public RenderObject() {
        m_mesh = null;
        m_worldLocation = FloatMatrix.zeros(3);
        m_worldSize = FloatMatrix.ones(3);
    }

    void attachMesh(Mesh mesh) {
        m_mesh = mesh;
    }

    void setLocation(float x, float y, float z) {
        m_worldLocation = new FloatMatrix(3, 1, x, y, z);
    }

    void setSize(float sx, float sy, float sz) {
        m_worldSize = new FloatMatrix(3, 1, sx, sy, sz);
    }

    void update(double deltaTime) {
//        m_worldLocation.put(0, m_worldLocation.get(0) + 10f);
//        m_worldSize.put(1, m_worldSize.get(1) + 10f);
    }

    void render(Graphics2D graphics, Pipeline pipeline) {
        pipeline.translate(m_worldLocation.get(0), m_worldLocation.get(1), m_worldLocation.get(2));
        pipeline.scale(m_worldSize.get(0), m_worldSize.get(1), m_worldSize.get(2));
        m_mesh.render(graphics, pipeline);
        pipeline.scale(1f / m_worldSize.get(0), 1f / m_worldSize.get(1), 1f / m_worldSize.get(2));
        pipeline.translate(-m_worldLocation.get(0), -m_worldLocation.get(1), -m_worldLocation.get(2));
    }
}
