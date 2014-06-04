package com.fsfive.renderer;

import org.jblas.FloatMatrix;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class JBlasTest {

    @Test
    public void testMatrixMultiply() throws Exception {
        System.out.println("Setup");
        FloatMatrix A = new FloatMatrix(new float[][] {
                {1f, 0f},
                {1f, 1f}
        });
//        FloatMatrix A = new FloatMatrix(2, 2,
//                1f, 0f,
//                1f, 1f);
        FloatMatrix B = new FloatMatrix(new float[][] {
                {1f, 2f},
                {3f, 4f}
        });
//        FloatMatrix B = new FloatMatrix(2, 2,
//                1f, 2f,
//                3f, 4f);
        FloatMatrix x = new FloatMatrix(new float[][] {
                {2f, 3f}
        });
        System.out.print("A = "); A.print();
        System.out.print("B = "); B.print();
        System.out.print("x = "); x.print();

//        System.out.println("A.mul(B)");
//        FloatMatrix M0 = A.mul(B);
//        System.out.print("A = "); A.print();
//        System.out.print("B = "); B.print();
//        M0.print();
//
//        System.out.println("A.muli(B)");
//        FloatMatrix M1 = A.muli(B);
//        System.out.print("A = "); A.print();
//        System.out.print("B = "); B.print();
//        M1.print();

        System.out.println("A.mmul(B)");
        FloatMatrix M2 = A.mmul(B);
        System.out.print("A = "); A.print();
        System.out.print("B = "); B.print();
        M2.print();

//        System.out.println("A.mmuli(B)");
//        FloatMatrix M3 = A.mmuli(B);
//        System.out.print("A = "); A.print();
//        System.out.print("B = "); B.print();
//        M3.print();

        System.out.println("x.mmul(A)");
        FloatMatrix c = x.mmul(A);
        System.out.print("x = "); x.print();
        System.out.print("A = "); A.print();
        c.print();
    }
}