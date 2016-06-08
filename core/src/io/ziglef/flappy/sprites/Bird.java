package io.ziglef.flappy.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Bird {

    private static final int GRAVITY = -15;
    private static final int MOVEMENT = 100;

    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;
    private Texture animationTexture;
    private Animation birdAnimation;
    private Sound flapSound;

    public Bird(int x, int y){
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        animationTexture = new Texture("birdanimation.png");
        birdAnimation = new Animation(animationTexture, 3, 0.5f);
        bounds = new Rectangle(x, y, animationTexture.getWidth() / 3, animationTexture.getHeight() / 3);
        flapSound = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
    }

    public void update(float dt){
        birdAnimation.update(dt);

        if(position.y > 0)
            velocity.add(0, GRAVITY, 0);

        velocity.scl(dt);
        position.add(MOVEMENT * dt, velocity.y, 0);
        velocity.scl(1/dt);

        if(position.y < 0)
            position.y = 0;

        bounds.setPosition(position.x, position.y);
    }

    public void jump(){
        velocity.y = 250;
        flapSound.play(0.15f);
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getBird() {
        return birdAnimation.getFrame();
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public void dispose(){
        animationTexture.dispose();
        flapSound.dispose();
    }
}
