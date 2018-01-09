package com.projectcastle.game.entities;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.projectcastle.game.screens.ActionMenu;
import com.projectcastle.game.util.Constants;

/**
 * Created by ernestogonzalezchacon on 12/5/17.
 */

public class Unit extends Actor {

    public final static String TAG = Unit.class.getName();
    protected Vector2 position;
    protected int attack;
    protected int defense;
    protected String name;
    protected int health;
    protected TextureRegion region;
    protected boolean isEnemy;

    public Unit(Vector2 position, int attack, int defense, String name, int health, TextureRegion region, final ActionMenu actionMenu) {

        this.setPosition(position.x, position.y);
        this.setBounds(position.x, position.y, region.getRegionWidth(), region.getRegionHeight());
        this.setTouchable(Touchable.enabled);
        this.attack = attack;
        this.defense = defense;
        this.name = name;
        this.health = health;
        this.region = region;
        this.setScale(Constants.CHARACTER_SCALE, Constants.CHARACTER_SCALE);
        this.setRotation(0);

        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if (isEnemy){
                    Gdx.app.log(TAG, "Don't touch me!");
                } else {
                    //TODO: Hacer que aparezca el ActionMenu en el sitio correcto
                    actionMenu.setPosition(getPositionX(), getPositionY());
                    actionMenu.setVisible(true);
                }
                return true;
            }
        });
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

    public float getPositionX() {return position.x; }

    public float getPositionY() {return position.y; }

}
