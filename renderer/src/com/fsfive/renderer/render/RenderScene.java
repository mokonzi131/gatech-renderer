package com.fsfive.renderer.render;

import com.fsfive.renderer.engine.Display;
import com.fsfive.renderer.engine.Scene;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Michael on 6/3/2014.
 */
public class RenderScene implements Scene, WindowListener {
    private static final Logger LOGGER = Logger.getLogger(RenderScene.class.getName());

    private boolean m_finished;
    private Display m_masterDisplay;

    public RenderScene() {
        m_finished = false;

        m_masterDisplay = new Display(800, 600);
        m_masterDisplay.initialize(null, "Michael Landes");
        m_masterDisplay.setCloseListener(this);
    }

    @Override
    public void update(double deltaTime) {
        LOGGER.log(Level.INFO, "" + 1.0 / deltaTime + " FPS");
    }

    @Override
    public void render() {
        BufferedImage image = m_masterDisplay.getContext();
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());

        int[] xp = {0, 50, 150};
        int[] yp = {0, 150, 50};
        graphics.setColor(Color.RED);
        graphics.drawPolygon(xp, yp, 3);
    }

    @Override
    public boolean finished() {
        return m_finished;
    }

    @Override public void windowOpened(WindowEvent e) {}
    @Override public void windowClosing(WindowEvent e) { m_finished = true; }
    @Override public void windowClosed(WindowEvent e) {}
    @Override public void windowIconified(WindowEvent e) {}
    @Override public void windowDeiconified(WindowEvent e) {}
    @Override public void windowActivated(WindowEvent e) {}
    @Override public void windowDeactivated(WindowEvent e) {}
}
