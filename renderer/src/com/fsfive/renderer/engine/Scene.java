package com.fsfive.renderer.engine;

/**
 * Created by Michael on 6/3/2014.
 */
public interface Scene {
    public void update(double deltaTime);

    public void render();

    public boolean finished();
}
