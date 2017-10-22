package me.gcx11.survivalgame.api.input;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created on 27.3.2017.
 */
public class MouseInfo implements MouseListener, MouseMotionListener {

    private JPanel gamePanel;
    private int x, y;
    private boolean isPressed;
    private boolean wasLeftClicked;
    private boolean wasRightClicked;

    /**
     * High level API for mouse input.
     *
     * @param gamePanel game panel
     */
    public MouseInfo(JPanel gamePanel){
        this.gamePanel = gamePanel;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Checks if mouse is pressed.
     *
     * @return whether is mouse currently pressed
     */
    public boolean isPressed() {
        return isPressed;
    }

    /**
     * Clears records of any mouse button pressed.
     */
    public void resetClicked(){
        wasLeftClicked = false;
        wasRightClicked = false;
    }

    /**
     * Checks if left mouse button was pressed during this game loop.
     *
     * @return whether left mouse button was pressed
     */
    public boolean wasLeftClicked(){
        return wasLeftClicked;
    }

    /**
     * Checks if right mouse button was pressed during this game loop.
     *
     * @return whether right mouse button was pressed
     */
    public boolean wasRightClicked(){
        return wasRightClicked;
    }

    @Override
    /**
     * Event handler.
     */
    public void mouseClicked(MouseEvent e) {
        if (gamePanel.getMousePosition() == null) return;
        x = gamePanel.getMousePosition().x;
        y = gamePanel.getMousePosition().y;
        if (e.getButton() == MouseEvent.BUTTON1) wasLeftClicked = true;
        else if (e.getButton() == MouseEvent.BUTTON3) wasRightClicked = true;
    }

    @Override
    /**
     * Event handler.
     */
    public void mousePressed(MouseEvent e) {
        isPressed = true;
    }

    @Override
    /**
     * Event handler.
     */
    public void mouseReleased(MouseEvent e) {
        isPressed = false;
    }

    @Override
    /**
     * Event handler.
     */
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    /**
     * Event handler.
     */
    public void mouseExited(MouseEvent e) {

    }

    @Override
    /**
     * Event handler.
     */
    public void mouseDragged(MouseEvent e) {
        if (gamePanel.getMousePosition() == null) return;
        x = gamePanel.getMousePosition().x;
        y = gamePanel.getMousePosition().y;
    }

    @Override
    /**
     * Event handler.
     */
    public void mouseMoved(MouseEvent e) {
        if (gamePanel.getMousePosition() == null) return;
        x = gamePanel.getMousePosition().x;
        y = gamePanel.getMousePosition().y;
    }
}
