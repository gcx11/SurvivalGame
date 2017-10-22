package me.gcx11.survivalgame.objects.items;

/**
 * Created on 15.5.2017.
 */
public class ArmorItem extends Item {

    protected double armorPercentage;
    public int uses;

    /**
     * Creates new armor item with itemdata.
     *
     * @param itemData item data
     * @see me.gcx11.survivalgame.objects.items.ItemData
     */
    public ArmorItem(ItemData itemData) {
        super(itemData);
        ItemMetaData metaData = itemData.getMetaData();
        if (metaData == null) return;
        else if (metaData instanceof ArmorMetaData){
            ArmorMetaData armorMetaData = (ArmorMetaData) metaData;
            this.armorPercentage = armorMetaData.armorPercentage;
            this.uses = armorMetaData.maxUses;
        }
    }

    /**
     * Checks whether is armor item broken.
     *
     * @return whether is armor item broken
     */
    public boolean isBroken(){
        return uses <= 0;
    }

    /**
     * Decreases usage of the armor.
     */
    public void decreaseUses(){
        uses--;
    }

    /**
     * Gets protection percentage of armor.
     *
     * @return protection percentage of armor
     */
    public double getArmorPercentage(){
        return armorPercentage;
    }

    /**
     * Gets armor item type.
     *
     * @return armor item type
     * @see me.gcx11.survivalgame.objects.items.ArmorType
     */
    public ArmorType getArmorType(){
        ItemMetaData metaData = itemData.getMetaData();
        if (metaData == null) return ArmorType.NONE;
        else if (metaData instanceof ArmorMetaData){
            ArmorMetaData armorMetaData = (ArmorMetaData) metaData;
            return armorMetaData.armorType;
        }
        return ArmorType.NONE;
    }

    @Override
    public boolean isStackable() {
        return false;
    }

    @Override
    public Item copy(){
        return new ArmorItem(itemData);
    }
}
