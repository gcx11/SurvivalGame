package me.gcx11.survivalgame.api.interfaces;

/**
 * Created on 9.9.2017.
 */
public interface LivingEntity {

    /**
     * Gets entity health.
     * @return Entity health
     */
    public double getHealth();

    /**
     * Gets entity max health.
     * @return Entity maximal health
     */
    public double getMaxHealth();

    /**
     * Sets health of the entity.
     * @param amount amount of health
     */
    public void setHealth(double amount);

    /**
     * Sets maximal health of the entity.
     * @param amount amount of maximal health
     */
    public void setMaxHealth(double amount);

}
