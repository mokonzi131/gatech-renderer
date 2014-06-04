package com.fsfive.renderer.system.java;

import com.fsfive.renderer.demo.Renderer;

/**
 * Created by Michael on 6/2/2014.
 */
public class JavaApplicationMain {
    public static void main(String[] args) {
        Renderer renderer = new Renderer();
        new Thread(renderer, "Renderer").start();
    }
}
