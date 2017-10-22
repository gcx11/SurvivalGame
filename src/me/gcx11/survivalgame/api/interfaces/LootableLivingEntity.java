package me.gcx11.survivalgame.api.interfaces;

import me.gcx11.survivalgame.objects.inventories.LootProbability;

import java.util.List;

/**
 * Created on 11.9.2017.
 */
public interface LootableLivingEntity extends LivingEntity {

    /**
     * Gets loot table items with probabilities.
     * @return Loot table
     */
    List<LootProbability> getLootTable();

    /**
     * Sets loot table items with probabilities.
     */
    void setLootTable(List<LootProbability> lootTable);

    /**
     * Gets entity level.
     * @return entity level
     */
    int getLevel();

    /**
     * Sets entity level.
     * @param level new level
     */
    void setLevel(int level);
}
