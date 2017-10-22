package me.gcx11.survivalgame.scenes.ui;

import me.gcx11.survivalgame.scenes.Scene;

import java.awt.*;

/**
 * Created on 17.5.2017.
 */
public abstract class UIObject {

    protected Scene scene;

    /**
     * Creates new UIObject.
     *
     * @param scene scene
     */
    public UIObject(Scene scene){
        this.scene = scene;
    }

    /**
     * Updates UIObject.
     */
    public abstract void update();

    /**
     * Renders UIObject.
     *
     * @param graphics Graphics object
     * @see java.awt.Graphics
     */
    public abstract void render(Graphics graphics);
}
