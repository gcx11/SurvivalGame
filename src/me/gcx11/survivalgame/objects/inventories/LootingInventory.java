package me.gcx11.survivalgame.objects.inventories;

import me.gcx11.survivalgame.objects.entities.Player;
import me.gcx11.survivalgame.objects.items.Item;
import me.gcx11.survivalgame.objects.entities.Entity;

import java.awt.*;
import java.util.List;

/**
 * Created on 13.5.2017.
 */
public class LootingInventory extends Inventory {

    /**
     * Creates new inventory with loot.
     *
     * @param owner owner of the inventory
     * @param size size of inventory
     * @param loot list of items in the inventory
     */
    public LootingInventory(Entity owner, int size, List<Item> loot) {
        super(owner, owner.gameScene.camera.recalculateX(owner.x), owner.gameScene.camera.recalculateY(owner.y), size);
        addLoot(loot);
    }

    /**
     * Adds item to inventory.
     *
     * @param loot list of loot
     */
    private void addLoot(List<Item> loot) {
        for (int i=0; i<items.length && i<loot.size(); i++){
            items[i] = loot.get(i);
        }
    }

    @Override
    protected void handleLeftClickedItem(int slot, Item item) {
        Player player = (Player) owner.gameScene.collidables.stream().filter(obj -> obj instanceof Player).findAny().orElseGet(null);
        if (player == null) return;
        Item itemCopy = item.copy();
        itemCopy.count = 1;
        if (player.inventory.addItem(itemCopy)) removeItemAt(slot, 1);
    }

    @Override
    protected void handleRightClickedItem(int slot, Item item) {
        handleLeftClickedItem(slot, item);
    }

    @Override
    public void render(Graphics graphics) {
        x = owner.gameScene.camera.recalculateX(owner.x);
        y = owner.gameScene.camera.recalculateY(owner.y);
        super.render(graphics);
    }
}
