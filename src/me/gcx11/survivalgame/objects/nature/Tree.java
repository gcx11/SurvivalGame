package me.gcx11.survivalgame.objects.nature;

import me.gcx11.survivalgame.api.interfaces.Collidable;
import me.gcx11.survivalgame.api.GameObject;
import me.gcx11.survivalgame.scenes.GameScene;
import me.gcx11.survivalgame.api.rendering.Textures;

import java.awt.*;

/**
 * Created on 29.3.2017.
 */
public class Tree extends GameObject implements Collidable {

    private Image texture = Textures.getTexture("tree_green");
    private final float initialDurability = 10.0f;
    private float durability = initialDurability;

    public boolean wasHarvested = false;

    /**
     * Creates new tree game object.
     *
     * @param gameScene game scene
     * @param x x coordinate on the map
     * @param y y coordinate on the map
     */
    public Tree(GameScene gameScene, int x, int y){
        super(gameScene);
        this.x = x;
        this.y = y;
        this.width = texture.getWidth(null);
        this.height = texture.getHeight(null);
    }

    @Override
    public void update(float delta) {
        if (wasHarvested){
            durability += delta/10f;
            if (durability > initialDurability){
                durability = initialDurability;
                wasHarvested = false;
                texture = Textures.getTexture("tree_green");
            }
            else if (durability > initialDurability - delta){
                texture = Textures.getTexture("tree_15");
            }
            else if (durability > initialDurability - 2*delta){
                texture = Textures.getTexture("tree_14");
            }
            else if (durability > initialDurability - 3*delta){
                texture = Textures.getTexture("tree_13");
            }
            else if (durability > initialDurability - 4*delta){
                texture = Textures.getTexture("tree_12");
            }
            else if (durability > initialDurability/2f){
                texture = Textures.getTexture("tree_10");
            }
            else if (durability > initialDurability/4f){
                texture = Textures.getTexture("tree_6");
            }
            else if (durability > initialDurability/8f){
                texture = Textures.getTexture("tree_3");
            }
        }
    }

    /**
     * Damages the tree.
     *
     * @param delta time in seconds that passed since last game update
     */
    public void damage(float delta){
        if (durability > 0) durability -= 200*delta;
    }

    /**
     * Checks whether is tree destroyed.
     *
     * @return whether is tree destroyed
     */
    public boolean isDestroyed(){
        return durability <= 0;
    }

    /**
     * Harvests tree.
     */
    public void harvest(){
        texture = Textures.getTexture("trunk");
        wasHarvested = true;
    }

    @Override
    public void render(Graphics graphics) {
        graphics.drawImage(texture, gameScene.camera.recalculateX(x), gameScene.camera.recalculateY(y), null);
    }

    @Override
    public int getCollidableX() {
        return x + 3;
    }

    @Override
    public int getCollidableY() {
        return y + 3;
    }

    @Override
    public int getCollidableWidth() {
        return width - 6;
    }

    @Override
    public int getCollidableHeight() {
        return height - 10;
    }
}