package com.projectcastle.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.projectcastle.game.util.Constants;
import com.projectcastle.game.util.MapLoader;

/**
 * Created by ernestogonzalezchacon on 18/5/17.
 */

public class GameplayScreen extends ScreenAdapter {

    public static final String TAG = GameplayScreen.class.getName();

    SpriteBatch batch;
    TiledMap map;
    OrthographicCamera camera;
    OrthogonalTiledMapRenderer renderer;

    @Override
    public void show() {
        batch = new SpriteBatch();
        map = MapLoader.load(Constants.TEST_MAP);
        camera = new OrthographicCamera();
        camera.setToOrtho(true, 20, 20);
        renderer = new OrthogonalTiledMapRenderer(map, Constants.UNIT_SCALE);
    }

    @Override
    public void resize(int width, int height) {
        camera.update();

    }
}
