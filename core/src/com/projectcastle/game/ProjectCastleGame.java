package com.projectcastle.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.projectcastle.game.screens.TestMapScreen;

public class ProjectCastleGame extends Game {

	public static final String TAG = ProjectCastleGame.class.getName();

	public FPSLogger fpsLogger;
	public SpriteBatch batch;
	public OrthogonalTiledMapRenderer tiledMapRenderer;
	public AssetManager manager;

	@Override
	public void create() {

		manager = new AssetManager();
		batch = new SpriteBatch();
		this.setScreen(new TestMapScreen(this));
		fpsLogger = new FPSLogger();

	}

	public void render(){
		super.render(); //Si no se hace, no renderiza la pantalla
		fpsLogger.log();
	}

	public void dispose(){
		batch.dispose();
	}
}
