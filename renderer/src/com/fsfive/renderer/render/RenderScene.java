package com.fsfive.renderer.render;

import com.fsfive.renderer.engine.Display;
import com.fsfive.renderer.engine.Scene;
import com.fsfive.renderer.graphics.Mesh;
import com.fsfive.renderer.graphics.Pipeline;
import com.fsfive.renderer.graphics.Vec4f;
import com.fsfive.renderer.util.RawObjectLoader;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Michael on 6/3/2014.
 */
public class RenderScene implements Scene, WindowListener {
    private static final Logger LOGGER = Logger.getLogger(RenderScene.class.getName());

    private boolean m_finished;
    private Display m_masterDisplay;
    private Pipeline m_pipeline;
    private RenderObject m_renderObject;
//    private Properties m_renderSettings;

    public RenderScene() {
        m_finished = false;

        // setup the window and display context
        m_masterDisplay = new Display(800, 600);
        m_masterDisplay.initialize(null, "Michael Landes");
        m_masterDisplay.setCloseListener(this);

        // setup the rendering pipeline
        m_pipeline = new Pipeline();
//        m_renderSettings = ResourceLoader.getProperties("/renderer.properties");

        // create the object we are rendering
        List<float[]> meshData = RawObjectLoader.loadRawFile("/shuttle_breneman_whitfield.raw");
//        List<float[]> meshData = RawObjectLoader.loadRawFile("/cessna_ben_perry.raw");
        Mesh mesh = Mesh.createRenderObjectFromVerticesList(meshData);
        m_renderObject = new RenderObject();
        m_renderObject.attachMesh(mesh);
        m_renderObject.setLocation(
                Settings.objectPosition[0],
                Settings.objectPosition[1],
                Settings.objectPosition[2]);
        m_renderObject.setSize(5f, 5f, 5f);
    }

    @Override
    public void update(double deltaTime) {
        LOGGER.log(Level.INFO, "" + 1.0 / deltaTime + " FPS");

        // update world objects
        m_renderObject.update(deltaTime);

//        Properties settings = ResourceLoader.getProperties("/renderer.properties");
//        if (settings != null)
//            m_renderSettings = settings;
    }

    @Override
    public void render() {
        BufferedImage image = m_masterDisplay.getContext();
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(new Color(
                Settings.lightAmbient[0],
                Settings.lightAmbient[1],
                Settings.lightAmbient[2]));
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());

        m_renderObject.render(graphics, m_pipeline);
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
