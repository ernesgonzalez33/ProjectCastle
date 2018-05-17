package com.projectcastle.game.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.projectcastle.game.util.Constants;

public class DamageOverlay extends Label {

    public DamageOverlay(CharSequence text, Skin skin, float x, float y, Color color){

        super(text, skin);
        this.setX(x);
        this.setY(y);
        this.setBounds(x, y, Constants.TILE_SIZE, Constants.TILE_SIZE);
        this.setAlignment(Align.center);
        this.setColor(color);
        this.setFontScale(Constants.DAMAGE_FONT_SCALE);

    }



}
