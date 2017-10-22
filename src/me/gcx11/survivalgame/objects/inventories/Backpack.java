package me.gcx11.survivalgame.objects.inventories;

import me.gcx11.survivalgame.objects.entities.Player;
import me.gcx11.survivalgame.objects.items.*;

import java.awt.*;

/**
 * Created on 12.5.2017.
 */
public class Backpack extends Inventory {

    private Player player;

    /**
     * Creates new instance of backpack.
     *
     * @param player player
     * @param x x coordinate on screen
     * @param y y coordinate on screen
     * @param size slots count
     */
    public Backpack(Player player, int x, int y, int size) {
        super(player, x, y, size);
        this.player = player;
    }

    @Override
    protected void handleLeftClickedItem(int slot, Item item) {
        ItemMetaData metaData = item.itemData.getMetaData();
        if (metaData == null) return;
        if (metaData instanceof FoodMetaData){
            FoodMetaData foodMetaData = (FoodMetaData) metaData;
            removeItemAt(slot, 1);
            player.feed(foodMetaData.getFoodValue());
            player.heal(foodMetaData.getHealValue());
            player.applyDamage(foodMetaData.getDamageValue());
        }
        else if (item instanceof WeaponItem){
            player.hotBarInventory.switchWeapons(slot);
        }
        else if (item instanceof ArmorItem){
            player.hotBarInventory.switchArmor(slot);
        }
    }

    @Override
    protected void handleRightClickedItem(int slot, Item item) {
        removeItemAt(slot, 1);
    }

    @Override
    public void render(Graphics graphics) {
        super.render(graphics);
        x = owner.gameScene.camera.getWindowWidth() - getWidth() - 20;
        y = owner.gameScene.camera.getWindowHeight() - getHeight() - 40;
    }
}
