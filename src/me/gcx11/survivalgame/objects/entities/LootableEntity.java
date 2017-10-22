package me.gcx11.survivalgame.objects.entities;

import me.gcx11.survivalgame.api.events.LootableEntityRenderTextEvent;
import me.gcx11.survivalgame.api.interfaces.LootableLivingEntity;
import me.gcx11.survivalgame.scenes.GameScene;
import me.gcx11.survivalgame.api.animations.EntityAnimation;
import me.gcx11.survivalgame.objects.items.Item;
import me.gcx11.survivalgame.objects.inventories.LootProbability;
import me.gcx11.survivalgame.objects.inventories.LootingInventory;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Created on 13.5.2017.
 */
public class LootableEntity extends Entity implements LootableLivingEntity {

    protected List<LootProbability> lootTable;
    public LootingInventory lootingInventory = null;

    public int level = 1;

    /**
     * Creates new lootable entity with specified loot table.
     *
     * @param gameScene game scene
     * @param x x position on map
     * @param y y position on map
     * @param entityAnimation entity animation
     * @param corpseTexture image of dead entity
     * @param lootTable list of special data structures for entity loot
     */
    public LootableEntity(GameScene gameScene, int x, int y, EntityAnimation entityAnimation, Image corpseTexture, List<LootProbability> lootTable) {
        super(gameScene, x, y, entityAnimation, corpseTexture);
        this.lootTable = lootTable;
    }

    /**
     * Sets difficulty level of entity.
     *
     * @param level level of difficulty
     */
    public void setLevel(int level){
        this.level = level;
        setMaxHealth(baseMaxHealth * level);
        setHealth(getMaxHealth());
    }

    @Override
    /**
     * Returns attack strength.
     */
    protected double getAttackStrength() {
        return super.getAttackStrength() * level;
    }

    @Override
    public void update(float delta){
        if (isDead() && (timeToDisappear - delta) <= 0.0f && lootingInventory != null && lootingInventory.isOpened()
                && !lootingInventory.isEmpty()){
            return;
        }
        super.update(delta);
        // TODO: close inv when to far away
    }

    /**
     * Handles click on dead entity.
     */
    public void handleClickOnCorpse(){
        if (lootingInventory == null){
            List<Item> itemsToLoot = new ArrayList<>();
            for (LootProbability loot: lootTable){
                if (rnd.nextDouble() <= loot.probability) itemsToLoot.add(loot.item);
            }
            lootingInventory = new LootingInventory(this, 4, itemsToLoot);
        }
        if (!lootingInventory.isOpened()){
            lootingInventory.open();
        }
    }

    @Override
    public void render(Graphics graphics) {
        super.render(graphics);
        Font oldFont = graphics.getFont();
        Font newFont = new Font("COMIC SANS", Font.PLAIN, 8);
        graphics.setFont(newFont);
        Color textColor = Color.RED;
        LootableEntityRenderTextEvent event = new LootableEntityRenderTextEvent(this, "LV " + level, textColor);
        event.emit();
        graphics.setColor(event.getColor());
        graphics.drawString(event.getText(), gameScene.camera.recalculateX(x), gameScene.camera.recalculateY(y));
        graphics.setFont(oldFont);
    }

    /**
     * Renders the loot inventory.
     *
     * @param graphics Graphics object
     * @see java.awt.Graphics
     */
    public void renderInventory(Graphics graphics){
        if (isDead() && lootingInventory != null){
            lootingInventory.render(graphics);
        }
    }

    @Override
    public List<LootProbability> getLootTable() {
        return new ArrayList<>(lootTable);
    }

    @Override
    public void setLootTable(List<LootProbability> lootTable) {
        if (lootTable != null) this.lootTable = lootTable;
    }

    @Override
    public int getLevel() {
        return level;
    }
}
