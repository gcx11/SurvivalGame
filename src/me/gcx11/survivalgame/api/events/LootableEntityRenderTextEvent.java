package me.gcx11.survivalgame.api.events;

import me.gcx11.survivalgame.api.interfaces.LivingEntity;
import me.gcx11.survivalgame.objects.entities.LootableEntity;

import java.awt.*;

/**
 * Created on 9.9.2017.
 */
public class LootableEntityRenderTextEvent extends Event {

    public LivingEntity entity;
    private String text;
    private Color color;

    /**
     * Event called on rendering text above entities.
     *
     * @param entity entity
     * @param text text to show
     * @param color color of the text
     */
    public LootableEntityRenderTextEvent(LivingEntity entity, String text, Color color){
        this.entity = entity;
        this.text = text;
        this.color = color;
    }

    /**
     * Gets text above the entity.
     * @return text above the entity
     */
    public String getText(){
        return text;
    }

    /**
     * Sets text above the entity.
     * @param text new text
     */
    public void setText(String text){
        if (text != null) this.text = text;
    }

    /**
     * Gets color of the text.
     * @return color of the text
     */
    public Color getColor(){
        return color;
    }

    /**
     * Sets color of the text.
     * @param color new color of the text
     */
    public void setColor(Color color){
        this.color = color;
    }
}
