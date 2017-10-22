package me.gcx11.survivalgame.world.tiles;

import me.gcx11.survivalgame.api.GameObject;
import me.gcx11.survivalgame.scenes.GameScene;
import me.gcx11.survivalgame.api.rendering.Textures;

import java.awt.*;

/**
 * Created on 27.4.2017.
 */
public class RockyTile extends GameObject {

    private static Image image = Textures.getTexture("dirt");

    /**
     * Creates new rocky tile.
     *
     * @param gameScene game scene
     * @param x x coordinate on the map
     * @param y y coordinate on the map
     */
    public RockyTile(GameScene gameScene, int x, int y){
        super(gameScene);
        this.x = x;
        this.y = y;
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(Graphics graphics) {
        graphics.drawImage(image, gameScene.camera.recalculateX(x), gameScene.camera.recalculateY(y), null);
    }
}
