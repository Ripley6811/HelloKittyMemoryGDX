package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/**
 * Created by Jay on 4/2/2016.
 */
public class TitleScreen extends InputAdapter implements Screen {
    private static final String TAG = TitleScreen.class.getName();

    HelloKittyMemory game;
    ExtendViewport actionViewport;
    SpriteBatch batch;

    TextureAtlas atlas;
    TextureRegion startScreen;

    public TitleScreen(HelloKittyMemory game) {
        this.game = game;

        actionViewport = new ExtendViewport(Constants.GAMEPLAY_WIDTH, Constants.GAMEPLAY_HEIGHT);
        actionViewport.apply(true);
        batch = new SpriteBatch();

        /** LOAD ASSETS **/
        atlas = game.assets.get(Constants.MAIN_ATLAS);
        startScreen = atlas.createSprite("startScreen");
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);

    }

    @Override
    public void render(float delta) {
        if (delta > 0.05f) return;  // Avoids spikes in delta value.
        // Background color fill
        DrawingUtils.clearScreen();

        batch.begin();
        batch.draw(startScreen, 0, 0);
        batch.end();
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

    @Override
    public boolean keyDown(int keycode) {
        game.setScreen(game.introScreen);
        return super.keyDown(keycode);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        game.setScreen(game.introScreen);
        return super.touchDown(screenX, screenY, pointer, button);
    }
}
