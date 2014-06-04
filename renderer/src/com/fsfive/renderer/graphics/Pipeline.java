package com.fsfive.renderer.graphics;

import org.jblas.FloatMatrix;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 6/3/2014.
 */
public class Pipeline {
    // TODO use mmuli to multiply matrices in-place for speed improvements
    public static final int SIZE = 4;

    private List<FloatMatrix> base;

    public Pipeline() {
        base = new ArrayList<>();
        base.add(FloatMatrix.eye(SIZE));
        base.get(0).print();
    }

    public FloatMatrix current() {
        return base.get(0);
    }

    private void applyMatrix(FloatMatrix input) {
        if (!input.sameSize(current()))
            return;

        base.add(0, input.mmul(base.remove(0)));
    }

    public void push() {
        base.add(0, base.get(0).dup());
    }

    public void pop() {
        base.remove(0);
        if (base.size() <= 0)
            base.add(FloatMatrix.eye(SIZE));
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
    
    public void translate(float tx, float ty, float tz) {
        FloatMatrix T = new FloatMatrix(new float[][] {
                {1f, 0f, 0f, 0f},
                {0f, 1f, 0f, 0f},
                {0f, 0f, 1f, 0f},
                {tx, ty, tz, 1f}
        });
        applyMatrix(T);
    }

    public void rotate(int dx, int dy, int dz) {
        final float DEGREES_TO_RAD = (float) Math.PI / 180;

        float rx = dx * DEGREES_TO_RAD;
        float ry = dy * DEGREES_TO_RAD;
        float rz = dz * DEGREES_TO_RAD;

        rotate(rx, ry, rz);
    }

    public void rotate(float rx, float ry, float rz) {
        // rotate around x
        FloatMatrix Rx = new FloatMatrix(new float[][] {
                {1f, 0f, 0f, 0f},
                {0f, (float)Math.cos(rx), (float)Math.sin(rx), 0f},
                {0f, -(float)Math.sin(rx), (float)Math.cos(rx), 0f},
                {0f, 0f, 0f, 1f}
        });
        applyMatrix(Rx);

        // rotate around y
        FloatMatrix Ry = new FloatMatrix(new float[][] {
                {(float)Math.cos(ry), 0f, -(float)Math.sin(ry), 0f},
                {0f, 1f, 0f, 0f},
                {(float)Math.sin(ry), 0f, (float)Math.cos(ry), 0f},
                {0f, 0f, 0f, 1f}
        });
        applyMatrix(Ry);

        // rotate around z
        FloatMatrix Rz = new FloatMatrix(new float[][] {
                {(float)Math.cos(rz), (float)Math.sin(rz), 0f, 0f},
                {-(float)Math.sin(rz), (float)Math.cos(rz), 0f, 0f},
                {0f, 0f, 1f, 0f},
                {0f, 0f, 0f, 1f}
        });
        applyMatrix(Rz);
    }
}
