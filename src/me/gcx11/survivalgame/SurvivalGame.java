package me.gcx11.survivalgame;

import javax.swing.*;
import java.awt.*;

public class SurvivalGame extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SurvivalGame app = new SurvivalGame();
            app.setVisible(true);
        });
    }

    /**
     * Creates new game window.
     */
    private SurvivalGame(){
        this.getContentPane().add("Center", new GamePanel());
        this.setMinimumSize(new Dimension(480, 360));
        //this.setResizable(false);
        this.setTitle("Survival Game");
        this.pack();
        this.setLocationRelativeTo(null); // set window to center of the screen
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
