package com.fdu.swcontest.Hooks;

import android.content.Context;
import android.net.Uri;

public abstract class AbstractHook {
    ClassLoader classloader;
    Class<?> hookclass;
    Context hookContext;
    Uri uri_test = Uri.parse("content://com.fdu.swcontentprovider/test");
    Uri uri_sequence = Uri.parse("content://com.fdu.swcontentprovider/api");

    public abstract void doHook();
    public abstract void setClassLoader(ClassLoader classLoader);
    public abstract void setContext(Context context);
}
