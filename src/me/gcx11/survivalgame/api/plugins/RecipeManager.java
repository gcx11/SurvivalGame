package me.gcx11.survivalgame.api.plugins;

import me.gcx11.survivalgame.objects.recipes.Recipe;
import me.gcx11.survivalgame.objects.recipes.Recipes;

import java.util.*;

/**
 * Created on 29.8.2017.
 */
public class RecipeManager extends ManagerBase {

    private HashMap<Plugin, Set<Recipe>> customRecipes = new HashMap<>();

    /**
     * Registers custom recipe.
     * @param plugin plugin instance
     * @param recipe new recipe
     */
    public void registerCustomRecipe(Plugin plugin, Recipe recipe){
        for (Set<Recipe> recipes: customRecipes.values()){
            if (recipes.contains(recipe)) return;
        }
        Set<Recipe> pluginRecipes = customRecipes.getOrDefault(plugin, new HashSet<>());
        pluginRecipes.add(recipe);
        customRecipes.put(plugin, pluginRecipes);
        Recipes.customRecipes.add(recipe);
    }

    @Override
    public void unloadPlugin(Plugin plugin) {
        if (!customRecipes.containsKey(plugin)) return;
        Recipes.customRecipes.removeAll(customRecipes.remove(plugin));

    }
}
