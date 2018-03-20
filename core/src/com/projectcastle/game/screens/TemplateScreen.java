package com.projectcastle.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.projectcastle.game.entities.Unit;
import com.projectcastle.game.gameplay.InputProcessorHelp;
import com.projectcastle.game.util.TextureTools;

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
    }
}
