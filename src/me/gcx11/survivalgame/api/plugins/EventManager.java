package me.gcx11.survivalgame.api.plugins;

import me.gcx11.survivalgame.api.events.Event;
import me.gcx11.survivalgame.api.events.EventHandler;
import me.gcx11.survivalgame.api.events.EventListener;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Created on 3.9.2017.
 */
public class EventManager extends ManagerBase {

    private static HashMap<Plugin, Set<Method>> pluginsMethods = new HashMap<>();
    private static HashMap<EventListener, Set<Method>> eventListenersMethods = new HashMap<>();
    private static HashMap<Class<? extends Event>, Set<Method>> eventsMethods = new HashMap<>();

    /**
     * Propagates event to plugins.
     * @param event event
     */
    public static void handleEvent(Event event){
        Set<Method> methods = eventsMethods.getOrDefault(event.getClass(), null);
        if (methods == null) return;
        for (Method method: methods){
            try{
                EventListener listener = getEventListenerForMethod(method);
                if (listener == null) continue;
                method.invoke(listener, event);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private static EventListener getEventListenerForMethod(Method method) {
        for (Entry<EventListener, Set<Method>> entry: eventListenersMethods.entrySet()){
            if (entry.getValue().contains(method)) return entry.getKey();
        }
        return null;
    }

    /**
     * Registers all methods for listening events from EventListener.
     * @param plugin plugin instance
     * @param listener listener instance
     */
    public void registerListener(Plugin plugin, EventListener listener){
        Class<?> klazz = listener.getClass();
        for (Method method: klazz.getMethods()){
            if (method == null) continue;
            if (method.getDeclaredAnnotation(EventHandler.class) == null) continue;
            if (method.getParameterCount() == 1 && Event.class.isAssignableFrom(method.getParameterTypes()[0])){
                final Class<? extends Event> eventClass = method.getParameterTypes()[0].asSubclass(Event.class);
                registerMethod(plugin, listener, eventClass, method);
            }
        }
    }

    private void registerMethod(Plugin plugin, EventListener eventListener, Class<? extends Event> eventClass, Method method) {
        addMethodToPluginHashMap(plugin, method);
        addMethodToListenersHashMap(eventListener, method);
        addMethodToEventsHashMap(eventClass, method);
    }


    private void addMethodToPluginHashMap(Plugin plugin, Method method) {
        Set<Method> methods = pluginsMethods.getOrDefault(plugin, new HashSet<>());
        methods.add(method);
        pluginsMethods.put(plugin, methods);
    }


    private void addMethodToListenersHashMap(EventListener listener, Method method) {
        Set<Method> methods = eventListenersMethods.getOrDefault(listener, new HashSet<>());
        methods.add(method);
        eventListenersMethods.put(listener, methods);
    }

    private void addMethodToEventsHashMap(Class<? extends Event> eventClass, Method method) {
        Set<Method> methods = eventsMethods.getOrDefault(eventClass, new HashSet<>());
        methods.add(method);
        eventsMethods.put(eventClass, methods);
    }

    @Override
    public void unloadPlugin(Plugin plugin) {
        Set<Method> methods = pluginsMethods.remove(plugin);
        for (Set<Method> methodSet: eventsMethods.values()){
            methodSet.removeAll(methods);
        }
    }
}
