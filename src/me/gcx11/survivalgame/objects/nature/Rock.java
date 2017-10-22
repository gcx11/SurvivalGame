package me.gcx11.survivalgame.objects.nature;

import me.gcx11.survivalgame.api.interfaces.Collidable;
import me.gcx11.survivalgame.api.GameObject;
import me.gcx11.survivalgame.scenes.GameScene;
import me.gcx11.survivalgame.api.rendering.Textures;

import java.awt.*;

/**
 * Created on 27.4.2017.
 */
public class Rock extends GameObject implements Collidable {

    private float durability = 5.0f;
    private Image texture = Textures.getTexture("rock");

    /**
     * Creates new rock game object.
     *
     * @param gameScene
     * @param x
     * @param y
     */
    public Rock(GameScene gameScene, int x, int y) {
        super(gameScene);
        this.x = x;
        this.y = y;
        this.width = texture.getWidth(null);
        this.height = texture.getHeight(null);
    }

    @Override
    public int getCollidableX() {
        return x + 4;
    }

    @Override
    public int getCollidableY() {
        return y + 6;
    }

    @Override
    public int getCollidableWidth() {
        return width - 8;
    }

    @Override
    public int getCollidableHeight() {
        return height - 12;
    }

    @Override
    public void update(float delta) {

    }

    /**
     * Damages rock.
     *
     * @param delta time in seconds that passed since last game update
     */
    public void damage(float delta){
        //System.out.println("Damaged: " + durability);
        durability -= 20*delta;
        if (durability < 2.0f) texture = Textures.getTexture("rock_damaged_3");
        else if (durability < 3.0f) texture = Textures.getTexture("rock_damaged_2");
        else if (durability < 4.0f) texture = Textures.getTexture("rock_damaged");
    }

    /**
     * Checks whether is rock destroyed.
     *
     * @return whether is rock destroyed
     */
    public boolean isDestroyed(){
        return durability < 0;
    }

    @Override
    public void render(Graphics graphics) {
        //graphics.setColor(Color.RED);
        graphics.drawImage(texture, gameScene.camera.recalculateX(x), gameScene.camera.recalculateY(y), null);
    }
}
