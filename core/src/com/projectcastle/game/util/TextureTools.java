package com.projectcastle.game.util;

/**
 * Created by ernestogonzalezchacon on 8/9/17.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

//TODO: Utilizar esta clase para dividir y escalar texturas

/**
 * Clase para dividir las imágenes
 */
public class TextureTools {

    public TextureTools(){}

    public TextureRegion[][] divide(Texture texture, int tileWidth, int tileHeight){

        TextureRegion[][] regions = new TextureRegion[8][12];
        regions = TextureRegion.split(texture, tileWidth, tileHeight);

        return regions;

    }


}
