package com.projectcastle.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.projectcastle.game.util.Constants;
import com.projectcastle.game.util.MapLoader;
import com.projectcastle.game.util.OrthogonalTiledMapRendererWithSprites;

class ProjectCastleGame extends Game implements InputProcessor {
	SpriteBatch batch;
	TiledMap map;
	OrthographicCamera camera;
	OrthogonalTiledMapRenderer renderer;
	Viewport viewport;
	MapLayer objectLayer;

	@Override
	public void create () {
		batch = new SpriteBatch();
		map = MapLoader.load(Constants.TEST_MAP);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 20, 20);
		camera.update();
		renderer = new OrthogonalTiledMapRenderer(map, Constants.UNIT_SCALE);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		camera.update();
		renderer.setView(camera);
		renderer.render();

//		batch.draw(img, 0, 0);
		batch.end();
	}
}
