package me.gcx11.survivalgame.api.plugins;

import me.gcx11.survivalgame.GamePanel;
import me.gcx11.survivalgame.api.events.EventListener;
import me.gcx11.survivalgame.objects.recipes.Recipe;

import java.util.Arrays;
import java.util.List;

/**
 * Created on 29.8.2017.
 */
public class PluginManager {

    private static GamePanel gamePanel;
    private static RecipeManager recipeManager = new RecipeManager();
    private static EventManager eventManager = new EventManager();
    private static List<ManagerBase> managers = Arrays.asList(recipeManager, eventManager);

    public static void setGamePanel(GamePanel gamePanel){

        if (gamePanel != null )PluginManager.gamePanel = gamePanel;
    }

    /**
     * Registers custom recipe for plugin.
     * @param plugin plugin isntance
     * @param recipe new recipe
     */
    public static void registerCustomRecipe(Plugin plugin, Recipe recipe) {
        recipeManager.registerCustomRecipe(plugin, recipe);
    }

    /**
     * Registers event listener.
     * @param plugin plugin instance
     * @param eventListener event listener instance
     */
    public static void registerEventListener(Plugin plugin, EventListener eventListener){
        eventManager.registerListener(plugin, eventListener);
        System.out.println("Registering listener");
    }

    /**
     * Unloads plugin.
     * @param plugin plugin instance
     */
    static void unloadPlugin(Plugin plugin){
        for (ManagerBase manager: managers){
            manager.unloadPlugin(plugin);
        }
    }


}
