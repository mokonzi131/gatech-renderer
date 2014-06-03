package com.fsfive.renderer.render;

import com.fsfive.renderer.engine.Scene;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Michael on 6/3/2014.
 */
public class ObjectScene implements Scene {
    private static final Logger LOGGER = Logger.getLogger(ObjectScene.class.getName());

    private boolean m_finished;

    public ObjectScene() {
        m_finished = false;
    }

    @Override
    public void update(double deltaTime) {
        LOGGER.log(Level.INFO, "" + 1.0 / deltaTime + " FPS");
    }

    @Override
    public void render() {
        // TODO
        m_finished = true;
    }

    @Override
    public boolean finished() {
        return m_finished;
    }
}
