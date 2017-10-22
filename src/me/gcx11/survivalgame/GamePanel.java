package me.gcx11.survivalgame;

import me.gcx11.survivalgame.api.input.*;
import me.gcx11.survivalgame.api.input.MouseInfo;
import me.gcx11.survivalgame.api.plugins.PluginLoader;
import me.gcx11.survivalgame.api.plugins.PluginManager;
import me.gcx11.survivalgame.scenes.GameScene;
import me.gcx11.survivalgame.scenes.MenuScene;
import me.gcx11.survivalgame.scenes.Scene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Created on 10.2.2017.
 */
public class GamePanel extends JPanel implements Runnable {
    private static int PWIDTH = 600;
    private static int PHEIGHT = 400;
    private Thread animator;
    private boolean running = false;
    private boolean paused = false;
    private Graphics dbg;
    private Image dbImage = null;
    private KeyboardListener keyboardListener = new KeyboardListener();
    private MouseInfo mouseInfo = new MouseInfo(this);
    private Scene currentScene = new MenuScene(this);
    private PluginLoader pluginLoader = new PluginLoader(this);

    /**
     * Creates new GamePanel.
     */
    public GamePanel() {
        setBackground(Color.black);
        setPreferredSize(new Dimension(PWIDTH, PHEIGHT));
        PluginManager.setGamePanel(this);
    }

    /**
     * Changes current scene to another.
     *
     * @param scene
     */
    public void changeScene(Scene scene){
        currentScene = scene;
        currentScene.load();
    }

    /**
     * Adds notification to the panel.
     */
    public void addNotify() {
        super.addNotify();
        startGame();
    }

    /**
     * Starts game.
     */
    private void startGame() {
        if (animator == null || !running) {
            animator = new Thread(this);
            animator.start();
        }
    }

    /**
     * Stops game.
     */
    public void stopGame() { running = false; }

    /**
     * Main game loop.
     */
    public void run() {
        setupGame();
        running = true;
        long lastTime = System.currentTimeMillis();
        long current;
        while(running) {
            current = System.currentTimeMillis();
            float delta = (current - lastTime)/1000f;
            gameUpdate(delta);
            pluginLoader.update();
            mouseInfo.resetClicked();
            keyboardListener.resetPressed();
            gameRender();
            repaint();
            try {
                Thread.sleep(8);
            } catch(InterruptedException ex) {
                System.out.println("Interrputed");
            }
            lastTime = current;
        }
        System.exit(0);
    }

    /**
     * Setups game.
     */
    private void setupGame() {
        getTopLevelAncestor().addKeyListener(keyboardListener);
        getTopLevelAncestor().addMouseListener(mouseInfo);
        getTopLevelAncestor().addMouseMotionListener(mouseInfo);
        getTopLevelAncestor().addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                PWIDTH = e.getComponent().getWidth();
                PHEIGHT = e.getComponent().getHeight();
                dbImage = createImage(PWIDTH, PHEIGHT);
                dbg = dbImage.getGraphics();
                if (currentScene instanceof GameScene) {
                    GameScene gameScene = (GameScene) currentScene;
                    gameScene.camera.resize(PWIDTH, PHEIGHT);
                }
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {
            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
        getTopLevelAncestor().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                paused = false;
            }

            @Override
            public void focusLost(FocusEvent e) {
                paused = true;
            }
        });
        pluginLoader.loadPlugins();
        currentScene.load();
    }

    /**
     * Updates current scene if game is not paused.
     *
     * @param delta time in second that passed since last game update
     */
    private void gameUpdate(float delta) {
        if (!paused){
            currentScene.update(delta);
        }
    }

    /**
     * Renders game.
     */
    private void gameRender() {
        if (dbImage == null) {
            dbImage = createImage(PWIDTH, PHEIGHT);
            if (dbImage == null) {
                System.out.println("dbImage is null");
                return;
            }
            dbg = dbImage.getGraphics();
        }
        dbg.setColor(Color.black);
        dbg.fillRect(0, 0, PWIDTH, PHEIGHT);
        currentScene.render(dbg);
    }

    /**
     * Paints game panel.
     *
     * @param g Graphics
     * @see java.awt.Graphics
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (dbImage != null) {
            g.drawImage(dbImage, 0, 0, null);
        }
    }

    /**
     * Gets keyboard listener.
     *
     * @return keyboard listener
     */
    public KeyboardListener getKeyboardListener() {
        return keyboardListener;
    }

    /**
     * Gets mouse info.
     *
     * @return mouse info
     */
    public MouseInfo getMouseInfo() {
        return mouseInfo;
    }
}
