package com.tanks.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MenuScreen implements Screen {
    private SpriteBatch batch;
    private TextureRegion textureBackground;
    private BitmapFont font;

    public void update(float dt) {
        //обработка нажатия мыши на область
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            //начать игру
            if (Gdx.input.getX() < 400 && Gdx.input.getX() > 100 && Gdx.input.getY() > 450 && Gdx.input.getY() < 500)
                ScreenManager.getInstance().switchScreen(ScreenManager.ScreenType.GAME);
            //выйти из игры
            if (Gdx.input.getX() < 400 && Gdx.input.getX() > 100 && Gdx.input.getY() > 560 && Gdx.input.getY() < 600)
                Gdx.app.exit();
        }
    }

    public MenuScreen(SpriteBatch batch) {
        this.batch = batch;
    }

    @Override
    public void show() {
        font = Assets.getInstance().getAssetManager().get("zorque48.ttf", BitmapFont.class);
        textureBackground = Assets.getInstance().getAtlas().findRegion("background");
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        batch.draw(textureBackground, 0, 0);
        font.draw(batch, "START GAME", 100, 250);
        font.draw(batch, "EXIT", 100, 150);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        ScreenManager.getInstance().onResize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
