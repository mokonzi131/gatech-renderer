package com.fsfive.renderer.engine;

import com.fsfive.renderer.system.FSResourceFactory;
import com.fsfive.renderer.system.IFSTime;

import java.util.logging.Logger;

/**
 * Created by Michael on 6/3/2014.
 */
public class Engine {
    private static final Logger LOGGER = Logger.getLogger(Engine.class.getName());
    private static final int DEFAULT_FRAMERATE = 30;

    private Scene m_currentScene;
    private boolean m_running;
    private IFSTime m_fstime;
    private int m_framerate;

    public Engine() {
        m_currentScene = null;
        m_running = false;
        m_fstime = FSResourceFactory.createIFSTime();
        m_framerate = DEFAULT_FRAMERATE;
    }

    public void setScene(Scene scene) {
        m_currentScene = scene;
    }

    public void setFramerate(int framerate) {
        m_framerate = framerate;
    }

    public void start() {
        if (!m_running) {
            m_running = true;
            run();
        }
    }

    public void stop() {
        m_running = false;
    }

    private void run() {
        final long TICK_PERIOD = m_fstime.ticksPerSecond() / m_framerate;

        // central game loop
        long lastTick = m_fstime.getSystemTicks();
        while (m_running) {
            // throttle running loop to FPS
            long elapsedTicks = m_fstime.getSystemTicks() - lastTick;
            while (elapsedTicks < TICK_PERIOD)
                elapsedTicks = m_fstime.getSystemTicks() - lastTick;
            lastTick += elapsedTicks;
            double deltaTime = (double)elapsedTicks / m_fstime.ticksPerSecond();

            // simple update scheme
            m_currentScene.update(deltaTime);
            m_currentScene.render();

            // finished?
            if (m_currentScene.finished())
                stop();
        }
    }
}
