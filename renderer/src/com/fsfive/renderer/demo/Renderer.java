package com.fsfive.renderer.demo;

import com.fsfive.renderer.engine.Engine;
import com.fsfive.renderer.engine.Game;

import java.util.logging.Logger;

/**
 * Created by Michael on 6/3/2014.
 */
public class Renderer extends Game {
    private static final Logger LOGGER = Logger.getLogger(Game.class.getName());

    private Engine m_engine;

    public Renderer() {
        m_engine = new Engine();
        m_engine.setFramerate(Constants.FRAMERATE);
    }

    @Override
    public void run() {
        m_engine.setScene(new RenderScene());
        m_engine.start();

        System.exit(0);
    }
}
