package me.gcx11.survivalgame.scenes;

import me.gcx11.survivalgame.GamePanel;
import me.gcx11.survivalgame.api.Coord;
import me.gcx11.survivalgame.api.rendering.Textures;
import me.gcx11.survivalgame.scenes.ui.UIObject;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created on 18.5.2017.
 */
public class UIScene extends Scene {

    protected List<UIObject> uiObjects = new ArrayList<>();

    /**
     * Creates new UIScene.
     *
     * @param gamePanel game panel
     */
    public UIScene(GamePanel gamePanel) {
        super(gamePanel);
    }

    @Override
    public final void update(float delta) {
        uiObjects.forEach(UIObject::update);
    }

    @Override
    public void render(Graphics graphics) {
        graphics.fillRect(0, 0, gamePanel.getWidth(), gamePanel.getHeight());
        Image grasstexture = Textures.getTexture("grass_tiny");
        for (int y = 0; y <= gamePanel.getHeight()/grasstexture.getHeight(null); y++){
            for (int x = 0; x <= gamePanel.getWidth()/grasstexture.getWidth(null); x++){
                graphics.drawImage(grasstexture, x*grasstexture.getWidth(null), y*grasstexture.getHeight(null), null);
            }
        }
        uiObjects.forEach(uiObject -> uiObject.render(graphics));
    }

    @Override
    public void load() {

    }
}
