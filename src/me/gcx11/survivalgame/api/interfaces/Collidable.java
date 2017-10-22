package me.gcx11.survivalgame.api.interfaces;

/**
 * Created on 26.2.2017.
 */
public interface Collidable {

    /**
     * Gets x coordinate of upper left corner of collidable.
     *
     * @return x coordinate of upper left corner
     */
    public int getCollidableX();

    /**
     * Gets y coordinate of upper left corner of collidable.
     *
     * @return y coordinate of upper left corner
     */
    public int getCollidableY();

    /**
     * Gets width of collidable.
     *
     * @return width of collidable
     */
    public int getCollidableWidth();

    /**
     * Gets height of collidable.
     *
     * @return height of collidable
     */
    public int getCollidableHeight();

    /**
     * Checks if object is in collision with other.
     *
     * @param obj collidable for collision test
     * @return whether are objects in collision
     */
    public default boolean isOverLappingWith(Collidable obj){
        return getCollidableX() < obj.getCollidableX() + obj.getCollidableWidth()
                && getCollidableX() + getCollidableWidth() > obj.getCollidableX()
                && getCollidableY() < obj.getCollidableY() + obj.getCollidableHeight()
                && getCollidableY() + getCollidableHeight() > obj.getCollidableY();
    }

    /*
    public static boolean areOverLapping(Collidable obj1, Collidable obj2){
        return obj1.getCollidableX() < obj2.getCollidableX() + obj2.getCollidableWidth()
                && obj1.getCollidableX()  + obj1.getCollidableWidth() > obj2.getCollidableX()
                && obj1.getCollidableY() < obj2.getCollidableY() + obj2.getCollidableHeight()
                && obj1.getCollidableY() + obj1.getCollidableHeight() > obj2.getCollidableY();
    }*/
}
