package me.gcx11.survivalgame.world.generator;

import me.gcx11.survivalgame.api.interfaces.Collidable;
import me.gcx11.survivalgame.api.Coord;
import me.gcx11.survivalgame.api.GameObject;
import me.gcx11.survivalgame.objects.entities.Entity;
import me.gcx11.survivalgame.scenes.GameScene;
import me.gcx11.survivalgame.settings.GameMode;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created on 7.4.2017.
 */
public class Chunk {

    public static Map<Coord, Chunk> chunks = new HashMap<>();

    public static WorldGenerator worldGenerator = new WorldGenerator();

    public static final int SIZE = 160;//160*2;
    public final GameScene gameScene;

    public int id_x, id_y;
    public Chunk up, down, left, right;
    public BiomeType biome;
    public boolean isActive = true;

    public List<GameObject> nonCollidables = new ArrayList<>();
    public List<GameObject> collidables = new ArrayList<>();

    /**
     * Creates new chunk.
     *
     * @param gameScene game scene
     * @param id_x x id of the chunk
     * @param id_y y id of the chunk
     */
    public Chunk(GameScene gameScene, int id_x, int id_y){
        this.gameScene = gameScene;
        this.id_x = id_x;
        this.id_y = id_y;
        worldGenerator.generateTerrain(this);
    }

    /**
     * Adds object to the chunk.
     *
     * @param gameObject game object to add
     */
    public void addObject(GameObject gameObject){
        if (gameObject instanceof Collidable) collidables.add(gameObject);
        else nonCollidables.add(gameObject);
    }

    /**
     * Removes object from the chunk.
     *
     * @param gameObject game object to remove
     */
    public void removeObject(GameObject gameObject){
        if (gameObject instanceof Collidable) collidables.remove(gameObject);
        else nonCollidables.remove(gameObject);
    }

    /**
     * Updates chunk.
     *
     * @param delta time in seconds that passed since last game update.
     */
    public void update(float delta){
        //if (!isActive) return;
        for (GameObject obj: nonCollidables){
            obj.update(delta);
        }
        for (GameObject obj: collidables){
            obj.update(delta);
        }
    }

    /**
     * Renders chunk.
     *
     * @param graphics Graphics object
     * @see java.awt.Graphics
     */
    public void render(Graphics graphics){
        //if (!isActive) return;
        //for (GameObject obj: nonCollidables) obj.render(graphics);
        //for (GameObject obj: collidables) obj.render(graphics);
        graphics.setColor(Color.PINK);
        for (GameObject obj: nonCollidables){
            if (obj.x < gameScene.camera.mouseToGameX(gameScene.camera.getWindowWidth())
                    && obj.x + obj.width + 20 > gameScene.camera.mouseToGameX(0)
                    && obj.y < gameScene.camera.mouseToGameY(gameScene.camera.getWindowHeight())
                    && obj.y + obj.height + 20 > gameScene.camera.mouseToGameY(0))
            obj.render(graphics);
        }
        for (GameObject obj: collidables){
            if (obj.x < gameScene.camera.mouseToGameX(gameScene.camera.getWindowWidth())
                    && obj.x + obj.width + 20 > gameScene.camera.mouseToGameX(0)
                    && obj.y < gameScene.camera.mouseToGameY(gameScene.camera.getWindowHeight())
                    && obj.y + obj.height + 20 > gameScene.camera.mouseToGameY(0))
            obj.render(graphics);
        }
        //drawBorders(graphics);
    }

    /**
     * Draws chunk borders - debug only!
     *
     * @param graphics Graphics object
     * @see java.awt.Graphics
     */
    private void drawBorders(Graphics graphics) {
        graphics.drawLine(gameScene.camera.recalculateX(id_x*SIZE), gameScene.camera.recalculateY(id_y*SIZE),
                gameScene.camera.recalculateX(id_x*SIZE+SIZE), gameScene.camera.recalculateY(id_y*SIZE));
        graphics.drawLine(gameScene.camera.recalculateX(id_x*SIZE), gameScene.camera.recalculateY(id_y*SIZE),
                gameScene.camera.recalculateX(id_x*SIZE), gameScene.camera.recalculateY(id_y*SIZE+SIZE));
        graphics.drawLine(gameScene.camera.recalculateX(id_x*SIZE+SIZE), gameScene.camera.recalculateY(id_y*SIZE),
                gameScene.camera.recalculateX(id_x*SIZE+SIZE), gameScene.camera.recalculateY(id_y*SIZE+SIZE));
        graphics.drawLine(gameScene.camera.recalculateX(id_x*SIZE), gameScene.camera.recalculateY(id_y*SIZE+SIZE),
                gameScene.camera.recalculateX(id_x*SIZE+SIZE), gameScene.camera.recalculateY(id_y*SIZE+SIZE));
    }

    /**
     * Loads chunk.
     */
    public void load(){
        if (gameScene.gameMode == GameMode.EASY) worldGenerator.spawnMobs(this);
    }

    /**
     * Unloads chunk.
     */
    public void unload(){
        List<GameObject> toRemove = new ArrayList<>();
        for (GameObject obj: gameScene.collidables){
            if (!(obj instanceof Entity)) continue;
            if (obj.x >= id_x*SIZE
                    && obj.x < id_x*SIZE + SIZE
                    && obj.y >= id_y*SIZE
                    && obj.y < id_y*SIZE + SIZE){
                toRemove.add(obj);
            }
        }
        gameScene.collidables.removeAll(toRemove);
    }

    @Override
    public String toString() {
        return "[" + id_x + ", " + id_y + "] biome: " + biome.name();
    }
}
