package com.projectcastle.game.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by ernestogonzalezchacon on 12/5/17.
 */

public class Enemy extends Unit {

    public final static String TAG = Unit.class.getName();


    public Enemy(Vector2 position, int attack, int defense, String name, int health, TextureRegion region) {
        super(position, attack, defense, name, health, region);
    }
}
