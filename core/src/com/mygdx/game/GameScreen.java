package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.Random;

/**
 * Created by Jay on 4/2/2016.
 */
public class GameScreen extends InputAdapter implements Screen {
    private static final String TAG = GameScreen.class.getName();

    private HelloKittyMemory game;
    private ExtendViewport actionViewport;
    private SpriteBatch batch;

    private TextureAtlas atlas;
    private Animation cardCoverAnim;
    private Array<TextureRegion> cards;

    private Random random = new Random(TimeUtils.millis());
    private int[][] cardID;  // Card index number
    private float[][] cardTime;  // Open and close animation state
    private boolean[][] cardShowing;  // True if card is showing
    private int pickSize;
    private int[] pickStack;
    private int matches;

    public GameScreen(HelloKittyMemory game) {
        Gdx.input.setCatchBackKey(true);
        this.game = game;

        actionViewport = new ExtendViewport(Constants.GAMEPLAY_WIDTH, Constants.GAMEPLAY_HEIGHT);
        actionViewport.apply(true);
        batch = new SpriteBatch();

        /** LOAD ASSETS **/
        atlas = game.assets.get(Constants.MAIN_ATLAS);
        cardCoverAnim = new Animation(
                1f,
                atlas.createSprites("open"),
                Animation.PlayMode.NORMAL
        );
        cards = new Array<TextureRegion>(atlas.createSprites("card"));
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);

        resetCards();
    }

    private void resetCards() {
        pickSize = 0;
        pickStack = new int[4];
        matches = 0;

        /** RESTART CARD LAYOUT */
        // NOTE: int and float default values are zero.
        cardID = new int[Constants.ROW_SIZE][Constants.COL_SIZE];
        cardTime = new float[Constants.ROW_SIZE][Constants.COL_SIZE];
        // NOTE: boolean default value is false.
        cardShowing = new boolean[Constants.ROW_SIZE][Constants.COL_SIZE];

        /** CHANGE CARD ID DEFAULTS */
        for (int i=0; i<Constants.ROW_SIZE; i++) {
            for (int j=0; j<Constants.COL_SIZE; j++) {
                cardID[i][j] = -1;
            }
        }

        /** RANDOMLY PLACE CARDS */
        for (int i=0; i<Constants.ROW_SIZE*Constants.COL_SIZE/2; i++) {
            int cardNo = i;
            int cardsRemaining = 2;
            while (cardsRemaining > 0) {
                int randomRow = random.nextInt(Constants.ROW_SIZE);
                int randomCol = random.nextInt(Constants.COL_SIZE);
                if (cardID[randomRow][randomCol] < 0) {
                    cardID[randomRow][randomCol] = cardNo;
                    cardsRemaining -= 1;
                }
            }
        }
    }

    @Override
    public void render(float delta) {
        if (delta > 0.05f) return;  // Avoids spikes in delta value.
        // Background color fill
        DrawingUtils.clearScreen();

        for (int i=0; i<Constants.ROW_SIZE; i++) {
            for (int j=0; j<Constants.COL_SIZE; j++) {
                renderCard(delta, i, j);
            }
        }
    }

    private void renderCard(float delta, int x, int y) {
        if (cardShowing[x][y]) {
            cardTime[x][y] = Math.min(cardTime[x][y] + Constants.CARD_COVER_SPEED * delta, cardCoverAnim.getKeyFrames().length);
        } else {
            cardTime[x][y] = Math.max(cardTime[x][y] - Constants.CARD_COVER_SPEED * delta, 0f);
        }

        batch.begin();
        batch.draw(
                cards.get(cardID[x][y]),
                x * (Constants.CARD_WIDTH + Constants.CARD_WIDTH_PADDING),
                y * (Constants.CARD_HEIGHT + Constants.CARD_HEIGHT_PADDING)
        );
        batch.draw(
                cardCoverAnim.getKeyFrame(cardTime[x][y]),
                x * (Constants.CARD_WIDTH + Constants.CARD_WIDTH_PADDING),
                y * (Constants.CARD_HEIGHT + Constants.CARD_HEIGHT_PADDING)
        );
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
        return super.keyDown(keycode);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 pos = actionViewport.unproject(new Vector2(screenX, screenY));
        int x = (int)Math.floor(pos.x / (Constants.CARD_WIDTH + Constants.CARD_WIDTH_PADDING));
        int y = (int)Math.floor(pos.y / (Constants.CARD_HEIGHT + Constants.CARD_HEIGHT_PADDING));
        if (x < 0 | x >= Constants.ROW_SIZE) return false;
        if (y < 0 | y >= Constants.COL_SIZE) return false;

        if (pickSize == 4) {
            cardShowing[pickStack[0]][pickStack[1]] = false;
            cardShowing[pickStack[2]][pickStack[3]] = false;
            pickSize = 0;
        }

        if (pickSize < 4) {
            if (!cardShowing[x][y]){
                cardShowing[x][y] = true;
                pickStack[pickSize] = x;
                pickStack[pickSize + 1] = y;
                pickSize += 2;
            }
        }

        /** IF GAME FINISHED THEN RETURN TO INTRO KITTY */
        if (pickSize == 0) {
            boolean gameOver = true;
            for (int i = 0; i < Constants.ROW_SIZE; i++) {
                for (int j = 0; j < Constants.COL_SIZE; j++) {
                    if (!cardShowing[i][j]) gameOver = false;
                }
            }
            if (gameOver) game.setScreen(game.introScreen);
        }

        /** IF MATCH THEN SCORE A POINT AND KEEP OPEN */
        if (pickSize == 4 & cardID[pickStack[0]][pickStack[1]] == cardID[pickStack[2]][pickStack[3]]) {
            pickSize = 0;
            matches += 1;
        }

        return super.touchDown(screenX, screenY, pointer, button);
    }
}
