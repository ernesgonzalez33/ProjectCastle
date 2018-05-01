package com.projectcastle.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.projectcastle.game.Map;
import com.projectcastle.game.ProjectCastleGame;
import com.projectcastle.game.util.Assets;

public class GameplayScreen implements InputProcessor, Screen {

    ProjectCastleGame game;
    SpriteBatch batch;
    private Map map;


    public GameplayScreen(final ProjectCastleGame game){

        //Initializing
        this.game = game;
        this.batch = game.batch;

        Assets.instance.init(game.manager);

        startNewMap();

    }

    private void startNewMap(){

        map = Map.debugMap(this.game);

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public void show() {


    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        map.camera.update();
        batch.setProjectionMatrix(map.camera.combined);

        map.update(delta);

        map.render(game.batch);

    }

    @Override
    public void resize(int width, int height) {

        map.viewport.update(width, height);
        map.stage.getViewport().update(width, height);

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

        Assets.instance.dispose();

    }
}
