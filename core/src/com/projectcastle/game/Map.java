package com.projectcastle.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.utils.SnapshotArray;
import com.projectcastle.game.entities.Enemy;
import com.projectcastle.game.entities.Hero;
import com.projectcastle.game.gameplay.InputProcessorHelp;

public class Map extends TiledMap {

    SnapshotArray<Enemy> enemies;
    SnapshotArray<Hero> heroes;
    InputProcessorHelp inputProcessorHelp;
    TiledMapTileLayer selectedTileLayer;
    TiledMapTileSet selectedTileSet;
    TextureRegion selectedSpriteRegion;

}
