package com.projectcastle.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.projectcastle.game.screens.ActionMenu;

/**
 * Created by ernestogonzalezchacon on 12/5/17.
 */

public class Hero extends Unit {

    public final static String TAG = Hero.class.getName();


    public Hero(final Vector2 position, int attack, int defense, final String name, int health, TextureRegion region, final ActionMenu actionMenu) {
        super(position, attack, defense, name, health, region, actionMenu);

        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                actionMenu.setPosition(position.x, position.y);
                actionMenu.setVisible(true);
                actionMenu.setCalledBy(name);

                return true;
            }
        });

    }
}
