package com.projectcastle.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.projectcastle.game.ProjectCastleGame;
import com.projectcastle.game.util.Constants;
import com.projectcastle.game.util.MapLoader;

/**
 * Created by ernestogonzalezchacon on 23/9/17.
 */

public class TestMapScreen implements Screen {

    public static final String TAG = TestMapScreen.class.getName();

    final ProjectCastleGame game;
    OrthographicCamera camera;
    TiledMap map;

    public TestMapScreen(final ProjectCastleGame game){
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 640);
        map = MapLoader.load(Constants.TEST_MAP);
        game.tiledMapRenderer = new OrthogonalTiledMapRenderer(map, 1);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        camera.update();
        game.tiledMapRenderer.setView(camera);
        game.tiledMapRenderer.render();

        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {

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

        map.dispose();

    }
}
