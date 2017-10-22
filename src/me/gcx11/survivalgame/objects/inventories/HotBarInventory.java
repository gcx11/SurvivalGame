package me.gcx11.survivalgame.objects.inventories;

import me.gcx11.survivalgame.objects.entities.Player;
import me.gcx11.survivalgame.objects.items.ArmorType;
import me.gcx11.survivalgame.objects.items.Item;
import me.gcx11.survivalgame.objects.items.ArmorItem;
import me.gcx11.survivalgame.objects.items.WeaponItem;

import java.awt.*;

/**
 * Created on 15.5.2017.
 */
public class HotBarInventory extends Inventory {

    private Player player;

    /**
     * Creates new hotbar inventory.
     *
     * @param player player
     * @param x x coordinate on screen
     * @param y y coordinate on screen
     */
    public HotBarInventory(Player player, int x, int y) {
        super(player, x, y, 6);
        this.player = player;
        this.slotsInRow = 6;
    }

    /**
     * Gets weapon from weapon slot.
     *
     * @return weapon from weapon slot
     */
    public WeaponItem getWeapon(){
        return (WeaponItem) items[0];
    }

    /**
     * Sets weapon into weapon slot.
     *
     * @param item weapon
     */
    public void setWeapon(WeaponItem item){
        items[0] = item;
    }

    /**
     * Removes weapon from weapon slot.
     */
    public void removeWeapon(){
        setWeapon(null);
    }

    /**
     * Gets armor of specified type from inventory
     *
     * @param armorType type of armor
     * @return armor of specified type from inventory
     */
    public ArmorItem getArmor(ArmorType armorType){
        if (armorType == ArmorType.SHIELD) return (ArmorItem) items[1];
        else if (armorType == ArmorType.HELMET) return (ArmorItem) items[2];
        else if (armorType == ArmorType.BODY) return (ArmorItem) items[3];
        else if (armorType == ArmorType.BOOTS) return (ArmorItem) items[4];
        else if (armorType == ArmorType.GLOVES) return (ArmorItem) items[5];
        else return null;
    }

    /**
     * Sets armor to inventory.
     *
     * @param item armor to set into inventory
     */
    public void setArmor(ArmorItem item){
        ArmorType armorType = item.getArmorType();
        if (armorType == ArmorType.SHIELD) items[1] = item;
        if (armorType == ArmorType.HELMET) items[2] = item;
        else if (armorType == ArmorType.BODY) items[3] = item;
        else if (armorType == ArmorType.BOOTS) items[4] = item;
        else if (armorType == ArmorType.GLOVES) items[5] = item;
    }

    /**
     * Switches weapon from hotbar with weapon inside player inventory
     * from specified slot.
     *
     * @param slot slot of player inventory
     */
    public void switchWeapons(int slot){
        Item item = player.inventory.getItemAt(slot);
        if (!(item instanceof WeaponItem)) return;
        WeaponItem newWeaponItem = (WeaponItem) item;
        WeaponItem oldWeaponItem = getWeapon();
        player.inventory.setItemAt(slot, oldWeaponItem);
        setWeapon(newWeaponItem);
    }

    /**
     * Switches armor from hotbar with weapon inside player inventory
     * from specified slot.
     *
     * @param slot slot of player inventory
     */
    public void switchArmor(int slot){
        Item item = player.inventory.getItemAt(slot);
        if (!(item instanceof ArmorItem)) return;
        ArmorItem newArmorItem = (ArmorItem) item;
        ArmorItem oldArmoritem = getArmor(newArmorItem.getArmorType());
        player.inventory.setItemAt(slot, oldArmoritem);
        setArmor(newArmorItem);
    }

    @Override
    protected void handleLeftClickedItem(int slot, Item item) {

    }

    @Override
    protected void handleRightClickedItem(int slot, Item item) {
        removeItemAt(slot, 1);
    }

    @Override
    public void render(Graphics graphics) {
        super.render(graphics);
        x = (owner.gameScene.camera.getWindowWidth() - getWidth())/2;
        y = owner.gameScene.camera.getWindowHeight() - getHeight() - 40;
    }

    /**
     * Removes broken armor from hotbar.
     */
    public void removeBrokenArmor() {
        for (int i=1; i<6; i++){
            Item item = items[i];
            if (item == null || (!(item instanceof ArmorItem))) continue;
            ArmorItem armorItem = (ArmorItem) item;
            if (armorItem.isBroken()) items[i] = null;
        }
    }
}
