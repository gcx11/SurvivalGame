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
public class Skeleton extends LootableEntity {

    /**
     * Creates new instance of skeleton on specified coordinates.
     *
     * @param gameScene game scene
     * @param x x coordinate of position on the map
     * @param y y coordinate of position on the map
     */
    public Skeleton(GameScene gameScene, int x, int y) {
        super(gameScene, x, y, newAnimation(), Textures.getTexture("dead_skeleton"),
                Arrays.asList(new LootProbability(new Item(ItemData.BONE), 1.0d),
                        new LootProbability(new Item(ItemData.FLOUR), 0.2d),
                        new LootProbability(new Item(ItemData.GOLD_ROCK), 0.5d),
                        new LootProbability(new Item(ItemData.IRON_ROCK), 0.5d)));
        this.attackStrenght = 2.0d;
    }

    /**
     * Creates new animation for skeleton.
     *
     * @return new entity animation for skeleton
     */
    private static EntityAnimation newAnimation() {
        return new EntityAnimation(new Image[][]{
                new Image[]{Textures.getTexture("skeleton_mob_1"), Textures.getTexture("skeleton_mob_2"),
                        Textures.getTexture("skeleton_mob_3")},
                new Image[]{Textures.getTexture("skeleton_mob_10"), Textures.getTexture("skeleton_mob_11"),
                        Textures.getTexture("skeleton_mob_12")},
                new Image[]{Textures.getTexture("skeleton_mob_4"), Textures.getTexture("skeleton_mob_5"),
                        Textures.getTexture("skeleton_mob_6")},
                new Image[]{Textures.getTexture("skeleton_mob_7"), Textures.getTexture("skeleton_mob_8"),
                        Textures.getTexture("skeleton_mob_9")},
        });
    }
}
