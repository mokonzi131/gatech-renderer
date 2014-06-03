package com.fsfive.renderer.engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Michael on 6/3/2014.
 */
public class InputMap implements KeyListener {
    //	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
    public static final int KEY_LEFT = 0;
    public static final int KEY_RIGHT = 1;
    public static final int KEY_UP = 2;
    public static final int KEY_DOWN = 3;
    public static final int KEY_ACTION = 4;
    private static final int NUM_KEYS = 5;

    private boolean[] m_keys;
    private int m_lastKey;

    public InputMap() {
        m_keys = new boolean[NUM_KEYS];
        for (int i = 0; i < NUM_KEYS; ++i)
            m_keys[i] = false;
        m_lastKey = KEY_ACTION;
    }

    public boolean[] getKeys() {
        return m_keys;
    }

    public int getLastKey() {
        int lastKey = m_lastKey;
        m_lastKey = KEY_ACTION;
        return lastKey;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Ignore...
    }

    @Override
    public void keyPressed(KeyEvent e) {
        toggleKey(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        toggleKey(e.getKeyCode(), false);
    }

    private void toggleKey(int code, boolean isPressed) {
        switch(code) {
            case KeyEvent.VK_LEFT:
                m_keys[KEY_LEFT] = isPressed;
                m_lastKey = KEY_LEFT;
                break;
            case KeyEvent.VK_RIGHT:
                m_keys[KEY_RIGHT] = isPressed;
                m_lastKey = KEY_RIGHT;
                break;
            case KeyEvent.VK_UP:
                m_keys[KEY_UP] = isPressed;
                m_lastKey = KEY_UP;
                break;
            case KeyEvent.VK_DOWN:
                m_keys[KEY_DOWN] = isPressed;
                m_lastKey = KEY_DOWN;
                break;
            case KeyEvent.VK_SPACE:
                m_keys[KEY_ACTION] = isPressed;
                m_lastKey = KEY_ACTION;
                break;
        }
    }
}
