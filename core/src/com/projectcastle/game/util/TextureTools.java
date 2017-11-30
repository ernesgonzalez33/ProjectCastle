package com.projectcastle.game.util;

/**
 * Created by ernestogonzalezchacon on 8/9/17.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

//TODO: Utilizar esta clase para dividir y escalar texturas

/**
 * Clase para dividir las imágenes
 */
public class TextureTools {

    public Texture units;
    public TextureRegion[][] unitsRegions = new TextureRegion[8][12];

    public void divide(){

        units = new Texture(Gdx.files.internal("characters_1.png"));
        unitsRegions = TextureRegion.split(units, 16, 16);

    }


}
