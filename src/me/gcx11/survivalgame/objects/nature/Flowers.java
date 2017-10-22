package me.gcx11.survivalgame.objects.nature;

import me.gcx11.survivalgame.api.GameObject;
import me.gcx11.survivalgame.scenes.GameScene;
import me.gcx11.survivalgame.api.rendering.Textures;

import java.awt.*;
import java.util.Random;

/**
 * Created on 29.3.2017.
 */
public class Flowers extends GameObject {

    private static String[] names = {"flowers", "flowers_2", "flowers_3", "flowers_4"};
    private static Random rnd = new Random();
    private Image texture;

    /**
     * Creates new flowers game object.
     *
     * @param gameScene game scene
     * @param x x coordinate on the map
     * @param y y coordinate on the map
     */
    public Flowers(GameScene gameScene, int x, int y) {
        super(gameScene);
        this.x = x;
        this.y = y;
        this.texture = Textures.getTexture(names[rnd.nextInt(names.length)]);
        this.width = texture.getWidth(null);
        this.height = texture.getHeight(null);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(Graphics graphics) {
        //graphics.setColor(Color.RED);
        graphics.drawImage(texture, gameScene.camera.recalculateX(x), gameScene.camera.recalculateY(y), null);
        //graphics.drawRect(scene.camera.recalculateX(x), scene.camera.recalculateY(y), width, height);
    }
}
