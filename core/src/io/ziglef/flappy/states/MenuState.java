package io.ziglef.flappy.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.ziglef.flappy.Flappy;

public class MenuState extends State{

    private Texture bg;
    private Texture playBtn;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        bg = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");
        cam.setToOrtho(false, Flappy.WIDTH / 2, Flappy.HEIGHT / 2);
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        sb.draw(bg, 0, 0);
        sb.draw(playBtn, cam.position.x - playBtn.getWidth() / 2, cam.position.y);

        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        playBtn.dispose();
    }
}
