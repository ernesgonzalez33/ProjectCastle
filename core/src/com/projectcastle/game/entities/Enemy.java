package com.projectcastle.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.SnapshotArray;
import com.projectcastle.game.screens.ActionMenu;
import com.projectcastle.game.screens.TemplateScreen;
import com.projectcastle.game.screens.VictoryScreen;
import com.projectcastle.game.util.Constants;
import com.projectcastle.game.util.Enums;

/**
 * Created by Ernesto Gonzalez on 12/5/17.
 * Kind of class: ${PACKAGE_NAME}
 */

public class Enemy extends Unit {

    private final static String TAG = Enemy.class.getName();

    private boolean showingInfo;

    public Enemy(float positionX, float positionY, int attack, int defense, final String name, final int health, TextureRegion region, final ActionMenu actionMenu, int moveLimit, final TemplateScreen screen) {
        super(positionX, positionY, attack, defense, name, health, region, actionMenu, moveLimit, screen);

        this.showingInfo = false;

        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if (screen.game.turnMessage.isVisible()){
                    screen.game.turnMessage.setVisible(false);
                }

                if (screen.game.activeTurn == Enums.Turn.PLAYER){
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
                            setStatsAfterAttack(actionMenu.getCalledBy(), getThis());
                            if (getHealth() < 1){
                                remove();
                                screen.getEnemies().removeValue((Enemy) getThis(), true);
                                if (screen.getEnemies().size == 0){
                                    screen.game.setScreen(new VictoryScreen(screen.game));
                                }
                            }
                            actionMenu.getCalledBy().setState(Enums.UnitState.ATTACKED);
                        } else {}
                    } else {}
                }
                return true;
            }
        });

    }

    public void runAI(){

        //Actual position of this enemy
        Vector2 actualPosition = new Vector2(getX(), getY());

        //If the enemy can attack, it attacks depending on the selectAttack method result
        SnapshotArray<Hero> heroesICanAttack =  canAttack(actualPosition);

        if (heroesICanAttack.size > 0){
            attackHero(selectAttack(heroesICanAttack));
        } else {
            //If the enemy can't attack, it has to move
            Vector2 positionToMove = selectMove();
            positionToMove = getScreen().getTextureTools().tileFinder((int) positionToMove.x, (int) positionToMove.y);
            addAction(Actions.moveTo(positionToMove.x, positionToMove.y, 2));
            getScreen().clearHighlightedTiles(getThis());
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

    private Hero selectAttack(SnapshotArray<Hero> heroes){

        //Hero to compare defense
        Hero auxiliarHero = heroes.get(0);

        for (Hero hero : heroes){
            //If I can kill it, I attack it
            if ((this.getAttack() - hero.getDefense()) > hero.getHealth()){
                return hero;
            } else {
                if (hero.getDefense() < auxiliarHero.getDefense()){
                    auxiliarHero = hero;
                }
            }
        }
        //If I can't kill a hero, I attack the one with less defense
        return auxiliarHero;

    }

    private void attackHero (Hero hero){

        setStatsAfterAttack(this, hero);
        if (hero.getHealth() < 0){
            hero.remove();
            getScreen().getHeroes().removeValue(hero, true);
        }
        this.setState(Enums.UnitState.ATTACKED);

    }

    private Vector2 selectMove(){

        //Find the hero to pursue
        Vector2 heroToPursuePosition = new Vector2();
        heroToPursuePosition = findHeroToPursue();

        //Find the positions I can get
        getScreen().highlightTilesToMove(this);

        //Find the best nearest position to the hero I have to pursue
        Vector2 auxiliarVector = new Vector2();
        float distance = 10000;
        for (Vector2 position : this.getCanMovePositions()){
            if (position.x == (heroToPursuePosition.x / Constants.TILE_SIZE) && position.y == (heroToPursuePosition.y / Constants.TILE_SIZE)){
                Gdx.app.log(TAG, "Son iguales");
            } else {
                if (Math.abs(position.x - (heroToPursuePosition.x / Constants.TILE_SIZE)) + Math.abs(position.y - (heroToPursuePosition.y / Constants.TILE_SIZE)) < distance){
                    distance = Math.abs(position.x - (heroToPursuePosition.x / Constants.TILE_SIZE)) + Math.abs(position.y - (heroToPursuePosition.y / Constants.TILE_SIZE));
                    auxiliarVector = position;
                }
            }
        }

        //Return the nearest position
        auxiliarVector.x = auxiliarVector.x * Constants.TILE_SIZE;
        auxiliarVector.y = auxiliarVector.y * Constants.TILE_SIZE;
        return auxiliarVector;

    }

    private Vector2 findHeroToPursue(){

        //Hero to compare defense
        Hero auxiliarHero = new Hero(1000);

        for (Hero hero : this.getScreen().getHeroes()){
            //If I can kill it, I attack it
            if ((this.getAttack() - hero.getDefense()) > hero.getHealth()){
                return new Vector2(hero.getX(), hero.getY());
            } else {
                if (hero.getDefense() < auxiliarHero.getDefense()){
                    auxiliarHero = hero;
                }
            }
        }
        //If I can't kill a hero, I attack the one with less defense
        return new Vector2(auxiliarHero.getX(), auxiliarHero.getY());

    }

    public boolean isShowingInfo() {
        return showingInfo;
    }

    public void setShowingInfo(boolean showingInfo) {

        this.showingInfo = showingInfo;
        if (!showingInfo){
            getScreen().clearHighlightedTiles(this);
            getScreen().game.information.setVisible(false);
        } else {
            getScreen().highlightTilesToMove(getThis());
            getScreen().game.information.setCalledBy(getThis());
            getScreen().game.information.setVisible(true);
        }

    }


}
