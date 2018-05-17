package com.projectcastle.game.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.projectcastle.game.Map;
import com.projectcastle.game.screens.ActionMenu;
import com.projectcastle.game.util.Enums;

/**
 * Created by Ernesto Gonzalez on 12/5/17.
 * Kind of class: ${PACKAGE_NAME}
 */

public class Hero extends Unit {

    public Hero(float positionX, float positionY, int attack, int defense, final String name, int health, TextureRegion region, final ActionMenu actionMenu, int moveLimit, final Map map) {
        super(positionX, positionY, attack, defense, name, health, region, moveLimit, map);

        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                map.clearHighlightedTiles(getThis());

                if (map.game.turnMessage.isVisible()){
                    map.game.turnMessage.setVisible(false);
                }

                if (map.game.activeTurn == Enums.Turn.PLAYER){
                    actionMenu.setPosition(getX(), getY()); //TODO: (opcional) Hacer que el ActionMenu aparezca más centrado en la unidad
                    actionMenu.setVisible(true);
                    actionMenu.setCalledBy(Hero.this);
                    map.game.information.setCalledBy(Hero.this);
                    map.game.information.setVisible(true);
                }

                return true;
            }
        });
    }

    Hero (int defense){

        super(defense);

    }

}
