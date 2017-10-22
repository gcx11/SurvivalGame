package me.gcx11.survivalgame.objects.inventories;

import me.gcx11.survivalgame.api.GameObject;
import me.gcx11.survivalgame.objects.items.Item;
import me.gcx11.survivalgame.objects.items.ItemData;
import me.gcx11.survivalgame.objects.entities.Entity;

import java.awt.*;

/**
 * Created on 5.5.2017.
 */
public abstract class Inventory {

    protected final int slots;
    protected Item[] items;
    protected Entity owner;
    protected int x;
    protected int y;

    private final Color INVENTORY_COLOR = new Color(209, 187, 57);
    private boolean isVisible = true;

    protected int spacing = 3;
    protected int cellSize = 24;
    protected int slotsInRow = 2;

    /**
     * Creates new inventory.
     * @param owner owner of the inventory
     * @param x x position on the map
     * @param y y position on the map
     * @param size
     */
    public Inventory(Entity owner, int x, int y, int size){
        this.owner = owner;
        this.x = x;
        this.y = y;
        this.slots = size;
        this.items = new Item[slots];
    }

    /**
     * Opens the inventory.
     */
    public void open(){
        isVisible = true;
    }

    /**
     * Closes the inventory.
     */
    public void close(){
        isVisible = false;
    }

    /**
     * Checks whether is inventory opened.
     *
     * @return whether is inventory opened
     */
    public boolean isOpened(){
        return isVisible;
    }

    /**
     * Checks if inventory is used and handles its usage.
     *
     * @return whether is inventory used
     */
    public boolean handleUse(){
        if (!isVisible) return false;
        if (owner.gameScene.mouseInfo.wasLeftClicked() || owner.gameScene.mouseInfo.wasRightClicked()) {
            int mouseX = owner.gameScene.mouseInfo.getX();
            int mouseY = owner.gameScene.mouseInfo.getY();
            for (int i = 0; i < slots; i++) {
                if (isInsideSlot(mouseX, mouseY, x + spacing * (i % slotsInRow + 1) + cellSize * (i % slotsInRow),
                        y + spacing * (i / slotsInRow + 1) + cellSize * (i / slotsInRow))) {
                    Item clickedItem = items[i];
                    if (clickedItem != null){
                        if (owner.gameScene.mouseInfo.wasLeftClicked()) handleLeftClickedItem(i, clickedItem);
                        else if (owner.gameScene.mouseInfo.wasRightClicked()) handleRightClickedItem(i, clickedItem);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Handles clicking on item with left mouse button.
     *
     * @param slot clicked slot number
     * @param item clicked item
     */
    abstract protected void handleLeftClickedItem(int slot, Item item);

    /**
     * Handles clicking on item with right mouse button.
     *
     * @param slot clicked slot number
     * @param item clicked item
     */
    abstract protected void handleRightClickedItem(int slot, Item item);

    /**
     * Makes an attempt to add game object to the inventory.
     *
     * @param obj game object
     * @return whether was success or not
     */
    public boolean tryAddGameObject(GameObject obj){
        if (ItemData.isConvertable(obj)){
            Item item = Item.fromGameObject(obj);
            return addItem(item);
        }
        return false;
    }

    /**
     * Makes an attempt to add item to the inventory.
     *
     * @param item item to add into the inventory
     * @return whether was success or not
     */
    public boolean addItem(Item item){
        for (int i=0; i < slots; i++){
            if (items[i] != null && items[i].isSameType(item) && items[i].isStackable()){
                items[i].stackItems(item);
                return true;
            }
        }
        for (int i=0; i < slots; i++){
            if (items[i] == null){
                items[i] = item;
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether there is enough space in the inventory.
     *
     * @param item item to add into the inventory
     * @return whether there is enough space in the inventory
     */
    public boolean hasGotAtLeastItem(Item item){
        int count = item.count;
        for (int i=0; i < slots; i++){
            if (items[i] != null && items[i].isSameType(item)){
                count -= items[i].count;
            }
        }
        return count <= 0;
    }

    /**
     * Removes item from inventory.
     *
     * @param item item to remove
     */
    public void removeItem(Item item){
        int count = item.count;
        for (int i=0; i < slots; i++){
            if (items[i] != null && items[i].isSameType(item)){
                if (items[i].count - count <= 0){
                    count -= items[i].count;
                    items[i] = null;
                }
                else{
                    items[i].count -= count;
                    return;
                }
            }
        }
    }

    /**
     * Returns item on specified slot.
     *
     * @param slot slot number
     * @return item on specified slot
     */
    public Item getItemAt(int slot){
        if (slot >= slots || slot < 0) return null;
        return items[slot];
    }

    /**
     * Sets item on specified slot.
     *
     * @param slot slot number
     * @param item item to set there
     */
    public void setItemAt(int slot, Item item){
        if (slot < slots && slot >= 0) items[slot] = item;
    }

    /**
     * Removes item at specified slot of specified amount.
     *
     * @param slot slot number
     * @param count amount of items to remove
     */
    public void removeItemAt(int slot, int count){
        Item item = getItemAt(slot);
        if (item == null) return;
        if (item.count > count) {
            item.count -= count;
            setItemAt(slot, item);
        }
        else setItemAt(slot, null);
    }

    /**
     * Checks whether was clicked inside inventory.
     *
     * @return whether was clicked inside inventory
     */
    public boolean wasClickedInside(){
        if (!isVisible) return false;
        if (!owner.gameScene.mouseInfo.wasLeftClicked()) return false;
        int mouseX = owner.gameScene.mouseInfo.getX();
        int mouseY = owner.gameScene.mouseInfo.getY();
        return (x <= mouseX && mouseX <= x + (slotsInRow+1)*spacing+cellSize*slotsInRow &&
                y <= mouseY && mouseY <= y + (slots/slotsInRow+1)*spacing+cellSize*(slots/slotsInRow));
    }

    public void render(Graphics graphics){
        if (!isVisible) return;
        graphics.setColor(INVENTORY_COLOR);
        graphics.fillRect(x, y, (slotsInRow+1)*spacing+cellSize*slotsInRow, (slots/slotsInRow+1)*spacing+cellSize*(slots/slotsInRow));
        graphics.setColor(Color.GRAY);
        graphics.drawLine(x - 1, y - 1, x + (slotsInRow+1) * spacing + cellSize * slotsInRow, y - 1);
        graphics.drawLine(x-1, y-1, x-1, y+(slots/slotsInRow+1)*spacing+cellSize*(slots/slotsInRow));
        graphics.drawLine(x+(slotsInRow+1)*spacing+cellSize*slotsInRow, y-1,
                x+(slotsInRow+1)*spacing+cellSize*slotsInRow, y+(slots/slotsInRow+1)*spacing+cellSize*(slots/slotsInRow));
        graphics.drawLine(x-1, y+(slots/slotsInRow+1)*spacing+cellSize*(slots/slotsInRow),
                x+(slotsInRow+1)*spacing+cellSize*slotsInRow, y+(slots/slotsInRow+1)*spacing+cellSize*(slots/slotsInRow));
        Font oldFont = graphics.getFont();
        Font newFont = new Font("Comic Sans", Font.PLAIN, 10);
        graphics.setFont(newFont);
        for (int i=0; i < slots; i++){
            graphics.setColor(Color.GRAY);
            graphics.fillRect(x+spacing*(i%slotsInRow+1)+cellSize*(i%slotsInRow),
                    y+spacing*(i/slotsInRow+1)+cellSize*(i/slotsInRow), cellSize, cellSize);
            if (items[i] != null){
                graphics.drawImage(items[i].itemData.getTexture(), x+spacing*(i%slotsInRow+1)+cellSize*(i%slotsInRow),
                        y+spacing*(i/slotsInRow+1)+cellSize*(i/slotsInRow), null);
                if (items[i].count != 1){
                    graphics.setColor(Color.WHITE);
                    graphics.drawString(Integer.toString(items[i].count), x+spacing*(i%slotsInRow+1)+cellSize*(i%slotsInRow)+12,
                            y+spacing*(i/slotsInRow+1)+cellSize*(i/slotsInRow)+20);
                }
            }
        }
        graphics.setFont(oldFont);
    }

    /**
     * Checks whether is mouse inside slot.
     *
     * @param mouseX mouse x coordinate
     * @param mouseY mouse y coordinate
     * @param slotX slot x coordinate
     * @param slotY slot y coordinate
     * @return whether is mouse inside slot
     */
    protected boolean isInsideSlot(int mouseX, int mouseY, int slotX, int slotY){
        return (slotX <= mouseX && mouseX <= slotX + cellSize &&
                slotY <= mouseY && mouseY <= slotY + cellSize);
    }

    /**
     * Gets width of inventory.
     *
     * @return width of inventory
     */
    public int getWidth(){
        return (slotsInRow+1)*spacing+cellSize*slotsInRow;
    }

    /**
     * Gets height of inventory.
     *
     * @return height of inventory
     */
    public int getHeight(){
        return (slots/slotsInRow+1)*spacing+cellSize*(slots/slotsInRow);
    }

    /**
     * Checks whether is inventory completely empty.
     *
     * @return whether is inventory completely empty
     */
    public boolean isEmpty(){
        for (int i=0; i<items.length; i++){
            if (items[i] != null) return false;
        }
        return true;
    }
}
