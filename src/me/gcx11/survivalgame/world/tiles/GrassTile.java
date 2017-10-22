package me.gcx11.survivalgame.world.tiles;


import me.gcx11.survivalgame.api.GameObject;
import me.gcx11.survivalgame.scenes.GameScene;
import me.gcx11.survivalgame.api.rendering.Textures;

import java.awt.*;
import java.util.Random;

/**
 * Created on 29.3.2017.
 */
public class GrassTile extends GameObject {

    private static Random rnd = new Random();

    private static Image[] images = {Textures.getTexture("grass_tiny"), Textures.getTexture("grass_tiny_2"),
            Textures.getTexture("grass_tiny_3"), Textures.getTexture("grass_tiny_4"),
            Textures.getTexture("grass_tiny_5")};
    private Image image;// = Textures.getTexture("grass_tiny");

    /**
     * Creates new grass tile.
     *
     * @param gameScene game scene
     * @param x x coordinate on the map
     * @param y y coordinate on the map
     */
    public GrassTile(GameScene gameScene, int x, int y){
        super(gameScene);
        this.x = x;
        this.y = y;
        this.image = images[rnd.nextInt(images.length)];
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(Graphics graphics) {
        graphics.drawImage(image, gameScene.camera.recalculateX(x), gameScene.camera.recalculateY(y), null);
    }
}
