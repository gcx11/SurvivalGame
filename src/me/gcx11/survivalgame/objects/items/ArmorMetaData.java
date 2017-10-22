package me.gcx11.survivalgame.objects.items;

/**
 * Created on 15.5.2017.
 */
public class ArmorMetaData extends ItemMetaData {

    public double armorPercentage;
    public int maxUses;
    public ArmorType armorType;

    /**
     * Creates new ArmorMetaData with specified armorType.
     * @param armorType armor type
     */
    public ArmorMetaData(ArmorType armorType){
        this.armorType = armorType;
    }

    /**
     * Sets armor metadata protection percentage.
     *
     * @param armorPercentage percentage of armor
     * @return modified armor metadata
     */
    public ArmorMetaData setArmorPercentage(double armorPercentage){
        this.armorPercentage = armorPercentage;
        return this;
    }


    /**
     * Sets max uses of armor metadata.
     *
     * @param uses max uses
     * @return modified armor metadata
     */
    public ArmorMetaData setMaxUses(int uses){
        this.maxUses = uses;
        return this;
    }

    /**
     * Sets armor type of armor metadata.
     *
     * @param armorType armor type
     * @return modified armor metadata
     */
    public ArmorMetaData setArmorType(ArmorType armorType){
        this.armorType = armorType;
        return this;
    }

    /**
     * Creates new ArmorMetaData.
     *
     * @param armorPercentage armor protection percentage
     * @param maxUses max uses
     * @param armorType armor type
     */
    public ArmorMetaData(double armorPercentage, int maxUses, ArmorType armorType){
        this.armorPercentage = armorPercentage;
        this.maxUses = maxUses;
        this.armorType = armorType;
    }
}
