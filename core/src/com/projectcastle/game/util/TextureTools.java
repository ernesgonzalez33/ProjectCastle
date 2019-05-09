package com.projectcastle.game.util;

import com.badlogic.gdx.math.Vector2;

/**
 * Class for working with the textures
 */
public class TextureTools {

    public TextureTools(){}

    public Vector2 positionConverter (int x, int y){

        return new Vector2(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE);

    }

    public Vector2 tileFinder (int x, int y){

        x = x / Constants.TILE_SIZE;
        y = y / Constants.TILE_SIZE;

        return new Vector2(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE);

    }

    public Vector2 positionConverter (Vector2 position){

        return new Vector2(position.x / Constants.TILE_SIZE, position.y / Constants.TILE_SIZE);

    }

}
