package me.gcx11.survivalgame.objects.inventories;

import me.gcx11.survivalgame.objects.items.Item;

/**
 * Created on 13.5.2017.
 */
public class LootProbability {

    public Item item;
    public double probability;

    /**
     * Loot and its probability pair.
     *
     * @param item loot
     * @param probability probability of getting loot
     */
    public LootProbability(Item item, double probability){
        this.item = item;
        this.probability = probability;
    }
}
