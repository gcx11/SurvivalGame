package me.gcx11.survivalgame.api.rendering;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Created on 27.4.2017.
 */
public class Textures {

    private static BufferedImage overworld, objects, character, items, characters, rpgItems, deadCharacters;
    private static HashMap<String, Image> textures = new HashMap<>();

    private static boolean wasLoaded = false;

    /**
     * Lazily loads and returns image with specified name.
     *
     * @param name name of image asset
     * @return image with specified name
     */
    public static Image getTexture(String name){
        if (!wasLoaded){
            System.out.println("Loading images...");
            overworld = loadImage("Overworld.png");
            objects = loadImage("objects.png");
            character = loadImage("character.png");
            items = loadImage("roguelikeitems.png");
            characters = loadImage("characters.png");
            rpgItems = loadImage("rsz_rpg_items.png");
            deadCharacters = loadImage("dead_characters.png");
            loadTextures();
            wasLoaded = true;
        }
        return textures.getOrDefault(name, null);
    }

    /**
     * Loads image from filename in .jar file.
     *
     * @param fileName name of file with image atlas
     * @return buffered image or null when loading fails
     * @see java.awt.image.BufferedImage
     */
    private static BufferedImage loadImage(String fileName){
        try{
            InputStream inputStream = Textures.class.getClassLoader().getResourceAsStream(fileName);
            if (inputStream != null) return ImageIO.read(inputStream);
        }
        catch (IOException e){}
        return null;
    }

    /**
     * Loads all textures and their names.
     */
    private static void loadTextures() {
        textures.put("grass_tiny", overworld.getSubimage(0, 0, 16, 16));
        textures.put("grass_tiny_2", overworld.getSubimage(16*7, 16*9, 16, 16));
        textures.put("grass_tiny_3", overworld.getSubimage(16*8, 16*9, 16, 16));
        textures.put("grass_tiny_4", overworld.getSubimage(16*7, 16*10, 16, 16));
        textures.put("grass_tiny_5", overworld.getSubimage(16*8, 16*10, 16, 16));
        textures.put("trunk", overworld.getSubimage(16*31, 16*3, 32, 32));
        textures.put("heart_full", objects.getSubimage(16*4, 0, 16, 16));
        textures.put("heart_empty", objects.getSubimage(16*8, 0, 16, 16));
        textures.put("player_down", character.getSubimage(0, 5, 16, 23));
        textures.put("player_down_2", character.getSubimage(16, 5, 16, 23));
        textures.put("player_down_3", character.getSubimage(32, 5, 16, 23));
        textures.put("player_down_4", character.getSubimage(48, 5, 16, 23));
        textures.put("player_up", character.getSubimage(0, 5+64, 16, 23));
        textures.put("player_up_2", character.getSubimage(16, 5+64, 16, 23));
        textures.put("player_up_3", character.getSubimage(32, 5+64, 16, 23));
        textures.put("player_up_4", character.getSubimage(48, 5+64, 16, 23));
        textures.put("player_left", character.getSubimage(0, 5+96, 16, 23));
        textures.put("player_left_2", character.getSubimage(16, 5+96, 16, 23));
        textures.put("player_left_3", character.getSubimage(32, 5+96, 16, 23));
        textures.put("player_left_4", character.getSubimage(48, 5+96, 16, 23));
        textures.put("player_right", character.getSubimage(0, 5+32, 16, 23));
        textures.put("player_right_2", character.getSubimage(16, 5+32, 16, 23));
        textures.put("player_right_3", character.getSubimage(32, 5+32, 16, 23));
        textures.put("player_right_4", character.getSubimage(48, 5+32, 16, 23));
        textures.put("flowers", overworld.getSubimage(0, 16*8, 16, 16));
        textures.put("flowers_2", overworld.getSubimage(16, 16*8, 16, 16));
        textures.put("flowers_3", overworld.getSubimage(48, 16*11, 16, 16));
        textures.put("flowers_4", overworld.getSubimage(48, 16*12, 16, 16));
        textures.put("tree_green", overworld.getSubimage(80, 16*16, 32, 32));
        for (int i=0; i<=7; i++){
            textures.put("tree_" + i, objects.getSubimage(i*32, 160-1, 32, 32));
            textures.put("tree_" + (i + 8), objects.getSubimage(i*32, 192-1, 32, 32));
        }
        textures.put("tree", objects.getSubimage(8*32, 192, 32, 32));
        textures.put("rock", overworld.getSubimage(16*9, 16*11, 32, 32));
        textures.put("dirt", overworld.getSubimage(16*12, 16*13, 16, 16));
        textures.put("plains_nw", overworld.getSubimage(0, 16*3, 16, 16));
        textures.put("plains_n", overworld.getSubimage(16, 16*3, 16, 16));
        textures.put("plains_ne", overworld.getSubimage(32, 16*3, 16, 16));
        textures.put("plains_w", overworld.getSubimage(0, 16*4, 16, 16));
        textures.put("plains", overworld.getSubimage(16, 16*4, 16, 16));
        textures.put("plains_e", overworld.getSubimage(32, 16*4, 16, 16));
        textures.put("plains_sw", overworld.getSubimage(0, 16*5, 16, 16));
        textures.put("plains_s", overworld.getSubimage(16, 16*5, 16, 16));
        textures.put("plains_se", overworld.getSubimage(32, 16*5, 16, 16));

        textures.put("corner_nw", overworld.getSubimage(0, 16*6, 16, 16));
        textures.put("corner_ne", overworld.getSubimage(16, 16*6, 16, 16));
        textures.put("corner_sw", overworld.getSubimage(0, 16*7, 16, 16));
        textures.put("corner_se", overworld.getSubimage(16, 16*7, 16, 16));

        textures.put("plant", objects.getSubimage(16*2, 0, 16, 16));
        textures.put("plant_destroy", objects.getSubimage(72, 24, 16, 16));
        textures.put("plant_destroy_2", objects.getSubimage(102, 29, 16, 16));
        textures.put("plant_destroy_3", objects.getSubimage(130, 33, 16, 16));
        textures.put("plant_destroy_4", objects.getSubimage(162, 33, 16, 16));
        textures.put("plant_item", items.getSubimage(16, 16*12, 16, 16));

        textures.put("flower_item", items.getSubimage(0, 16*13, 16, 16));

        textures.put("rock_damaged", objects.getSubimage(32*10, 0, 32, 32));
        textures.put("rock_damaged_2", objects.getSubimage(32*11, 0, 32, 32));
        textures.put("rock_damaged_3", objects.getSubimage(32*12, 0, 32, 32));

        //textures.put("rock_item", items.getSubimage(0, 16*4, 16, 16));
        textures.put("rock_item", rpgItems.getSubimage(16*7+12, 16*18+4, 16, 16));
        textures.put("log_item", items.getSubimage(16*2, 16*6, 16, 16));

        textures.put("slime_mob", characters.getSubimage(16*1, 16*4, 16, 16));
        for (int i=0; i<12; i++){
            textures.put("slime_mob_" + (i+1), characters.getSubimage(16*(i%3), 16*(i/3+4), 16, 16));
        }

        for (int i=0; i<12; i++){
            textures.put("spider_mob_" + (i+1), characters.getSubimage(16*(i%3+9), 16*(i/3+4), 16, 16));
        }

        for (int i=0; i<12; i++){
            textures.put("skeleton_mob_" + (i+1), characters.getSubimage(16*(i%3+9), 16*(i/3), 16, 16));
        }

        for (int i=0; i<12; i++){
            textures.put("bat_mob_" + (i+1), characters.getSubimage(16*(i%3+3), 16*(i/3+4), 16, 16));
        }

        textures.put("stone", overworld.getSubimage(16*6, 16*5, 16, 16));
        textures.put("stone_2", overworld.getSubimage(16*7, 16*5, 16, 16));
        textures.put("stone_3", overworld.getSubimage(16*8, 16*5, 16, 16));
        textures.put("stone_4", overworld.getSubimage(16*9, 16*5, 16, 16));
        textures.put("stone_5", overworld.getSubimage(16*10, 16*5, 16, 16));

        textures.put("bones", overworld.getSubimage(16*27, 16*1, 16, 16));
        textures.put("bones_2", overworld.getSubimage(16*27, 16*2, 16, 16));
        textures.put("bones_3", overworld.getSubimage(16*28, 16*2, 16, 16));
        textures.put("bones_4", overworld.getSubimage(16*29, 16*2, 16, 16));
        textures.put("bones_5", overworld.getSubimage(16*27, 16*3, 16, 16));
        textures.put("bones_6", overworld.getSubimage(16*28, 16*3, 16, 16));
        textures.put("bones_7", overworld.getSubimage(16*29, 16*3, 16, 16));

        textures.put("dead_slime", deadCharacters.getSubimage(16, 32, 16, 16));
        textures.put("dead_spider", deadCharacters.getSubimage(16, 48, 16, 16));
        textures.put("dead_skeleton", deadCharacters.getSubimage(0, 32, 16, 16));
        textures.put("dead_bat", deadCharacters.getSubimage(32, 32, 16, 16));

        textures.put("heal_potion_item", rpgItems.getSubimage(6, 16*16+12, 16, 16));

        textures.put("hunger_bar", rpgItems.getSubimage(4*16+12, 3*16+6, 16, 16));
        textures.put("rotten_meat", rpgItems.getSubimage(292, 30, 16, 16));
        textures.put("apple_item", rpgItems.getSubimage(4, 4, 16, 16));
        textures.put("egg_item", rpgItems.getSubimage(268, 4, 16, 16));
        textures.put("fried_egg_item", rpgItems.getSubimage(148, 53, 16, 16));
        textures.put("flour_item", rpgItems.getSubimage(124, 28, 16, 16));
        textures.put("kebab_item", rpgItems.getSubimage(364, 28, 16, 16));
        textures.put("bone_item", rpgItems.getSubimage(52, 292, 16, 16));
        textures.put("bread_item", rpgItems.getSubimage(29, 4, 16, 16));
        textures.put("hamburger_item", rpgItems.getSubimage(28, 53, 16, 16));
        textures.put("cherry_item", rpgItems.getSubimage(364, 5, 16, 16));
        textures.put("berries_item", rpgItems.getSubimage(148, 5, 16, 16));
        textures.put("carrot_item", rpgItems.getSubimage(173, 5, 16, 16));
        textures.put("candy_item", rpgItems.getSubimage(365, 53, 16, 16));
        textures.put("soup_item", rpgItems.getSubimage(4, 29, 16, 16));
        textures.put("pork_item", rpgItems.getSubimage(340, 53, 16, 16));
        textures.put("cheese_item", rpgItems.getSubimage(52, 4, 16, 16));
        textures.put("ham_item", rpgItems.getSubimage(100, 5, 16, 16));
        textures.put("small_axe_item", rpgItems.getSubimage(125, 101, 16, 16));
        textures.put("small_wand_item", rpgItems.getSubimage(4, 124, 16, 16));
        textures.put("small_knife_item", rpgItems.getSubimage(5, 100, 16, 16));
        textures.put("refined_stone_item", rpgItems.getSubimage(4, 340, 16, 16));
        textures.put("leather_boots_item", rpgItems.getSubimage(4, 244, 16, 16));
        textures.put("leather_gloves_item", rpgItems.getSubimage(149, 220, 16, 16));
        textures.put("wooden_shield_item", rpgItems.getSubimage(4, 221, 16, 16));
        textures.put("leather_vest_item", rpgItems.getSubimage(28, 197, 16, 16));
        textures.put("gold_ore_item", rpgItems.getSubimage(221, 292, 16, 17));
        textures.put("iron_ore_item", rpgItems.getSubimage(245, 293, 16, 16));
        textures.put("iron_knife_item", rpgItems.getSubimage(28, 100, 16, 16));
        textures.put("gold_knife_item", rpgItems.getSubimage(76, 100, 16, 16));
        textures.put("iron_axe_item", rpgItems.getSubimage(147, 99, 18, 18));
        textures.put("gold_axe_item", rpgItems.getSubimage(170, 98, 21, 21));
        textures.put("sword_item", rpgItems.getSubimage(4, 76, 16, 16));
        textures.put("gold_sword_item", rpgItems.getSubimage(267, 74, 19, 19));
        textures.put("iron_sword_item", rpgItems.getSubimage(52, 76, 16, 16));
        textures.put("iron_gloves_item", rpgItems.getSubimage(173, 220, 16, 16));
        textures.put("gold_gloves_item", rpgItems.getSubimage(196, 221, 16, 16));
        textures.put("iron_shield_item", rpgItems.getSubimage(28, 220, 16, 16));
        textures.put("gold_shield_item", rpgItems.getSubimage(52, 220, 16, 16));
        textures.put("iron_helmet_item", rpgItems.getSubimage(220, 197, 16, 16));
        textures.put("gold_helmet_item", rpgItems.getSubimage(364, 196, 16, 16));
        textures.put("iron_body_item", rpgItems.getSubimage(53, 195, 16, 18));
        textures.put("gold_body_item", rpgItems.getSubimage(100, 195, 16, 18));
        textures.put("iron_boots_item", rpgItems.getSubimage(53, 245, 16, 18));
        textures.put("gold_boots_item", rpgItems.getSubimage(76, 244, 16, 18));
        textures.put("iron_ingot_item", rpgItems.getSubimage(124, 364, 16, 18));
        textures.put("gold_ingot_item", rpgItems.getSubimage(4, 364, 16, 18));
    }
}
