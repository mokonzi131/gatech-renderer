package com.fsfive.renderer.graphics;

import org.junit.Test;

import static org.junit.Assert.*;

public class Vec4fTest {
    private static float DELTA = 0.0001f;

    @Test
    public void testVector() throws Exception {
        Vec4f vector = new Vec4f();
        float[] values = {0f, 0f, 0f, 0f};
        assertArrayEquals(values, vector.vector(), DELTA);

        values = new float[]{1f, 2f, 3f, 4f};
        vector = new Vec4f(values[0], values[1], values[2], values[3]);
        assertArrayEquals(values, vector.vector(), DELTA);
    }
}