package com.projectcastle.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.projectcastle.game.screens.ActionMenu;
import com.projectcastle.game.screens.TestMapScreen;
import com.projectcastle.game.util.Constants;

public class ProjectCastleGame extends Game {

	public static final String TAG = ProjectCastleGame.class.getName();

	public FPSLogger fpsLogger;
	public SpriteBatch batch;
	public OrthogonalTiledMapRenderer tiledMapRenderer;
	public AssetManager manager;
	public ActionMenu actionMenu;
	public Skin actionMenuSkin;

	@Override
	public void create() {

		manager = new AssetManager();
		batch = new SpriteBatch();

		//Creating Action Menu
		actionMenuSkin = new Skin(Gdx.files.internal(Constants.FLAT_SKIN));
		actionMenu = new ActionMenu(actionMenuSkin);

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
