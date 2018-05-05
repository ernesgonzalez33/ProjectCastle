package com.projectcastle.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.projectcastle.game.entities.Enemy;
import com.projectcastle.game.entities.Hero;
import com.projectcastle.game.entities.Unit;
import com.projectcastle.game.gameplay.InputProcessorHelp;
import com.projectcastle.game.screens.GameOverScreen;
import com.projectcastle.game.util.Assets;
import com.projectcastle.game.util.Constants;
import com.projectcastle.game.util.Enums;
import com.projectcastle.game.util.TextureTools;

import java.util.ArrayList;

public class Map implements InputProcessor {

    public static final String TAG = Map.class.getName();
    private TiledMap tiledMap;
    public boolean gameOver;
    public boolean victory;
    SnapshotArray<Enemy> enemies;
    SnapshotArray<Hero> heroes;
    TextureTools textureTools;
    public Stage stage;
    public Viewport viewport;
    public OrthogonalTiledMapRenderer tiledMapRenderer;
    public OrthographicCamera camera;
    public ShapeRenderer shapeRenderer;
    public ProjectCastleGame game;
    TiledMapTileLayer selectedTileLayer;
    TiledMapTileSet selectedTileSet;
    TextureRegion selectedSpriteRegion;
    InputProcessorHelp inputProcessorHelp;


    public Map(int mapID, final ProjectCastleGame game){

        gameOver = false;
        victory = false;
        enemies = new SnapshotArray<Enemy>();
        heroes = new SnapshotArray<Hero>();
        textureTools = new TextureTools();
        stage = new Stage();
        textureTools = new TextureTools();
        viewport = new FitViewport(Constants.WIDTH, Constants.HEIGHT);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.WIDTH, Constants.HEIGHT);

        if (mapID == 0){
            tiledMap = Assets.instance.mapAssets.testMap;
        }
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, Constants.UNIT_SCALE);
        shapeRenderer = new ShapeRenderer();
        this.game = game;

        stage.addActor(game.actionMenu);
        stage.addActor(game.information);
        stage.addActor(game.turnMessage);

        //Input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(inputMultiplexer);
        inputProcessorHelp = new InputProcessorHelp(this.game);

        // Setting the selected layer
        selectedTileLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Selected");
        selectedTileSet = tiledMap.getTileSets().getTileSet("SelectedSprite");
        selectedTileLayer.setOpacity(0.6f);
        selectedSpriteRegion = Assets.instance.selectedAssets.selectedRegion;

    }

    public void update (float delta) {

        stage.act(delta);

    }

    public void render (SpriteBatch batch){

        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        batch.begin();

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Point);
        shapeRenderer.setColor(Color.DARK_GRAY);
        //Horizontal lines
        int ii = 0;
        while (ii < Constants.HEIGHT){
            int jj = 0;
            while (jj < Constants.WIDTH){
                shapeRenderer.point(jj, ii, 0);
                jj += 2;
            }
            ii += Constants.TILE_SIZE;
        }
        //Vertical lines
        ii = 0;
        while (ii < Constants.WIDTH){
            int jj = 0;
            while (jj < Constants.HEIGHT){
                shapeRenderer.point(ii, jj, 0);
                jj += 2;
            }
            ii += Constants.TILE_SIZE;
        }
        shapeRenderer.end();

        //Drawing stage after the lines
        batch.begin();

        stage.draw();

        batch.end();

    }

    public void dispose(){

        stage.dispose();
        shapeRenderer.dispose();
        tiledMapRenderer.dispose();

    }

    public static Map debugMap(final ProjectCastleGame game){

        Map map = new Map(0, game);
        map.initializeDebugMap();
        return map;

    }

    private void initializeDebugMap(){

        // Creating the characters
        Vector2 positionNumber1 = textureTools.positionConverter(9, 3);
        Vector2 positionNumber2 = textureTools.positionConverter(11, 3);
        Vector2 positionTheOne = textureTools.positionConverter(10, 5);
        Hero number1 = new Hero(positionNumber1.x, positionNumber1.y, 15, 16, "Number1", 11, Assets.instance.unitsAssets.eirikaRegion, this.game.actionMenu, Constants.MOVE_LIMIT, this);
        Hero number2 = new Hero(positionNumber2.x, positionNumber2.y, 15, 7, "Number2", 11, Assets.instance.unitsAssets.christianRegion, this.game.actionMenu, Constants.MOVE_LIMIT, this);
        Enemy theOne = new Enemy(positionTheOne.x, positionTheOne.y, 10, 9, "TheOne", 20, Assets.instance.unitsAssets.skeletonRegion, this.game.actionMenu, Constants.MOVE_LIMIT, this);

        //Adding the heroes and enemies to the Stage and their lists
        enemies = new SnapshotArray<Enemy>();
        heroes = new SnapshotArray<Hero>();
        enemies.add(theOne);
        heroes.add(number1);
        heroes.add(number2);
        stage.addActor(number1);
        stage.addActor(number2);
        stage.addActor(theOne);

    }

    public void highlightTilesToMove(Unit calledBy){

        int tileLimit = calledBy.getMoveLimit();
        Vector2 initialPosition = new Vector2(calledBy.getX() / Constants.TILE_SIZE, calledBy.getY() / Constants.TILE_SIZE);
        calledBy.getCanMovePositions().add(initialPosition);
        createCellsList(initialPosition.x, initialPosition.y, tileLimit, calledBy.getCanMovePositions());

        for (Vector2 position:calledBy.getCanMovePositions()) {
            TiledMapTileLayer.Cell selectedCell = new TiledMapTileLayer.Cell();
            selectedCell.setTile(calledBy.getMap().selectedTileSet.getTile(Constants.SELECTED_TILE_ID));
            StaticTiledMapTile selectedTile = new StaticTiledMapTile(calledBy.getMap().selectedSpriteRegion);
            selectedCell.setTile(selectedTile);
            calledBy.getMap().selectedTileLayer.setCell((int) position.x, (int) position.y, selectedCell);
        }

    }

    private void createCellsList(float x, float y, int limit, ArrayList<Vector2> cells){ //TODO: (opcional) Revisar si puedo hacer que funcione para otros valores fuera de 1, 2 y 4

        if (limit > 0){
            //x+1 y
            if (!cells.contains(new Vector2(x+1, y))){
                cells.add(new Vector2(x+1, y));
                createCellsList(x+1, y, limit-1, cells);
            }
            //x y+1
            if (!cells.contains(new Vector2(x, y+1))){
                cells.add(new Vector2(x, y+1));
                createCellsList(x, y+1, limit-1, cells);
            }
            //x-1 y
            if (!cells.contains(new Vector2(x-1, y))){
                cells.add(new Vector2(x-1, y));
                createCellsList(x-1, y, limit-1, cells);
            }
            //x y-1
            if (!cells.contains(new Vector2(x, y-1))){
                cells.add(new Vector2(x, y-1));
                createCellsList(x, y-1, limit-1, cells);
            }
        }

    }

    public void clearHighlightedTiles(Unit calledBy){

        for (int ii = 0; ii < Constants.WIDTH / Constants.TILE_SIZE; ii++){
            for (int jj = 0; jj < Constants.HEIGHT / Constants.TILE_SIZE; jj++){
                selectedTileLayer.setCell(ii, jj, null);
            }
        }
        calledBy.getCanMovePositions().clear();
        //Clear the showing info variable in all enemies
        if (calledBy.getClass().getName().equals(Constants.HERO_CLASS_NAME)){
            for (Enemy enemy: this.getEnemies()){
                enemy.setShowingInfo(false);
            }
        }
    }

    private void changeTurn() {

        //Resetting all units to IDLE
        for (Enemy enemy : getEnemies()) {
            enemy.setState(Enums.UnitState.IDLE);
        }
        for (Hero hero : getHeroes()) {
            hero.setState(Enums.UnitState.IDLE);
        }

        //Changing turn and displaying turn message
        if (game.activeTurn == Enums.Turn.PLAYER) {
            game.activeTurn = Enums.Turn.ENEMY;
            if (getHeroes().size == 0){
                game.setScreen(new GameOverScreen(game));
            } else {
                game.turnMessage.setTurn(Enums.Turn.ENEMY);
                game.timer.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        runAI();
                    }
                }, Constants.DELAY);
            }
        } else {
            game.activeTurn = Enums.Turn.PLAYER;
            game.turnMessage.setTurn(Enums.Turn.PLAYER);
        }

    }

    public void verifyTurnChange(){
        int cont = 0;
        for (Hero hero: this.getHeroes()){
            if (hero.getState() == Enums.UnitState.ATTACKED){
                cont++;
            }
        }
        if (cont == this.getHeroes().size){
            this.changeTurn();
        }
    }

    private void runAI(){

        for (int ii = 0; ii < getEnemies().size; ii++) {
            getEnemies().get(ii).runAI();
        }
        game.timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                changeTurn();
            }
        }, Constants.DELAY);

    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public void setTiledMap(TiledMap tiledMap) {
        this.tiledMap = tiledMap;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isVictory() {
        return victory;
    }

    public void setVictory(boolean victory) {
        this.victory = victory;
    }

    public SnapshotArray<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(SnapshotArray<Enemy> enemies) {
        this.enemies = enemies;
    }

    public SnapshotArray<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(SnapshotArray<Hero> heroes) {
        this.heroes = heroes;
    }

    public TextureTools getTextureTools() {
        return textureTools;
    }

    public void setTextureTools(TextureTools textureTools) {
        this.textureTools = textureTools;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public OrthogonalTiledMapRenderer getTiledMapRenderer() {
        return tiledMapRenderer;
    }

    public void setTiledMapRenderer(OrthogonalTiledMapRenderer tiledMapRenderer) {
        this.tiledMapRenderer = tiledMapRenderer;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public void setShapeRenderer(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }

    public ProjectCastleGame getGame() {
        return game;
    }

    public void setGame(ProjectCastleGame game) {
        this.game = game;
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
            return inputProcessorHelp.MapTouchDown(this, screenX, screenY, pointer, button);
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
