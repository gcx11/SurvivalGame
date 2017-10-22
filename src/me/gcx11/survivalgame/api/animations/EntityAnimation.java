package me.gcx11.survivalgame.api.animations;

import java.awt.*;

/**
 * Created on 2.4.2017.
 */
public class EntityAnimation {

    private Image[][] states;
    private int state;
    private int frame;

    private int counter;

    /**
     * EntityAnimation is data structure for storing more linear animations together.
     * Each state is divided into a frames.
     *
     * @param states array of arrays with Image sequence
     */
    public EntityAnimation(Image[][] states){
        this.states = states;
        this.state = 0;
        this.frame = 0;
        this.counter = 0;
    }

    /**
     * Returns texture based on current state and frame.
     *
     * @return current texture of the animation
     */
    public Image getTexture(){
        return states[state][frame];
    }

    /**
     * Can change state of animation.
     *
     * @param newState possible transition to the new state
     */
    public void changeState(int newState){
        changeState(newState, false);
    }

    /**
     * Can change state of animation and reset frame. If state is same as it was before, it just updates to the next frame.
     *
     * @param newstate possible transition to the new state
     * @param isRunning if true, states are changing much faster
     */
    public void changeState(int newstate, boolean isRunning) {
        counter++;
        if (newstate == state){
            if (counter % (isRunning ? 10 : 15) == 0){
                frame = (frame + 1) % states[state].length;
            }
        }
        else{
            counter = 0;
            state = newstate;
            frame = 0;
        }
    }

    /**
     * Resets animation of current animation state
     */
    public void resetFrame(){
        frame = 0;
    }
}
