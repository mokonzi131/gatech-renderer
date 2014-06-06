package com.fsfive.renderer;

import com.fsfive.renderer.demo.Renderer;
import com.fsfive.renderer.demo.Settings;
import com.fsfive.renderer.util.ResourceLoader;

import java.util.Properties;

/**
 * Created by Michael on 6/2/2014.
 */
public class JavaApplicationMain {
    public static void main(String[] args) {
        Properties properties = ResourceLoader.getProperties("/renderer.properties");

        Settings.filename = properties.getProperty("filename", "/shuttle_breneman_whitfield.raw");
        Settings.framerate = Integer.parseInt(properties.getProperty("framerate", "30"));
        String[] objectPosition = properties.getProperty("objectPosition", "0, -5, 10").split("\\s*,\\s*");
        Settings.objectPosition = new float[] {
                Float.parseFloat(objectPosition[0]),
                Float.parseFloat(objectPosition[1]),
                Float.parseFloat(objectPosition[2])
        };
        String[] objectOrientation = properties.getProperty("objectOrientation", "180, 0, 0").split("\\s*,\\s*");
        Settings.objectOrientation = new int[] {
                Integer.parseInt(objectOrientation[0]),
                Integer.parseInt(objectOrientation[1]),
                Integer.parseInt(objectOrientation[2])
        };
        String[] cameraEye = properties.getProperty("cameraEye", "0, 0, 0").split("\\s*,\\s*");
        Settings.cameraEye = new float[] {
                Float.parseFloat(cameraEye[0]),
                Float.parseFloat(cameraEye[1]),
                Float.parseFloat(cameraEye[2])
        };
        String[] cameraLookAt = properties.getProperty("cameraLookAt", "0, -5, 10").split("\\s*,\\s*");
        Settings.cameraLookAt = new float[] {
                Float.parseFloat(cameraLookAt[0]),
                Float.parseFloat(cameraLookAt[1]),
                Float.parseFloat(cameraLookAt[2])
        };
        Settings.cameraFOV = Float.parseFloat(properties.getProperty("cameraFOV", "90"));
        Settings.cameraNear = Float.parseFloat(properties.getProperty("cameraNear", "7"));
        Settings.cameraFar = Float.parseFloat(properties.getProperty("cameraFar", "12"));
        String[] lightEmissive = properties.getProperty("lightEmissive", "112, 110, 115").split("\\s*,\\s*");
        Settings.lightEmissive = new int[] {
                Integer.parseInt(lightEmissive[0]),
                Integer.parseInt(lightEmissive[1]),
                Integer.parseInt(lightEmissive[2])
        };
        String[] lightAmbient = properties.getProperty("lightAmbient", "115, 115, 50").split("\\s*,\\s*");
        Settings.lightAmbient = new int[] {
                Integer.parseInt(lightAmbient[0]),
                Integer.parseInt(lightAmbient[1]),
                Integer.parseInt(lightAmbient[2])
        };
        String[] lightPosition = properties.getProperty("lightPosition", "5, 5, 5").split("\\s*,\\s*");
        Settings.lightPosition = new float[] {
                Float.parseFloat(lightPosition[0]),
                Float.parseFloat(lightPosition[1]),
                Float.parseFloat(lightPosition[2])
        };
        String[] lightColor = properties.getProperty("lightColor", "50, 0, 155").split("\\s*,\\s*");
        Settings.lightColor = new int[] {
                Integer.parseInt(lightColor[0]),
                Integer.parseInt(lightColor[1]),
                Integer.parseInt(lightColor[2])
        };
        Settings.dx = Float.parseFloat(properties.getProperty("dx", ".0"));
        Settings.dy = Float.parseFloat(properties.getProperty("dy", ".0"));
        Settings.dz = Float.parseFloat(properties.getProperty("dz", ".0"));
        Settings.rotx = Float.parseFloat(properties.getProperty("rotx", "5"));
        Settings.roty = Float.parseFloat(properties.getProperty("roty", "60"));
        Settings.rotz = Float.parseFloat(properties.getProperty("rotz", "5"));

        Renderer renderer = new Renderer();
        new Thread(renderer, "Renderer").start();
    }
}
