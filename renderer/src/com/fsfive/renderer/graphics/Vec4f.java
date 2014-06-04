package com.fsfive.renderer.graphics;

/**
 * Created by Michael on 6/3/2014.
 */
public class Vec4f {
    private float[] m_vector;

    public Vec4f(float x, float y, float z, float a) {
        m_vector = new float[4];
        m_vector[0] = x;
        m_vector[1] = y;
        m_vector[2] = z;
        m_vector[3] = a;
    }

    public Vec4f() {
        this(0f, 0f, 0f, 0f);
    }

    public float[] vector() {
        return m_vector;
    }
}
