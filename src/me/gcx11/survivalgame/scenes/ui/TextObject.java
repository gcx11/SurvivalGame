package me.gcx11.survivalgame.scenes.ui;

import me.gcx11.survivalgame.scenes.Scene;

import java.awt.*;

/**
 * Created on 18.5.2017.
 */
public abstract class TextObject extends UIObject {

    protected int x, y;
    protected int textWidth, textHeigth;
    protected int padding = 2;
    protected String text;
    protected static Font buttonFont = new Font("COMIC SANS", Font.PLAIN, 30);
    protected Color backgroundColor = new Color(209, 187, 57);
    protected Color textColor = Color.GRAY;
    protected float xRatio, yRatio;

    /**
     * Creates new TextObject.
     *
     * @param scene scene
     * @param xRatio x relative position on scene from 0.0 to 1.0
     * @param yRatio y relative position on scene from 0.0 to 1.0
     * @param text text to display on UIObject
     */
    public TextObject(Scene scene, float xRatio, float yRatio, String text) {
        super(scene);
        this.xRatio = xRatio;
        this.yRatio = yRatio;
        this.text = text;
        FontMetrics fontMetrics = scene.gamePanel.getFontMetrics(buttonFont);
        this.textWidth = fontMetrics.stringWidth(text);
        this.textHeigth = fontMetrics.getHeight();
        updatePosition();
    }

    @Override
    public void update() {
        
    }

    @Override
    public void render(Graphics graphics) {
        Font oldFont = graphics.getFont();
        graphics.setColor(backgroundColor);
        graphics.setFont(buttonFont);
        graphics.fillRect(x, y, textWidth + 2 * padding, textHeigth + 2 * padding);
        graphics.setColor(textColor);
        graphics.drawString(text, x+padding, y+textHeigth-2*padding);
        graphics.setFont(oldFont);
        updatePosition();
    }

    /**
     * Updates position on the screen.
     */
    protected void updatePosition(){
        this.x = (int) (xRatio * (scene.gamePanel.getWidth() - textWidth));
        this.y = (int) (yRatio * (scene.gamePanel.getHeight() - textHeigth));
    }
}
