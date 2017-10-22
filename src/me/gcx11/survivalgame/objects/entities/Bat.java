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
 * Created on 12.5.2017.
 */
public class Bat extends LootableEntity {

    /**
     * Creates new instance of bat on specified coordinates.
     *
     * @param gameScene game scene
     * @param x x coordinate of position on the map
     * @param y y coordinate of position on the map
     */
    public Bat(GameScene gameScene, int x, int y) {
        super(gameScene, x, y, newAnimation(), Textures.getTexture("dead_bat"),
                Arrays.asList(new LootProbability(new Item(ItemData.ROTTEN_MEAT), 1.0d)));
        this.maxFollowDistance = 50.0d;
    }

    /**
     * Creates new animation for bat.
     *
     * @return new entity animation for bat
     */
    private static EntityAnimation newAnimation() {
        return new EntityAnimation(new Image[][]{
                new Image[]{Textures.getTexture("bat_mob_1"), Textures.getTexture("bat_mob_2"),
                        Textures.getTexture("bat_mob_3")},
                new Image[]{Textures.getTexture("bat_mob_10"), Textures.getTexture("bat_mob_11"),
                        Textures.getTexture("bat_mob_12")},
                new Image[]{Textures.getTexture("bat_mob_4"), Textures.getTexture("bat_mob_5"),
                        Textures.getTexture("bat_mob_6")},
                new Image[]{Textures.getTexture("bat_mob_7"), Textures.getTexture("bat_mob_8"),
                        Textures.getTexture("bat_mob_9")},
        });
    }
}
