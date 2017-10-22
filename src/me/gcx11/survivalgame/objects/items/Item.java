package me.gcx11.survivalgame.objects.items;

import me.gcx11.survivalgame.api.GameObject;
import me.gcx11.survivalgame.objects.nature.Flowers;
import me.gcx11.survivalgame.objects.nature.Plant;
import me.gcx11.survivalgame.objects.nature.Rock;
import me.gcx11.survivalgame.objects.nature.Tree;

/**
 * Created on 5.5.2017.
 */
public class Item {

    public final ItemData itemData;
    public int count = 1;

    /**
     * Creates new Item from ItemData
     *
     * @param itemData item data
     */
    public Item(ItemData itemData){
        this.itemData = itemData;
    }

    /**
     * Creates new Item from ItemData and specified count
     *
     * @param itemData item data
     * @param count count
     */
    public Item(ItemData itemData, int count){
        this.itemData = itemData;
        this.count = count;
    }

    /**
     * Creates item from game object.
     *
     * @param obj game object
     * @return item from game object
     */
    public static Item fromGameObject(GameObject obj){
        if (obj instanceof Plant && !((Plant)obj).isDisappearing){
            return new Item(ItemData.LEAF);
        }
        else if (obj instanceof Flowers){
            return new Item(ItemData.FLOWERS);
        }
        else if (obj instanceof Rock){
            return new Item(ItemData.ROCK);
        }
        else if (obj instanceof Tree && !((Tree)obj).wasHarvested){
            return new Item(ItemData.WOOD);
        }
        return null;
    }

    /**
     * Checks if item is same type.
     *
     * @param other item to compare
     * @return whether item is same type
     */
    public boolean isSameType(Item other) {
        return other != null && itemData == other.itemData;
    }

    /**
     * Returns if it is stackable.
     *
     * @return whether it is stackable
     */
    public boolean isStackable(){
        return true;
    }

    /**
     * Stacks items together.
     *
     * @param other other item
     */
    public void stackItems(Item other){
        count += other.count;
    }

    /**
     * Returns copy of item.
     *
     * @return copy of item
     */
    public Item copy(){
        return new Item(itemData, count);
    }
}
