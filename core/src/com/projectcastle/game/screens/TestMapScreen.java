package com.projectcastle.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.projectcastle.game.ProjectCastleGame;
import com.projectcastle.game.entities.Enemy;
import com.projectcastle.game.entities.Hero;
import com.projectcastle.game.gameplay.InputProcessorHelp;
import com.projectcastle.game.util.Constants;
import com.projectcastle.game.util.Enums;
import com.projectcastle.game.util.TextureTools;

/**
 * Created by Ernesto Gonzalez on 23/9/17.
 * Kind of class: ${PACKAGE_NAME}
 */

public class TestMapScreen extends TemplateScreen implements InputProcessor {

    private static final String TAG = TestMapScreen.class.getName();

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

        //TODO: (opcional) Meter estas cosas en un método dentro de la clase TemplateScreen o en otra especializada

        //Setting the stage
        stage = new Stage();
        stage.addActor(this.game.actionMenu);
        stage.addActor(this.game.turnMessage);
        stage.addActor(this.game.information);

        // Creating the characters
        Vector2 positionNumber1 = textureTools.positionConverter(9, 3);
        Vector2 positionNumber2 = textureTools.positionConverter(11, 3);
        Vector2 positionTheOne = textureTools.positionConverter(10, 5);
        Vector2 positionTheTwo = textureTools.positionConverter(10, 15);
        Hero number1 = new Hero(positionNumber1.x, positionNumber1.y, 15, 16, "Number1", 11, charactersRegions[0][0], this.game.actionMenu, Constants.MOVE_LIMIT, this);
        Hero number2 = new Hero(positionNumber2.x, positionNumber2.y, 15, 7, "Number2", 11, charactersRegions[0][3], this.game.actionMenu, Constants.MOVE_LIMIT, this);
        Enemy theOne = new Enemy(positionTheOne.x, positionTheOne.y, 10, 9, "TheOne", 20, charactersRegions[0][9], this.game.actionMenu, Constants.MOVE_LIMIT, this);
        Enemy theTwo = new Enemy(positionTheTwo.x, positionTheTwo.y, 30, 1, "TheTwo", 50, charactersRegions[0][9], this.game.actionMenu, Constants.MOVE_LIMIT, this);

        //Adding the heroes and enemies to the Stage and their lists
        enemies = new SnapshotArray<Enemy>();
        heroes = new SnapshotArray<Hero>();
        enemies.add(theOne);
        enemies.add(theTwo);
        heroes.add(number1);
        heroes.add(number2);
        stage.addActor(number1);
        stage.addActor(number2);
        stage.addActor(theOne);
        stage.addActor(theTwo);

        //Input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(inputMultiplexer);
        inputProcessorHelp = new InputProcessorHelp(this.game);

        // Setting the selected layer
        selectedTileLayer = (TiledMapTileLayer) map.getLayers().get("Selected");
        selectedTileSet = map.getTileSets().getTileSet("SelectedTile");
        selectedTileLayer.setOpacity(0.6f);
        Texture selectedTexture = new Texture(Gdx.files.internal(Constants.SELECTED_SPRITE_ASSET));
        selectedSpriteRegion = new TextureRegion(selectedTexture, Constants.TILE_SIZE, Constants.TILE_SIZE);

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

        if (game.activeTurn == Enums.Turn.PLAYER)
            return inputProcessorHelp.ScreenTouchDown(this, screenX, screenY, pointer, button);
        else
            return false;

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
