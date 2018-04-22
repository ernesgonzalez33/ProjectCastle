package com.projectcastle.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.projectcastle.game.Map;
import com.projectcastle.game.ProjectCastleGame;
import com.projectcastle.game.gameplay.InputProcessorHelp;
import com.projectcastle.game.util.Constants;
import com.projectcastle.game.util.TextureTools;

public class GameplayScreen implements InputProcessor, Screen {

    ProjectCastleGame game;
    SpriteBatch batch;
    private Map map;
    OrthographicCamera camera;
    Viewport viewport;
    Stage stage;
    TextureTools textureTools;
    InputProcessorHelp inputProcessorHelp;

    public GameplayScreen(final ProjectCastleGame game){

        //Initializing
        this.game = game;
        this.batch = game.batch;
        camera = new OrthographicCamera();
        viewport = new FitViewport(Constants.WIDTH, Constants.HEIGHT, camera);
        camera.setToOrtho(false, Constants.WIDTH, Constants.HEIGHT);
        stage = new Stage();
        textureTools = new TextureTools();
        inputProcessorHelp = new InputProcessorHelp(game);


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

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        game.tiledMapRenderer.setView(camera);
        game.tiledMapRenderer.render();
        stage.act(delta); //In the case something need to move

        game.batch.begin();

        stage.draw();

        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {

        viewport.update(width, height);
        stage.getViewport().update(width, height, true);

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

        stage.dispose();

    }
}
