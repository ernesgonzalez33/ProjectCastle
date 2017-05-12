package com.projectcastle.game.util;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by ernestogonzalezchacon on 12/5/17.
 */

public class Assets implements Disposable, AssetErrorListener {

    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();

    private AssetManager assetManager;

    private Assets() {
    }

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);
        assetManager.load(Constants.TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS);
//        gigaGalAssets = new GigaGalAssets(atlas);
//        platformAssets = new PlatformAssets(atlas);
//        bulletAssets = new BulletAssets(atlas);
//        enemyAssets = new EnemyAssets(atlas);
//        explosionAssets = new ExplosionAssets(atlas);
//        powerupAssets = new PowerupAssets(atlas);
//        exitPortalAssets = new ExitPortalAssets(atlas);
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {

    }

    @Override
    public void dispose() {

    }
}
