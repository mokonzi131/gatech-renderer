package com.fsfive.renderer.util;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class RawObjectLoaderTest {

    @Test
    public void testLoadRawFile() throws Exception {
        List<float[]> data = RawObjectLoader.loadRawFile("/shuttle_breneman_whitfield.raw");

        for (float[] vector : data) {
            for (int i = 0; i < vector.length; ++i)
                System.out.print(vector[i] + ", ");
            System.out.println();
        }
    }

    @Test
    public void testReadLines() throws Exception {
        List<String> lines = RawObjectLoader.readLines("/shuttle_breneman_whitfield.raw");
        assertEquals(617, lines.size());
    }
}