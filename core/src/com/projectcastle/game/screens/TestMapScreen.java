package com.projectcastle.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.projectcastle.game.ProjectCastleGame;
import com.projectcastle.game.entities.Enemy;
import com.projectcastle.game.entities.Hero;
import com.projectcastle.game.util.Constants;
import com.projectcastle.game.util.TextureTools;

/**
 * Created by ernestogonzalezchacon on 23/9/17.
 */

public class TestMapScreen implements Screen {

    public static final String TAG = TestMapScreen.class.getName();

    final ProjectCastleGame game;
    OrthographicCamera camera;
    TiledMap map;
    Viewport viewport;
    Texture characters;
    Stage stage;
    public TextureRegion [][] charactersRegions;
    TextureTools textureTools;
    Hero number1;
    Hero number2;
    Enemy theOne;

    public TestMapScreen(final ProjectCastleGame game){
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new FitViewport(Constants.WIDTH, Constants.HEIGHT, camera);
        camera.setToOrtho(false, Constants.WIDTH, Constants.HEIGHT);

        // loading map
        game.manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        game.manager.load(Constants.TEST_MAP, TiledMap.class);

        // loading the characters
        game.manager.setLoader(Texture.class, new TextureLoader(new InternalFileHandleResolver()));
        game.manager.load(Constants.CHARACTERS_ASSET, Texture.class);

        game.manager.finishLoading();
        // once the asset manager is done loading
        TiledMap map = game.manager.get(Constants.TEST_MAP);
        game.tiledMapRenderer = new OrthogonalTiledMapRenderer(map, Constants.UNIT_SCALE);

        // Treating the textures
        characters = game.manager.get(Constants.CHARACTERS_ASSET);
        textureTools = new TextureTools();
        charactersRegions = textureTools.divide(characters, 8, 12, Constants.CHARACTER_SIZE, Constants.CHARACTER_SIZE);

        //Setting the stage
        stage = new Stage();

        // Creating the characters
        Vector2 positionNumber1 = textureTools.positionConverter(9, 3);
        Vector2 positionNumber2 = textureTools.positionConverter(11, 3);
        Vector2 positionTheOne = textureTools.positionConverter(10, 8);
        number1 = new Hero(positionNumber1, 10, 10, "Number1", 10, charactersRegions[0][0]);
        number2 = new Hero(positionNumber2, 10, 10, "Number2", 10, charactersRegions[0][3]);
        theOne = new Enemy(positionTheOne, 10, 10, "TheOne", 10, charactersRegions[0][9]);
        stage.addActor(number1);
        stage.addActor(number2);
        stage.addActor(theOne);

        //Working with the ActionMenu
        stage.addActor(this.game.actionMenu);
        this.game.actionMenu.setPosition(50, 50);
        this.game.actionMenu.setVisible(true);

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

        stage.draw();

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
