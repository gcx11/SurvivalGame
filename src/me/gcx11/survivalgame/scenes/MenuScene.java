package me.gcx11.survivalgame.scenes;

import me.gcx11.survivalgame.GamePanel;
import me.gcx11.survivalgame.api.rendering.Camera;
import me.gcx11.survivalgame.api.rendering.Textures;
import me.gcx11.survivalgame.settings.GameMode;
import me.gcx11.survivalgame.scenes.ui.Button;

import java.awt.*;

/**
 * Created on 17.5.2017.
 */
public class MenuScene extends UIScene{

    /**
     * Creates new MenuScene.
     *
     * @param gamePanel game panel
     */
    public MenuScene(GamePanel gamePanel) {
        super(gamePanel);
    }

    @Override
    public void render(Graphics graphics) {
        super.render(graphics);
    }

    @Override
    public void load() {
        uiObjects.add(new Button(this, 0.5f, 0.2f, "Easy Mode") {
            @Override
            public void onClick() {
                changeScene(new GameScene(gamePanel, new Camera(gamePanel.getWidth(), gamePanel.getHeight()), GameMode.EASY));
            }
        });
        uiObjects.add(new Button(this, 0.5f, 0.5f, "Hardcore Mode") {
            @Override
            public void onClick() {
                changeScene(new GameScene(gamePanel, new Camera(gamePanel.getWidth(), gamePanel.getHeight()), GameMode.HARDCORE));
            }
        });
        uiObjects.add(new Button(this, 0.5f, 0.8f, "Credits") {
            @Override
            public void onClick() {
                changeScene(new CreditsScene(gamePanel));
            }
        });
    }
}
