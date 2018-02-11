package com.projectcastle.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.projectcastle.game.screens.ActionMenu;
import com.projectcastle.game.util.Enums;

/**
 * Created by Ernesto Gonzalez on 12/5/17.
 * Kind of class: ${PACKAGE_NAME}
 */

public class Enemy extends Unit {

    private final static String TAG = Enemy.class.getName();


    public Enemy(float positionX, float positionY, int attack, int defense, final String name, final int health, TextureRegion region, final ActionMenu actionMenu, int moveLimit) {
        super(positionX, positionY, attack, defense, name, health, region, actionMenu, moveLimit);

        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                //Issue #1 solved
                if (actionMenu.getCalledBy() == null) {
                    Gdx.app.log(TAG, "Can't touch this!");
                    return true;
                }

                if (actionMenu.getCalledBy().getState() == Enums.UnitState.ATTACKING){
                    Gdx.app.log(TAG, "Attacking " + getName() + " by " + actionMenu.getCalledBy().getName());
                    setStatsAfterAttack(actionMenu.getCalledBy(), getThis());
                    if (getHealth() < 1){
                        Gdx.app.log(TAG, getName() + " died!");
                        remove();
                    }
                    actionMenu.getCalledBy().setState(Enums.UnitState.IDLE);
                } else if (actionMenu.getCalledBy().getState() == Enums.UnitState.IDLE) {
                    Gdx.app.log(TAG, "Can't touch this!");
                } else {
                    Gdx.app.log(TAG, "Can't move there");
                }

                return true;
            }
        });

    }
}
