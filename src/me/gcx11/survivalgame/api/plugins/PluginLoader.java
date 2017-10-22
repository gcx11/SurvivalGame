package me.gcx11.survivalgame.api.plugins;

import com.sun.istack.internal.NotNull;
import me.gcx11.survivalgame.GamePanel;
import me.gcx11.survivalgame.SurvivalGame;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created on 29.8.2017.
 */
public class PluginLoader {

    List<Plugin> loadedPlugins = new ArrayList<>();
    private GamePanel gamePanel;
    private boolean alreadyLoaded = false;

    public PluginLoader(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    /**
     * Checks for key presses related to plugin loading/unloading.
     */
    public void update(){
        if (gamePanel.getKeyboardListener().wasPressed('r')) reloadPlugins();
        else if (gamePanel.getKeyboardListener().wasPressed('u')) unloadPlugins();
        else if (gamePanel.getKeyboardListener().wasPressed('l')) loadPlugins();
    }

    /**
     * Loads all plugins from folder plugins.
     */
    public void loadPlugins(){
        if (alreadyLoaded){
            System.out.println("Plugins have been already loaded!");
            return;
        }
        File pluginsFolder = getPluginsFolder();
        if (pluginsFolder == null){
            System.out.println("Failed to locate jar folder!");
            return;
        }
        try{
            List<Plugin> discoveredPlugins = loadJars(pluginsFolder);
            discoveredPlugins.forEach(this::loadPlugin);
            alreadyLoaded = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadPlugin(Plugin p) {
        try{
            p.load();
            System.out.println("Loaded plugin " + p.getName());
            loadedPlugins.add(p);
        }
        catch (Exception | AbstractMethodError e){
            System.out.println("Failed to load plugin!");
            e.printStackTrace();
        }
    }

    /**
     * Loads jars from plugin folder.
     * @param pluginsFolder plugin folder
     * @return list with loaded plugins
     * @throws Exception
     */
    @NotNull
    private List<Plugin> loadJars(File pluginsFolder) throws Exception {
        File[] files = pluginsFolder.listFiles(f -> f.getName().endsWith(".jar"));
        if (files == null) return new ArrayList<>();

        List<Plugin> plugins = new ArrayList<>();

        for (File file: files) {
            String pathToFile = file.getAbsolutePath();
            JarFile jarFile = new JarFile(pathToFile);
            Collection<JarEntry> jarEntries = Collections.list(jarFile.entries());

            URL[] urls = {new URL("jar:file:" + pathToFile + "!/")};
            URLClassLoader cl = URLClassLoader.newInstance(urls);

            for (JarEntry je: jarEntries){
                if (je.isDirectory() || !je.getName().endsWith(".class")) {
                    continue;
                }

                String className = je.getName().substring(0, je.getName().length() - ".class".length());
                className = className.replace('/', '.');
                Class c = cl.loadClass(className);
                //System.out.println(c.getName());
                Plugin p = getPluginFromClass(c);
                if (p != null) plugins.add(p);
            }
        }
        return plugins;
    }

    private Plugin getPluginFromClass(Class cls) {
        Class<Plugin> pluginInterface = Plugin.class;
        if (cls.isArray() || cls.isInterface() || cls.isPrimitive() || cls.isEnum() || cls.isAnnotation()) {
            System.out.println(cls.getName() + " is not class!");
            return null;
        }
        if (!pluginInterface.isAssignableFrom(cls)) {
            System.out.println("Class " + cls.getName() + " doesn't implement interface Plugin");
            return null;
        }

        Plugin plugin;
        try{
            plugin = (Plugin) cls.newInstance();
        }
        catch (InstantiationException | IllegalAccessException e){
            return null;
        }
        return plugin;
    }

    /**
     * Unloads all plugins.
     */
    public void unloadPlugins(){
        loadedPlugins.forEach(this::unloadPlugin);
        loadedPlugins.clear();
        alreadyLoaded = false;
    }

    private void unloadPlugin(Plugin p) {
        try{
            PluginManager.unloadPlugin(p);
            p.unload();
            System.out.println("Unloaded plugin " + p.getName());
        }
        catch (Exception e){
            System.out.println("Failed to unload plugin!");
            e.printStackTrace();
        }
    }

    /**
     * Reloads plugins.
     */
    public void reloadPlugins(){
        unloadPlugins();
        loadPlugins();
    }

    /**
     * Gets plugin folder.
     * @return file or null on failure.
     */
    private File getPluginsFolder(){
        String jarFolderPath = getJarContainingFolder(SurvivalGame.class);
        Path pluginsFolderPath = Paths.get(jarFolderPath, "plugins");
        if (!Files.exists(pluginsFolderPath)){
            try{
                Files.createDirectory(pluginsFolderPath);
                System.out.println("Created plugins folder!");
            }
            catch (IOException e){
                System.out.println("Failed to create plugins folder!");
                return null;
            }
        }
        return pluginsFolderPath.toFile();
    }

    /**
     * Returns path to jar with class or null on failure.
     * @param klazz class to find.
     * @return string with path or null on failure
     */
    private String getJarContainingFolder(Class klazz) {
        CodeSource codeSource = klazz.getProtectionDomain().getCodeSource();

        File jarFile;

        try {
            if (codeSource.getLocation() != null) {
                jarFile = new File(codeSource.getLocation().toURI());
            } else {
                String path = klazz.getResource(klazz.getSimpleName() + ".class").getPath();
                String jarFilePath = path.substring(path.indexOf(":") + 1, path.indexOf("!"));
                jarFilePath = URLDecoder.decode(jarFilePath, "UTF-8");
                jarFile = new File(jarFilePath);
            }
        } catch (URISyntaxException | UnsupportedEncodingException e) {
            return null;
        }

        return jarFile.getParentFile().getAbsolutePath();
    }
}
