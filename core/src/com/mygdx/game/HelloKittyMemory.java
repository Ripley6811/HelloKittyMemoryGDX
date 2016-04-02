package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class HelloKittyMemory extends Game {

	AssetManager assets;

	TitleScreen titleScreen;
	IntroScreen introScreen;
	GameScreen gameScreen;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Constants.LOG_LEVEL);
		DrawingUtils.initGLSettings(Constants.BACKGROUND_COLOR);
		assets = new AssetManager();
		assets.load(Constants.MAIN_ATLAS, TextureAtlas.class);
		assets.finishLoading();

		titleScreen = new TitleScreen(this);
		introScreen = new IntroScreen(this);
		gameScreen = new GameScreen(this);
		setScreen(titleScreen);
	}
}
