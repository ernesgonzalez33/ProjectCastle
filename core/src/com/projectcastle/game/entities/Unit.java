package com.projectcastle.game.entities;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by ernestogonzalezchacon on 12/5/17.
 */

public class Unit {

    public final static String TAG = Unit.class.getName();
    protected Vector2 position;
    protected int attack;
    protected int defense;
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    protected int health;

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }
}
