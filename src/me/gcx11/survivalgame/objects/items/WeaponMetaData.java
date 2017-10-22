package me.gcx11.survivalgame.objects.items;

/**
 * Created on 14.5.2017.
 */
public class WeaponMetaData extends ItemMetaData {

    public double attackStrenght;
    public int maxUses;

    /**
     * Creates new WeaponMetaData.
     */
    public WeaponMetaData(){

    }

    /**
     * Sets WeaponMetaData attack strength.
     *
     * @param attackStrenght attack strength
     * @return modified WeaponMetaData
     */
    public WeaponMetaData setAttackStrenght(double attackStrenght){
        this.attackStrenght = attackStrenght;
        return this;
    }


    /**
     * Sets maximal WeaponMetaData uses.
     *
     * @param uses maximal amount of uses
     * @return modified WeaponMetaData
     */
    public WeaponMetaData setMaxUses(int uses){
        this.maxUses = uses;
        return this;
    }

    /**
     * Sets WeaponMetaData attack strength and maximal uses.
     *
     * @param attackStrenght attack strength
     * @param maxUses maximal amount of uses
     */
    public WeaponMetaData(double attackStrenght, int maxUses){
        this.attackStrenght = attackStrenght;
        this.maxUses = maxUses;
    }

}
