package me.gcx11.survivalgame.objects.recipes;

import me.gcx11.survivalgame.api.custom.MultiIterable;
import me.gcx11.survivalgame.objects.entities.Player;
import me.gcx11.survivalgame.objects.items.Item;
import me.gcx11.survivalgame.objects.items.ItemData;
import me.gcx11.survivalgame.objects.items.ArmorItem;
import me.gcx11.survivalgame.objects.items.WeaponItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created on 11.5.2017.
 */
public class Recipes {

    public static List<Recipe> recipes = Arrays.asList(
            new Recipe(new Item(ItemData.HEAL_POTION), new Item(ItemData.LEAF, 10), new Item(ItemData.FLOWERS, 10)),
            new Recipe(new Item(ItemData.FRIED_EGG), new Item(ItemData.EGG), new Item(ItemData.WOOD, 3)),
            new Recipe(new Item(ItemData.MEAT_ON_STICK), new Item(ItemData.ROTTEN_MEAT), new Item(ItemData.WOOD, 3)),
            new Recipe(new Item(ItemData.FLOUR), new Item(ItemData.BONE), new Item(ItemData.ROCK)),
            new Recipe(new Item(ItemData.BREAD), new Item(ItemData.FLOUR, 3), new Item(ItemData.FRIED_EGG),
                    new Item(ItemData.WOOD, 5)),
            new Recipe(new Item(ItemData.HAMBURGER), new Item(ItemData.BREAD, 2),
                    new Item(ItemData.ROTTEN_MEAT, 2), new Item(ItemData.LEAF), new Item(ItemData.WOOD, 4)),
            new Recipe(new Item(ItemData.CANDY), new Item(ItemData.BERRIES), new Item(ItemData.CHERRIES)),
            new Recipe(new Item(ItemData.SOUP), new Item(ItemData.PORK), new Item(ItemData.CARROT),
                    new Item(ItemData.EGG)),
            new Recipe(new WeaponItem(ItemData.WAND), new Item(ItemData.WOOD, 2), new Item(ItemData.LEAF)),
            new Recipe(new Item(ItemData.REFINED_STONE), new Item(ItemData.ROCK)),
            new Recipe(new WeaponItem(ItemData.SMALL_KNIFE), new Item(ItemData.REFINED_STONE, 2), new Item(ItemData.WOOD)),
            new Recipe(new WeaponItem(ItemData.SMALL_AXE), new Item(ItemData.REFINED_STONE, 3), new Item(ItemData.WOOD)),
            new Recipe(new ArmorItem(ItemData.WOODEN_SHIELD), new Item(ItemData.WOOD, 3), new Item(ItemData.REFINED_STONE)),
            new Recipe(new ArmorItem(ItemData.LEATHER_GLOVES), new Item(ItemData.ROTTEN_MEAT, 3)),
            new Recipe(new ArmorItem(ItemData.LEATHER_BOOTS), new Item(ItemData.ROTTEN_MEAT, 3)),
            new Recipe(new Item(ItemData.GOLD_INGOT), new Item(ItemData.GOLD_ROCK)),
            new Recipe(new Item(ItemData.IRON_INGOT), new Item(ItemData.IRON_ROCK)),
            new Recipe(new ArmorItem(ItemData.LEATHER_VEST), new Item(ItemData.ROTTEN_MEAT, 7)),
            new Recipe(new WeaponItem(ItemData.GOLD_KNIFE), new Item(ItemData.GOLD_INGOT, 2), new Item(ItemData.WOOD)),
            new Recipe(new WeaponItem(ItemData.IRON_KNIFE), new Item(ItemData.IRON_INGOT, 2), new Item(ItemData.WOOD)),
            new Recipe(new WeaponItem(ItemData.GOLD_AXE), new Item(ItemData.GOLD_INGOT, 3), new Item(ItemData.WOOD)),
            new Recipe(new WeaponItem(ItemData.IRON_AXE), new Item(ItemData.IRON_INGOT, 3), new Item(ItemData.WOOD)),
            new Recipe(new WeaponItem(ItemData.STONE_SWORD), new Item(ItemData.REFINED_STONE, 5), new Item(ItemData.WOOD)),
            new Recipe(new WeaponItem(ItemData.GOLD_SWORD), new Item(ItemData.GOLD_INGOT, 5), new Item(ItemData.WOOD)),
            new Recipe(new WeaponItem(ItemData.IRON_SWORD), new Item(ItemData.IRON_INGOT, 5), new Item(ItemData.WOOD)),
            new Recipe(new ArmorItem(ItemData.GOLD_SHIELD), new Item(ItemData.GOLD_INGOT, 3), new Item(ItemData.BONE)),
            new Recipe(new ArmorItem(ItemData.IRON_SHIELD), new Item(ItemData.IRON_INGOT, 3), new Item(ItemData.BONE)),
            new Recipe(new ArmorItem(ItemData.GOLD_HELMET), new Item(ItemData.GOLD_INGOT, 2)),
            new Recipe(new ArmorItem(ItemData.IRON_HELMET), new Item(ItemData.IRON_INGOT, 2)),
            new Recipe(new ArmorItem(ItemData.GOLD_BODY_ARMOR), new Item(ItemData.GOLD_INGOT, 5)),
            new Recipe(new ArmorItem(ItemData.IRON_BODY_ARMOR), new Item(ItemData.IRON_INGOT, 5)),
            new Recipe(new ArmorItem(ItemData.GOLD_BOOTS), new Item(ItemData.GOLD_INGOT, 3)),
            new Recipe(new ArmorItem(ItemData.IRON_BOOTS), new Item(ItemData.IRON_INGOT, 3)),
            new Recipe(new ArmorItem(ItemData.GOLD_GLOVES), new Item(ItemData.GOLD_INGOT, 3)),
            new Recipe(new ArmorItem(ItemData.IRON_GLOVES), new Item(ItemData.IRON_INGOT, 3)));

    public static List<Recipe> customRecipes = new ArrayList<>();

    /**
     * Gets list of all avaible recipes for the player.
     *
     * @param player in-game player
     * @return list of all avaible recipes for the player
     */
    public static List<Recipe> validRecipesFor(Player player){
        List<Recipe> validRecipes = new ArrayList<>();
        for (Recipe recipe: MultiIterable.From(recipes, customRecipes)){
            if (canBeMade(recipe, player)){
                validRecipes.add(recipe);
            }
        }
        return validRecipes;
    }

    /**
     * Checks whether player has got enough items for using recipe.
     *
     * @param recipe recipe to be used
     * @param player in-game player
     * @return whether player has got enough items for using recipe
     */
    private static boolean canBeMade(Recipe recipe, Player player) {
        for (Item ingredient: recipe.ingredients){
            if (!player.inventory.hasGotAtLeastItem(ingredient)) return false;
        }
        return true;
    }

}
