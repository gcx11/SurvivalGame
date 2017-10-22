package me.gcx11.survivalgame.objects.entities;

import me.gcx11.survivalgame.api.*;
import me.gcx11.survivalgame.api.animations.EntityAnimation;
import me.gcx11.survivalgame.api.events.PlayerMoveEvent;
import me.gcx11.survivalgame.api.rendering.Textures;
import me.gcx11.survivalgame.objects.inventories.*;
import me.gcx11.survivalgame.objects.items.*;
import me.gcx11.survivalgame.objects.nature.Flowers;
import me.gcx11.survivalgame.objects.nature.Plant;
import me.gcx11.survivalgame.objects.nature.Rock;
import me.gcx11.survivalgame.objects.nature.Tree;
import me.gcx11.survivalgame.scenes.DeathScene;
import me.gcx11.survivalgame.scenes.GameScene;
import me.gcx11.survivalgame.world.generator.Chunk;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created on 24.2.2017.
 */
public class Player extends Entity {

    private final int SPEED = 60;

    public Inventory inventory = new Backpack(this, 530, 280, 8);
    public CraftingInventory craftingInventory = new CraftingInventory(this, 20, 150);
    public HotBarInventory hotBarInventory = new HotBarInventory(this, 240, 360);

    protected double defaultAttackStrenght = 1.0d;

    protected double maxHunger = 10.0f;
    protected double currentHunger = maxHunger;
    protected final double HUNGER_DRAIN_IDLE = 0.01;
    protected final double HUNGER_DRAIN_WALKING = 0.02;
    protected final double HUNGER_DRAIN_RUNNING = 0.06;
    protected final double HUNGER_DRAIN_WOUNDED = 0.08;

    protected final double HUNGER_HEALING = 0.1;
    protected final double HUNGER_DAMAGE = 0.5;

    private boolean wasPressedMoveButton;

    private int xStep = 0;
    private int yStep = 0;

    public Player(GameScene gameScene){
        super(gameScene, 50, 50, newAnimation(), Textures.getTexture("dead_skeleton"));
    }

    private static EntityAnimation newAnimation(){
        return new EntityAnimation(new Image[][]{
                new Image[]{Textures.getTexture("player_down"), Textures.getTexture("player_down_2"),
                        Textures.getTexture("player_down_3"), Textures.getTexture("player_down_4")},
                new Image[]{Textures.getTexture("player_up"), Textures.getTexture("player_up_2"),
                        Textures.getTexture("player_up_3"), Textures.getTexture("player_up_4")},
                new Image[]{Textures.getTexture("player_left"), Textures.getTexture("player_left_2"),
                        Textures.getTexture("player_left_3"), Textures.getTexture("player_left_4")},
                new Image[]{Textures.getTexture("player_right"), Textures.getTexture("player_right_2"),
                        Textures.getTexture("player_right_3"), Textures.getTexture("player_right_4")},
        });
    }

    @Override
    public void update(float delta) {
        if (isDead()){
            onPlayerDeath();
        }
        updatePosition(delta);
        updatePsychiology(delta);
        handleClick(delta);
        handleKeys(delta);
    }

    /**
     * Called when player dies in-game.
     */
    private void onPlayerDeath() {
        gameScene.gamePanel.changeScene(new DeathScene(gameScene.gamePanel));
    }

    /**
     * Updates health and hunger status.
     *
     * @param delta how much seconds passed since last game loop update
     */
    private void updatePsychiology(float delta) {
        double hungerModifier;
        if (isWounded()){
            hungerModifier = HUNGER_DRAIN_WOUNDED;
            if (!isHungry()) heal(delta*HUNGER_HEALING);
        }
        else if (isRunning()) hungerModifier =  HUNGER_DRAIN_RUNNING;
        else if (isMoving()) hungerModifier = HUNGER_DRAIN_WALKING;
        else hungerModifier = HUNGER_DRAIN_IDLE;
        currentHunger -= delta*hungerModifier;
        if (isStarving()){
            currentHunger = 0.0d;
            applyDamage(delta*HUNGER_DAMAGE);
        }

    }

    /**
     * Checks whether is player starving.
     *
     * @return whether is player starving
     */
    private boolean isStarving(){
        return currentHunger <= 0.0d;
    }

    /**
     * Checks whether is player hungry.
     *
     * @return whether is player hungry
     */
    private boolean isHungry() {
        return currentHunger < maxHunger / 2.0d;
    }

    /**
     * Checks whether is player wounded.
     *
     * @return whether is player wounded
     */
    private boolean isWounded(){
        return currentHealth < maxHealth;
    }

    /**
     * Checks whether is player trying to run.
     *
     * @return whether is player trying to run
     */
    private boolean isTryingToRun(){
        return gameScene.keyboardListener.isPressedShift() && isMoving();
    }

    /**
     * Checks whether is player running.
     *
     * @return whether is player running
     */
    private boolean isRunning(){
        return isTryingToRun() && (currentHunger >= maxHunger/2d);
    }

    /**
     * Checks whether is some move button pressed.
     *
     * @return whether is some move button pressed
     */
    private boolean isMoving(){
        return wasPressedMoveButton;
    }

    /**
     * Feeds player with specified amount of food.
     *
     * @param amount amount of food
     */
    public void feed(double amount){
        currentHunger += amount;
        if (currentHunger > maxHunger) currentHunger = maxHunger;
    }

    /**
     * Updates position of player based on pressed buttons and resolves collisions.
     *
     * @param delta time in seconds passed since last game loop update
     */
    private void updatePosition(float delta){
        wasPressedMoveButton = false;
        int old_position_x = x;
        int old_position_y = y;

        if (gameScene.keyboardListener.isPressed('w')){
            wasPressedMoveButton = true;
            move_up(delta);
            if (hasAnyCollision()){
                //y++;
                y -= yStep;
            }
            if (!gameScene.keyboardListener.isPressed('a') && !gameScene.keyboardListener.isPressed('d')) entityAnimation.changeState(1, isRunning());

        }
        else if (gameScene.keyboardListener.isPressed('s')){
            wasPressedMoveButton = true;
            move_down(delta);
            if (hasAnyCollision()){
                //y--;
                y -= yStep;
            }
            if (!gameScene.keyboardListener.isPressed('a') && !gameScene.keyboardListener.isPressed('d')) entityAnimation.changeState(0, isRunning());
        }

        if (gameScene.keyboardListener.isPressed('a')){
            wasPressedMoveButton = true;
            move_left(delta);
            if (hasAnyCollision()){
                //x++;
                x -= xStep;
            }
            entityAnimation.changeState(2, isRunning());
        }
        else if (gameScene.keyboardListener.isPressed('d')){
            wasPressedMoveButton = true;
            move_right(delta);
            if (hasAnyCollision()){
                //x--;
                x -= xStep;
            }
            entityAnimation.changeState(3, isRunning());
        }

        if (!wasPressedMoveButton){
            entityAnimation.resetFrame();
        }
        else{
            PlayerMoveEvent playerMoveEvent = new PlayerMoveEvent(this, x - old_position_x, y - old_position_y);
            playerMoveEvent.emit();
        }
    }

    /**
     * Does move left.
     *
     * @param delta time passed since last game loop update
     */
    private void move_left(float delta){
        xPosAcc -= isRunning() ? delta*(SPEED+40) : delta*SPEED;
        xStep = (int) xPosAcc;
        xPosAcc -= xStep;
        x += xStep;
        /*if (xPosAcc < -1){
            xPosAcc = (xPosAcc + 1) % 1f;
            x--;
        }
        else if (xPosAcc > 1) xPosAcc = 0;*/
    }

    /**
     * Does move right.
     *
     * @param delta time passed since last game loop update
     */
    private void move_right(float delta){
        xPosAcc += isRunning() ? delta*(SPEED+40) : delta*SPEED;
        xStep = (int) xPosAcc;
        xPosAcc -= xStep;
        x += xStep;
        /*if (xPosAcc > 1){
            xPosAcc = (xPosAcc - 1) % 1f;
            x++;
        }
        else if (xPosAcc < -1) xPosAcc = 0;*/
    }

    /**
     * Does move up.
     *
     * @param delta time passed since last game loop update
     */
    private void move_up(float delta){
        yPosAcc -= isRunning() ? delta*(SPEED+40) : delta*SPEED;
        yStep = (int) yPosAcc;
        yPosAcc -= yStep;
        y += yStep;
        /*if (yPosAcc < -1){
            yPosAcc = (yPosAcc + 1) % 1f;
            y--;
        }
        else if (yPosAcc > 1) yPosAcc = 0;*/
    }

    /**
     * Does move down.
     *
     * @param delta time passed since last game loop update
     */
    private void move_down(float delta){
        yPosAcc += isRunning() ? delta*(SPEED+40) : delta*SPEED;
        yStep = (int) yPosAcc;
        yPosAcc -= yStep;
        y += yStep;
        /*if (yPosAcc > 1){
            yPosAcc = (yPosAcc - 1) % 1f;
            y++;
        }
        else if (yPosAcc < -1) yPosAcc = 0;*/
    }


    /**
     * Resolves pressed keys for non-movement related things.
     *
     * @param delta time passed since last game loop update
     */
    private void handleKeys(float delta) {
        if (gameScene.keyboardListener.wasPressed('i')){
            if (inventory.isOpened()) inventory.close();
            else inventory.open();
        }
        if (gameScene.keyboardListener.wasPressed('c')){
            if (craftingInventory.isOpened()) craftingInventory.close();
            else craftingInventory.open();
        }
        else if (gameScene.keyboardListener.wasPressed('e')){
            List<GameObject> nearest = getNearbyGameObjects(30.0d);
            resolveInteraction(delta, Arrays.asList(nearest.get(rnd.nextInt(nearest.size()))));
        }
    }

    /**
     * Handles click on screen.
     *
     * @param delta time passed in seconds since last game loop update
     */
    private void handleClick(float delta) {
        if (inventory.handleUse()) return;
        else if (craftingInventory.handleUse()) return;
        else if (hotBarInventory.handleUse()) return;
        boolean clickedSomeInv = false;
        for (GameObject obj: gameScene.collidables){
            if (obj instanceof LootableEntity){
                LootableEntity lootableEntity = (LootableEntity) obj;
                LootingInventory lootInv = lootableEntity.lootingInventory;
                if (lootInv == null) continue;
                else if (lootInv.isOpened()){
                    if (!clickedSomeInv && lootInv.wasClickedInside()){
                        lootInv.handleUse();
                        clickedSomeInv = true;
                    }
                }
            }
        }
        if (clickedSomeInv) return;
        if (gameScene.mouseInfo.wasLeftClicked()){
            for (GameObject obj: gameScene.collidables){
                if (obj instanceof LootableEntity) {
                    LootableEntity lootableEntity = (LootableEntity) obj;
                    LootingInventory lootInv = lootableEntity.lootingInventory;
                    if (lootInv != null && lootInv.isOpened()){
                        lootInv.close();
                    }
                }
            }
            List<GameObject> gameObjects = gameScene.camera.gameObjectsUnderMouse(gameScene, gameScene.mouseInfo.getX(), gameScene.mouseInfo.getY());
            resolveInteraction(delta, gameObjects);
        }
    }

    /**
     * Decreases armor durability.
     */
    private void decreaseArmorUses() {
        ArmorType[] armorTypes = {ArmorType.SHIELD, ArmorType.HELMET, ArmorType.BODY, ArmorType.BOOTS, ArmorType.GLOVES};
        for (ArmorType armorType: armorTypes){
            ArmorItem item = hotBarInventory.getArmor(armorType);
            if (item != null) item.decreaseUses();
        }
    }

    /**
     * Returns total armor percentage of current armor worn by player.
     *
     * @return total armor percentage of current worn armor
     */
    private double getTotalArmor(){
        double totalArmor = 0.0d;
        ArmorType[] armorTypes = {ArmorType.SHIELD, ArmorType.HELMET, ArmorType.BODY, ArmorType.BOOTS, ArmorType.GLOVES};
        for (ArmorType armorType: armorTypes){
            ArmorItem item = hotBarInventory.getArmor(armorType);
            if (item != null){
                totalArmor += item.getArmorPercentage();
            }
        }
        if (totalArmor > 1.0d) totalArmor = 1.0d;
        return totalArmor;
    }

    @Override
    /**
     * Deals damage to player.
     *
     * @param damage to be applied to player
     */
    public void applyDamage(double damage) {
        double protection = getTotalArmor();
        decreaseArmorUses();
        hotBarInventory.removeBrokenArmor();
        damage -= damage*protection;
        super.applyDamage(damage);
    }

    @Override
    /**
     * Gets attack strength of player.
     *
     * @return attack strength of player
     */
    protected double getAttackStrength(){
        WeaponItem weaponItem = hotBarInventory.getWeapon();
        if (weaponItem == null) return defaultAttackStrenght;
        else return weaponItem.getAttackStrenght();
    }

    @Override
    /**
     * Attacks another entity.
     *
     * @param entity entity to be attacked
     */
    protected void attackEntity(Entity entity) {
        if (!entity.isDead()){
            WeaponItem weaponItem = hotBarInventory.getWeapon();
            if (weaponItem != null){
                weaponItem.decreaseUses();
                if (weaponItem.isBroken()) hotBarInventory.removeWeapon();
            }
        }
        super.attackEntity(entity);
    }

    /**
     * Resolves interaction with game objects.
     *
     * @param delta time passed in seconds since last game update
     * @param gameObjects collection of game objects to be resolved
     */
    private void resolveInteraction(float delta, Collection<GameObject> gameObjects) {
        for (GameObject obj: gameObjects) {
            if (!isReacheable(obj)) continue;
            if (obj instanceof Entity && obj != this){
                Entity entity = (Entity) obj;
                if (!entity.isDead()) attackEntity(entity);
                else{
                    if (entity instanceof LootableEntity){
                        LootableEntity lootableEntity = (LootableEntity) entity;
                        lootableEntity.handleClickOnCorpse();
                    }
                }
            }
            else if (obj instanceof Plant) {
                boolean wasThereSpace = inventory.tryAddGameObject(obj);
                if (wasThereSpace) {
                    ((Plant)obj).destroy();
                }
            }
            else if (obj instanceof Flowers){
                boolean wasThereSpace = inventory.tryAddGameObject(obj);
                if (wasThereSpace) gameScene.deleteLater(obj);
            }
            else if (obj instanceof Rock){
                Rock rock = (Rock) obj;
                rock.damage(delta);
                if (rock.isDestroyed()){
                    boolean wasThereSpace = inventory.tryAddGameObject(obj);
                    if (wasThereSpace) gameScene.deleteLater(obj);
                }
            }
            else if (obj instanceof Tree){
                Tree tree = (Tree) obj;
                tree.damage(delta);
                if (tree.isDestroyed() && !tree.wasHarvested){
                    boolean wasThereSpace = inventory.tryAddGameObject(obj);
                    if (wasThereSpace){
                        tree.harvest();
                        if (rnd.nextInt(10) == 0) inventory.addItem(new Item(ItemData.APPLE));
                    }
                }
            }
        }
    }

    /**
     * Gets distance from another game object.
     *
     * @param obj game object
     * @return distance from another game object
     */
    private double getDistanceToGameObject(GameObject obj){
        return Math.hypot(Math.abs(x - obj.x - obj.width/2), Math.abs(y - obj.y - obj.height/2));
    }

    /**
     * Returns whether is game object in reachable area.
     *
     * @param obj game object
     * @param distance distance from mouse
     * @return whether is game object in reachable area
     */
    private boolean isReacheable(GameObject obj, double distance) {
        return getDistanceToGameObject(obj) <= distance;
    }

    /**
     * Returns whether is game object reachable within default distance.
     *
     * @param obj game object
     * @return whether is game object reachable within default distance
     */
    private boolean isReacheable(GameObject obj) {
        return isReacheable(obj, 50.0d);
    }

    /**
     * Gets list of all reachable objects within specified distance.
     *
     * @param distance max distance from mouse
     * @return list of all reachable objects within specified distance
     */
    private List<GameObject> getNearbyGameObjects(double distance){
        List<GameObject> result = new ArrayList<>();
        for (Chunk chunk: GameScene.active){
            for (GameObject obj: chunk.collidables){
                if (isReacheable(obj, distance)) result.add(obj);
            }
            for (GameObject obj: chunk.nonCollidables){
                if (isReacheable(obj, distance)) result.add(obj);
            }
        }
        for (GameObject obj: gameScene.collidables){
            if (isReacheable(obj, distance)) result.add(obj);
        }
        return result;
    }

    @Override
    public void render(Graphics graphics) {
        super.render(graphics);
        //graphics.drawString(getTotalArmor() + "", scene.camera.recalculateX(x), scene.camera.recalculateY(y));
        renderHearts(graphics);
        renderHunger(graphics);
        inventory.render(graphics);
        craftingInventory.render(graphics);
        hotBarInventory.render(graphics);
    }

    /**
     * Renders health bar.
     *
     * @param graphics Graphics object
     */
    private void renderHearts(Graphics graphics) {
        int hearts = (int)Math.ceil(currentHealth);
        for (int i=0; i < hearts; i++)
            graphics.drawImage(Textures.getTexture("heart_full"), 20+16*i, 10, null);
        int maxHearts = (int)Math.ceil(maxHealth);
        for (int i=hearts; i<maxHearts; i++){
            graphics.drawImage(Textures.getTexture("heart_empty"), 20+16*i, 10, null);
        }
    }

    /**
     * Renders hunger bar.
     *
     * @param graphics Graphics object
     */
    private void renderHunger(Graphics graphics) {
        int hungers = (int)Math.ceil(currentHunger);
        for (int i=0; i < hungers; i++)
            graphics.drawImage(Textures.getTexture("hunger_bar"), 20+16*i, 26, null);
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
}
