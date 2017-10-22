package me.gcx11.survivalgame.objects.entities;

import me.gcx11.survivalgame.api.interfaces.LivingEntity;
import me.gcx11.survivalgame.scenes.GameScene;
import me.gcx11.survivalgame.api.*;
import me.gcx11.survivalgame.api.animations.EntityAnimation;
import me.gcx11.survivalgame.api.interfaces.Collidable;
import me.gcx11.survivalgame.api.interfaces.Targetable;
import me.gcx11.survivalgame.world.generator.Chunk;

import java.awt.*;
import java.util.*;

/**
 * Created on 8.5.2017.
 */
public abstract class Entity extends GameObject implements Collidable, Targetable, LivingEntity {

    protected Random rnd = new Random();

    protected float xPosAcc = 0f;
    protected float yPosAcc = 0f;

    protected int SPEED = 50;
    protected double minFollowDistance = 30.0d;
    protected double maxFollowDistance = 90.0d;
    protected double maxAttackRange = 50.0d;
    protected double maxHealth = 10.0d;
    protected double currentHealth = maxHealth;

    protected double baseMaxHealth = maxHealth;

    private float currentAttackDelay = 0.0f;
    protected float attackDelay = 2.0f;
    protected double attackStrenght = 1.0d;

    private boolean isWandering = false;
    private int ticksToTired = 50;
    private int xDestination, yDestination;

    protected Image corpseTexture;

    protected EntityAnimation entityAnimation;

    protected float timeToDisappear = 10.0f;

    protected float xStep = 0;
    protected float yStep = 0;

    /**
     * Creates new entity.
     *
     * @param gameScene game scene
     * @param x x coordinate on the map
     * @param y y coordinate on the map
     * @param entityAnimation entity animation
     * @param corpseTexture image which is being used when entity is considered to be dead
     */
    public Entity(GameScene gameScene, int x, int y, EntityAnimation entityAnimation, Image corpseTexture){
        super(gameScene);
        this.x = x;
        this.y = y;
        this.entityAnimation = entityAnimation;
        this.width = entityAnimation.getTexture().getWidth(null);
        this.height = entityAnimation.getTexture().getHeight(null);
        this.corpseTexture = corpseTexture;
    }

    @Override
    /**
     * Updates entity AI and position.
     *
     * @see me.gcx11.survivalgame.api.GameObject
     */
    public void update(float delta) {
        if (isDead()){
            timeToDisappear -= delta;
            if (timeToDisappear <= 0.0f){
                gameScene.deleteLater(this);
            }
            return;
        }
        Player player = (Player) gameScene.collidables.stream().filter(obj -> obj instanceof Player).findAny().orElse(null);
        double distance;
        if (player != null){
            distance = Math.hypot(Math.abs(getCenterX() - player.getCenterX()), Math.abs(getCenterY() - player.getCenterY()));
        }
        else distance = 0;
        int xDirection = 0;
        int yDirection = 0;
        if (player != null && distance > minFollowDistance && distance < maxFollowDistance){
            isWandering = false;
            double angle = Math.atan2(getCenterX() - player.getCenterX(), getCenterY() - player.getCenterY());
            if (angle > -Math.PI/2d && angle < Math.PI/2d) yDirection = -1;
            else if (angle > Math.PI/2d || angle < -Math.PI/2d) yDirection = 1;

            if (angle > 0 && angle < Math.PI) xDirection = -1;
            else if (angle < 0 && angle > -Math.PI) xDirection = 1;
        }
        else{
            if (!isWandering && rnd.nextInt(1000) == 0){
                isWandering = true;
                ticksToTired = 50;
                xDestination = x + rnd.nextInt(101) - 50;
                yDestination = y + rnd.nextInt(101) - 50;
            }
            else if (isWandering && ticksToTired-- > 0 && x != xDestination && y != yDestination ){
                double angle = Math.atan2(x - xDestination, y - yDestination);
                if (angle > -Math.PI/2d && angle < Math.PI/2d) yDirection = -1;
                else if (angle > Math.PI/2d || angle < -Math.PI/2d) yDirection = 1;

                if (angle > 0 && angle < Math.PI) xDirection = -1;
                else if (angle < 0 && angle > -Math.PI) xDirection = 1;
            }
            else{
                isWandering = false;
            }
        }
        if (player != null && distance < maxAttackRange){
            tryAttackEntity(delta, player);
        }
        else{
            resetAttackDelay();
        }

        updatePosition(delta, xDirection, yDirection);
    }

    /**
     * Resets attack delay.
     */
    private void resetAttackDelay() {
        currentAttackDelay = 0;
    }

    /**
     * Attacks entity when certain amount of time have passed.
     *
     * @param delta time in seconds from last update
     * @param entity entity to attack
     */
    private void tryAttackEntity(float delta, Entity entity){
        if (currentAttackDelay < attackDelay){
            currentAttackDelay += delta;
        }
        else{
            attackEntity(entity);
            resetAttackDelay();
        }
    }

    /**
     * Returns attack strength of entity.
     *
     * @return attack strength
     */
    protected double getAttackStrength(){
        return attackStrenght;
    }

    /**
     * Attacks entity
     *
     * @param entity
     */
    protected void attackEntity(Entity entity) {
        entity.applyDamage(getAttackStrength());
    }

    /**
     * Heals entity with specified amount of health.
     *
     * @param amount amount of health to be healed
     */
    public void heal(double amount){
        currentHealth += amount;
        if (currentHealth > maxHealth) currentHealth = maxHealth;
    }

    /**
     * Applies damage to entity.
     *
     * @param damage amount of damage to be taken by entity
     */
    public void applyDamage(double damage){
        currentHealth -= damage;
        if (currentHealth <= 0.0d) currentHealth = 0.0d;
    }

    /**
     * Checks whether is entity dead.
     *
     * @return whether is entity dead
     */
    public boolean isDead(){
        return currentHealth <= 0.0d;
    }

    /**
     * Updates position of entity and resolves possible collisions.
     *
     * @param delta time passed since last game loop update
     * @param xDirection possible values: -1 entity is going left, 1 entity is going right,
     *                   otherwise entity does not any move on x-axis
     * @param yDirection possible values: -1 entity is going up, 1 entity is going down,
     *                   otherwise entity does not any move on y-axis
     */
    public void updatePosition(float delta, int xDirection, int yDirection){
        boolean wasPressedSomething = false;

        if (yDirection == -1){
            move_up(delta);
            if (hasAnyCollision()){
                y -= yStep; isWandering = false;
            }
            if (xDirection == 0) entityAnimation.changeState(1);

            wasPressedSomething = true;
        }
        else if (yDirection == 1){
            move_down(delta);
            if (hasAnyCollision()){
                y -= yStep; isWandering = false;
            }
            if (xDirection == 0) entityAnimation.changeState(0);
            wasPressedSomething = true;
        }

        if (xDirection == -1){
            move_left(delta);
            if (hasAnyCollision()){
                x -= xStep; isWandering = false;
            }
            entityAnimation.changeState(2);
            wasPressedSomething = true;
        }
        else if (xDirection == 1){
            move_right(delta);
            if (hasAnyCollision()){
                x -= xStep; isWandering = false;
            }
            entityAnimation.changeState(3);
            wasPressedSomething = true;
        }

        if (!wasPressedSomething) entityAnimation.resetFrame();
    }

    /**
     * Checks whether entity has got any active collision.
     *
     * @return whether entity has got any active collision
     */
    protected boolean hasAnyCollision() {
        for (GameObject obj: gameScene.collidables){
            if (obj == this) continue;
            if (isOverLappingWith((Collidable) obj)){
                return true;
            }
        }
        for (Chunk chunk: GameScene.active) {
            for (GameObject obj: chunk.collidables) {
                if (obj == this) continue;
                if (isOverLappingWith((Collidable) obj)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Does move left.
     *
     * @param delta time passed since last game loop update
     */
    private void move_left(float delta){
        xPosAcc -= delta*SPEED;
        xStep = (int) xPosAcc;
        xPosAcc -= xStep;
        x += xStep;
    }

    /**
     * Does move right.
     *
     * @param delta time passed since last game loop update
     */
    private void move_right(float delta){
        xPosAcc += delta*SPEED;
        xStep = (int) xPosAcc;
        xPosAcc -= xStep;
        x += xStep;
    }

    /**
     * Does move up.
     *
     * @param delta time passed since last game loop update
     */
    private void move_up(float delta){
        yPosAcc -= delta*SPEED;
        yStep = (int) yPosAcc;
        yPosAcc -= yStep;
        y += yStep;
    }

    /**
     * Does move down.
     *
     * @param delta time passed since last game loop update
     */
    private void move_down(float delta){
        yPosAcc += delta*SPEED;
        yStep = (int) yPosAcc;
        yPosAcc -= yStep;
        y += yStep;
    }

    @Override
    public void render(Graphics graphics) {
        Image texture = isDead() ? corpseTexture : entityAnimation.getTexture();
        graphics.drawImage(texture, gameScene.camera.recalculateX(x), gameScene.camera.recalculateY(y), null);
    }

    @Override
    public int getCenterX() {
        return x + width/2;
    }

    @Override
    public int getCenterY() {
        return y + height/2;
    }

    @Override
    public int getCollidableX() {
        return x;
    }

    @Override
    public int getCollidableY() {
        return y;
    }

    @Override
    public int getCollidableWidth() {
        return width;
    }

    @Override
    public int getCollidableHeight() {
        return height;
    }

    @Override
    public double getHealth() {
        return currentHealth;
    }

    @Override
    public double getMaxHealth() {
        return maxHealth;
    }

    @Override
    public void setHealth(double amount) {
        currentHealth = amount;
    }

    @Override
    public void setMaxHealth(double amount) {
        maxHealth = amount;
        if (currentHealth > maxHealth) currentHealth = maxHealth;
    }
}
