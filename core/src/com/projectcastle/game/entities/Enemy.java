package com.projectcastle.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
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


    public Enemy(final Vector2 position, int attack, int defense, final String name, int health, TextureRegion region, final ActionMenu actionMenu) {
        super(position, attack, defense, name, health, region, actionMenu);

        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if (actionMenu.getCalledBy().getState() == Enums.UnitState.ATTACKING){
                    Gdx.app.log(TAG, "Are you attacking me?"); //TODO: Hacer que el ataque funcione
                } else {
                    Gdx.app.log(TAG, "Can't touch this!");
                }

                return true;
            }
        });

    }
}
