package io.ziglef.flappy.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import io.ziglef.flappy.Flappy;
import io.ziglef.flappy.sprites.Bird;
import io.ziglef.flappy.sprites.Tube;

public class PlayState extends State {

    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private static final int GROUND_Y_OFFSET = -50;

    private Bird bird;
    private Texture bg;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;
    private Array<Tube> tubes;
    private BitmapFont scoreFnt;
    private int score;
    private boolean score_hack;

    protected PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50, 300);
        tubes = new Array<Tube>();
        bg = new Texture("bg.png");
        ground = new Texture("ground.png");
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);

        for (int i = 1; i <= TUBE_COUNT; i++) {
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.WIDTH)));
        }

        scoreFnt = new BitmapFont();
        scoreFnt.setUseIntegerPositions(false);
        scoreFnt.setColor(Color.BLACK);
        score = 0;
        score_hack = false;

        cam.setToOrtho(false, Flappy.WIDTH / 2, Flappy.HEIGHT / 2);
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched())
            bird.jump();
    }

    @Override
    public void update(float dt) {
        handleInput();
        bird.update(dt);
        updateGround();

        for(Tube tube : tubes){
            if(cam.position.x - (cam.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()) {
                tube.reposition(tube.getPosTopTube().x + ((Tube.WIDTH + TUBE_SPACING) * TUBE_COUNT));
                score_hack = false;
            }

            if(bird.getPosition().x > tube.getPosTopTube().x + tube.getTopTube().getWidth() && !score_hack) {
                score++;
                score_hack = true;
            }

            if(tube.collides(bird.getBounds())) {
                gsm.set(new EndState(gsm, score));
                break;
            }
        }

        if(bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET)
            gsm.set(new PlayState(gsm));

        cam.position.x = bird.getPosition().x + 80;
        cam.update();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(bird.getBird(), bird.getPosition().x, bird.getPosition().y);

        for(Tube tube : tubes){
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }

        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);

        scoreFnt.draw(sb, String.valueOf(score), cam.position.x + cam.viewportWidth / 2 - 25, cam.position.y + cam.viewportHeight / 2 - 25);

        sb.end();

        /* DEBUG
        ShapeRenderer sr = new ShapeRenderer();
        sr.setProjectionMatrix(cam.combined);
        sr.setAutoShapeType(true);
        sr.begin();

        for(Tube tube : tubes) {
            sr.rect(tube.getBoundsBot().x, tube.getBoundsBot().y, tube.getBoundsBot().width, tube.getBoundsBot().height);
            sr.rect(tube.getBoundsTop().x, tube.getBoundsTop().y, tube.getBoundsTop().width, tube.getBoundsTop().height);
        }

        sr.rect(bird.getBounds().x, bird.getBounds().y, bird.getBounds().width, bird.getBounds().height);

        sr.end();
        */

    }

    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
        ground.dispose();
        for(Tube tube : tubes)
            tube.dispose();
    }

    private void updateGround(){
        if(cam.position.x - cam.viewportWidth / 2 > groundPos1.x + ground.getWidth())
            groundPos1.add(ground.getWidth() * 2, 0);

        if(cam.position.x - cam.viewportWidth / 2 > groundPos2.x + ground.getWidth())
            groundPos2.add(ground.getWidth() * 2, 0);
    }
}
