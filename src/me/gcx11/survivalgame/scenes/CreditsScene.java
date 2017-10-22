package me.gcx11.survivalgame.scenes;

import me.gcx11.survivalgame.GamePanel;
import me.gcx11.survivalgame.scenes.ui.Button;
import me.gcx11.survivalgame.scenes.ui.Label;

import java.awt.*;

/**
 * Created on 18.5.2017.
 */
public class CreditsScene extends UIScene {

    /**
     * Creates new credits scene with some info about used assets.
     *
     * @param gamePanel game panel
     */
    public CreditsScene(GamePanel gamePanel) {
        super(gamePanel);
    }

    @Override
    public void render(Graphics graphics) {
        super.render(graphics);
    }

    @Override
    public void load() {
        uiObjects.add(new Label(this, 0.5f, 0.0f, "Game assest origin"));
        uiObjects.add(new Label(this, 0.5f, 0.2f, "OpenGameArt - ArMM1998"));
        uiObjects.add(new Label(this, 0.5f, 0.4f, "OpenGameArt - Lanea Zimmerman"));
        uiObjects.add(new Label(this, 0.5f, 0.6f, "OpenGameArt - @JoeCreates"));
        uiObjects.add(new Label(this, 0.5f, 0.8f, "DevianArt - Orteil"));
        uiObjects.add(new Button(this, 0.5f, 1.0f, "Back to menu") {
            @Override
            public void onClick() {
                gamePanel.changeScene(new MenuScene(gamePanel));
            }
        });
    }
}
