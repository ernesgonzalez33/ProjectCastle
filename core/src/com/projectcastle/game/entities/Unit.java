package com.projectcastle.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.projectcastle.game.screens.ActionMenu;
import com.projectcastle.game.util.Constants;
import com.projectcastle.game.util.Enums;

/**
 * Created by Ernesto Gonzalez on 12/5/17.
 * Kind of class: ${PACKAGE_NAME}
 */

public class Unit extends Actor {

    public final static String TAG = Unit.class.getName();
    private int attack;
    private int defense;
    private String name;
    private int health;
    private TextureRegion region;
    private Enums.UnitState state;
    private int moveLimit;

    Unit(float positionX, float positionY, int attack, int defense, String name, final int health, TextureRegion region, final ActionMenu actionMenu, int moveLimit) {

        this.setPosition(positionX, positionY);
        this.setBounds(this.getX(), this.getY(), region.getRegionWidth(), region.getRegionHeight());
        this.setTouchable(Touchable.enabled);
        this.attack = attack;
        this.defense = defense;
        this.name = name;
        this.health = health;
        this.region = region;
        this.setScale(Constants.CHARACTER_SCALE, Constants.CHARACTER_SCALE);
        this.setRotation(0);
        this.setState(Enums.UnitState.IDLE);
        this.moveLimit = moveLimit;

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(this.region, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
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

    public Enums.UnitState getState() {
        return state;
    }

    public void setState(Enums.UnitState state) {
        this.state = state;
    }

    public int getMoveLimit() { return moveLimit; }

    public void setMoveLimit(int moveLimit) { this.moveLimit = moveLimit; }

}
