package io.ziglef.flappy;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.ziglef.flappy.states.GameStateManager;
import io.ziglef.flappy.states.MenuState;

public class Flappy extends ApplicationAdapter {
	public static final int WIDTH = 480;
    public static final int HEIGHT = 800;
    public static final String TITLE = "Flappy GDX";

    private GameStateManager gsm;
	private SpriteBatch sb;

    private Music gameMusic;
	
	@Override
	public void create() {
		sb = new SpriteBatch();
        gsm = new GameStateManager();
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        gameMusic.setLooping(true);
        gameMusic.setVolume(0.1f);
        gameMusic.play();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        gsm.push(new MenuState(gsm));
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(sb);
	}

    @Override
    public void dispose() {
        super.dispose();
        gameMusic.dispose();
    }
}
