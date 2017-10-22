package me.gcx11.survivalgame.api.debug;

import me.gcx11.survivalgame.api.GameObject;
import me.gcx11.survivalgame.world.generator.Chunk;
import me.gcx11.survivalgame.scenes.GameScene;
import me.gcx11.survivalgame.world.generator.WorldGenerator;

import java.awt.*;

/**
 * Created on 25.2.2017.
 */
public class DebugInfo extends GameObject {

    private boolean isVisible = false;
    private long seed = WorldGenerator.getSeed();
    private int counter = 0;
    private int fps;

    /**
     * Used mainly for debug informations such as chunk count, average frame per second and other debug-related stuff.
     *
     * @param gameScene current game scene
     */
    public DebugInfo(GameScene gameScene){
        super(gameScene);
    }

    @Override
    public void update(float delta) {
        if (gameScene.keyboardListener.wasPressed('b')){
            isVisible = !isVisible;
        }
        if (counter > 20){
            fps = (int)(1/delta);
            counter = 0;
        }
        counter++;
    }

    @Override
    public void render(Graphics graphics) {
        if (!isVisible) return;
        graphics.setColor(Color.WHITE);
        graphics.drawString("Average FPS: " + fps, 20, 60);
        graphics.drawString("x: " + gameScene.mouseInfo.getX() + " y: " + gameScene.mouseInfo.getY() +
                " clicked: " + gameScene.mouseInfo.isPressed(), 20, 80);
        Chunk c = gameScene.camera.getChunkInMiddle();
        graphics.drawString("Current chunk: " + c.toString(), 20, 100);
        graphics.drawString("Chunks loaded/total: " +
                GameScene.active.size() + "/" + Chunk.chunks.size(), 20, 120);
        //graphics.drawString("Seed: " + seed, 20, 140);
    }
}
