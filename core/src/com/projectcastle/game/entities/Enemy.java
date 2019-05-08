package com.projectcastle.game.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.Timer;
import com.projectcastle.game.Map;
import com.projectcastle.game.ai.HeroAgent;
import com.projectcastle.game.screens.ActionMenu;
import com.projectcastle.game.util.Constants;
import com.projectcastle.game.util.Enums;

/**
 * Created by Ernesto Gonzalez on 12/5/17.
 * Kind of class: ${PACKAGE_NAME}
 */

public class Enemy extends Unit {

    private boolean showingInfo;

    public Enemy(float positionX, float positionY, int attack, int defense, final String name, final int health, TextureRegion region, final ActionMenu actionMenu, int moveLimit, final Map map) {
        super(positionX, positionY, attack, defense, name, health, region, moveLimit, map);

        this.showingInfo = false;

        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if (map.game.turnMessage.isVisible()){
                    map.game.turnMessage.setVisible(false);
                }

                if (map.game.activeTurn == Enums.Turn.PLAYER){
                    //Issue #1 solved
                    if (actionMenu.getCalledBy() == null || (actionMenu.getCalledBy().getState() != Enums.UnitState.MOVING && actionMenu.getCalledBy().getState() != Enums.UnitState.ATTACKING)) {
                        if (!showingInfo){
                            setShowingInfo(true);
                        }
                        else {
                            setShowingInfo(false);
                        }
                        return true;
                    }

                    if (actionMenu.getCalledBy().getState() == Enums.UnitState.ATTACKING){
                        if (actionMenu.getCalledBy().isAdjacent(getThis())){
                            map.game.timer.scheduleTask(new Timer.Task() {
                                @Override
                                public void run() {
                                    setStatsAfterAttack(actionMenu.getCalledBy(), getThis());
                                }
                            }, 1);
                            actionMenu.getCalledBy().setState(Enums.UnitState.ATTACKED);
                        }
                    }
                }
                return true;
            }
        });

    }


    public void runAI(){

        //If the enemy can attack, it attacks depending on the selectAttack method result
        SnapshotArray<Unit> heroesICanAttack =  canAttackInZone();

        if (heroesICanAttack.size > 0){
            Unit heroToAttack = selectAttack(heroesICanAttack);
            //Move to an adjacent space where the hero is
            Vector2 positionToMove = moveToAdjacent(heroToAttack);
            positionToMove = getMap().getTextureTools().tileFinder((int) positionToMove.x, (int) positionToMove.y);
            getMap().enemiesNewPositions.add(positionToMove);
            addAction(Actions.moveTo(positionToMove.x, positionToMove.y, 2));
            getMap().clearHighlightedTiles(getThis());
            attackHero(heroToAttack);
        } else {
            //If the enemy can't attack, it has to move
            Vector2 positionToMove = selectMove();
            positionToMove = getMap().getTextureTools().tileFinder((int) positionToMove.x, (int) positionToMove.y);
            getMap().enemiesNewPositions.add(positionToMove);
            addAction(Actions.moveTo(positionToMove.x, positionToMove.y, 2));
            getMap().clearHighlightedTiles(getThis());
            setState(Enums.UnitState.MOVED);
            //Verify if I can attack
            heroesICanAttack = canAttack(positionToMove);
            if (heroesICanAttack.size > 0){
                attackHero(selectAttack(heroesICanAttack));
            } else {
                setState(Enums.UnitState.ATTACKED);
            }
        }

    }

    private Vector2 moveToAdjacent(Unit heroToAttack){

        //Find the positions I can get
        getMap().highlightTilesToMove(this);

        //Find the nearest position to the hero I have to pursue
        Vector2 auxiliaryVector = new Vector2();
        float distance = 10000;
        for (Vector2 position : this.getCanMovePositions()){
            if (!(position.x == (heroToAttack.getX() / Constants.TILE_SIZE)) || !(position.y == (heroToAttack.getY() / Constants.TILE_SIZE))) {
                if (Math.abs(position.x - (heroToAttack.getX() / Constants.TILE_SIZE)) + Math.abs(position.y - (heroToAttack.getY() / Constants.TILE_SIZE)) < distance){
                    distance = Math.abs(position.x - (heroToAttack.getX() / Constants.TILE_SIZE)) + Math.abs(position.y - (heroToAttack.getY() / Constants.TILE_SIZE));
                    auxiliaryVector = position;
                }
            }
        }

        //Return the nearest position
        auxiliaryVector.x = auxiliaryVector.x * Constants.TILE_SIZE;
        auxiliaryVector.y = auxiliaryVector.y * Constants.TILE_SIZE;
        return auxiliaryVector;

    }

    private SnapshotArray<Unit> canAttackInZone(){

        SnapshotArray<Unit> heroesICanAttack = new SnapshotArray<Unit>();

        //Find the positions I can get
        getMap().highlightTilesToMove(this);

        for (Vector2 positionItCanMove:this.getCanMovePositions()){
            for (Hero hero:getMap().getHeroes()){
                if (isAdjacent(positionItCanMove, hero)){
                    heroesICanAttack.add(hero);
                }
            }
            for (HeroAgent agent:getMap().getAgents()){
                if (isAdjacent(positionItCanMove, agent)){
                    heroesICanAttack.add(agent);
                }
            }
        }

        //Clearing the highlighted tiles
        getMap().clearHighlightedTiles(getThis());

        return heroesICanAttack;

    }

    @Override
    public boolean isAdjacent(Vector2 callingUnit, Unit attackedUnit) {

        int attackingX = (int) callingUnit.x;
        int attackingY = (int) callingUnit.y;
        int attackedX = (int) (attackedUnit.getX() / Constants.TILE_SIZE);
        int attackedY = (int) (attackedUnit.getY() / Constants.TILE_SIZE);

        if (attackingX + 1 == attackedX && attackingY == attackedY)
            return true;

        if (attackingX - 1 == attackedX && attackingY == attackedY)
            return true;

        if (attackingX == attackedX && attackingY + 1 == attackedY)
            return true;

        return attackingX == attackedX && attackingY - 1 == attackedY;

    }

    private Unit selectAttack(SnapshotArray<Unit> heroes){

        //Hero to compare defense
        Unit auxiliaryHero = heroes.get(0);

        for (Unit hero : heroes){
            //If I can kill it, I attack it
            if ((this.getAttack() - hero.getDefense()) > hero.getHealth()){
                return hero;
            } else {
                if (hero.getDefense() < auxiliaryHero.getDefense()){
                    auxiliaryHero = (Unit) hero;
                }
            }
        }
        //If I can't kill a hero, I attack the one with less defense
        return auxiliaryHero;

    }

    private void attackHero (final Unit hero){

        getMap().game.timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                setStatsAfterAttack(getThis(), hero);
            }
        }, 1);
        this.setState(Enums.UnitState.ATTACKED);

    }

    private Vector2 selectMove(){

        //Find the hero to pursue
        Vector2 heroToPursuePosition;
        heroToPursuePosition = findHeroToPursue();

        //Find the positions I can get
        getMap().highlightTilesToMove(this);

        //Find the nearest position to the hero I have to pursue
        Vector2 auxiliaryVector = new Vector2();
        float distance = 10000;
        for (Vector2 position : this.getCanMovePositions()){
            if (!(position.x == (heroToPursuePosition.x / Constants.TILE_SIZE)) || !(position.y == (heroToPursuePosition.y / Constants.TILE_SIZE))) {
                if (Math.abs(position.x - (heroToPursuePosition.x / Constants.TILE_SIZE)) + Math.abs(position.y - (heroToPursuePosition.y / Constants.TILE_SIZE)) < distance){
                    distance = Math.abs(position.x - (heroToPursuePosition.x / Constants.TILE_SIZE)) + Math.abs(position.y - (heroToPursuePosition.y / Constants.TILE_SIZE));
                    auxiliaryVector = position;
                }
            }
        }

        //Return the nearest position
        auxiliaryVector.x = auxiliaryVector.x * Constants.TILE_SIZE;
        auxiliaryVector.y = auxiliaryVector.y * Constants.TILE_SIZE;
        return auxiliaryVector;

    }

    private Vector2 findHeroToPursue(){

        //Hero to compare defense
        Unit auxiliaryHero = new Unit(1000);

        for (Hero hero : this.getMap().getHeroes()){
            //If I can kill it, I attack it
            if ((this.getAttack() - hero.getDefense()) > hero.getHealth()){
                return new Vector2(hero.getX(), hero.getY());
            } else {
                if (hero.getDefense() < auxiliaryHero.getDefense()){
                    auxiliaryHero = hero;
                }
            }
        }
        for (HeroAgent agent : this.getMap().getAgents()){
            //If I can kill it, I attack it
            if ((this.getAttack() - agent.getDefense()) > agent.getHealth()){
                return new Vector2(agent.getX(), agent.getY());
            } else {
                if (agent.getDefense() < auxiliaryHero.getDefense()){
                    auxiliaryHero = agent;
                }
            }
        }
        //If I can't kill a hero, I attack the one with less defense
        return new Vector2(auxiliaryHero.getX(), auxiliaryHero.getY());

    }

    public boolean isShowingInfo() {
        return showingInfo;
    }

    public void setShowingInfo(boolean showingInfo) {

        this.showingInfo = showingInfo;
        if (!showingInfo){
            getMap().clearHighlightedTiles(this);
            getMap().game.information.setVisible(false);
        } else {
            getMap().highlightTilesToMove(getThis());
            getMap().game.information.setCalledBy(getThis());
            getMap().game.information.setVisible(true);
        }

    }


}
