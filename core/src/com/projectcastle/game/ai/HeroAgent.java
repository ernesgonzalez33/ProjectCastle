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

import java.util.Random;

public class HeroAgent extends Unit {

    StateQLearning stateQLearning;
    double explorationRateThreshold;
    double explorationRate;
    Random random;


    public HeroAgent(float positionX, float positionY, int attack, int defense, String name, int health, TextureRegion region, int moveLimit, Map map) {
        super(positionX, positionY, attack, defense, name, health, region, moveLimit, map);

        stateQLearning = new StateQLearning(false, false);
        explorationRateThreshold = 0.0;
        explorationRate = 1.0;
        random = new Random();

    }

    public void runTrainedAgent(){

        if (getMap().game.activeTurn == Enums.Turn.PLAYER){
            setStateQLearning();
            //Selecciono la acción con mejor valor
            double action = 0.0;
            int selected = 0;
            for (int jj = 0; jj < Constants.ACTIONS; jj++){
                if (getMap().qTable.qTable[stateQLearning.getStateID()][jj] > action){
                    action = getMap().qTable.qTable[stateQLearning.getStateID()][jj];
                    selected = jj;
                }
            }
            while (this.getState() != Enums.UnitState.ATTACKED){ selectActionTrained(selected); }
        }

    }

    public void runAgent(){

        explorationRateThreshold = Math.random();
        if (explorationRateThreshold > explorationRate){
            //Selecciono la acción con mejor valor
            double action = 0.0;
            int selected = 0;
            for (int jj = 0; jj < Constants.ACTIONS; jj++){
                if (getMap().qTable.qTable[stateQLearning.getStateID()][jj] > action){
                    action = getMap().qTable.qTable[stateQLearning.getStateID()][jj];
                    selected = jj;
                }
            }
            while (this.getState() != Enums.UnitState.ATTACKED){ selectAction(selected); }
        } else {
            while (this.getState() != Enums.UnitState.ATTACKED){ selectAction(random.nextInt(27)); }
        }



    }

    public boolean enemyInZone(){

        SnapshotArray<Enemy> enemiesICanAttack = new SnapshotArray<Enemy>();

        //Find the positions I can get
        getMap().highlightTilesToMove(this);



        for (Vector2 positionItCanMove:this.getCanMovePositions()) {
            for (Enemy enemy : getMap().getEnemies()) {
                Vector2 positionAux = new Vector2(positionItCanMove.x * Constants.TILE_SIZE, positionItCanMove.y * Constants.TILE_SIZE);
                if (isAdjacent(positionAux, enemy)) {
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

        int reward = 0;

        //Checking false positions to move
        getMap().highlightTilesToMove(this);
        Vector2 positionAux = new Vector2(0, 0);

        switch (actionID){
            case 0:
                positionAux.set(this.getX(), this.getY() + 3 * Constants.TILE_SIZE);
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                    reward++;
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 1:
                positionAux.set(this.getX() - Constants.TILE_SIZE, this.getY() + 2 * Constants.TILE_SIZE);
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                    reward++;
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 2:
                positionAux.set(this.getX(), this.getY() + 2 * Constants.TILE_SIZE);
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                    reward++;
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 3:
                positionAux.set(this.getX() + Constants.TILE_SIZE, this.getY() + 2 * Constants.TILE_SIZE);
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                    reward++;
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 4:
                positionAux.set(this.getX() - 2*Constants.TILE_SIZE, this.getY() + Constants.TILE_SIZE);
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                    reward++;
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 5:
                positionAux.set(this.getX() - Constants.TILE_SIZE, this.getY() + Constants.TILE_SIZE);
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                    reward++;
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 6:
                positionAux.set(this.getX(), this.getY() + Constants.TILE_SIZE);
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                    reward++;
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 7:
                positionAux.set(this.getX() + Constants.TILE_SIZE, this.getY() + Constants.TILE_SIZE);
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                    reward++;
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 8:
                positionAux.set(this.getX() + 2*Constants.TILE_SIZE, this.getY() + Constants.TILE_SIZE);
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                    reward++;
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 9:
                positionAux.set(this.getX() - 3*Constants.TILE_SIZE, this.getY());
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                    reward++;
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 10:
                positionAux.set(this.getX() - 2*Constants.TILE_SIZE, this.getY());
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                    reward++;
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 11:
                positionAux.set(this.getX() - Constants.TILE_SIZE, this.getY());
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                    reward++;
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 12:
                positionAux.set(this.getX(), this.getY());
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                    reward++;
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 13:
                positionAux.set(this.getX() + Constants.TILE_SIZE, this.getY());
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                    reward++;
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 14:
                positionAux.set(this.getX() + 2*Constants.TILE_SIZE, this.getY());
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                    reward++;
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 15:
                positionAux.set(this.getX() + 3*Constants.TILE_SIZE, this.getY());
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                    reward++;
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 16:
                positionAux.set(this.getX() - 2*Constants.TILE_SIZE, this.getY() - Constants.TILE_SIZE);
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                    reward++;
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 17:
                positionAux.set(this.getX() - Constants.TILE_SIZE, this.getY() - Constants.TILE_SIZE);
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                    reward++;
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 18:
                positionAux.set(this.getX(), this.getY() - Constants.TILE_SIZE);
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                    reward++;
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 19:
                positionAux.set(this.getX() + Constants.TILE_SIZE, this.getY() - Constants.TILE_SIZE);
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                    reward++;
                }
                break;
            case 20:
                positionAux.set(this.getX() + 2*Constants.TILE_SIZE, this.getY() - Constants.TILE_SIZE);
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                    reward++;
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 21:
                positionAux.set(this.getX() - Constants.TILE_SIZE, this.getY() - 2*Constants.TILE_SIZE);
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                    reward++;
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 22:
                positionAux.set(this.getX(), this.getY() - 2*Constants.TILE_SIZE);
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                    reward++;
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 23:
                positionAux.set(this.getX() + Constants.TILE_SIZE, this.getY() - 2*Constants.TILE_SIZE);
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                    reward++;
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 24:
                positionAux.set(this.getX(), this.getY() - 3*Constants.TILE_SIZE);
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                    reward++;
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 25: //Ataca
                if (this.stateQLearning.canAttack){
                    attack();
                    this.setState(Enums.UnitState.ATTACKED);
                    reward += 5;
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 26: //No ataca
                setState(Enums.UnitState.ATTACKED);
                break;
        }

        getMap().clearHighlightedTiles(this);

        int stateQLearning = this.stateQLearning.getStateID();
        setStateQLearning();
        int newStateQLearning = this.stateQLearning.getStateID();

        //Consigo la mejor acción del siguiente estado
        double action = 0.0;
        int selected = 0;
        for (int jj = 0; jj < Constants.ACTIONS; jj++){
            if (getMap().qTable.qTable[newStateQLearning][jj] > action){
                action = getMap().qTable.qTable[newStateQLearning][jj];
                selected = jj;
            }
        }

        //Actualizo la tabla Q-Learning
        getMap().qTable.qTable[stateQLearning][actionID] = getMap().qTable.qTable[stateQLearning][actionID] * (1 - Constants.LEARNING_RATE) + Constants.LEARNING_RATE * (reward + Constants.DISCOUNT_RATE * getMap().qTable.qTable[newStateQLearning][selected]);

        //Actualizo los rewards
        getMap().rewardsCurrentEpisode += reward;

        //Actualizo el exploration rate
        explorationRate = Constants.MIN_EXPLORATION_RATE + (Constants.MAX_EXPLORATION_RATE - Constants.MIN_EXPLORATION_RATE) * Math.exp(-Constants.EXPLORATION_DECAY_RATE*getMap().game.episodesCont);


    }

    public void selectActionTrained (int actionID) {


        //Checking false positions to move
        getMap().highlightTilesToMove(this);
        Vector2 positionAux = new Vector2(0, 0);

        switch (actionID){
            case 0:
                positionAux.set(this.getX(), this.getY() + 3 * Constants.TILE_SIZE);
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 1:
                positionAux.set(this.getX() - Constants.TILE_SIZE, this.getY() + 2 * Constants.TILE_SIZE);
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 2:
                positionAux.set(this.getX(), this.getY() + 2 * Constants.TILE_SIZE);
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 3:
                positionAux.set(this.getX() + Constants.TILE_SIZE, this.getY() + 2 * Constants.TILE_SIZE);
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 4:
                positionAux.set(this.getX() - 2*Constants.TILE_SIZE, this.getY() + Constants.TILE_SIZE);
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 5:
                positionAux.set(this.getX() - Constants.TILE_SIZE, this.getY() + Constants.TILE_SIZE);
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 6:
                positionAux.set(this.getX(), this.getY() + Constants.TILE_SIZE);
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 7:
                positionAux.set(this.getX() + Constants.TILE_SIZE, this.getY() + Constants.TILE_SIZE);
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 8:
                positionAux.set(this.getX() + 2*Constants.TILE_SIZE, this.getY() + Constants.TILE_SIZE);
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 9:
                positionAux.set(this.getX() - 3*Constants.TILE_SIZE, this.getY());
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 10:
                positionAux.set(this.getX() - 2*Constants.TILE_SIZE, this.getY());
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 11:
                positionAux.set(this.getX() - Constants.TILE_SIZE, this.getY());
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 12:
                positionAux.set(this.getX(), this.getY());
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 13:
                positionAux.set(this.getX() + Constants.TILE_SIZE, this.getY());
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 14:
                positionAux.set(this.getX() + 2*Constants.TILE_SIZE, this.getY());
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 15:
                positionAux.set(this.getX() + 3*Constants.TILE_SIZE, this.getY());
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 16:
                positionAux.set(this.getX() - 2*Constants.TILE_SIZE, this.getY() - Constants.TILE_SIZE);
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 17:
                positionAux.set(this.getX() - Constants.TILE_SIZE, this.getY() - Constants.TILE_SIZE);
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 18:
                positionAux.set(this.getX(), this.getY() - Constants.TILE_SIZE);
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 19:
                positionAux.set(this.getX() + Constants.TILE_SIZE, this.getY() - Constants.TILE_SIZE);
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                }
                break;
            case 20:
                positionAux.set(this.getX() + 2*Constants.TILE_SIZE, this.getY() - Constants.TILE_SIZE);
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 21:
                positionAux.set(this.getX() - Constants.TILE_SIZE, this.getY() - 2*Constants.TILE_SIZE);
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 22:
                positionAux.set(this.getX(), this.getY() - 2*Constants.TILE_SIZE);
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 23:
                positionAux.set(this.getX() + Constants.TILE_SIZE, this.getY() - 2*Constants.TILE_SIZE);
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 24:
                positionAux.set(this.getX(), this.getY() - 3*Constants.TILE_SIZE);
                if (getState() == Enums.UnitState.IDLE && getCanMovePositions().contains(getMap().getTextureTools().positionConverter(positionAux))){
                    this.addAction(Actions.moveTo(positionAux.x, positionAux.y, 1));
                    if (this.canAttack(positionAux, getStage())) setState(Enums.UnitState.MOVED);
                    else setState(Enums.UnitState.ATTACKED);
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 25: //Ataca
                if (this.stateQLearning.canAttack){
                    attack();
                    this.setState(Enums.UnitState.ATTACKED);
                } else setState(Enums.UnitState.ATTACKED);
                break;
            case 26: //No ataca
                setState(Enums.UnitState.ATTACKED);
                break;
        }

        getMap().clearHighlightedTiles(this);


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
