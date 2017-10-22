package me.gcx11.survivalgame.objects.nature;

import me.gcx11.survivalgame.scenes.GameScene;
import me.gcx11.survivalgame.api.GameObject;
import me.gcx11.survivalgame.api.rendering.Textures;

import java.awt.*;
import java.util.Random;

/**
 * Created on 11.5.2017.
 */
public class Stone extends GameObject {

    private Random rnd = new Random();

    private static Image[] textures = {Textures.getTexture("stone"), Textures.getTexture("stone_2"),
            Textures.getTexture("stone_3"), Textures.getTexture("stone_4"), Textures.getTexture("stone_5")};

    private Image texture = textures[rnd.nextInt(textures.length)];

    /**
     * Creates new stone game object.
     *
     * @param gameScene
     * @param x
     * @param y
     */
    public Stone(GameScene gameScene, int x, int y) {
        super(gameScene);
        this.x = x;
        this.y = y;
        this.width = texture.getWidth(null);
        this.height = texture.getHeight(null);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(Graphics graphics) {
        graphics.drawImage(texture, gameScene.camera.recalculateX(x), gameScene.camera.recalculateY(y), null);
    }
}
