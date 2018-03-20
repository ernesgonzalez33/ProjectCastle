package com.projectcastle.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.projectcastle.game.entities.Unit;
import com.projectcastle.game.gameplay.InputProcessorHelp;
import com.projectcastle.game.util.Constants;
import com.projectcastle.game.util.TextureTools;

import java.util.ArrayList;

public abstract class TemplateScreen implements Screen {

    private static final String TAG = TemplateScreen.class.getName();

    OrthographicCamera camera;
    TiledMap map;
    Viewport viewport;
    Texture characters;
    Stage stage;
    TextureRegion[][] charactersRegions;
    TextureTools textureTools;
    InputProcessorHelp inputProcessorHelp;
    TiledMapTileLayer selectedTileLayer;
    TiledMapTileSet selectedTileSet;
    TextureRegion selectedSpriteRegion;

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    public TiledMap getMap() {
        return map;
    }

    public void setMap(TiledMap map) {
        this.map = map;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public Texture getCharacters() {
        return characters;
    }

    public void setCharacters(Texture characters) {
        this.characters = characters;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public TextureRegion[][] getCharactersRegions() {
        return charactersRegions;
    }

    public void setCharactersRegions(TextureRegion[][] charactersRegions) {
        this.charactersRegions = charactersRegions;
    }

    public TextureTools getTextureTools() {
        return textureTools;
    }

    public void setTextureTools(TextureTools textureTools) {
        this.textureTools = textureTools;
    }

    public InputProcessorHelp getInputProcessorHelp() {
        return inputProcessorHelp;
    }

    public void setInputProcessorHelp(InputProcessorHelp inputProcessorHelp) {
        this.inputProcessorHelp = inputProcessorHelp;
    }

    public void highlightTilesToMove(Unit calledBy){

        int tileLimit = calledBy.getMoveLimit();
        Vector2 initialPosition = new Vector2(calledBy.getX() / Constants.TILE_SIZE, calledBy.getY() / Constants.TILE_SIZE);
        ArrayList<Vector2> canMovePositions = new ArrayList<Vector2>();
        canMovePositions.add(initialPosition);

        createCellsList(initialPosition.x, initialPosition.y, tileLimit, canMovePositions);

        for (Vector2 position:canMovePositions) {
            TiledMapTileLayer.Cell selectedCell = new TiledMapTileLayer.Cell();
            selectedCell.setTile(calledBy.getScreen().selectedTileSet.getTile(1765));
            StaticTiledMapTile selectedTile = new StaticTiledMapTile(calledBy.getScreen().selectedSpriteRegion);
            selectedCell.setTile(selectedTile);
            calledBy.getScreen().selectedTileLayer.setCell((int) position.x, (int) position.y, selectedCell);
        }

    }

    private void createCellsList(float x, float y, int limit, ArrayList<Vector2> cells){

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
}
