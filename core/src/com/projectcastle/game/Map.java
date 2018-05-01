package com.projectcastle.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.projectcastle.game.entities.Enemy;
import com.projectcastle.game.entities.Hero;
import com.projectcastle.game.util.Assets;
import com.projectcastle.game.util.Constants;
import com.projectcastle.game.util.TextureTools;

public class Map {

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


    public Map(int mapID){

        gameOver = false;
        victory = false;
        enemies = new SnapshotArray<Enemy>();
        heroes = new SnapshotArray<Hero>();
        textureTools = new TextureTools();
        stage = new Stage();
        textureTools = new TextureTools();
        viewport = new FitViewport(Constants.WIDTH, Constants.HEIGHT);

        if (mapID == 0){
            tiledMap = Assets.instance.mapAssets.testMap;
        }
        tiledMapRenderer = new OrthogonalTiledMapRenderer(this.tiledMap, Constants.UNIT_SCALE);

    }

    public void update (float delta) {

        stage.act(delta);

    }

    public void render (SpriteBatch batch){

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        //TODO: Renderizar el mapa
        //Rendering map
        tiledMapRenderer.setView((OrthographicCamera) viewport.getCamera());
        tiledMapRenderer.render();

        batch.end();


    }

    public void dispose(){

        stage.dispose();

    }

    public static Map debugMap(){

        Map map = new Map(0);
        map.initializeDebugMap();
        return map;

    }

    private void initializeDebugMap(){



        // Creating the characters
//        Vector2 positionNumber1 = textureTools.positionConverter(9, 3);
//        Vector2 positionNumber2 = textureTools.positionConverter(11, 3);
//        Vector2 positionTheOne = textureTools.positionConverter(10, 5);
//        Vector2 positionTheTwo = textureTools.positionConverter(10, 15);
//        Hero number1 = new Hero(positionNumber1.x, positionNumber1.y, 15, 16, "Number1", 11, charactersRegions[0][0], this.game.actionMenu, Constants.MOVE_LIMIT, this);
//        Hero number2 = new Hero(positionNumber2.x, positionNumber2.y, 15, 7, "Number2", 11, charactersRegions[0][3], this.game.actionMenu, Constants.MOVE_LIMIT, this);
//        Enemy theOne = new Enemy(positionTheOne.x, positionTheOne.y, 10, 9, "TheOne", 20, charactersRegions[0][9], this.game.actionMenu, Constants.MOVE_LIMIT, this);
//        Enemy theTwo = new Enemy(positionTheTwo.x, positionTheTwo.y, 30, 1, "TheTwo", 50, charactersRegions[0][9], this.game.actionMenu, Constants.MOVE_LIMIT, this);

        //Adding the heroes and enemies to the Stage and their lists
//        enemies = new SnapshotArray<Enemy>();
//        heroes = new SnapshotArray<Hero>();
//        enemies.add(theOne);
//        enemies.add(theTwo);
//        heroes.add(number1);
//        heroes.add(number2);
//        stage.addActor(number1);
//        stage.addActor(number2);
//        stage.addActor(theOne);
//        stage.addActor(theTwo);

    }

}
