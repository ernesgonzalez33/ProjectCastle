package com.projectcastle.game.entities;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by ernestogonzalezchacon on 12/5/17.
 */

public class Enemy extends Unit {

    public final static String TAG = Unit.class.getName();

    public Enemy (Vector2 initialPosition, int attack, int defense, int health, String name){

        this.position = initialPosition;
        this.attack = attack;
        this.defense = defense;
        this.health = health;
        this.name = name;

    }

}
