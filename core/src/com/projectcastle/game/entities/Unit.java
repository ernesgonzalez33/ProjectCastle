package com.projectcastle.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    protected int health;
    protected TextureRegion region;

    public Unit(Vector2 position, int attack, int defense, String name, int health, TextureRegion region){

        this.position = position;
        this.attack = attack;
        this.defense = defense;
        this.name = name;
        this.health = health;
        this.region = region;

    }

    public void render(SpriteBatch batch){

        //TODO: Dibujar aqui el sprite
//        batch.draw();

    }

    public String getName() { return name; }

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

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }
}
