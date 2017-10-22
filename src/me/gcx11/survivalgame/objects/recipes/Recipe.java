package me.gcx11.survivalgame.objects.recipes;

import me.gcx11.survivalgame.objects.items.Item;

/**
 * Created on 11.5.2017.
 */
public class Recipe {

    public final Item result;
    public final Item[] ingredients;

    /**
     * Creates new recipe for some item.
     *
     * @param result resulting item from recipe
     * @param ingredients list of items required to make final item
     */
    public Recipe(Item result, Item... ingredients){
        this.result = result;
        this.ingredients = ingredients;
    }
}
