package com.projectcastle.game.util;

/**
 * Created by ernestogonzalezchacon on 12/5/17.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * This class loads the Tiled map
 */
public class MapLoader {

    public static final String TAG = MapLoader.class.getName();

    public static TiledMap load(String path){

        TiledMap map = new TiledMap();

        try {
            map = new TmxMapLoader().load(path);
        } catch (Exception ex){
            Gdx.app.log(TAG, ex.getMessage());
            Gdx.app.log(TAG, Constants.MAP_ERROR_MESSAGE);
        }

        return map;

    }




}
