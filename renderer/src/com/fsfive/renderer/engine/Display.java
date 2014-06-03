package com.fsfive.renderer.engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;

/**
 * Created by Michael on 6/3/2014.
 */
public class Display {
    private int m_width;
    private int m_height;
    private DisplayComponent m_component;
    private JFrame m_frame;

    public Display(int width, int height) {
        m_width = width;
        m_height = height;
        m_component = null;
    }

    public BufferedImage getContext() {
        return m_component.getContext();
    }

    public void setCloseListener(WindowListener listener) {
        m_frame.addWindowListener(listener);
    }

    public void initialize(InputMap input, String title) {
        m_component = new DisplayComponent(m_width, m_height, input);
        m_component.initialize();

        m_frame = new JFrame(title);
        m_frame.setContentPane(m_component);
        m_frame.setResizable(true);
        m_frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        m_frame.pack();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        m_frame.setLocation((screenSize.width - m_frame.getWidth())/2, (screenSize.height - m_frame.getHeight())/2);
        m_frame.setVisible(true);
    }
}
