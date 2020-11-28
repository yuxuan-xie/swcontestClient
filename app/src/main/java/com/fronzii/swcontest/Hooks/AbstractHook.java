package com.fronzii.swcontest.Hooks;

public abstract class AbstractHook {
    public ClassLoader classloader;
    public Class<?> hookclass;

    public abstract void doHook();
    public abstract void setClassLoader(ClassLoader classLoader);
}
