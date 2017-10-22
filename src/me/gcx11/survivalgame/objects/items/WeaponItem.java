package me.gcx11.survivalgame.objects.items;

/**
 * Created on 14.5.2017.
 */
public class WeaponItem extends Item {

    protected double attackStrenght;
    public int uses;

    /**
     * Creates new WeaponItem.
     *
     * @param itemData weaponItemData
     */
    public WeaponItem(ItemData itemData) {
        super(itemData);
        ItemMetaData metaData = itemData.getMetaData();
        if (metaData == null) return;
        else if (metaData instanceof WeaponMetaData){
            WeaponMetaData weaponMetaData = (WeaponMetaData) metaData;
            this.attackStrenght = weaponMetaData.attackStrenght;
            this.uses = weaponMetaData.maxUses;
        }
    }

    /**
     * Checks whether is weapon item broken.
     *
     * @return whether is weapon item broken
     */
    public boolean isBroken(){
        return uses <= 0;
    }

    /**
     * Decreases number of usage of the item.
     */
    public void decreaseUses(){
        uses--;
    }

    /**
     * Gets weapon item attack strength.
     *
     * @return weapon item attack strength
     */
    public double getAttackStrenght(){
        return attackStrenght;
    }

    @Override
    public boolean isStackable() {
        return false;
    }

    @Override
    public Item copy(){
        return new WeaponItem(itemData);
    }
}
