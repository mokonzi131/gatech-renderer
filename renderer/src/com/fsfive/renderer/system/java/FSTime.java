package com.fsfive.renderer.system.java;

import com.fsfive.renderer.system.IFSTime;

import java.util.concurrent.TimeUnit;

/**
 * Created by Michael on 6/3/2014.
 */
public class FSTime implements IFSTime {

    @Override
    public long ticksPerSecond() {
        return TimeUnit.NANOSECONDS.convert(1L, TimeUnit.SECONDS);
    }

    @Override
    public long getSystemTicks() {
        return System.nanoTime();
    }
}
