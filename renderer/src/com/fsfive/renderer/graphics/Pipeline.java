package com.fsfive.renderer.graphics;

import com.fsfive.renderer.demo.Settings;
import org.jblas.FloatMatrix;
import org.jblas.Geometry;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Michael on 6/3/2014.
 */
public class Pipeline {
    // TODO use mmuli to multiply matrices in-place for speed improvements
    public static final int SIZE = 4;

    private List<FloatMatrix> base;
    private List<FloatMatrix> lighting;
    public FloatMatrix m_eye;
    public FloatMatrix materialColor;
    public FloatMatrix ambientColor;
    public FloatMatrix lightPosition;
    public FloatMatrix lightColor;

    public TreeMap<Float, TriangleSurface> painterList;

    public Pipeline() {
        base = new ArrayList<>();
        base.add(FloatMatrix.eye(SIZE));

        lighting = new ArrayList<>();
        lighting.add(FloatMatrix.eye(SIZE));

        materialColor = new FloatMatrix(new float[][] {
                {Settings.lightEmissive[0] / 255f, Settings.lightEmissive[1] / 255f,
                        Settings.lightEmissive[2] / 255f, 1f}
        });
        ambientColor = new FloatMatrix(new float[][] {
                {Settings.lightAmbient[0] / 255f, Settings.lightAmbient[1] / 255f,
                        Settings.lightAmbient[2] / 255f, 1f}
        });
        lightPosition = new FloatMatrix(new float[][] {
                {Settings.lightPosition[0], Settings.lightPosition[1], Settings.lightPosition[2]}
        });
        lightColor = new FloatMatrix(new float[][] {
                {Settings.lightColor[0] / 255f, Settings.lightColor[1] / 255f,
                    Settings.lightColor[2] / 255f, 1f}
        });

        painterList = new TreeMap<>();
    }

    public FloatMatrix current() {
        return base.get(0);
    }

    public FloatMatrix lighting() {
        return lighting.get(0);
    }

    private void applyMatrix(FloatMatrix input, boolean light) {
        if (!input.sameSize(current()))
            return;

        base.add(0, input.mmul(base.remove(0)));
        if (light)
            lighting.add(0, input.mmul(lighting.remove(0)));
    }

    public void push() {
        base.add(0, base.get(0).dup());
        lighting.add(0, lighting.get(0).dup());
    }

    public void pop() {
        base.remove(0);
        if (base.size() <= 0)
            base.add(FloatMatrix.eye(SIZE));
        lighting.remove(0);
        if (lighting.size() <= 0)
            lighting.add(FloatMatrix.eye(SIZE));
    }

    public void scale(float sx, float sy, float sz, boolean light) {
        FloatMatrix S = new FloatMatrix(new float[][] {
                {sx, 0f, 0f, 0f},
                {0f, sy, 0f, 0f},
                {0f, 0f, sz, 0f},
                {0f, 0f, 0f, 1f}
        });
        applyMatrix(S, light);
    }
    
    public void translate(float tx, float ty, float tz, boolean light) {
        FloatMatrix T = new FloatMatrix(new float[][] {
                {1f, 0f, 0f, 0f},
                {0f, 1f, 0f, 0f},
                {0f, 0f, 1f, 0f},
                {tx, ty, tz, 1f}
        });
        applyMatrix(T, light);
    }

    public void rotate(int dx, int dy, int dz, boolean light) {
        final float DEGREES_TO_RAD = (float) Math.PI / 180;

        float rx = dx * DEGREES_TO_RAD;
        float ry = dy * DEGREES_TO_RAD;
        float rz = dz * DEGREES_TO_RAD;

        rotate(rx, ry, rz, light);
    }

    public void rotate(float rx, float ry, float rz, boolean light) {
        // rotate around x
        FloatMatrix Rx = new FloatMatrix(new float[][] {
                {1f, 0f, 0f, 0f},
                {0f, (float)Math.cos(rx), (float)Math.sin(rx), 0f},
                {0f, -(float)Math.sin(rx), (float)Math.cos(rx), 0f},
                {0f, 0f, 0f, 1f}
        });
        applyMatrix(Rx, light);

        // rotate around y
        FloatMatrix Ry = new FloatMatrix(new float[][] {
                {(float)Math.cos(ry), 0f, -(float)Math.sin(ry), 0f},
                {0f, 1f, 0f, 0f},
                {(float)Math.sin(ry), 0f, (float)Math.cos(ry), 0f},
                {0f, 0f, 0f, 1f}
        });
        applyMatrix(Ry, light);

        // rotate around z
        FloatMatrix Rz = new FloatMatrix(new float[][] {
                {(float)Math.cos(rz), (float)Math.sin(rz), 0f, 0f},
                {-(float)Math.sin(rz), (float)Math.cos(rz), 0f, 0f},
                {0f, 0f, 1f, 0f},
                {0f, 0f, 0f, 1f}
        });
        applyMatrix(Rz, light);
    }

    public FloatMatrix cross(FloatMatrix U, FloatMatrix V, boolean LHS) {
        float scale = LHS ? -1f : 1f;
        // assuming 3x3 matrices coming in...
        //    c0 = u1v2 - u2v1
        //    c1 = u2v0 - u0v2
        //    c2 = u0v1 - u1v0
        float c0 = U.get(1) * V.get(2) - U.get(2) * V.get(1);
        float c1 = U.get(2) * V.get(0) - U.get(0) * V.get(2);
        float c2 = U.get(0) * V.get(1) - U.get(1) * V.get(0);
        FloatMatrix C = new FloatMatrix(new float[][] {
                // negate because we are LHS
                {scale * c0, scale * c1, scale * c2}
        });

        return C;
    }

    public void setView(FloatMatrix at, FloatMatrix eye, FloatMatrix up) {
        // create the axes
        //        zaxis = normal(At - Eye)
        //        xaxis = normal(cross(Up, zaxis))
        //        yaxis = cross(zaxis, xaxis)
        FloatMatrix Z = Geometry.normalize(eye.sub(at));
        FloatMatrix X = Geometry.normalize(cross(up, Z, true));
        FloatMatrix Y = cross(Z, X, true);

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

        applyMatrix(view, false);
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
        //        0          0       far / (near - far)         -1
        //        0          0       near * far / (near - far)     0
        FloatMatrix projection = new FloatMatrix(new float[][] {
                {xScale, 0f, 0f, 0f},
                {0f, yScale, 0f, 0f},
                {0f, 0f, far / (near - far), -1f},
                {0f, 0f, near * far / (near - far), 0f}
        });

        applyMatrix(projection, false);
    }

    public void setViewport(float width, float height, float dx, float dy, float minz, float maxz) {
        // dwX, dwY = viewport offsets in pixels
        // dwWidth, dwHeight = viewport width and height in pixels

        // build matrix
        // width    0       0   0
        // 0    -height     0   0
        // 0        0       1   0
        // dx   height + dy 0   1
        FloatMatrix viewport = new FloatMatrix(new float[][] {
                {width / 2f, 0f, 0f, 0f},
                {0f, -height / 2f, 0f, 0f},
                {0f, 0f, maxz - minz, 0f},
                {dx + width / 2f, height / 2f + dy, minz, 1f}
        });

        applyMatrix(viewport, false);
    }

    public void drawPainterList(Graphics2D graphics) {
        for (TriangleSurface surface : painterList.descendingMap().values()) {
            surface.draw(graphics);
        }
    }
}
