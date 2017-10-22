package me.gcx11.survivalgame.world.generator;

import me.gcx11.survivalgame.api.Coord;
import me.gcx11.survivalgame.api.GameObject;
import me.gcx11.survivalgame.api.events.LootableEntitySpawnEvent;
import me.gcx11.survivalgame.api.interfaces.Collidable;
import me.gcx11.survivalgame.api.interfaces.LivingEntity;
import me.gcx11.survivalgame.objects.entities.*;
import me.gcx11.survivalgame.objects.nature.*;
import me.gcx11.survivalgame.world.tiles.DesertTile;
import me.gcx11.survivalgame.world.tiles.GrassTile;
import me.gcx11.survivalgame.world.tiles.RockyTile;

import java.util.*;

/**
 * Created on 28.4.2017.
 */
public class WorldGenerator {

    //private static final long seed = 1234;
    private static Random rnd = new Random();
    private static final long seed = rnd.nextLong();
    //private static final int BIOMESIZE = 20;

    //private static OpenSimplexNoise noise = new OpenSimplexNoise(seed);
    private static SimplexValueNoise noise = new SimplexValueNoise(rnd.nextLong());

    /**
     * Creates new WorldGenerator.
     */
    public WorldGenerator(){
        System.out.println("seed: " + seed);
    }

    /**
     * Gets biome for chunk at specified coordinates.
     *
     * @param id_x chunk x id coordinate
     * @param id_y chunk y id coordinate
     * @return biome type
     */
    private BiomeType getBiomeFor(int id_x, int id_y){
        //double result = noise.eval(id_x/50d, id_y/50d);
        double result = noise.eval(id_x/10d, id_y/10d);
        if (result < 0.0d) return BiomeType.FOREST;
        else if (result <= 0.15d) return BiomeType.PLAINS;
        else return BiomeType.DESERT;
    }

    /**
     * Gets chunk difficulty for specified chunk.
     *
     * @param id_x chunk x id coordinate
     * @param id_y chunk y id coordinate
     * @return entities difficulty
     */
    private int getChunkDifficultyLevel(int id_x, int id_y){
        return (int)(Math.hypot(id_x*id_x,id_y*id_y)/20) + 1;
    }

    /**
     * Gets seed of world generator.
     *
     * @return seed
     */
    public static long getSeed(){
        return seed;
    }

    /**
     * Generates terrain for specified chunk.
     *
     * @param chunk chunk
     */
    public void generateTerrain(Chunk chunk) {
        chunk.biome = getBiomeFor(chunk.id_x, chunk.id_y);
        generateTiles(chunk);
        generateTrees(chunk);
        generateFlora(chunk);
        generateDesertObjects(chunk);
        spawnMobs(chunk);
        playerSpawnProtection(chunk);
        deleteCollidingGameObjects(chunk);
    }

    /**
     * Removes any object that could stuck player on spawn.
     *
     * @param chunk starting chunk
     */
    private void playerSpawnProtection(Chunk chunk) {
        if (chunk.id_x == 0 && chunk.id_y == 0){
            Collidable player = (Collidable)chunk.gameScene.collidables.stream().filter(p -> p instanceof Player).findAny().get();
            chunk.collidables.removeIf(obj -> player.isOverLappingWith((Collidable)obj));
        }
    }

    /**
     * Generates desert object if biome type is desert.
     *
     * @param chunk chunk
     */
    private void generateDesertObjects(Chunk chunk) {
        if (chunk.biome == BiomeType.DESERT) {
            for (int i = 0; i < Chunk.SIZE / 160; i++) {
                chunk.addObject(new Rock(chunk.gameScene, chunk.id_x * Chunk.SIZE + 32 * rnd.nextInt(Chunk.SIZE / 32), chunk.id_y * Chunk.SIZE + 32 * rnd.nextInt(Chunk.SIZE / 32)));
            }
            int bonesCount = rnd.nextInt(3)+1;
            for (int i=0; i < bonesCount; i++)
            chunk.addObject(new Bones(chunk.gameScene, chunk.id_x * Chunk.SIZE + 16 * rnd.nextInt(Chunk.SIZE / 16), chunk.id_y * Chunk.SIZE + 16 * rnd.nextInt(Chunk.SIZE / 16)));

        }
    }

    /**
     * Generates plants inside chunk.
     *
     * @param chunk chunk
     */
    private void generateFlora(Chunk chunk) {
        if (chunk.biome == BiomeType.FOREST){
            for (int i = 0; i < Chunk.SIZE / 16; i++) {
                chunk.addObject(new Flowers(chunk.gameScene, chunk.id_x * Chunk.SIZE + 16 * rnd.nextInt(Chunk.SIZE / 16), chunk.id_y * Chunk.SIZE + 16 * rnd.nextInt(Chunk.SIZE / 16)));
            }
            chunk.addObject(new Plant(chunk.gameScene, chunk.id_x * Chunk.SIZE + 16 * rnd.nextInt(Chunk.SIZE / 16),
                    chunk.id_y * Chunk.SIZE + 16 * rnd.nextInt(Chunk.SIZE / 16) ));

        }
        if (chunk.biome == BiomeType.PLAINS ||
                (chunk.biome == BiomeType.FOREST &&
                 (getBiomeFor(chunk.id_x-1, chunk.id_y) == BiomeType.PLAINS || getBiomeFor(chunk.id_x, chunk.id_y-1) == BiomeType.PLAINS
                  || getBiomeFor(chunk.id_x+1, chunk.id_y) == BiomeType.PLAINS || getBiomeFor(chunk.id_x, chunk.id_y+1) == BiomeType.PLAINS))){
            for (int i = 0; i < Chunk.SIZE / 8; i++) {
                chunk.addObject(new Flowers(chunk.gameScene, chunk.id_x * Chunk.SIZE + 16 * rnd.nextInt(Chunk.SIZE / 16), chunk.id_y * Chunk.SIZE + 16 * rnd.nextInt(Chunk.SIZE / 16)));
            }
            for (int i=0; i < Chunk.SIZE/4; i++){
                chunk.addObject(new Plant(chunk.gameScene, chunk.id_x * Chunk.SIZE + 16 * rnd.nextInt(Chunk.SIZE / 16),
                        chunk.id_y * Chunk.SIZE + 16 * rnd.nextInt(Chunk.SIZE / 16) ));
            }
        }
    }

    /**
     * Generates trees inside chunk.
     *
     * @param chunk chunk
     */
    private void generateTrees(Chunk chunk) {
        if (chunk.biome == BiomeType.FOREST) {
            int parts = Chunk.SIZE/32;
            ArrayList<Coord> randomCoords = new ArrayList<>();
            for (int x=0; x<parts; x++){
                for (int y=0; y<parts; y++) {
                    randomCoords.add(new Coord(x, y));
                }
            }
            Collections.shuffle(randomCoords);
            for (int i=0; i<randomCoords.size()/3; i++){
                Coord randomCoord = randomCoords.get(i);
                chunk.addObject(new Tree(chunk.gameScene, chunk.id_x * Chunk.SIZE + 32 * randomCoord.getX(),
                        chunk.id_y * Chunk.SIZE + 32 * randomCoord.getY()));
            }
        }
    }

    /**
     * Generates tiles in chunk.
     *
     * @param chunk chunk
     */
    private void generateTiles(Chunk chunk) {
        if (chunk.biome == BiomeType.FOREST || chunk.biome == BiomeType.PLAINS) {
            for (int i = 0; i < Chunk.SIZE / 16; i++) {
                for (int j = 0; j < Chunk.SIZE / 16; j++) {
                    chunk.addObject(new GrassTile(chunk.gameScene, chunk.id_x * Chunk.SIZE + i * 16, chunk.id_y * Chunk.SIZE + j * 16));
                }
            }
        }
        else if (chunk.biome == BiomeType.DESERT) {
            for (int i = 0; i < Chunk.SIZE / 16; i++) {
                for (int j = 0; j < Chunk.SIZE / 16; j++) {
                    String rightTexture = getRightPlainsTile(chunk.id_x, chunk.id_y, i, j, Chunk.SIZE/16);
                    chunk.addObject(new DesertTile(chunk.gameScene, chunk.id_x * Chunk.SIZE + i * 16, chunk.id_y * Chunk.SIZE + j * 16,
                                    rightTexture));
                }
            }
        }
        // not used
        else {
            for (int i = 0; i < Chunk.SIZE / 16; i++) {
                for (int j = 0; j < Chunk.SIZE / 16; j++) {
                    chunk.addObject(new RockyTile(chunk.gameScene, chunk.id_x * Chunk.SIZE + i * 16, chunk.id_y * Chunk.SIZE + j * 16));
                }
            }
        }
    }

    /**
     * Spawns entities in chunk.
     *
     * @param chunk chunk
     */
    public void spawnMobs(Chunk chunk) {
        if (chunk.biome == BiomeType.PLAINS){
            if (rnd.nextInt(5) == 0){
                Slime slime = new Slime(chunk.gameScene, chunk.id_x * Chunk.SIZE + 16 * rnd.nextInt(Chunk.SIZE / 16), chunk.id_y * Chunk.SIZE + 16 * rnd.nextInt(Chunk.SIZE / 16));
                slime.setLevel(getChunkDifficultyLevel(chunk.id_x, chunk.id_y));
                if (chunk.collidables.stream().noneMatch(obj -> slime.isOverLappingWith((Collidable)obj))){
                    LootableEntitySpawnEvent event = new LootableEntitySpawnEvent(slime);
                    event.emit();
                    LivingEntity entity = event.getEntity();
                    if (entity instanceof GameObject) chunk.gameScene.addObject((GameObject)entity);
                }
            }
        }
        else if (chunk.biome == BiomeType.FOREST){
            if (rnd.nextInt(8) == 0){
                int count = rnd.nextInt(2)+1;
                for (int i=0; i<count; i++){
                    Spider spider = new Spider(chunk.gameScene, chunk.id_x * Chunk.SIZE + 16 * rnd.nextInt(Chunk.SIZE / 16), chunk.id_y * Chunk.SIZE + 16 * rnd.nextInt(Chunk.SIZE / 16));
                    spider.setLevel(getChunkDifficultyLevel(chunk.id_x, chunk.id_y));
                    if (chunk.collidables.stream().noneMatch(obj -> spider.isOverLappingWith((Collidable)obj))){
                        LootableEntitySpawnEvent event = new LootableEntitySpawnEvent(spider);
                        event.emit();
                        LivingEntity entity = event.getEntity();
                        if (entity instanceof GameObject) chunk.gameScene.addObject((GameObject)entity);
                    }
                }
            }
            else if (rnd.nextInt(16) == 0){
                Bat bat = new Bat(chunk.gameScene, chunk.id_x * Chunk.SIZE + 16 * rnd.nextInt(Chunk.SIZE / 16), chunk.id_y * Chunk.SIZE + 16 * rnd.nextInt(Chunk.SIZE / 16));
                bat.setLevel(getChunkDifficultyLevel(chunk.id_x, chunk.id_y));
                if (chunk.collidables.stream().noneMatch(obj -> bat.isOverLappingWith((Collidable)obj))){
                    LootableEntitySpawnEvent event = new LootableEntitySpawnEvent(bat);
                    event.emit();
                    LivingEntity entity = event.getEntity();
                    if (entity instanceof GameObject) chunk.gameScene.addObject((GameObject)entity);
                }
            }
        }
        else if (chunk.biome == BiomeType.DESERT){
            if (rnd.nextInt(3) == 0){
                Skeleton skeleton = new Skeleton(chunk.gameScene, chunk.id_x * Chunk.SIZE + 16 * rnd.nextInt(Chunk.SIZE / 16), chunk.id_y * Chunk.SIZE + 16 * rnd.nextInt(Chunk.SIZE / 16));
                skeleton.setLevel(getChunkDifficultyLevel(chunk.id_x, chunk.id_y));
                if (chunk.collidables.stream().noneMatch(obj -> skeleton.isOverLappingWith((Collidable)obj))){
                    LootableEntitySpawnEvent event = new LootableEntitySpawnEvent(skeleton);
                    event.emit();
                    LivingEntity entity = event.getEntity();
                    if (entity instanceof GameObject) chunk.gameScene.addObject((GameObject)entity);
                }
            }
        }
    }

    /**
     * Deletes colliding game object in chunk.
     *
     * @param chunk chunk
     */
    private void deleteCollidingGameObjects(Chunk chunk) {
        chunk.collidables.removeIf(col -> chunk.collidables.stream().anyMatch(
                col2 -> col != col2 && ((Collidable)col).isOverLappingWith((Collidable)col2)));
    }

    /**
     * Gets correct plains tile base on nearby biomes.
     *
     * @param id_x chunk x id coordinate
     * @param id_y chunk y id coordinate
     * @param x x tile position inside chunk
     * @param y y tile position inside chunk
     * @param maxsize maximal number of tiles for chunk side
     * @return texture name
     */
    private String getRightPlainsTile(int id_x, int id_y, int x, int y, int maxsize) {
        if (x == 0 && y == 0 && getBiomeFor(id_x-1, id_y) != BiomeType.DESERT && getBiomeFor(id_x, id_y-1) != BiomeType.DESERT){
            return "plains_nw";
        }
        else if (x == 0 && y == maxsize-1 && getBiomeFor(id_x-1, id_y) != BiomeType.DESERT && getBiomeFor(id_x, id_y+1) != BiomeType.DESERT){
            return "plains_sw";
        }
        else if (x == maxsize-1 && y == 0 && getBiomeFor(id_x+1, id_y) != BiomeType.DESERT && getBiomeFor(id_x, id_y-1) != BiomeType.DESERT){
            return "plains_ne";
        }
        else if (x == maxsize-1 && y == maxsize-1 && getBiomeFor(id_x+1, id_y) != BiomeType.DESERT && getBiomeFor(id_x, id_y+1) != BiomeType.DESERT){
            return "plains_se";
        }
        else if (y == 0 && getBiomeFor(id_x, id_y-1) != BiomeType.DESERT){
            return "plains_n";
        }
        else if (y == maxsize-1 && getBiomeFor(id_x, id_y+1) != BiomeType.DESERT){
            return "plains_s";
        }
        else if (x == 0 && getBiomeFor(id_x-1, id_y) != BiomeType.DESERT){
            return "plains_w";
        }
        else if (x == maxsize-1 && getBiomeFor(id_x+1, id_y) != BiomeType.DESERT){
            return "plains_e";
        }
        else if (x == 0 && y == 0 && getBiomeFor(id_x-1, id_y) == BiomeType.DESERT &&
                getBiomeFor(id_x, id_y-1) == BiomeType.DESERT &&
                getBiomeFor(id_x-1, id_y-1) != BiomeType.DESERT) return "corner_se";
        else if (x == 0 && y == maxsize-1 && getBiomeFor(id_x-1, id_y) == BiomeType.DESERT &&
                getBiomeFor(id_x, id_y+1) == BiomeType.DESERT &&
                getBiomeFor(id_x-1, id_y+1) != BiomeType.DESERT) return "corner_ne";
        else if (x == maxsize-1 && y == 0 && getBiomeFor(id_x+1, id_y) == BiomeType.DESERT &&
                getBiomeFor(id_x, id_y-1) == BiomeType.DESERT &&
                getBiomeFor(id_x+1, id_y-1) != BiomeType.DESERT) return "corner_sw";
        else if (x == maxsize-1 && y == maxsize-1 && getBiomeFor(id_x+1, id_y) == BiomeType.DESERT &&
                getBiomeFor(id_x+1, id_y) == BiomeType.DESERT &&
                getBiomeFor(id_x+1, id_y+1) != BiomeType.DESERT) return "corner_nw";
        return "plains";
    }
}
