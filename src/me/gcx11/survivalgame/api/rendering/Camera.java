package me.gcx11.survivalgame.api.rendering;

import me.gcx11.survivalgame.api.Coord;
import me.gcx11.survivalgame.api.GameObject;
import me.gcx11.survivalgame.api.interfaces.Targetable;
import me.gcx11.survivalgame.world.generator.Chunk;
import me.gcx11.survivalgame.scenes.GameScene;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created on 24.2.2017.
 */
public class Camera {

    private Targetable target;
    private int windowWidth, windowHeight;

    private Chunk previsiousMiddleChunk = null;

    /**
     * Creates new camera with specified size.
     *
     * @param windowWidth camera width
     * @param windowHeight camera height
     */
    public Camera(int windowWidth, int windowHeight){
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }

    /**
     * Changes camera sides length.
     *
     * @param windowWidth new camera width
     * @param windowHeight new camera height
     */
    public void resize(int windowWidth, int windowHeight){
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }

    /**
     * Gets camera width.
     *
     * @return camera width
     */
    public int getWindowWidth(){
        return windowWidth;
    }

    /**
     * Gets camera height
     *
     * @return camera height
     */
    public int getWindowHeight(){
        return windowHeight;
    }

    /**
     * Sets targetable to follow.
     *
     * @param target targetable object
     */
    public void follow(Targetable target){
        this.target = target;
    }

    /**
     * Gets x position of object compared to camera.
     *
     * @param x x coordinate of object
     * @return x position of object compared to camera
     */
    public int recalculateX(int x){
        return windowWidth/2 + x - target.getCenterX();
    }

    /**
     * Gets y position of object compared to camera.
     *
     * @param y y coordinate of object
     * @return y position of object compared to camera
     */
    public int recalculateY(int y){
        return windowHeight/2 + y - target.getCenterY();
    }

    /**
     * Checks whether is targetable current target for camera.
     *
     * @param targetable possible target of camera
     * @return whether is targetable current target for camera
     */
    public boolean isTarget(Targetable targetable){
        return target == targetable;
    }

    /**
     * Returns x coordinate of the center of the camera.
     *
     * @return x coordinate of the center of the camera
     */
    public int getCenterX(){
        return windowWidth/2;
    }

    /**
     * Returns y coordinate of the center of the camera.
     *
     * @return y coordinate of the center of the camera
     */
    public int getCenterY(){
        return windowHeight/2;
    }

    /**
     * Returns x mouse coordinate converted to screen coordinates.
     *
     * @param mouseX x coordinate of mouse
     * @return x mouse coordinate converted to screen coordinate
     */
    public int mouseToGameX(int mouseX){
        return target.getCenterX() + mouseX - windowWidth/2;
    }

    /**
     * Returns y mouse coordinate converted to screen coordinates.
     *
     * @param mouseY y coordinate of mouse
     * @return y mouse coordinate converted to screen coordinate
     */
    public int mouseToGameY(int mouseY){
        return target.getCenterY() + mouseY - windowHeight/2;
    }

    /**
     *  Returns mouse coordinates converted to game chunk.
     *
     * @param mouseX x coordinate of mouse
     * @param mouseY y coordinate of mouse
     * @return mouse coordinates converted to game chunk
     */
    public Chunk mouseToChunk(int mouseX, int mouseY){
        int tempX = mouseToGameX(mouseX);
        int tempY = mouseToGameY(mouseY);
        return Chunk.chunks.getOrDefault(new Coord((tempX - (tempX >>> 31) * (Chunk.SIZE-1)) / Chunk.SIZE,
                (tempY - (tempY >>> 31) * (Chunk.SIZE-1)) / Chunk.SIZE), null);
    }

    /**
     * Gets list of all game objects under the mouse.
     *
     * @param gameScene game scene
     * @param mouseX x coordinate of mouse
     * @param mouseY y coordinate of mouse
     * @return all game objects under the mouse
     */
    public List<GameObject> gameObjectsUnderMouse(GameScene gameScene, int mouseX, int mouseY){
        Chunk chunk = mouseToChunk(mouseX, mouseY);
        int x = mouseToGameX(mouseX);
        int y = mouseToGameY(mouseY);

        return Stream.concat(Stream.concat(chunk.collidables.stream(), chunk.nonCollidables.stream()), gameScene.collidables.stream())
                .filter(obj -> (obj.x < x && x < obj.x + obj.width && obj.y < y && y < obj.y + obj.height))
                .collect(Collectors.toList());
    }

    /**
     * Gets chunk in the middle of game scene.
     *
     * @return chunk in the middle of game scene
     */
    public Chunk getChunkInMiddle(){

        if (previsiousMiddleChunk == null){
            //System.out.println("Yes");
        }
        else if (isTargetInChunk(previsiousMiddleChunk)){
            return previsiousMiddleChunk;
        }
        else if (previsiousMiddleChunk.left != null && isTargetInChunk(previsiousMiddleChunk.left)){
            previsiousMiddleChunk = previsiousMiddleChunk.left;
            return previsiousMiddleChunk;
        }
        else if (previsiousMiddleChunk.right != null && isTargetInChunk(previsiousMiddleChunk.right)){
            previsiousMiddleChunk = previsiousMiddleChunk.right;
            return previsiousMiddleChunk;
        }
        else if (previsiousMiddleChunk.up != null && isTargetInChunk(previsiousMiddleChunk.up)){
            previsiousMiddleChunk = previsiousMiddleChunk.up;
            return previsiousMiddleChunk;
        }
        else if (previsiousMiddleChunk.down != null && isTargetInChunk(previsiousMiddleChunk.down)){
            previsiousMiddleChunk = previsiousMiddleChunk.down;
            return previsiousMiddleChunk;
        }
        else if (previsiousMiddleChunk.left != null && previsiousMiddleChunk.left.up != null && isTargetInChunk(previsiousMiddleChunk.left.up)){
            previsiousMiddleChunk = previsiousMiddleChunk.left.up;
            return previsiousMiddleChunk;
        }
        else if (previsiousMiddleChunk.left != null && previsiousMiddleChunk.left.down != null && isTargetInChunk(previsiousMiddleChunk.left.down)){
            previsiousMiddleChunk = previsiousMiddleChunk.left.down;
            return previsiousMiddleChunk;
        }
        else if (previsiousMiddleChunk.right != null && previsiousMiddleChunk.right.up != null && isTargetInChunk(previsiousMiddleChunk.right.up)){
            previsiousMiddleChunk = previsiousMiddleChunk.right.up;
            return previsiousMiddleChunk;
        }
        else if (previsiousMiddleChunk.right != null && previsiousMiddleChunk.right.down != null && isTargetInChunk(previsiousMiddleChunk.right.down)){
            previsiousMiddleChunk = previsiousMiddleChunk.right.down;
            return previsiousMiddleChunk;
        }

        for (Chunk chunk: GameScene.active){
            if (isTargetInChunk(chunk)) return chunk;
        }

        return null;
    }

    /**
     * Checks whether is camera target in specified chunk.
     *
     * @param chunk game chunk
     * @return whether is camera target in specified chunk
     */
    private boolean isTargetInChunk(Chunk chunk){
        return ((chunk.id_x)*Chunk.SIZE <= target.getCenterX() && target.getCenterX() <= (chunk.id_x+1)* Chunk.SIZE) &&
                ((chunk.id_y)* Chunk.SIZE <= target.getCenterY() && target.getCenterY() <= (chunk.id_y+1)* Chunk.SIZE);
    }

    /**
     * Checks whether is chunk visible by camera.
     *
     * @param chunk game chunk
     * @return whether is chunk visible by camera
     */
    public boolean isChunkVisible(Chunk chunk){
        return (chunk.id_x* Chunk.SIZE <= target.getCenterX()+getCenterX()) &&
                ((chunk.id_x+1) * Chunk.SIZE >= target.getCenterX()-getCenterX()) &&
                (chunk.id_y* Chunk.SIZE <= target.getCenterY()+getCenterY()) &&
                ((chunk.id_y+1)* Chunk.SIZE >= target.getCenterY()-getCenterY());
    }
}
