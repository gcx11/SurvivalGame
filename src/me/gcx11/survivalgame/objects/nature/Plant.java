package me.gcx11.survivalgame.objects.nature;

import me.gcx11.survivalgame.scenes.GameScene;
import me.gcx11.survivalgame.api.GameObject;
import me.gcx11.survivalgame.api.rendering.Textures;

import java.awt.*;

/**
 * Created on 3.5.2017.
 */
public class Plant extends GameObject {
    public boolean isDisappearing;
    private float timeToDeath = 0.5f;

    //public int x, y, width, height;

    private Image texture = Textures.getTexture("plant");

    /**
     * Creates new plant game object.
     *
     * @param gameScene game scene
     * @param x x coordinate on the map
     * @param y y coordinate on the map
     */
    public Plant(GameScene gameScene, int x, int y) {
        super(gameScene);
        this.x = x;
        this.y = y;
        this.width = texture.getWidth(null);
        this.height = texture.getHeight(null);
        this.isDisappearing = false;
    }

    @Override
    public void update(float delta) {
        if (isDisappearing){
            timeToDeath -= delta;
        }
        if (timeToDeath < 0) gameScene.deleteLater(this);
        else if (timeToDeath < 0.1f) texture = Textures.getTexture("plant_destroy_4");
        else if (timeToDeath < 0.2f) texture = Textures.getTexture("plant_destroy_3");
        else if (timeToDeath < 0.3f) texture = Textures.getTexture("plant_destroy_2");
        else if (timeToDeath < 0.4f) texture = Textures.getTexture("plant_destroy");

    }

    @Override
    public void render(Graphics graphics) {
        graphics.drawImage(texture, gameScene.camera.recalculateX(x), gameScene.camera.recalculateY(y),
                texture.getWidth(null), texture.getHeight(null), null);
    }

    /**
     * Destroys flower.
     */
    public void destroy(){
        isDisappearing = true;
    }
}
