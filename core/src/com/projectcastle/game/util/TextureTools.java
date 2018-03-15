package com.projectcastle.game.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Class for working with the textures
 */
public class TextureTools {

    public TextureTools(){}

    public TextureRegion[][] divide(Texture texture, int i, int j, int tileWidth, int tileHeight){

        TextureRegion[][] regions;
        regions = TextureRegion.split(texture, tileWidth, tileHeight);

        return regions;

    }

    public Vector2 positionConverter (int x, int y){

        return new Vector2(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE);

    }

    public Vector2 tileFinder (int x, int y){

        x = x / Constants.TILE_SIZE;
        y = y / Constants.TILE_SIZE;

        return new Vector2(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE);

    }


}
