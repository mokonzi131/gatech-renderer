package com.fsfive.renderer.graphics;

import org.jblas.FloatMatrix;
import org.jblas.Geometry;

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

    private FloatMatrix crossLHS(FloatMatrix U, FloatMatrix V) {
        // assuming 3x3 matrices coming in...
        //    c0 = u1v2 - u2v1
        //    c1 = u2v0 - u0v2
        //    c2 = u0v1 - u1v0
        float c0 = U.get(1) * V.get(2) - U.get(2) * V.get(1);
        float c1 = U.get(2) * V.get(0) - U.get(0) * V.get(2);
        float c2 = U.get(0) * V.get(1) - U.get(1) * V.get(0);
        FloatMatrix C = new FloatMatrix(new float[][] {
                // negate because we are LHS
                {-c0, -c1, -c2}
        });

        return C;
    }

    public void setView(FloatMatrix at, FloatMatrix eye, FloatMatrix up) {
        // create the axes
        //        zaxis = normal(At - Eye)
        //        xaxis = normal(crossLHS(Up, zaxis))
        //        yaxis = crossLHS(zaxis, xaxis)
        FloatMatrix Z = Geometry.normalize(eye.sub(at));
        FloatMatrix X = Geometry.normalize(crossLHS(up, Z));
        FloatMatrix Y = crossLHS(Z, X);

        // build the matrix
        //        xaxis.x           yaxis.x           zaxis.x          0
        //        xaxis.y           yaxis.y           zaxis.y          0
        //        xaxis.z           yaxis.z           zaxis.z          0
        //       -dot(xaxis, eye)  -dot(yaxis, eye)  -dot(zaxis, eye)  1
        FloatMatrix view = new FloatMatrix(new float[][] {
                {X.get(0), Y.get(0), Z.get(0), 0f},
                {X.get(1), Y.get(1), Z.get(1), 0f},
                {X.get(2), Y.get(2), Z.get(2), 0f},
                {-X.dot(eye), -Y.dot(eye), -Z.dot(eye), 1f}
        });

        applyMatrix(view);
    }

    public void setProjectionLHS(float fovy, float aspect, float near, float far) {
        // find scaling factors
        // yScale = cot(fovy/2)
        // xScale = yScale / aspect
        float yScale = (float) (1f / Math.tan(fovy / 2f));
        float xScale = yScale / aspect;

        // build matrix
        //        xScale     0          0               0
        //        0        yScale       0               0
        //        0          0       far / (far - near)         1
        //        0          0       -near * far / (far - near)     0
        FloatMatrix projection = new FloatMatrix(new float[][] {
                {xScale, 0f, 0f, 0f},
                {0f, yScale, 0f, 0f},
                {0f, 0f, far / (far - near), 1f},
                {0f, 0f, -near * far / (far - near), 0f}
        });

        applyMatrix(projection);
    }
}
