package com.fsfive.renderer.engine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Michael on 6/3/2014.
 */
public class DisplayComponent extends JComponent {
    private static final long serialVersionUID = 1L;

    private Dimension m_dimension;
    //	private GraphicsConfiguration m_graphicsConfiguration; // for initializing artwork TODO
    private BufferedImage m_image;

    public DisplayComponent(int width, int height, InputMap input) {
        m_dimension = new Dimension(width, height);

        if (input != null)
            this.addKeyListener(input);
        this.setEnabled(input != null);
        this.setFocusable(input != null);
        this.setPreferredSize(m_dimension);
        this.setMinimumSize(m_dimension);
        this.setMaximumSize(m_dimension);
        this.setDoubleBuffered(true);
    }

    public BufferedImage getContext() {
        // draw previous image to screen // TODO double buffer this later...
        this.getGraphics().drawImage(m_image, 0, 0, null);

        // get and clear graphics from the image
        return m_image;
    }

    public Dimension getPreferredSize() {
        return m_dimension;
    }

    public void initialize() {
        m_image = new BufferedImage(m_dimension.width, m_dimension.height, BufferedImage.TYPE_INT_ARGB);
    }
}