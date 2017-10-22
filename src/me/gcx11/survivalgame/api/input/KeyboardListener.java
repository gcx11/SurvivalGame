package me.gcx11.survivalgame.api.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

/**
 * Created on 24.2.2017.
 */
public class KeyboardListener implements KeyListener {

    /**
     * High level API for keyboard related stuff.
     */
    public KeyboardListener(){}

    private Set<Character> currentlyPressedKeys = new HashSet<>();
    private Set<Character> wasPressedKeys = new HashSet<>();

    private Set<Integer> currentlyPressedKeyCodes = new HashSet<>();

    @Override
    /**
     * Event listener.
     */
    public void keyTyped(KeyEvent e) {

    }

    @Override
    /**
     * Event listener.
     */
    public void keyPressed(KeyEvent e) {
        currentlyPressedKeys.add(e.getKeyChar());
        currentlyPressedKeyCodes.add(e.getKeyCode());
        wasPressedKeys.add(e.getKeyChar());
    }

    @Override
    /**
     * Event listener.
     */
    public void keyReleased(KeyEvent e) {
        currentlyPressedKeys.remove(e.getKeyChar());
        currentlyPressedKeyCodes.remove(e.getKeyCode());
    }

    /**
     * Returns whether specified char is pressed.
     *
     * @param c char
     * @return whether char is pressed
     */
    public boolean isPressed(char c){
        return currentlyPressedKeys.contains(c);
    }

    /**
     * Returns whether specified char was pressed during current game loop.
     *
     * @param c char
     * @return whether char was pressed during current game loop
     */
    public boolean wasPressed(char c) {return wasPressedKeys.contains(c);}

    /**
     * Clears records of chars pressed during current game loop.
     */
    public void resetPressed(){ wasPressedKeys.clear();}

    public boolean isPressedCtrl(){
        return currentlyPressedKeyCodes.contains(KeyEvent.VK_CONTROL);
    }

    public boolean isPressedShift(){
        return currentlyPressedKeyCodes.contains(KeyEvent.VK_SHIFT);
    }
}
