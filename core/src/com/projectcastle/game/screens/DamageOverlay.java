package com.projectcastle.game.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.projectcastle.game.util.Constants;

public class DamageOverlay extends Label {

    private final static String TAG = DamageOverlay.class.getName();

    public DamageOverlay(CharSequence text, Skin skin, float x, float y){

        super(text, skin);
        this.setX(x);
        this.setY(y);
        this.setBounds(x, y, Constants.TILE_SIZE, Constants.TILE_SIZE);
        this.setAlignment(Align.center);
        this.setColor(Color.RED);
        this.setFontScale(Constants.DAMAGE_FONT_SCALE);

    }



}
