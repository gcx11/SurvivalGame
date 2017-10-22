package me.gcx11.survivalgame.api.events;

import me.gcx11.survivalgame.api.interfaces.LootableLivingEntity;

/**
 * Created on 10.9.2017.
 */
public class LootableEntitySpawnEvent extends Event {

    private LootableLivingEntity entity;

    /**
     * Event called on lootable entity spawn
     * @param entity spawned entity
     */
    public LootableEntitySpawnEvent(LootableLivingEntity entity){
        this.entity = entity;
    }

    /**
     * Gets lootable entity.
     * @return lootable entity
     */
    public LootableLivingEntity getEntity() {
        return entity;
    }

    /**
     * Sets lootable entity.
     * @param entity lootable entity
     */
    public void setEntity(LootableLivingEntity entity) {
        if (entity != null) this.entity = entity;
    }
}
