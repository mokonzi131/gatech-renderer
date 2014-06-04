package com.fsfive.renderer.graphics;

import org.jblas.FloatMatrix;

/**
 * Created by Michael on 6/3/2014.
 */
public class Pipeline {
    public static final int SIZE = 4;

    private FloatMatrix base;

    public Pipeline() {
        base = FloatMatrix.eye(SIZE);
        base.print();
    }

    public FloatMatrix current() {
        return base;
    }

    private void applyMatrix(FloatMatrix input) {
        if (!input.sameSize(base))
            return;

        base = input.mmul(base);
    }

    public void scale(float sx, float sy, float sz) {
        FloatMatrix S = new FloatMatrix(new float[][] {
                {sx, 0f, 0f, 0f},
                {0f, sy, 0f, 0f},
                {0f, 0f, sz, 0f},
                {0f, 0f, 0f, 1f}
        });
        applyMatrix(S);
    }
    
    public void translate(float dx, float dy, float dz) {
        FloatMatrix T = new FloatMatrix(new float[][] {
                {1f, 0f, 0f, 0f},
                {0f, 1f, 0f, 0f},
                {0f, 0f, 1f, 0f},
                {dx, dy, dz, 1f}
        });
        applyMatrix(T);
    }
}
