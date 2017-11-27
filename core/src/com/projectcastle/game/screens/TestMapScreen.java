package com.projectcastle.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.projectcastle.game.ProjectCastleGame;
import com.projectcastle.game.util.Constants;

/**
 * Created by ernestogonzalezchacon on 23/9/17.
 */

public class TestMapScreen implements Screen {

    public static final String TAG = TestMapScreen.class.getName();

    final ProjectCastleGame game;
    OrthographicCamera camera;
    TiledMap map;
    Viewport viewport;


    //Test image
    Texture testTexture;

    public TestMapScreen(final ProjectCastleGame game){
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new FitViewport(Constants.WIDTH, Constants.HEIGHT, camera);
        camera.setToOrtho(false, Constants.WIDTH, Constants.HEIGHT);

        // only needed once
        game.manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        game.manager.load(Constants.TEST_MAP, TiledMap.class);

        game.manager.finishLoading();
        // once the asset manager is done loading
        TiledMap map = game.manager.get(Constants.TEST_MAP);
        game.tiledMapRenderer = new OrthogonalTiledMapRenderer(map, Constants.UNIT_SCALE);

        //Test image
        testTexture = new Texture(Gdx.files.internal("TestImage.png"));

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

        game.tiledMapRenderer.setView(camera);
        game.tiledMapRenderer.render();

        game.batch.begin();

        game.batch.draw(testTexture, 0, 0);

        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
