package me.gcx11.survivalgame.objects.entities;

import me.gcx11.survivalgame.scenes.GameScene;
import me.gcx11.survivalgame.api.animations.EntityAnimation;
import me.gcx11.survivalgame.api.rendering.Textures;
import me.gcx11.survivalgame.objects.items.Item;
import me.gcx11.survivalgame.objects.items.ItemData;
import me.gcx11.survivalgame.objects.inventories.LootProbability;

import java.awt.*;
import java.util.Arrays;

/**
 * Created on 10.5.2017.
 */
public class Spider extends LootableEntity {

    /**
     * Creates new instance of spider on specified coordinates.
     *
     * @param gameScene game scene
     * @param x x coordinate of position on the map
     * @param y y coordinate of position on the map
     */
    public Spider(GameScene gameScene, int x, int y) {
        super(gameScene, x, y, newAnimation(), Textures.getTexture("dead_spider"),
                Arrays.asList(new LootProbability(new Item(ItemData.ROTTEN_MEAT), 1d),
                        new LootProbability(new Item(ItemData.EGG), 0.2d)));
        this.baseMaxHealth = 5.0d;
    }

    /**
     * Creates new animation for spider.
     *
     * @return new entity animation for spider
     */
    private static EntityAnimation newAnimation() {
        return new EntityAnimation(new Image[][]{
                new Image[]{Textures.getTexture("spider_mob_1"), Textures.getTexture("spider_mob_2"),
                        Textures.getTexture("spider_mob_3")},
                new Image[]{Textures.getTexture("spider_mob_10"), Textures.getTexture("spider_mob_11"),
                        Textures.getTexture("spider_mob_12")},
                new Image[]{Textures.getTexture("spider_mob_4"), Textures.getTexture("spider_mob_5"),
                        Textures.getTexture("spider_mob_6")},
                new Image[]{Textures.getTexture("spider_mob_7"), Textures.getTexture("spider_mob_8"),
                        Textures.getTexture("spider_mob_9")},
        });
    }

    @Override
    public int getCollidableX() {
        return x + 2;
    }

    @Override
    public int getCollidableY() {
        return y;
    }

    @Override
    public int getCollidableWidth() {
        return width - 4;
    }

    @Override
    public int getCollidableHeight() {
        return height + 1;
    }
}
