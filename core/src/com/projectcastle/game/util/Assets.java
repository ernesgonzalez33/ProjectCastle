package com.projectcastle.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;

public class Assets implements Disposable, AssetErrorListener {

    private static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();

    public MapAssets mapAssets;
    public UnitsAssets unitsAssets;
    public SelectedAssets selectedAssets;

    private AssetManager assetManager;

    private Assets(){}

    public void init (AssetManager assetManager){

        this.assetManager = assetManager;
        assetManager.setErrorListener(this);

        //Loading assets
        assetManager.load(Constants.CHARACTERS_ASSET, Texture.class);
        assetManager.load(Constants.SELECTED_SPRITE_ASSET, Texture.class);

        //Loading maps
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        assetManager.load(Constants.TEST_MAP, TiledMap.class);
        assetManager.load(Constants.EASY_MAP, TiledMap.class);
        assetManager.load(Constants.MEDIUM_MAP, TiledMap.class);
        assetManager.load(Constants.HARD_MAP, TiledMap.class);
        assetManager.load(Constants.EASY_MAP_2, TiledMap.class);
        assetManager.load(Constants.MEDIUM_MAP_2, TiledMap.class);
        assetManager.load(Constants.HARD_MAP_2, TiledMap.class);
        assetManager.finishLoading();

        //Retrieving maps
        ArrayList<TiledMap> maps = new ArrayList<TiledMap>();
        maps.add(assetManager.get(Constants.TEST_MAP, TiledMap.class));
        maps.add(assetManager.get(Constants.EASY_MAP, TiledMap.class));
        maps.add(assetManager.get(Constants.MEDIUM_MAP, TiledMap.class));
        maps.add(assetManager.get(Constants.HARD_MAP, TiledMap.class));
        maps.add(assetManager.get(Constants.EASY_MAP_2, TiledMap.class));
        maps.add(assetManager.get(Constants.MEDIUM_MAP_2, TiledMap.class));
        maps.add(assetManager.get(Constants.HARD_MAP_2, TiledMap.class));

        //Splitting sprites
        TextureRegion[][] characters;
        characters = TextureRegion.split(assetManager.get(Constants.CHARACTERS_ASSET, Texture.class), Constants.TILE_SIZE / 2, Constants.TILE_SIZE / 2);

        //Converting selected sprite
        TextureRegion selectedSprite = new TextureRegion(assetManager.get(Constants.SELECTED_SPRITE_ASSET, Texture.class), Constants.TILE_SIZE, Constants.TILE_SIZE);

        mapAssets = new MapAssets(maps);
        unitsAssets = new UnitsAssets(characters);
        selectedAssets = new SelectedAssets(selectedSprite);

    }


    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset: " + asset.fileName, throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    public static class MapAssets {

        public final TiledMap testMap;
        public final TiledMap easyMap;
        public final TiledMap mediumMap;
        public final TiledMap hardMap;
        public final TiledMap easyMap2;
        public final TiledMap mediumMap2;
        public final TiledMap hardMap2;

        MapAssets(ArrayList<TiledMap> maps){

            testMap = maps.get(0);
            easyMap = maps.get(1);
            mediumMap = maps.get(2);
            hardMap = maps.get(3);
            easyMap2 = maps.get(4);
            mediumMap2 = maps.get(5);
            hardMap2 = maps.get(6);

        }

    }

    public class UnitsAssets {

        public final TextureRegion eirikaRegion;
        public final TextureRegion airmanagildRegion;
        public final TextureRegion christianRegion;
        public final TextureRegion skeletonRegion;
        public final TextureRegion limeRegion;

        UnitsAssets (TextureRegion[][] unitsAssets){

            eirikaRegion = unitsAssets[0][7];
            airmanagildRegion = unitsAssets [0][1];
            christianRegion = unitsAssets [0][4];
            skeletonRegion = unitsAssets [0][10];
            limeRegion = unitsAssets[4][1];

        }

    }

    public class SelectedAssets{

        public final TextureRegion selectedRegion;

        SelectedAssets (TextureRegion selectedRegion) {

            this.selectedRegion = selectedRegion;

        }

    }

}
