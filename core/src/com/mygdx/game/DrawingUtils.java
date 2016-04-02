package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

/**
 * Created by Jay on 4/2/2016.
 */
public class DrawingUtils {
    private static final String TAG = DrawingUtils.class.getName();

    public static void initGLSettings(Color BG_COLOR) {
        Gdx.gl.glClearColor(BG_COLOR.r, BG_COLOR.g, BG_COLOR.b, 1f);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    public static void clearScreen() {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public static boolean isBlendEnabled() {
        return Gdx.gl.glIsEnabled(GL20.GL_BLEND);
    }

    public static void enableBlend() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
    }

    public static void disableBlend() {
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
