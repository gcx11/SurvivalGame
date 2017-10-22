package me.gcx11.survivalgame.objects.nature;

import me.gcx11.survivalgame.scenes.GameScene;
import me.gcx11.survivalgame.api.GameObject;
import me.gcx11.survivalgame.api.rendering.Textures;

import java.awt.*;
import java.util.Random;

/**
 * Created on 11.5.2017.
 */
public class Bones extends GameObject {

    private Random rnd = new Random();

    private static Image[] textures = {Textures.getTexture("bones"), Textures.getTexture("bones_2"),
            Textures.getTexture("bones_3"), Textures.getTexture("bones_4"), Textures.getTexture("bones_5"),
            Textures.getTexture("bones_6"), Textures.getTexture("bones_7")};

    private Image texture = textures[rnd.nextInt(textures.length)];

    /**
     * Creates new bones game object.
     *
     * @param gameScene game scene
     * @param x x coordinate on the map
     * @param y y coordinate on the map
     */
    public Bones(GameScene gameScene, int x, int y) {
        super(gameScene);
        this.x = x;
        this.y = y;
        this.width = texture.getWidth(null);
        this.height = texture.getHeight(null);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(Graphics graphics) {
        graphics.drawImage(texture, gameScene.camera.recalculateX(x), gameScene.camera.recalculateY(y), null);
    }
}
