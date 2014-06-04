package com.fsfive.renderer.render;

/**
 * Created by Michael on 6/3/2014.
 */
public class Settings {
    // TODO continuous properties update to this file

    // OBJECT LOCATION / ORIENTATION
    // objectPosition : vec3f world coordinates
    // objectOrientation : vec3i degrees
    public static float[] objectPosition = { 1f, 2f, 3f };
    public static int[] objectOrientation = {90, 180, 270 };

    // CAMERA LOCATION / ORIENTATION
    // cameraPosition : vec3f world coordinates
    // cameraLook : vec3f world coordinates
    public static float[] cameraPosition = { 10f, 20f, 30f };
    public static float[] cameraLook = { 2f, 2f, 2f };

    // FIELD OF VIEW
    // cameraFOV : ???
    // cameraNear : ???
    // cameraFar : ???
    public static float cameraFOV = -1f;
    public static float cameraNear = -1f;
    public static float cameraFar = -1f;

    // AMBIENT LIGHT
    // lightAmbient : vec3i color
    public static int[] lightAmbient = { 115, 115, 50 };

    // LIGHT SOURCE
    // lightPosition : vec3f world coordinates
    // lightColor : vec3i color
    public static float[] lightPosition = { 5f, 5f, 5f };
    public static int[] lightColor = { 255, 0, 255 };
}
