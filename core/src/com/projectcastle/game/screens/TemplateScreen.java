package com.projectcastle.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.projectcastle.game.entities.Enemy;
import com.projectcastle.game.entities.Hero;
import com.projectcastle.game.gameplay.InputProcessorHelp;
import com.projectcastle.game.util.TextureTools;

abstract class TemplateScreen implements Screen {

    private static final String TAG = TemplateScreen.class.getName();

    public OrthographicCamera camera;
    public TiledMap map;
    public Viewport viewport;
    public Texture characters;
    public Stage stage;
    public TextureRegion[][] charactersRegions;
    public TextureTools textureTools;
    public SnapshotArray<Hero> heroes;
    public SnapshotArray<Enemy> enemies;
    public InputProcessorHelp inputProcessorHelp;

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

    public SnapshotArray<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(SnapshotArray<Hero> heroes) {
        this.heroes = heroes;
    }

    public SnapshotArray<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(SnapshotArray<Enemy> enemies) {
        this.enemies = enemies;
    }

    public InputProcessorHelp getInputProcessorHelp() {
        return inputProcessorHelp;
    }

    public void setInputProcessorHelp(InputProcessorHelp inputProcessorHelp) {
        this.inputProcessorHelp = inputProcessorHelp;
    }
}
