package com.projectcastle.game.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.projectcastle.game.screens.ActionMenu;

/**
 * Created by ernestogonzalezchacon on 12/5/17.
 */

public class Hero extends Unit {

    public final static String TAG = Unit.class.getName();


    public Hero(Vector2 position, int attack, int defense, String name, int health, TextureRegion region, ActionMenu actionMenu) {
        super(position, attack, defense, name, health, region, actionMenu);
    }
}
