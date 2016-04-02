package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.graphics.Color;

/**
 * Created by Jay on 4/2/2016.
 */
public class Constants {
    /* LOGGING LEVELS */
    // Application logging levels from lowest to highest. Choose one.
//    public static final int LOG_LEVEL = Application.LOG_NONE;
//    public static final int LOG_LEVEL = Application.LOG_ERROR;
//    public static final int LOG_LEVEL = Application.LOG_INFO;
    public static final int LOG_LEVEL = Application.LOG_DEBUG;


    public static final int GAMEPLAY_WIDTH = 500;
    public static final int GAMEPLAY_HEIGHT = 400;
    public static final Color BACKGROUND_COLOR = Color.YELLOW;
    public static final String MAIN_ATLAS = "images/hellokitty.pack.atlas";

    public static final float KITTY_INTRO_DURATION = 4f;
    public static final float KITTY_WAVE_DURATION = 0.2f;
    public static final float KITTY_WAVE_START_X = -10f;
    public static final float KITTY_WAVE_START_Y = GAMEPLAY_HEIGHT / 2;
    public static final float KITTY_WALK_SPEED = 160f;
    public static final float KITTY_WAVE_HALF_WIDTH = 33f;
    public static final float KITTY_WAVE_HALF_HEIGHT = 30f;

    public static final int ROW_SIZE = 8;
    public static final int COL_SIZE = 6;
    public static final int CARD_WIDTH = 60;
    public static final int CARD_HEIGHT = 64;
    public static final float CARD_WIDTH_PADDING = 2.8f;
    public static final float CARD_HEIGHT_PADDING = 3.2f;
    public static final float CARD_COVER_SPEED = 20f;
}
