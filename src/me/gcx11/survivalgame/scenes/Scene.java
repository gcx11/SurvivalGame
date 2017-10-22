package me.gcx11.survivalgame.scenes;

import me.gcx11.survivalgame.GamePanel;
import me.gcx11.survivalgame.api.input.KeyboardListener;
import me.gcx11.survivalgame.api.input.MouseInfo;

import java.awt.*;

/**
 * Created on 16.5.2017.
 */
public abstract class Scene {

    public GamePanel gamePanel;
    public KeyboardListener keyboardListener;
    public MouseInfo mouseInfo;

    /**
     * Creates new Scene.
     *
     * @param gamePanel game panel
     */
    public Scene(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        this.keyboardListener = gamePanel.getKeyboardListener();
        this.mouseInfo = gamePanel.getMouseInfo();
    }

    /**
     * Updates scene.
     *
     * @param delta time in seconds that passed since last game update
     */
    public abstract void update(float delta);

    /**
     * Renders scene.
     *
     * @param graphics Graphics object
     * @see java.awt.Graphics
     */
    public abstract void render(Graphics graphics);

    /**
     * Changes current scene.
     *
     * @param newScene new scene to be displayed
     */
    protected void changeScene(Scene newScene){
        gamePanel.changeScene(newScene);
    }

    /**
     * Loads content of new scene.
     */
    public abstract void load();
}
