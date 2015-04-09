package com.slusarzparadowski.model;

import android.content.Context;

import java.io.IOException;

/**
 * Created by Dominik on 2015-04-08.
 */
public interface IFile {

    public void saveToFile(Context context) throws IOException;
    public boolean loadFromFile(Context context);
}
