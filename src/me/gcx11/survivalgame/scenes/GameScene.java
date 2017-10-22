package me.gcx11.survivalgame.scenes;

import me.gcx11.survivalgame.GamePanel;
import me.gcx11.survivalgame.api.*;
import me.gcx11.survivalgame.api.debug.DebugInfo;
import me.gcx11.survivalgame.api.interfaces.Collidable;
import me.gcx11.survivalgame.api.rendering.Camera;
import me.gcx11.survivalgame.objects.entities.LootableEntity;
import me.gcx11.survivalgame.objects.entities.Player;
import me.gcx11.survivalgame.settings.GameMode;
import me.gcx11.survivalgame.world.generator.Chunk;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 11.2.2017.
 */
public class GameScene extends Scene {

    public Camera camera;
    public List<GameObject> nonCollidables = new ArrayList<>();
    public List<GameObject> collidables = new ArrayList<>();

    public static Set<Chunk> active = new HashSet<>();

    private List<GameObject> toDelete = new ArrayList<>();

    private boolean isPaused = false;

    public GameMode gameMode;

    /**
     * Creates new game scene.
     *
     * @param gamePanel game panel
     * @param camera game scene camera
     * @param gameMode game mode
     */
    public GameScene(GamePanel gamePanel, Camera camera, GameMode gameMode){
        super(gamePanel);
        this.camera = camera;
        this.gameMode = gameMode;
    }

    @Override
    public void load(){
        Chunk.chunks.clear();
        active.clear();
        Player player = new Player(this);
        addObject(player);
        addObject(new DebugInfo(this));
        camera.follow(player);
    }

    /**
     * Adds game object to scene.
     *
     * @param gameObject game object to add
     */
    public void addObject(GameObject gameObject){
        if (gameObject instanceof Collidable) collidables.add(gameObject);
        else nonCollidables.add(gameObject);
    }

    /**
     * Removes game object from scene.
     *
     * @param gameObject game object to remove
     */
    public void removeObject(GameObject gameObject){
        if (gameObject instanceof Collidable) collidables.remove(gameObject);
        else nonCollidables.remove(gameObject);
    }

    @Override
    public void update(float delta){
        if (keyboardListener.wasPressed('p')) isPaused = !isPaused;

        if (isPaused){
            removeObjectsToDelete();
            return;
        }

        Chunk currentChunk = camera.getChunkInMiddle();
        if (currentChunk == null){
            currentChunk = Chunk.chunks.getOrDefault(new Coord(0, 0), new Chunk(this, 0, 0));
            Chunk.chunks.putIfAbsent(new Coord(0, 0), currentChunk);
        }

        int widerSide = Math.max(camera.getWindowWidth(), camera.getWindowHeight());
        int depth = (int)Math.ceil(((widerSide/Chunk.SIZE)-1)/2.0);
        if (depth < 0) depth = 0;
        loadChunks(depth, currentChunk);

        Set<Chunk> toRemove = active.stream().filter(c -> !camera.isChunkVisible(c)).collect(Collectors.toSet());
        toRemove.stream().peek(Chunk::unload).forEach(active::remove);

        Set<Chunk> processed = new HashSet<>();
        List<Chunk> queue = new ArrayList<>();

        queue.add(currentChunk);

        while (!queue.isEmpty()){
            Chunk c = queue.remove(queue.size()-1);
            processed.add(c);
            c.isActive = camera.isChunkVisible(c);
            if (c.isActive){
                c.update(delta);
                if (active.add(c)) {
                    c.load();
                }
                Chunk[] temp = new Chunk[]{c.left, c.up, c.right, c.down};

                for (Chunk x: temp){
                    if (x != null && !processed.contains(x)) queue.add(x);
                }
            }
            else{
                if (active.remove(c)){
                    c.unload();
                }
            }

        }

        for (GameObject obj: nonCollidables){
            obj.update(delta);
        }
        for (GameObject obj: collidables){
            obj.update(delta);
        }
        removeObjectsToDelete();
    }

    /**
     * Removes all game objects marked to delete.
     */
    private void removeObjectsToDelete() {
        for (GameObject obj: toDelete){
            removeObject(obj);
            active.forEach(c -> c.removeObject(obj));
        }
    }

    /**
     * Loads chunks around current chunk with specified depth of loading recursion.
     *
     * @param depth recursion depth
     * @param currentChunk current chunk
     */
    private void loadChunks(int depth, Chunk currentChunk) {
        loadChunksAround(currentChunk);

        if (depth == 0) return;
        Chunk[] temp = new Chunk[]{currentChunk.left, currentChunk.up, currentChunk.right, currentChunk.down, currentChunk.left.up,
                currentChunk.left.down, currentChunk.right.up, currentChunk.right.down};

        for (Chunk chunk: temp){
            loadChunks(depth - 1, chunk);
        }
    }

    /**
     * Loads all neighbour chunks around chunk.
     *
     * @param chunk chunk to be used
     */
    private void loadChunksAround(Chunk chunk) {
        if (chunk.left == null){
            //System.out.println("Loading left " + (chunk.id_x-1) + " " + (chunk.id_y));
            chunk.left = Chunk.chunks.getOrDefault(new Coord(chunk.id_x-1, chunk.id_y), new Chunk(this, chunk.id_x-1, chunk.id_y));
            chunk.left.right = chunk;
            Chunk.chunks.putIfAbsent(new Coord(chunk.id_x-1, chunk.id_y), chunk.left);
        }
        if (chunk.right == null){
            //System.out.println("Loading right " + (chunk.id_x+1) + " " + (chunk.id_y));
            chunk.right = Chunk.chunks.getOrDefault(new Coord(chunk.id_x + 1, chunk.id_y), new Chunk(this, chunk.id_x+1, chunk.id_y));
            chunk.right.left = chunk;
            Chunk.chunks.putIfAbsent(new Coord(chunk.id_x + 1, chunk.id_y), chunk.right);
        }
        if (chunk.up == null){
            //System.out.println("Loading up " + (chunk.id_x) + " " + (chunk.id_y-1));
            chunk.up = Chunk.chunks.getOrDefault(new Coord(chunk.id_x, chunk.id_y-1), new Chunk(this, chunk.id_x, chunk.id_y-1));
            chunk.up.down = chunk;
            Chunk.chunks.putIfAbsent(new Coord(chunk.id_x, chunk.id_y-1), chunk.up);
        }
        if (chunk.down == null){
            //System.out.println("Loading down " + (chunk.id_x) + " " + (chunk.id_y+1));
            chunk.down = Chunk.chunks.getOrDefault(new Coord(chunk.id_x, chunk.id_y+1), new Chunk(this, chunk.id_x, chunk.id_y+1));
            chunk.down.up = chunk;
            Chunk.chunks.putIfAbsent(new Coord(chunk.id_x, chunk.id_y + 1), chunk.down);
        }
        
        // corners
        if (chunk.left.up == null && chunk.up.left == null){
            //System.out.println("Loading corner " + (chunk.id_x-1) + " " + (chunk.id_y-1));
            chunk.left.up = chunk.up.left = Chunk.chunks.getOrDefault(new Coord(chunk.id_x-1, chunk.id_y-1), new Chunk(this, chunk.id_x-1, chunk.id_y-1));
            chunk.left.up.down = chunk.left;
            chunk.left.up.right = chunk.up;
            Chunk.chunks.putIfAbsent(new Coord(chunk.id_x - 1, chunk.id_y - 1), chunk.left.up);
        }
        else if (chunk.left.up == null) chunk.left.up = chunk.up.left;
        else if (chunk.up.left == null) chunk.up.left = chunk.left.up;

        if (chunk.left.down == null && chunk.down.left == null){
            //System.out.println("Loading corner " + (chunk.id_x-1) + " " + (chunk.id_y+1));
            chunk.left.down = chunk.down.left = Chunk.chunks.getOrDefault(new Coord(chunk.id_x-1, chunk.id_y+1), new Chunk(this, chunk.id_x-1, chunk.id_y+1));
            chunk.left.down.up = chunk.left;
            chunk.left.down.right = chunk.down;
            Chunk.chunks.putIfAbsent(new Coord(chunk.id_x-1, chunk.id_y+1), chunk.left.down);
        }
        else if (chunk.left.down == null) chunk.left.down = chunk.down.left;
        else if (chunk.down.left == null) chunk.down.left = chunk.left.down;

        if (chunk.right.up == null && chunk.up.right == null){
            //System.out.println("Loading corner " + (chunk.id_x+1) + " " + (chunk.id_y-1));
            chunk.right.up = chunk.up.right = Chunk.chunks.getOrDefault(new Coord(chunk.id_x+1, chunk.id_y-1), new Chunk(this, chunk.id_x+1, chunk.id_y-1));
            chunk.right.up.left = chunk.up;
            chunk.right.up.down = chunk.right;
            Chunk.chunks.putIfAbsent(new Coord(chunk.id_x + 1, chunk.id_y - 1), chunk.right.up);
        }
        else if (chunk.right.up == null) chunk.right.up = chunk.up.right;
        else if (chunk.up.right == null) chunk.up.right = chunk.right.up;

        if (chunk.right.down == null && chunk.down.right == null){
            //System.out.println("Loading corner " + (chunk.id_x+1) + " " + (chunk.id_y+1));
            chunk.right.down = chunk.down.right = Chunk.chunks.getOrDefault(new Coord(chunk.id_x+1, chunk.id_y+1), new Chunk(this, chunk.id_x+1, chunk.id_y+1));
            chunk.right.down.up = chunk.right;
            chunk.right.down.left = chunk.down;
            Chunk.chunks.putIfAbsent(new Coord(chunk.id_x+1, chunk.id_y+1), chunk.right.down);
        }
        else if (chunk.right.down == null) chunk.right.down = chunk.down.right;
        else if (chunk.down.right == null) chunk.down.right = chunk.right.down;
    }

    @Override
    public void render(Graphics graphics){
        for (Chunk chunk: active){
            chunk.render(graphics);
        }

        for (GameObject obj: nonCollidables){
            if (obj instanceof DebugInfo) continue;
            obj.render(graphics);
        }
        for (GameObject obj: collidables){
            if (!(obj instanceof Player || obj instanceof LootableEntity)) obj.render(graphics);
        }
        collidables.stream().filter(obj -> obj instanceof LootableEntity)
                            .forEach(obj -> obj.render(graphics));
        collidables.stream().filter(obj -> obj instanceof Player)
                .forEach(obj -> obj.render(graphics));
        collidables.stream().filter(obj -> obj instanceof LootableEntity)
                .forEach(obj -> ((LootableEntity)obj).renderInventory(graphics));
        nonCollidables.stream().filter(obj -> obj instanceof DebugInfo).forEach(obj -> obj.render(graphics));
    }

    /**
     * Marks game object for deleting later.
     *
     * @param obj game object to delete
     */
    public void deleteLater(GameObject obj) {
        toDelete.add(obj);
    }
}
