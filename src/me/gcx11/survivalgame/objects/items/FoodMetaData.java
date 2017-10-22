package me.gcx11.survivalgame.objects.items;

/**
 * Created on 14.5.2017.
 */
public class FoodMetaData extends ItemMetaData {

    private double foodValue;
    private double healValue;
    private double damageValue;

    /**
     * Creates new FoodMetaData.
     */
    public FoodMetaData(){}

    /**
     * Creates new FoodMetaData.
     *
     * @param foodValue food value
     * @param healValue health value
     * @param damageValue damage value
     */
    public FoodMetaData(double foodValue, double healValue, double damageValue){
        this.foodValue = foodValue;
        this.healValue = healValue;
        this.damageValue = damageValue;
    }

    /**
     * Gets food value.
     *
     * @return food value
     */
    public double getFoodValue() {
        return foodValue;
    }

    /**
     * Gets health value.
     *
     * @return health value
     */
    public double getHealValue() {
        return healValue;
    }

    /**
     * Gets damage value.
     *
     * @return damage value
     */
    public double getDamageValue() {
        return damageValue;
    }

    /**
     * Sets food value of food.
     *
     * @param foodValue food value
     * @return modified food meta data
     */
    public FoodMetaData setFoodValue(double foodValue){
        this.foodValue = foodValue;
        return this;
    }

    /**
     * Sets health value of food.
     *
     * @param healValue health value
     * @return modified food meta data
     */
    public FoodMetaData setHealValue(double healValue){
        this.healValue = healValue;
        return this;
    }

    /**
     * Sets damage value of food.
     *
     * @param damageValue damage value
     * @return modified food meta data
     */
    public FoodMetaData setDamageValue(double damageValue){
        this.damageValue = damageValue;
        return this;
    }
}
