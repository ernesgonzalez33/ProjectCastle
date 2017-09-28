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
import com.projectcastle.game.screens.TestMapScreen;
import com.projectcastle.game.util.Constants;
import com.projectcastle.game.util.MapLoader;

public class ProjectCastleGame extends Game {

	public static final String TAG = ProjectCastleGame.class.getName();

	public FPSLogger fpsLogger;
	public SpriteBatch batch;
	public OrthogonalTiledMapRenderer tiledMapRenderer;

	@Override
	public void create() {

		batch = new SpriteBatch();
		this.setScreen(new TestMapScreen(this));

	}

	public void render(){
		super.render(); //Si no se hace, no renderiza la pantalla
	}

	public void dispose(){
		batch.dispose();
	}
}
