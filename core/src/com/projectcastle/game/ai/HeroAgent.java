package com.projectcastle.game.ai;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.SnapshotArray;
import com.projectcastle.game.Map;
import com.projectcastle.game.entities.Enemy;
import com.projectcastle.game.entities.Unit;
import com.projectcastle.game.util.Constants;
import com.projectcastle.game.util.Enums;

public class HeroAgent extends Unit {

    StateQLearning stateQLearning;
    double explorationRateThreshold;
    double explorationRate;


    public HeroAgent(float positionX, float positionY, int attack, int defense, String name, int health, TextureRegion region, int moveLimit, Map map) {
        super(positionX, positionY, attack, defense, name, health, region, moveLimit, map);

        stateQLearning = new StateQLearning(false, false);
        explorationRateThreshold = 0.0;
        explorationRate = 1.0;

    }

    public void runAgent(){

//        explorationRateThreshold = Math.random();
//        if (explorationRateThreshold > explorationRate){
//
//        }


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

        return enemiesICanAttack.size > 0;


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

    public void selectAction (int actionID) {

        //Checking false positions to move
        getMap().highlightTilesToMove(this);
        this.getCanMovePositions();
        Vector2 positionAux = new Vector2(0, 0);

        switch (actionID){
            case 0:
                positionAux.set(this.getX(), this.getY() + 3 * Constants.TILE_SIZE);
                if (getCanMovePositions().contains(positionAux)){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                }
                break;
            case 1:
                positionAux.set(this.getX() - Constants.TILE_SIZE, this.getY() + 2 * Constants.TILE_SIZE);
                if (getCanMovePositions().contains(positionAux)){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                }
                break;
            case 2:
                positionAux.set(this.getX(), this.getY() + 2 * Constants.TILE_SIZE);
                if (getCanMovePositions().contains(positionAux)){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                }
                break;
            case 3:
                positionAux.set(this.getX() + Constants.TILE_SIZE, this.getY() + 2 * Constants.TILE_SIZE);
                if (getCanMovePositions().contains(positionAux)){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                }
                break;
            case 4:
                positionAux.set(this.getX() - 2*Constants.TILE_SIZE, this.getY() + Constants.TILE_SIZE);
                if (getCanMovePositions().contains(positionAux)){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                }
                break;
            case 5:
                positionAux.set(this.getX() - Constants.TILE_SIZE, this.getY() + Constants.TILE_SIZE);
                if (getCanMovePositions().contains(positionAux)){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                }
                break;
            case 6:
                positionAux.set(this.getX(), this.getY() + Constants.TILE_SIZE);
                if (getCanMovePositions().contains(positionAux)){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                }
                break;
            case 7:
                positionAux.set(this.getX() + Constants.TILE_SIZE, this.getY() + Constants.TILE_SIZE);
                if (getCanMovePositions().contains(positionAux)){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                }
                break;
            case 8:
                positionAux.set(this.getX() + 2*Constants.TILE_SIZE, this.getY() + Constants.TILE_SIZE);
                if (getCanMovePositions().contains(positionAux)){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                }
                break;
            case 9:
                positionAux.set(this.getX() - 3*Constants.TILE_SIZE, this.getY());
                if (getCanMovePositions().contains(positionAux)){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                }
                break;
            case 10:
                positionAux.set(this.getX() - 2*Constants.TILE_SIZE, this.getY());
                if (getCanMovePositions().contains(positionAux)){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                }
                break;
            case 11:
                positionAux.set(this.getX() - Constants.TILE_SIZE, this.getY());
                if (getCanMovePositions().contains(positionAux)){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                }
                break;
            case 12:
                positionAux.set(this.getX(), this.getY());
                if (getCanMovePositions().contains(positionAux)){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                }
                break;
            case 13:
                positionAux.set(this.getX() + Constants.TILE_SIZE, this.getY());
                if (getCanMovePositions().contains(positionAux)){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                }
                break;
            case 14:
                positionAux.set(this.getX() + 2*Constants.TILE_SIZE, this.getY());
                if (getCanMovePositions().contains(positionAux)){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                }
                break;
            case 15:
                positionAux.set(this.getX() + 3*Constants.TILE_SIZE, this.getY());
                if (getCanMovePositions().contains(positionAux)){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                }
                break;
            case 16:
                positionAux.set(this.getX() - 2*Constants.TILE_SIZE, this.getY() - Constants.TILE_SIZE);
                if (getCanMovePositions().contains(positionAux)){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                }
                break;
            case 17:
                positionAux.set(this.getX() - Constants.TILE_SIZE, this.getY() - Constants.TILE_SIZE);
                if (getCanMovePositions().contains(positionAux)){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                }
                break;
            case 18:
                positionAux.set(this.getX(), this.getY() - Constants.TILE_SIZE);
                if (getCanMovePositions().contains(positionAux)){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                }
                break;
            case 19:
                positionAux.set(this.getX() + Constants.TILE_SIZE, this.getY() - Constants.TILE_SIZE);
                if (getCanMovePositions().contains(positionAux)){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                }
                break;
            case 20:
                positionAux.set(this.getX() + 2*Constants.TILE_SIZE, this.getY() - Constants.TILE_SIZE);
                if (getCanMovePositions().contains(positionAux)){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                }
                break;
            case 21:
                positionAux.set(this.getX() - Constants.TILE_SIZE, this.getY() - 2*Constants.TILE_SIZE);
                if (getCanMovePositions().contains(positionAux)){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                }
                break;
            case 22:
                positionAux.set(this.getX(), this.getY() - 2*Constants.TILE_SIZE);
                if (getCanMovePositions().contains(positionAux)){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                }
                break;
            case 23:
                positionAux.set(this.getX() + Constants.TILE_SIZE, this.getY() - 2*Constants.TILE_SIZE);
                if (getCanMovePositions().contains(positionAux)){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                }
                break;
            case 24:
                positionAux.set(this.getX(), this.getY() - 3*Constants.TILE_SIZE);
                if (getCanMovePositions().contains(positionAux)){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                }
                break;
            case 25: //Ataca
                if (this.getState() == Enums.UnitState.MOVED){
                    attack();
                    this.setState(Enums.UnitState.ATTACKED);
                }
                break;
            case 26: //No ataca
                if (this.getState() == Enums.UnitState.MOVED) {
                    this.setState(Enums.UnitState.ATTACKED);
                }
                break;
        }

    }

    private void attack () {

        for (Enemy enemy : getMap().getEnemies()) {
            if (this.isAdjacent(enemy)){
                this.setStatsAfterAttack(this, enemy);
                return;
            }
        }

    }

}
