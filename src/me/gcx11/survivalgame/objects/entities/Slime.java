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
 * Created on 8.5.2017.
 */
public class Slime extends LootableEntity {

    /**
     * Creates new instance of slime on specified coordinates.
     *
     * @param gameScene game scene
     * @param x x coordinate of position on the map
     * @param y y coordinate of position on the map
     */
    public Slime(GameScene gameScene, int x, int y){
        super(gameScene, x, y, newAnimation(), Textures.getTexture("dead_slime"),
                Arrays.asList(new LootProbability(new Item(ItemData.BERRIES), 0.5d),
                            new LootProbability(new Item(ItemData.CHERRIES), 0.5d),
                            new LootProbability(new Item(ItemData.PORK), 0.5d),
                            new LootProbability(new Item(ItemData.CARROT), 0.5d)
                        ));

        this.baseMaxHealth = 20.0d;
    }

    /**
     * Creates new animation for slime.
     *
     * @return new entity animation for slime
     */
    private static EntityAnimation newAnimation() {
        return new EntityAnimation(new Image[][]{
                new Image[]{Textures.getTexture("slime_mob_1"), Textures.getTexture("slime_mob_2"),
                        Textures.getTexture("slime_mob_3")},
                new Image[]{Textures.getTexture("slime_mob_10"), Textures.getTexture("slime_mob_11"),
                        Textures.getTexture("slime_mob_12")},
                new Image[]{Textures.getTexture("slime_mob_4"), Textures.getTexture("slime_mob_5"),
                        Textures.getTexture("slime_mob_6")},
                new Image[]{Textures.getTexture("slime_mob_7"), Textures.getTexture("slime_mob_8"),
                        Textures.getTexture("slime_mob_9")},
        });
    }


}
