package io.ziglef.flappy.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.ziglef.flappy.Flappy;

public class EndState extends State{

    private final Texture bg;
    private final Texture playBtn;
    private final BitmapFont scoreFnt;
    private int score;

    protected EndState(GameStateManager gsm, int score) {
        super(gsm);

        this.score = score;

        bg = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");
        cam.setToOrtho(false, Flappy.WIDTH / 2, Flappy.HEIGHT / 2);

        scoreFnt = new BitmapFont();
        scoreFnt.setUseIntegerPositions(false);
        scoreFnt.setColor(Color.BLACK);

    }

    @Override
    protected void handleInput() {
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
        sb.draw(playBtn, cam.position.x - playBtn.getWidth() / 2, cam.position.y - 35);

        scoreFnt.draw(sb, "Score: " + String.valueOf(score), cam.position.x - 30, cam.position.y + 50);

        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        playBtn.dispose();
    }
}
