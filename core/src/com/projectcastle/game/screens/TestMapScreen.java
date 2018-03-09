package com.projectcastle.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.projectcastle.game.ProjectCastleGame;
import com.projectcastle.game.entities.Enemy;
import com.projectcastle.game.entities.Hero;
import com.projectcastle.game.util.Constants;
import com.projectcastle.game.util.TextureTools;

import com.projectcastle.game.gameplay.InputProcessorHelp;

/**
 * Created by Ernesto Gonzalez on 23/9/17.
 * Kind of class: ${PACKAGE_NAME}
 */

public class TestMapScreen implements Screen, InputProcessor {

    private static final String TAG = TestMapScreen.class.getName();

    private final ProjectCastleGame game;
    private OrthographicCamera camera;
    private TiledMap map;
    private TiledMapTileLayer selectedTileLayer;
    private TiledMapTileSet selectedTileSet;
    private Viewport viewport;
    private Texture characters;
    private Stage stage;
    private TextureRegion [][] charactersRegions;
    private Texture selectedSprite;
    private TextureRegion selectedSpriteRegion;
    private TextureTools textureTools;
    private Hero number1;
    private Hero number2;
    private Enemy theOne;
    private InputProcessorHelp inputProcessorHelp;

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
        game.manager.load(Constants.SELECTED_SPRITE_ASSET, Texture.class);

        game.manager.finishLoading();
        // once the asset manager is done loading
        map = game.manager.get(Constants.TEST_MAP);
//        map.getLayers().get("Roof").setOpacity(0.9f); //TODO: (opcional) transparentar ciertas capas para que se vean los personajes
        game.tiledMapRenderer = new OrthogonalTiledMapRenderer(map, Constants.UNIT_SCALE);

        // Treating the textures
        characters = game.manager.get(Constants.CHARACTERS_ASSET);
        textureTools = new TextureTools();
        charactersRegions = textureTools.divide(characters, 8, 12, Constants.CHARACTER_SIZE, Constants.CHARACTER_SIZE);
        selectedSprite = game.manager.get(Constants.SELECTED_SPRITE_ASSET);
        selectedSpriteRegion = new TextureRegion(selectedSprite);
        selectedSpriteRegion.setRegionWidth(32);
        selectedSpriteRegion.setRegionHeight(32);

        //Setting the stage
        stage = new Stage();

        // Creating the characters
        Vector2 positionNumber1 = textureTools.positionConverter(9, 3);
        Vector2 positionNumber2 = textureTools.positionConverter(11, 3);
        Vector2 positionTheOne = textureTools.positionConverter(10, 8);
        number1 = new Hero(positionNumber1.x, positionNumber1.y, 15, 16, "Number1", 11, charactersRegions[0][0], this.game.actionMenu, 3);
        number2 = new Hero(positionNumber2.x, positionNumber2.y, 15, 7, "Number2", 11, charactersRegions[0][3], this.game.actionMenu, 3);
        theOne = new Enemy(positionTheOne.x, positionTheOne.y, 10, 9, "TheOne", 20, charactersRegions[0][9], this.game.actionMenu, 3);
        stage.addActor(number1);
        stage.addActor(number2);
        stage.addActor(theOne);
        stage.addActor(this.game.actionMenu);

        //Input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(inputMultiplexer);
        inputProcessorHelp = new InputProcessorHelp(this.game);

        // Experimenting with the map
        selectedTileLayer = (TiledMapTileLayer) map.getLayers().get("Selected");
        selectedTileSet = map.getTileSets().getTileSet("SelectedTile");
        selectedTileLayer.setOpacity(0.6f);

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
        stage.act(delta); //In the case something need to move

        game.batch.begin();

        stage.draw();

        game.batch.end();

        //Testing the inputs


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
        stage.dispose();

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        return inputProcessorHelp.ScreenTouchDown(screenX, screenY, viewport, selectedSpriteRegion, selectedTileSet, selectedTileLayer);

    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
