package com.projectcastle.game.ai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.projectcastle.game.Map;
import com.projectcastle.game.entities.Unit;
import com.projectcastle.game.util.Enums;

public class HeroAgent extends Unit {

    public HeroAgent(float positionX, float positionY, int attack, int defense, String name, int health, TextureRegion region, int moveLimit, Map map) {
        super(positionX, positionY, attack, defense, name, health, region, moveLimit, map);
    }

    public void runAgent(){

        Gdx.app.log("Hero", "El agente corre");
        this.addAction(Actions.moveTo(this.getX()+32, this.getY()+32, 1));
        this.setState(Enums.UnitState.ATTACKED);

    }
}
