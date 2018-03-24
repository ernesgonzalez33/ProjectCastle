package com.projectcastle.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Timer;
import com.projectcastle.game.screens.ActionMenu;
import com.projectcastle.game.screens.Information;
import com.projectcastle.game.screens.TestMapScreen;
import com.projectcastle.game.screens.TurnMessage;
import com.projectcastle.game.util.Constants;
import com.projectcastle.game.util.Enums;

public class ProjectCastleGame extends Game {

	public static final String TAG = ProjectCastleGame.class.getName();

	public FPSLogger fpsLogger;
	public SpriteBatch batch;
	public OrthogonalTiledMapRenderer tiledMapRenderer;
	public AssetManager manager;
	public ActionMenu actionMenu;
	public TurnMessage turnMessage;
	public Information information;
	public Skin skin;
	public Enums.Turn activeTurn;
	public Timer timer;

	@Override
	public void create() {

		manager = new AssetManager();
		batch = new SpriteBatch();
		timer = new Timer();

		//Creating Action Menu and Turn Message
		skin = new Skin(Gdx.files.internal(Constants.FLAT_SKIN));
		actionMenu = new ActionMenu(skin);
        turnMessage = new TurnMessage(skin);
        information = new Information(skin);

		activeTurn = Enums.Turn.PLAYER;

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
