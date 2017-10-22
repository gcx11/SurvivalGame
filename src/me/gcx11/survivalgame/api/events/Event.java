package me.gcx11.survivalgame.api.events;

import me.gcx11.survivalgame.api.plugins.EventManager;

/**
 * Created on 3.9.2017.
 */
public abstract class Event {

    /**
     * Emits event into the game.
     **/
    private boolean alreadyCalled = false;

    public final void emit(){
        if (!alreadyCalled){
            EventManager.handleEvent(this);
            alreadyCalled = true;
        }
    }
}
