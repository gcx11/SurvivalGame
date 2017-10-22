package me.gcx11.survivalgame.objects.inventories;

import me.gcx11.survivalgame.objects.entities.Player;
import me.gcx11.survivalgame.objects.items.Item;
import me.gcx11.survivalgame.objects.recipes.Recipe;
import me.gcx11.survivalgame.objects.recipes.Recipes;

import java.awt.*;
import java.util.List;

/**
 * Created on 11.5.2017.
 */
public class CraftingInventory {

    private Player player;
    private int x;
    private int y;

    private final Color INVENTORY_COLOR = new Color(209, 187, 57);
    private boolean isVisible = false;

    private int lastRecipeCount = 0;

    //global
    private int cellsize = 24;
    private int spacing = 3;
    private int gap = 5;

    /**
     * Creates new crafting inventory
     *
     * @param player player
     * @param x x coordinate of the inventory
     * @param y y coordinate of the inventory
     */
    public CraftingInventory(Player player, int x, int y){
        this.player = player;
        this.x = x;
        this.y = y;
    }

    /**
     * Checks if inventory is used and handles its usage.
     *
     * @return whether is inventory used
     */
    public boolean handleUse(){
        if (player.gameScene.mouseInfo.wasLeftClicked()){
            int mouseX = player.gameScene.mouseInfo.getX();
            int mouseY = player.gameScene.mouseInfo.getY();
            List<Recipe> recipes = Recipes.validRecipesFor(player);
            for (int i=0; i < recipes.size(); i++){
                if (isInsideSlot(mouseX, mouseY, x+spacing, y+(i+1)* spacing + i*cellsize)){
                    //System.out.println("Clicked slot: " + i);
                    Recipe recipe = recipes.get(i);
                    for (Item ingredient: recipe.ingredients){
                        player.inventory.removeItem(ingredient);
                    }
                    player.inventory.addItem(recipe.result.copy());
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Opens the inventory.
     */
    public void open(){
        isVisible = true;
    }

    /**
     * Closes the inventory.
     */
    public void close(){
        isVisible = false;
    }

    /**
     * Checks whether is inventory opened.
     *
     * @return whether is inventory opened
     */
    public boolean isOpened(){
        return isVisible;
    }

    /**
     * Renders inventory for crafting.
     *
     * @param graphics Graphics object
     */
    public void render(Graphics graphics){
        List<Recipe> recipes = Recipes.validRecipesFor(player);
        if (recipes.size() == 0){
            lastRecipeCount = 0;
            close();
        }
        else if (recipes.size() != lastRecipeCount){
            if (recipes.size() > lastRecipeCount) open();
            lastRecipeCount = recipes.size();
        }
        if (!isVisible) return;
        graphics.setColor(INVENTORY_COLOR);
        int maxSize = recipes.stream().mapToInt(recipe -> recipe.ingredients.length).max().orElse(2);
        int rows = recipes.size();
        graphics.fillRect(x, y, (maxSize+1)*cellsize+(maxSize+2)* spacing +gap, rows*cellsize + (rows+1)* spacing);
        graphics.setColor(Color.GRAY);
        graphics.drawLine(x-1, y-1, x+(maxSize+1)*cellsize+(maxSize+2)* spacing +gap, y-1);
        graphics.drawLine(x-1, y-1, x-1, y+rows*cellsize + (rows+1)* spacing);
        graphics.drawLine(x+(maxSize+1)*cellsize+(maxSize+2)* spacing +gap, y-1, x+(maxSize+1)*cellsize+(maxSize+2)* spacing +gap, y+rows*cellsize + (rows+1)* spacing);
        graphics.drawLine(x-1, y+rows*cellsize + (rows+1)* spacing, x+(maxSize+1)*cellsize+(maxSize+2)* spacing +gap, y+rows*cellsize + (rows+1)* spacing);
        Font oldFont = graphics.getFont();
        Font newFont = new Font("Comic Sans", Font.PLAIN, 10);
        graphics.setFont(newFont);
        for (int i=0; i < recipes.size(); i++){
            renderRecipe(graphics, i, recipes.get(i));
        }
        graphics.setFont(oldFont);
    }

    private void renderRecipe(Graphics graphics, int offset, Recipe recipe) {
        graphics.setColor(Color.GRAY);
        graphics.fillRect(x+spacing, y+(offset+1)* spacing +offset*cellsize, cellsize, cellsize);
        graphics.drawImage(recipe.result.itemData.getTexture(), x+ spacing, y+(offset+1)* spacing +offset*cellsize, null);
        if (recipe.result.count != 1){
            graphics.setColor(Color.WHITE);
            graphics.drawString(Integer.toString(recipe.result.count),
                    x+ spacing +12, y+(offset+1)* spacing +offset*cellsize+20);
        }
        graphics.setColor(Color.GRAY);
        int i;
        for (i=1; i <= recipe.ingredients.length; i++) {
            graphics.setColor(Color.GRAY);
            graphics.fillRect(x + (i + 1) * spacing + i * cellsize + gap, y + (offset + 1) * spacing + offset * cellsize, cellsize, cellsize);
            graphics.drawImage(recipe.ingredients[i-1].itemData.getTexture(),
                    x + (i + 1) * spacing + i * cellsize + gap, y + (offset + 1) * spacing + offset * cellsize, null);
            if (recipe.ingredients[i-1].count != 1){
                graphics.setColor(Color.WHITE);
                graphics.drawString(Integer.toString(recipe.ingredients[i-1].count),
                        x + (i + 1) * spacing + i * cellsize + gap + 12, y + (offset + 1) * spacing + offset * cellsize + 20);
            }
        }
    }

    /**
     * Checks whether is mouse inside slot.
     *
     * @param mouseX mouse x coordinate
     * @param mouseY mouse y coordinate
     * @param slotX slot x coordinate
     * @param slotY slot y coordinate
     * @return whether is mouse inside slot
     */
    public boolean isInsideSlot(int mouseX, int mouseY, int slotX, int slotY){
        return (slotX <= mouseX && mouseX <= slotX + cellsize &&
                slotY <= mouseY && mouseY <= slotY + cellsize);
    }
}
