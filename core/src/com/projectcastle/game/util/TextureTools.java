package com.projectcastle.game.util;

/**
 * Created by ernestogonzalezchacon on 8/9/17.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Class for working with the textures
 */
public class TextureTools {

    public TextureTools(){}

    public TextureRegion[][] divide(Texture texture, int tileWidth, int tileHeight){

        TextureRegion[][] regions = new TextureRegion[8][12];
        regions = TextureRegion.split(texture, tileWidth, tileHeight);

        return regions;

    }

    /**
     * Method that transforms from a regular position to the real position in the map
     * @param x
     * @param y
     * @return
     */
    public Vector2 positionConverter (int x, int y){

        return new Vector2(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE);

    }


}
