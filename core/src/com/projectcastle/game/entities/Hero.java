package com.projectcastle.game.entities;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by ernestogonzalezchacon on 12/5/17.
 */

public class Hero extends Unit {

    public final static String TAG = Unit.class.getName();

    public Hero (Vector2 initialPosition, String name, int attack, int defense, int health){

        this.position = initialPosition;
        this.name = name;
        this.attack = attack;
        this.defense = defense;
        this.health = health;

    }


}
