package com.fsfive.renderer.demo;

/**
 * Created by Michael on 6/3/2014.
 */
public class Settings {
    // FILENAME
    public static String filename = "/shuttle_breneman_whitfield.raw";

    // USING LEFT-HANDED SYSTEM
    public static int framerate = 30;

    // OBJECT LOCATION / ORIENTATION
    // objectPosition : vec3f world coordinates
    // objectOrientation : vec3i degrees
    public static float[] objectPosition = {0, -5, 10};
    public static int[] objectOrientation = {180, 0, 0};

    // CAMERA LOCATION / ORIENTATION
    // cameraPosition : vec3f world coordinates
    // cameraLook : vec3f world coordinates
    public static float[] cameraEye = {0f, 0f, 0f};
    public static float[] cameraLookAt = {0f, -5f, 10f};

    // FIELD OF VIEW
    // cameraFOV : float
    // cameraNear : float
    // cameraFar : float
    public static float cameraFOV = 90f;
    public static float cameraNear = 7f;
    public static float cameraFar = 12f;

    // SIMPLE LIGHTING
    // lightEmissive : vec3i color (this is the material color)
    // lightAmbient : vec3i color
    public static int[] lightEmissive = {112, 110, 115};
    public static int[] lightAmbient = {115, 115, 50};

    // LIGHT SOURCE
    // lightPosition : vec3f world coordinates
    // lightColor : vec3i color
    public static float[] lightPosition = {5f, 5f, 5f};
    public static int[] lightColor = {50, 0, 155};

    // OBJECT MOVEMENT
    public static float dx = .0f;
    public static float dy = .0f;
    public static float dz = .0f;
    public static float rotx = 5f; // specified in degrees per second
    public static float roty = 60f;
    public static float rotz = 5f;
}
