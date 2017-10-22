package me.gcx11.survivalgame.world.tiles;

import me.gcx11.survivalgame.api.GameObject;
import me.gcx11.survivalgame.scenes.GameScene;
import me.gcx11.survivalgame.api.rendering.Textures;

import java.awt.*;

public class DesertTile extends GameObject {

    private Image image;

    /**
     * Creates new desert tile.
     *
     * @param gameScene game scene
     * @param x x coordinate on the map
     * @param y y coordinate on the map
     * @param textureName texture name
     */
    public DesertTile(GameScene gameScene, int x, int y, String textureName){
        super(gameScene);
        this.x = x;
        this.y = y;
        this.image = Textures.getTexture(textureName);
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
