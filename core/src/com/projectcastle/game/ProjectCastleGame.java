package com.projectcastle.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.projectcastle.game.util.Constants;
import com.projectcastle.game.util.MapLoader;

public class ProjectCastleGame extends Game {

	public static final String TAG = ProjectCastleGame.class.getName();

	FPSLogger fpsLogger;
	SpriteBatch batch;
	TiledMap map;
	OrthographicCamera camera;
	OrthogonalTiledMapRenderer renderer;
	Viewport viewport;
	MapLayer objectLayer;
	MapObjects myObjects;

	@Override
	public void create () {

		fpsLogger = new FPSLogger();
		batch = new SpriteBatch();
		map = MapLoader.load(Constants.TEST_MAP);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 20, 20);
		camera.update();
		renderer = new OrthogonalTiledMapRenderer(map, Constants.UNIT_SCALE);

//		objectLayer = new MapLayer();
//		objectLayer = map.getLayers().get("objects");
//		myObjects = new MapObjects();
//		myObjects = objectLayer.getObjects();
//
//		for (int ii= 0; ii < objectLayer.getObjects().getCount(); ii++){
//			Gdx.app.log(TAG, objectLayer.getObjects().get(ii).getName());
//		}
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		camera.update();
		renderer.setView(camera);
		renderer.render();
		fpsLogger.log();

//		batch.draw(img, 0, 0);
		batch.end();
	}
}
