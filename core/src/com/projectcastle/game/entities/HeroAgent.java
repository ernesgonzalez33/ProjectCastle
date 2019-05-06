package com.projectcastle.game.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.projectcastle.game.Map;

public class HeroAgent extends Unit {

    HeroAgent(float positionX, float positionY, int attack, int defense, String name, int health, TextureRegion region, int moveLimit, Map map) {
        super(positionX, positionY, attack, defense, name, health, region, moveLimit, map);
    }
}
