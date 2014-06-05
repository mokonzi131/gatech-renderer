package com.fsfive.renderer.demo;

/**
 * Created by Michael on 6/3/2014.
 */
public class Settings {
    // TODO continuous properties update to this file

    // OBJECT LOCATION / ORIENTATION
    // objectPosition : vec3f world coordinates
    // objectOrientation : vec3i degrees
    public static float[] objectPosition = {0f, -10f, 10f};
    public static int[] objectOrientation = {180, 0, 0};

    // CAMERA LOCATION / ORIENTATION
    // cameraPosition : vec3f world coordinates
    // cameraLook : vec3f world coordinates
    public static float[] cameraEye = { 0f, 0f, -10f };
    public static float[] cameraLookAt = { 0f, -10f, 10f };

    // FIELD OF VIEW
    // cameraFOV : float
    // cameraNear : float
    // cameraFar : float
    public static float cameraFOV = (float) (90 * Math.PI / 180);
    public static float cameraNear = cameraEye[2] + 5f;
    public static float cameraFar = cameraEye[2] + 20f;

    // AMBIENT LIGHT
    // lightAmbient : vec3i color
    public static int[] lightAmbient = { 115, 115, 50 };

    // LIGHT SOURCE
    // lightPosition : vec3f world coordinates
    // lightColor : vec3i color
    public static float[] lightPosition = { 5f, 5f, 5f };
    public static int[] lightColor = { 255, 0, 255 };
}
