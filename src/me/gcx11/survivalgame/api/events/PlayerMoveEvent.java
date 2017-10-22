package me.gcx11.survivalgame.api.events;

import me.gcx11.survivalgame.objects.entities.Player;

/**
 * Created on 3.9.2017.
 */
public class PlayerMoveEvent extends Event {

    public final Player player;
    public int delta_x, delta_y;

    /**
     * Event called when player moves.
     * @param player player
     * @param delta_x x-axis motion
     * @param delta_y y-axis motion
     */
    public PlayerMoveEvent(Player player, int delta_x, int delta_y){
        this.player = player;
        this.delta_x = delta_x;
        this.delta_y = delta_y;
    }
}
