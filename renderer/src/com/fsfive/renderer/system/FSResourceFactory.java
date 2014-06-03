package com.fsfive.renderer.system;

import com.fsfive.renderer.system.java.FSTime;

/**
 * Created by Michael on 6/3/2014.
 */
public class FSResourceFactory {
    public static IFSTime createIFSTime() {
        return new FSTime();
    }
}
