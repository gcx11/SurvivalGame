package me.gcx11.survivalgame.objects.items;

import me.gcx11.survivalgame.api.GameObject;
import me.gcx11.survivalgame.api.rendering.Textures;
import me.gcx11.survivalgame.objects.nature.Flowers;
import me.gcx11.survivalgame.objects.nature.Plant;
import me.gcx11.survivalgame.objects.nature.Rock;
import me.gcx11.survivalgame.objects.nature.Tree;

import java.awt.*;

/**
 * Created on 11.5.2017.
 */
public enum ItemData {

    WOOD(1, Textures.getTexture("log_item"), Tree.class),
    ROCK(2, Textures.getTexture("rock_item"), Rock.class),
    LEAF(3, Textures.getTexture("plant_item"), Plant.class),
    FLOWERS(4, Textures.getTexture("flower_item"), Flowers.class),
    HEAL_POTION(5, Textures.getTexture("heal_potion_item"), new FoodMetaData().setHealValue(1.0d)),
    ROTTEN_MEAT(6, Textures.getTexture("rotten_meat"), new FoodMetaData().setFoodValue(0.5d).setDamageValue(0.5d)),
    APPLE(7, Textures.getTexture("apple_item"), new FoodMetaData().setFoodValue(0.5d)),
    EGG(8, Textures.getTexture("egg_item"), new FoodMetaData().setFoodValue(1.0d).setDamageValue(0.5d)),
    FRIED_EGG(9, Textures.getTexture("fried_egg_item"), new FoodMetaData().setFoodValue(1.5d)),
    FLOUR(10, Textures.getTexture("flour_item")),
    MEAT_ON_STICK(11, Textures.getTexture("kebab_item"), new FoodMetaData().setFoodValue(2.0d)),
    BONE(12, Textures.getTexture("bone_item")),
    BREAD(13, Textures.getTexture("bread_item"), new FoodMetaData().setFoodValue(4.0d)),
    HAMBURGER(14, Textures.getTexture("hamburger_item"), new FoodMetaData().setFoodValue(10.0d)),
    CHERRIES(15, Textures.getTexture("cherry_item"), new FoodMetaData().setFoodValue(0.5d)),
    BERRIES(16, Textures.getTexture("berries_item"), new FoodMetaData().setFoodValue(0.5d)),
    CANDY(17, Textures.getTexture("candy_item"), new FoodMetaData().setFoodValue(1.5d)),
    CARROT(18, Textures.getTexture("carrot_item"), new FoodMetaData().setFoodValue(0.5d)),
    PORK(19, Textures.getTexture("pork_item"), new FoodMetaData().setFoodValue(0.5d)),
    SOUP(20, Textures.getTexture("soup_item"), new FoodMetaData().setFoodValue(4.0d)),
    REFINED_STONE(21, Textures.getTexture("refined_stone_item")),
    WAND(22, Textures.getTexture("small_wand_item"), new WeaponMetaData().setAttackStrenght(1.5d).setMaxUses(20)),
    SMALL_KNIFE(23, Textures.getTexture("small_knife_item"), new WeaponMetaData().setAttackStrenght(2.0d).setMaxUses(40)),
    SMALL_AXE(24, Textures.getTexture("small_axe_item"), new WeaponMetaData().setAttackStrenght(2.5d).setMaxUses(50)),
    WOODEN_SHIELD(25, Textures.getTexture("wooden_shield_item"), new ArmorMetaData(ArmorType.SHIELD).setArmorPercentage(0.2d).setMaxUses(50)),
    LEATHER_BOOTS(26, Textures.getTexture("leather_boots_item"), new ArmorMetaData(ArmorType.BOOTS).setArmorPercentage(0.05d).setMaxUses(50)),
    LEATHER_GLOVES(27, Textures.getTexture("leather_gloves_item"), new ArmorMetaData(ArmorType.GLOVES).setArmorPercentage(0.05d).setMaxUses(50)),
    GOLD_ROCK(28, Textures.getTexture("gold_ore_item")),
    IRON_ROCK(29, Textures.getTexture("iron_ore_item")),
    GOLD_INGOT(30, Textures.getTexture("gold_ingot_item")),
    IRON_INGOT(31, Textures.getTexture("iron_ingot_item")),
    LEATHER_VEST(32, Textures.getTexture("leather_vest_item"), new ArmorMetaData(ArmorType.BODY).setArmorPercentage(0.05).setMaxUses(50)),
    GOLD_KNIFE(33, Textures.getTexture("gold_knife_item"), new WeaponMetaData().setAttackStrenght(2.5d).setMaxUses(70)),
    IRON_KNIFE(34, Textures.getTexture("iron_knife_item"), new WeaponMetaData().setAttackStrenght(3.0d).setMaxUses(70)),
    GOLD_AXE(35, Textures.getTexture("gold_axe_item"), new WeaponMetaData().setAttackStrenght(3.0d).setMaxUses(90)),
    IRON_AXE(36, Textures.getTexture("iron_axe_item"), new WeaponMetaData().setAttackStrenght(3.5d).setMaxUses(90)),
    STONE_SWORD(37, Textures.getTexture("sword_item"), new WeaponMetaData().setAttackStrenght(3.0d).setMaxUses(120)),
    GOLD_SWORD(38, Textures.getTexture("gold_sword_item"), new WeaponMetaData().setAttackStrenght(3.5d).setMaxUses(150)),
    IRON_SWORD(39, Textures.getTexture("iron_sword_item"), new WeaponMetaData().setAttackStrenght(4.0d).setMaxUses(150)),
    GOLD_GLOVES(40, Textures.getTexture("gold_gloves_item"), new ArmorMetaData(ArmorType.GLOVES).setArmorPercentage(0.1d).setMaxUses(80)),
    IRON_GLOVES(41, Textures.getTexture("iron_gloves_item"), new ArmorMetaData(ArmorType.GLOVES).setArmorPercentage(0.15d).setMaxUses(120)),
    GOLD_SHIELD(42, Textures.getTexture("gold_shield_item"),  new ArmorMetaData(ArmorType.SHIELD).setArmorPercentage(0.25d).setMaxUses(80)),
    IRON_SHIELD(43, Textures.getTexture("iron_shield_item"), new ArmorMetaData(ArmorType.SHIELD).setArmorPercentage(0.3d).setMaxUses(120)),
    GOLD_HELMET(44, Textures.getTexture("gold_helmet_item"), new ArmorMetaData(ArmorType.HELMET).setArmorPercentage(0.1d).setMaxUses(80)),
    IRON_HELMET(45, Textures.getTexture("iron_helmet_item"), new ArmorMetaData(ArmorType.HELMET).setArmorPercentage(0.15d).setMaxUses(120)),
    GOLD_BODY_ARMOR(46, Textures.getTexture("gold_body_item"), new ArmorMetaData(ArmorType.BODY).setArmorPercentage(0.15d).setMaxUses(80)),
    IRON_BODY_ARMOR(47, Textures.getTexture("iron_body_item"), new ArmorMetaData(ArmorType.BODY).setArmorPercentage(0.2d).setMaxUses(120)),
    GOLD_BOOTS(48, Textures.getTexture("gold_boots_item"), new ArmorMetaData(ArmorType.BOOTS).setArmorPercentage(0.1d).setMaxUses(80)),
    IRON_BOOTS(49, Textures.getTexture("iron_boots_item"), new ArmorMetaData(ArmorType.BOOTS).setArmorPercentage(0.15d).setMaxUses(120));





    private int id;
    private Image texture;
    private Class<? extends GameObject> klazz;
    private ItemMetaData metaData = null;

    /**
     * Creates new ItemData with id and texture.
     *
     * @param id item id
     * @param texture image of item
     */
    ItemData(int id, Image texture){
        this.id = id;
        this.texture = texture;
        this.metaData = null;
        this.klazz = null;
    }

    /**
     * Creates new ItemData with id, texture and meta data.
     *
     * @param id item id
     * @param texture image of item
     * @param metaData meta data
     */
    ItemData(int id, Image texture, ItemMetaData metaData){
        this.id = id;
        this.texture = texture;
        this.metaData = metaData;
        this.klazz = null;
    }

    /**
     * Creates new ItemData with id, texture and game object subclass.
     *
     * @param id item id
     * @param texture image of item
     * @param klazz GameObject subclass
     */
    ItemData(int id, Image texture, Class<? extends GameObject> klazz){
        this.id = id;
        this.texture = texture;
        this.klazz = klazz;
        this.metaData = null;
    }

    /**
     * Creates new ItemData with id, texture, game object subclass and metadata.
     *
     * @param id item id
     * @param texture image of item
     * @param klazz GameObject subclass
     * @param metaData item metadata
     */
    ItemData(int id, Image texture, Class<? extends GameObject> klazz, ItemMetaData metaData){
        this.id = id;
        this.texture = texture;
        this.klazz = klazz;
        this.metaData = metaData;
    }

    /**
     * Gets item metadata.
     *
     * @return item metadata
     */
    public ItemMetaData getMetaData(){
        return metaData;
    }

    /**
     * Checks whether is game object convertable to some item.
     *
     * @param object game object
     * @return whether is game object convertable to some item
     */
    public static boolean isConvertable(GameObject object){
        for (ItemData itemData: ItemData.values()){
            if (itemData.klazz == object.getClass()) return true;
        }
        return false;
    }

    /**
     * Gets ItemData id.
     *
     * @return ItemData id
     */
    public int getId(){
        return id;
    }

    /**
     * Gets texture of ItemData.
     *
     * @return texture of ItemData
     */
    public Image getTexture(){
        return texture;
    }
}
