package com.slusarzparadowski.model;

import com.slusarzparadowski.placeholder.Placeholder;

/**
 * Created by Dominik on 2015-04-06.
 */
public interface IObserver {

    public void attachPlaceholder(Placeholder placeholder);
    public void detachPlaceholder(Placeholder placeholder);
    public void notification();

}
