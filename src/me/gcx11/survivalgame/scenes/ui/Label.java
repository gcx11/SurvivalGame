package me.gcx11.survivalgame.scenes.ui;

import me.gcx11.survivalgame.scenes.Scene;

/**
 * Created on 17.5.2017.
 */
public class Label extends TextObject {

    /**
     * Creates new Label.
     *
     * @param scene scene
     * @param xRatio x relative position on scene from 0.0 to 1.0
     * @param yRatio y relative position on scene from 0.0 to 1.0
     * @param text text to display
     */
    public Label(Scene scene, float xRatio, float yRatio, String text) {
        super(scene, xRatio, yRatio, text);
    }
}
