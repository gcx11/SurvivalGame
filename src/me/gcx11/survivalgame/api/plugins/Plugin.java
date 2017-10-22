package me.gcx11.survivalgame.api.plugins;

/**
 * Created on 29.8.2017.
 */
public interface Plugin {

    /**
     * Called on plugin loading.
     */
    public void load();

    /**
     * Called on plugin unloading.
     */
    public void unload();

    /**
     * Gets name of the plugin.
     * @return name of the plugin
     */
    public String getName();

}
