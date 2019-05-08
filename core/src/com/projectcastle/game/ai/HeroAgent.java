package com.projectcastle.game.ai;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.SnapshotArray;
import com.projectcastle.game.Map;
import com.projectcastle.game.entities.Enemy;
import com.projectcastle.game.entities.Unit;

public class HeroAgent extends Unit {

    StateQLearning stateQLearning;

    public HeroAgent(float positionX, float positionY, int attack, int defense, String name, int health, TextureRegion region, int moveLimit, Map map) {
        super(positionX, positionY, attack, defense, name, health, region, moveLimit, map);

        stateQLearning = new StateQLearning(false, false);
    }

    public void runAgent(){



//        Gdx.app.log("Hero", "El agente corre");
//        this.addAction(Actions.moveTo(this.getX(), this.getY() + 32, 1));
//        this.setState(Enums.UnitState.ATTACKED);

    }

    public boolean enemyInZone(){

        SnapshotArray<Enemy> enemiesICanAttack = new SnapshotArray<Enemy>();

        //Find the positions I can get
        getMap().highlightTilesToMove(this);

        for (Vector2 positionItCanMove:this.getCanMovePositions()){
            for (Enemy enemy:getMap().getEnemies()){
                if (isAdjacent(positionItCanMove, enemy)){
                    enemiesICanAttack.add(enemy);
                }
            }
        }

        //Clearing the highlighted tiles
        getMap().clearHighlightedTiles(getThis());

        if (enemiesICanAttack.size > 0) return true;
        else return false;

    }

    public void setStateQLearning(){

        boolean canAttack = false;
        for (Enemy enemy : getMap().getEnemies()){
            if (this.isAdjacent(enemy)){
               canAttack = true;
            }
        }
        this.stateQLearning.canAttack = canAttack;
        this.stateQLearning.enemyInZone = enemyInZone();


    }
}
