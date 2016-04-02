package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/**
 * Created by Jay on 4/2/2016.
 */
public class IntroScreen implements Screen {
    private static final String TAG = IntroScreen.class.getName();

    HelloKittyMemory game;
    ExtendViewport actionViewport;
    SpriteBatch batch;

    TextureAtlas atlas;
    TextureRegion kittyWalk;
    Animation kittyWave;

    Vector2 kittyPosition;
    float elapsedTime;

    public IntroScreen(HelloKittyMemory game) {
        Gdx.input.setCatchBackKey(true);
        this.game = game;

        actionViewport = new ExtendViewport(Constants.GAMEPLAY_WIDTH, Constants.GAMEPLAY_HEIGHT);
        actionViewport.apply(true);
        batch = new SpriteBatch();

        /** LOAD ASSETS **/
        atlas = game.assets.get(Constants.MAIN_ATLAS);
        kittyWalk = atlas.createSprite("introKittyWalk");
        kittyWave = new Animation(
                Constants.KITTY_WAVE_DURATION,
                atlas.createSprites("introKitty"),
                Animation.PlayMode.LOOP_PINGPONG
        );
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {
        elapsedTime = 0;
        kittyPosition = new Vector2(Constants.KITTY_WAVE_START_X,
            Constants.KITTY_WAVE_START_Y);
    }

    @Override
    public void render(float delta) {
        elapsedTime += delta;
        if (delta > 0.05f) return;  // Avoids spikes in delta value.
        // Background color fill
        DrawingUtils.clearScreen();

        if (kittyPosition.x < Constants.GAMEPLAY_WIDTH / 2) {
            kittyPosition.x += Constants.KITTY_WALK_SPEED * delta;

            batch.begin();
            batch.draw(
                    kittyWalk,
                    kittyPosition.x - Constants.KITTY_WAVE_HALF_WIDTH,
                    kittyPosition.y - Constants.KITTY_WAVE_HALF_HEIGHT,
                    Constants.KITTY_WAVE_HALF_WIDTH,
                    Constants.KITTY_WAVE_HALF_HEIGHT,
                    Constants.KITTY_WAVE_HALF_WIDTH * 2,
                    Constants.KITTY_WAVE_HALF_HEIGHT * 2,
                    1f, 1f, 0f // Scale and rotation
            );
            batch.end();
        } else {
            batch.begin();
            batch.draw(
                    kittyWave.getKeyFrame(elapsedTime),
                    kittyPosition.x - Constants.KITTY_WAVE_HALF_WIDTH,
                    kittyPosition.y - Constants.KITTY_WAVE_HALF_HEIGHT,
                    Constants.KITTY_WAVE_HALF_WIDTH,
                    Constants.KITTY_WAVE_HALF_HEIGHT,
                    Constants.KITTY_WAVE_HALF_WIDTH * 2,
                    Constants.KITTY_WAVE_HALF_HEIGHT * 2,
                    1f, 1f, 0f // Scale and rotation
            );
            batch.end();
        }

        if (elapsedTime > Constants.KITTY_INTRO_DURATION) {
            game.setScreen(game.gameScreen);
        }
    }

    @Override
    public void resize(int width, int height) {
        actionViewport.update(width, height, true);
        actionViewport.getCamera().position.set(
                Constants.GAMEPLAY_WIDTH / 2, Constants.GAMEPLAY_HEIGHT / 2, 0f);
        actionViewport.getCamera().update();
        batch.setProjectionMatrix(actionViewport.getCamera().combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
