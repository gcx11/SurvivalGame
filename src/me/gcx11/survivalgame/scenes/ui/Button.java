package me.gcx11.survivalgame.scenes.ui;

import me.gcx11.survivalgame.scenes.Scene;

import java.awt.*;

/**
 * Created on 17.5.2017.
 */
public abstract class Button extends TextObject {

    /**
     * Creates new Button object.
     *
     * @param scene scene
     * @param xRatio x relative position on scene from 0.0 to 1.0
     * @param yRatio y relative position on scene from 0.0 to 1.0
     * @param text text to display
     */
    public Button(Scene scene, float xRatio, float yRatio, String text) {
        super(scene, xRatio, yRatio, text);
    }

    /**
     * Action that is performed when user clicks on the button.
     */
    public abstract void onClick();

    @Override
    public void update() {
        if (scene.mouseInfo.wasLeftClicked()){
            int mouseX = scene.gamePanel.getMouseInfo().getX();
            int mouseY = scene.gamePanel.getMouseInfo().getY();
            if (x <= mouseX && mouseX <= x + textWidth+2*padding &&
                    y <= mouseY && mouseY <= y + textHeigth+2*padding){
                onClick();
            }
        }
    }

    @Override
    public void render(Graphics graphics) {
        super.render(graphics);
    }
}
