package com.projectcastle.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
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
import com.projectcastle.game.ai.HeroAgent;
import com.projectcastle.game.ai.QTable;
import com.projectcastle.game.entities.Enemy;
import com.projectcastle.game.entities.Hero;
import com.projectcastle.game.entities.Unit;
import com.projectcastle.game.gameplay.InputProcessorHelp;
import com.projectcastle.game.util.Assets;
import com.projectcastle.game.util.Constants;
import com.projectcastle.game.util.Enums;
import com.projectcastle.game.util.TextureTools;

import java.util.ArrayList;

public class Map implements InputProcessor {

    private static final String TAG = Map.class.getName();
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
    public SnapshotArray<Vector2> enemiesNewPositions;
    public Enums.Level selectedLevel;

    //Agents
    public QTable qTable;
    SnapshotArray<HeroAgent> agents;
    public int rewardsCurrentEpisode;


    public Map(int mapID, final ProjectCastleGame game){

        gameOver = false;
        victory = false;
        enemies = new SnapshotArray<Enemy>();
        heroes = new SnapshotArray<Hero>();
        agents = new SnapshotArray<HeroAgent>();
        enemiesNewPositions = new SnapshotArray<Vector2>();
        textureTools = new TextureTools();
        stage = new Stage();
        textureTools = new TextureTools();
        viewport = new FitViewport(Constants.WIDTH, Constants.HEIGHT);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.WIDTH, Constants.HEIGHT);
        //Starting Q-Table
        qTable = new QTable(Constants.STATES, Constants.ACTIONS);

        //Tiled Map
        switch (mapID){
            case 0: tiledMap = Assets.instance.mapAssets.testMap;
            break;
            case Constants.EASY_MAP_ID: tiledMap = Assets.instance.mapAssets.easyMap;
            break;
            case Constants.MEDIUM_MAP_ID: tiledMap = Assets.instance.mapAssets.mediumMap;
            break;
            case Constants.HARD_MAP_ID: tiledMap = Assets.instance.mapAssets.hardMap;
            break;
            case Constants.EASY_MAP_ID_2: tiledMap = Assets.instance.mapAssets.easyMap2;
            break;
            case Constants.MEDIUM_MAP_ID_2: tiledMap = Assets.instance.mapAssets.mediumMap2;
            break;
            case Constants.HARD_MAP_ID_2: tiledMap = Assets.instance.mapAssets.hardMap2;
        }

        //Selected level
        if (mapID == 0){
            selectedLevel = Enums.Level.DEBUG;
        }
        if (mapID == Constants.EASY_MAP_ID || mapID == Constants.EASY_MAP_ID_2){
            selectedLevel = Enums.Level.EASY;
        }
        if (mapID == Constants.MEDIUM_MAP_ID || mapID == Constants.MEDIUM_MAP_ID_2){
            selectedLevel = Enums.Level.MEDIUM;
        }
        if (mapID == Constants.HARD_MAP_ID || mapID == Constants.HARD_MAP_ID_2){
            selectedLevel = Enums.Level.HARD;
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

        //Initializing the Map Objects
        initializeMapObjects();

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

    private void initializeMapObjects(){

        //Get the characters layer
        MapLayer objectsLayer = tiledMap.getLayers().get("Characters");
        MapObjects characters = objectsLayer.getObjects();

        //Loop to create the characters
        for (int ii = 0; ii < characters.getCount(); ii++){
            if (characters.get(ii).getName().equals(Constants.COMMANDER_NAME)){
                Hero airmanagild = new Hero((Float) characters.get(ii).getProperties().get("x"), (Float) characters.get(ii).getProperties().get("y"), (Integer) characters.get(ii).getProperties().get("attack"), (Integer) characters.get(ii).getProperties().get("defense"), characters.get(ii).getName(), (Integer) characters.get(ii).getProperties().get("health"), Assets.instance.unitsAssets.airmanagildRegion, this.game.actionMenu, Constants.MOVE_LIMIT, this);
                heroes.add(airmanagild);
                stage.addActor(airmanagild);
            } else if (characters.get(ii).getName().equals(Constants.PRINCESS_NAME)){
                Hero eirika = new Hero((Float) characters.get(ii).getProperties().get("x"), (Float) characters.get(ii).getProperties().get("y"), (Integer) characters.get(ii).getProperties().get("attack"), (Integer) characters.get(ii).getProperties().get("defense"), characters.get(ii).getName(), (Integer) characters.get(ii).getProperties().get("health"), Assets.instance.unitsAssets.eirikaRegion, this.game.actionMenu, Constants.MOVE_LIMIT, this);
                heroes.add(eirika);
                stage.addActor(eirika);
            } else if (characters.get(ii).getName().equals(Constants.PRINCE_NAME)){
                Hero christian = new Hero((Float) characters.get(ii).getProperties().get("x"), (Float) characters.get(ii).getProperties().get("y"), (Integer) characters.get(ii).getProperties().get("attack"), (Integer) characters.get(ii).getProperties().get("defense"), characters.get(ii).getName(), (Integer) characters.get(ii).getProperties().get("health"), Assets.instance.unitsAssets.christianRegion, this.game.actionMenu, Constants.MOVE_LIMIT, this);
                heroes.add(christian);
                stage.addActor(christian);
            } else if (characters.get(ii).getProperties().get("type").equals("skeleton")){
                Enemy auxSkeleton = new Enemy((Float) characters.get(ii).getProperties().get("x"), (Float) characters.get(ii).getProperties().get("y"), (Integer) characters.get(ii).getProperties().get("attack"), (Integer) characters.get(ii).getProperties().get("defense"), characters.get(ii).getName(), (Integer) characters.get(ii).getProperties().get("health"), Assets.instance.unitsAssets.skeletonRegion, this.game.actionMenu, Constants.MOVE_LIMIT, this);
                enemies.add(auxSkeleton);
                stage.addActor(auxSkeleton);
            } else {
                Enemy auxLime = new Enemy((Float) characters.get(ii).getProperties().get("x"), (Float) characters.get(ii).getProperties().get("y"), (Integer) characters.get(ii).getProperties().get("attack"), (Integer) characters.get(ii).getProperties().get("defense"), characters.get(ii).getName(), (Integer) characters.get(ii).getProperties().get("health"), Assets.instance.unitsAssets.limeRegion, this.game.actionMenu, Constants.MOVE_LIMIT, this);
                enemies.add(auxLime);
                stage.addActor(auxLime);
            }
        }

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

    public void initializeDebugMap(){

        //Erase all that was before
        for (Enemy enemy : enemies){
            enemy.remove();
        }
        for (Hero hero : heroes){
            hero.remove();
        }
        for (HeroAgent agent : agents){
            agent.remove();
        }

        // Creating the characters
        Vector2 positionNumber1 = textureTools.positionConverter(9, 3);
        Vector2 positionNumber2 = textureTools.positionConverter(11, 3);
        Vector2 positionTheOne = textureTools.positionConverter(10, 15);
        Vector2 positionTheTwo = textureTools.positionConverter(9, 15);
        HeroAgent number1 = new HeroAgent(positionNumber1.x, positionNumber1.y, 15, 1, Constants.PRINCESS_NAME, 11, Assets.instance.unitsAssets.eirikaRegion, Constants.MOVE_LIMIT, this);
        HeroAgent number2 = new HeroAgent(positionNumber2.x, positionNumber2.y, 15, 0, Constants.PRINCE_NAME, 11, Assets.instance.unitsAssets.christianRegion, Constants.MOVE_LIMIT, this);
        Enemy theOne = new Enemy(positionTheOne.x, positionTheOne.y, 10, 9, "TheOne", 20, Assets.instance.unitsAssets.skeletonRegion, this.game.actionMenu, Constants.MOVE_LIMIT, this);
        Enemy theTwo = new Enemy(positionTheTwo.x, positionTheTwo.y, 10, 9, "TheTwo", 20, Assets.instance.unitsAssets.skeletonRegion, this.game.actionMenu, Constants.MOVE_LIMIT, this);

        //Adding the heroes and enemies to the Stage and their lists
        enemies.add(theOne);
        agents.add(number1);
        agents.add(number2);
        enemies.add(theTwo);
        stage.addActor(number1);
        stage.addActor(number2);
        stage.addActor(theOne);
        stage.addActor(theTwo);

        //Setting initial state
        for (HeroAgent agent : getAgents()){
            agent.setStateQLearning();
        }

        //Setting rewards
        rewardsCurrentEpisode = 0;
        //Running agent
        runAgent();


    }

    public void highlightTilesToMove(Unit calledBy){

        int tileLimit = calledBy.getMoveLimit();
        Vector2 initialPosition = new Vector2(calledBy.getX() / Constants.TILE_SIZE, calledBy.getY() / Constants.TILE_SIZE);
        calledBy.getCanMovePositions().add(initialPosition);
        createCellsList(initialPosition.x, initialPosition.y, tileLimit, calledBy.getCanMovePositions());

        //Removing not valid positions

        //Setting the variables
        TiledMapTileLayer groundTileLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Ground");
        TiledMapTileSet overworldTileSet = tiledMap.getTileSets().getTileSet("overworld_1");
        TiledMapTileLayer.Cell auxCell = new TiledMapTileLayer.Cell();
        ArrayList<Vector2> positionsToRemove = new ArrayList<Vector2>();

        //Checking the getCanMovePositions
        for (int ii = 0; ii < calledBy.getCanMovePositions().size(); ii++){
            auxCell = groundTileLayer.getCell((int) calledBy.getCanMovePositions().get(ii).x, (int) calledBy.getCanMovePositions().get(ii).y);

            //Removing out-of-map positions
            if (auxCell == null) {
                positionsToRemove.add(calledBy.getCanMovePositions().get(ii));
            } else {
                //Removing water positions
                for (int cont = 0; cont < Constants.FORBIDDEN_ID_CELLS.size(); cont++){
                    if (auxCell.getTile().getId() == Constants.FIRST_GID_OVERWORLD + Constants.FORBIDDEN_ID_CELLS.get(cont)){
                        removeNextTiles(calledBy.getCanMovePositions(), positionsToRemove, calledBy.getX() / Constants.TILE_SIZE, calledBy.getY() / Constants.TILE_SIZE, calledBy.getCanMovePositions().get(ii).x, calledBy.getCanMovePositions().get(ii).y);
                    }
                }
            }

            //If calledBy is an enemy, also remove the positions where another enemies are situated
            if (calledBy.getClass().getName().equals(Constants.ENEMY_CLASS_NAME)){
                for (int jj = 0; jj < enemiesNewPositions.size; jj++){
                    if (enemiesNewPositions.get(jj).x / Constants.TILE_SIZE == calledBy.getCanMovePositions().get(ii).x && enemiesNewPositions.get(jj).y / Constants.TILE_SIZE == calledBy.getCanMovePositions().get(ii).y)
                        positionsToRemove.add(calledBy.getCanMovePositions().get(ii));
                }
                //Also, eliminate the positions where another heroes are situated
                for (int jj = 0; jj < getHeroes().size; jj++){
                    if (getHeroes().get(jj).getX() / Constants.TILE_SIZE == calledBy.getCanMovePositions().get(ii).x && getHeroes().get(jj).getY() / Constants.TILE_SIZE == calledBy.getCanMovePositions().get(ii).y){
                        positionsToRemove.add(calledBy.getCanMovePositions().get(ii));
                    }
                }
            }

        }

        //Actually remove the positions
        calledBy.getCanMovePositions().removeAll(positionsToRemove);

        //Highlight tiles
        for (Vector2 position:calledBy.getCanMovePositions()) {
            TiledMapTileLayer.Cell selectedCell = new TiledMapTileLayer.Cell();
            selectedCell.setTile(calledBy.getMap().selectedTileSet.getTile(Constants.SELECTED_TILE_ID));
            StaticTiledMapTile selectedTile = new StaticTiledMapTile(calledBy.getMap().selectedSpriteRegion);
            selectedCell.setTile(selectedTile);
            calledBy.getMap().selectedTileLayer.setCell((int) position.x, (int) position.y, selectedCell);
        }

    }

    private void removeNextTiles(ArrayList<Vector2> cells, ArrayList<Vector2> positionsToRemove, float originX, float originY, float x, float y){

        //All tiles after the X
        if (originX < x){
            for (int ii = 0; ii < cells.size(); ii++){
                if (cells.get(ii).y == y){
                    if (cells.get(ii).x >= x)
                        positionsToRemove.add(cells.get(ii));
                }
            }
        }

        //All tiles before the X
        if (originX > x){
            for (int ii = 0; ii < cells.size(); ii++){
                if (cells.get(ii).y == y){
                    if (cells.get(ii).x <= x)
                        positionsToRemove.add(cells.get(ii));
                }
            }
        }

        //All tiles after the Y
        if (originY < y){
            for (int ii = 0; ii < cells.size(); ii++){
                if (cells.get(ii).x == x){
                    if (cells.get(ii).y >= y)
                        positionsToRemove.add(cells.get(ii));
                }
            }
        }

        //All tiles before the Y
        if (originY > y){
            for (int ii = 0; ii < cells.size(); ii++){
                if (cells.get(ii).x == x){
                    if (cells.get(ii).y <= y)
                        positionsToRemove.add(cells.get(ii));
                }
            }
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
        for (HeroAgent agent : getAgents()) {
            agent.setState(Enums.UnitState.IDLE);
        }

        //Changing turn and displaying turn message
        if (game.activeTurn == Enums.Turn.PLAYER) {
            game.activeTurn = Enums.Turn.ENEMY;
            game.turnMessage.setTurn(Enums.Turn.ENEMY);
            game.timer.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        runAI();
                    }
                }, Constants.DELAY);
        } else {
            game.activeTurn = Enums.Turn.PLAYER;
            game.turnMessage.setTurn(Enums.Turn.PLAYER);
            if (getAgents().size > 0){
                runAgent();
            }
        }

    }

    public void verifyTurnChange(){
        if (getHeroes().size == 0) return;
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
        //Clear the Array of new positions
        this.enemiesNewPositions.clear();
        game.timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                changeTurn();
            }
        }, Constants.DELAY);

    }

    private void runAgent(){
        //Agente para los héroes
        for (int ii = 0; ii < getAgents().size; ii++){
            getAgents().get(ii).runAgent();
        }
        changeTurn();
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

    public SnapshotArray<HeroAgent> getAgents() {
        return agents;
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
            return inputProcessorHelp.MapTouchDown(this, screenX, screenY);
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
