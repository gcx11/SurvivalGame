package me.gcx11.survivalgame.scenes;

import me.gcx11.survivalgame.GamePanel;
import me.gcx11.survivalgame.scenes.ui.Button;
import me.gcx11.survivalgame.scenes.ui.Label;

import java.awt.*;

/**
 * Created on 18.5.2017.
 */
public class DeathScene extends UIScene {

    /**
     * Creates death scene.
     *
     * @param gamePanel game panel
     */
    public DeathScene(GamePanel gamePanel) {
        super(gamePanel);
    }

    @Override
    public void render(Graphics graphics) {
        super.render(graphics);
    }

    @Override
    public void load() {
        uiObjects.add(new Label(this, 0.5f, 0.3f, "You have died!"));
        uiObjects.add(new Button(this, 0.5f, 0.7f, "Back to menu") {
            @Override
            public void onClick() {
                gamePanel.changeScene(new MenuScene(gamePanel));
            }
        });
    }
}
