package com.projectcastle.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.Timer;
import com.projectcastle.game.Map;
import com.projectcastle.game.screens.DamageOverlay;
import com.projectcastle.game.screens.GameOverScreen;
import com.projectcastle.game.screens.GameplayScreen;
import com.projectcastle.game.screens.VictoryScreen;
import com.projectcastle.game.util.Constants;
import com.projectcastle.game.util.Enums;

import java.util.ArrayList;

/**
 * Created by Ernesto Gonzalez on 12/5/17.
 * Kind of class: ${PACKAGE_NAME}
 */

public class Unit extends Actor {

    private int attack;
    private int defense;
    private String name;
    private int health;
    private TextureRegion region;
    private Enums.UnitState state;
    private int moveLimit;
    private Map map;
    private ArrayList<Vector2> canMovePositions;

    Unit(float positionX, float positionY, int attack, int defense, String name, final int health, TextureRegion region, int moveLimit, Map map) {

        this.setPosition(positionX, positionY);
        this.setBounds(this.getX(), this.getY(), region.getRegionWidth(), region.getRegionHeight());
        this.setTouchable(Touchable.enabled);
        this.attack = attack;
        this.defense = defense;
        this.name = name;
        this.health = health;
        this.region = region;
        this.setScale(Constants.CHARACTER_SCALE, Constants.CHARACTER_SCALE);
        this.setRotation(0);
        this.setState(Enums.UnitState.IDLE);
        this.moveLimit = moveLimit;
        this.map = map;
        this.canMovePositions = new ArrayList<Vector2>();

    }

    Unit(int defense) {

        this.defense = defense;

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(this.region, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    void setStatsAfterAttack (final Unit attackingUnit, final Unit defendingUnit){

        //Setting health
        int damage = attackingUnit.getAttack() - defendingUnit.getDefense();
        if (damage < 0) damage = 0;

        //The attack can be missed.
        double chance = Math.random();
        if (chance > Constants.ATTACK_CHANCE){
            final DamageOverlay damageOverlay = new DamageOverlay("-", map.game.skin, defendingUnit.getX(), defendingUnit.getY(), Color.BLACK);
            map.getStage().addActor(damageOverlay);
            map.game.timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    damageOverlay.remove();
                }
            }, 1);
        } else {
            int newHealth = defendingUnit.getHealth() - damage;
            defendingUnit.setHealth(newHealth);
            final DamageOverlay damageOverlay = new DamageOverlay("" + damage, map.game.skin, defendingUnit.getX(), defendingUnit.getY(), Color.RED);
            map.getStage().addActor(damageOverlay);
            map.game.timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    damageOverlay.remove();
                }
            }, 1);
        }

        // Checking if the unit died
        if (defendingUnit.getHealth() < 1){
            defendingUnit.remove();
            if (defendingUnit.getClass().getName().equals(Constants.ENEMY_CLASS_NAME)){
                map.getEnemies().removeValue((Enemy) defendingUnit, true);
                if (map.getEnemies().size == 0 && map.game.getMapCont() == 1){
                    map.game.setScreen(new VictoryScreen(map.game));
                } else if (map.getEnemies().size == 0 && map.game.getMapCont() == 0){
                    map.game.setMapCont(1);
                    map.game.setScreen(new GameplayScreen(map.game, map.selectedLevel));
                }
            } else {
                getMap().getHeroes().removeValue((Hero) defendingUnit, true);
                if (map.getHeroes().size == 0){
                    map.game.setScreen(new GameOverScreen(map.game));
                }
            }

        }

        //Setting defense
        if (attackingUnit.getDefense() > defendingUnit.getDefense() + 5){
            defendingUnit.setDefense(defendingUnit.getDefense() + 3);
        } else if (attackingUnit.getDefense() < defendingUnit.getDefense() + 5) {
            defendingUnit.setDefense(defendingUnit.getDefense() + 1);
        } else {
            defendingUnit.setDefense(defendingUnit.getDefense() + 2);
        }

        //Setting attack
        if (attackingUnit.getAttack() > defendingUnit.getAttack() + 5){
            attackingUnit.setAttack(attackingUnit.getAttack() + 1);
        } else if (attackingUnit.getAttack() < defendingUnit.getAttack() + 5) {
            attackingUnit.setAttack(attackingUnit.getAttack() + 3);
        } else {
            attackingUnit.setAttack(attackingUnit.getAttack() + 2);
        }
    }


    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Enums.UnitState getState() {
        return state;
    }

    public void setState(Enums.UnitState state) {

        this.state = state;
        if (state == Enums.UnitState.ATTACKED)
            map.verifyTurnChange();

    }

    public int getMoveLimit() { return moveLimit; }

    public Unit getThis() { return this; }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public ArrayList<Vector2> getCanMovePositions() {
        return canMovePositions;
    }

    boolean isAdjacent(Unit attackedUnit){

        int attackingX = (int) (this.getX() / Constants.TILE_SIZE);
        int attackingY = (int) (this.getY() / Constants.TILE_SIZE);
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

    public boolean isAdjacent(Vector2 callingUnit, Unit attackedUnit){

        int attackingX = (int) (callingUnit.x / Constants.TILE_SIZE);
        int attackingY = (int) (callingUnit.y / Constants.TILE_SIZE);
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

    public boolean canAttack(Vector2 newPosition, Stage stage){

        for (Actor actor: stage.getActors()){
            if (this.getClass().getName().equals(Constants.HERO_CLASS_NAME)){
                if (actor.getClass().getName().equals(Constants.ENEMY_CLASS_NAME)){
                    if (isAdjacent(newPosition, (Unit) actor)){
                        return true;
                    }
                }
            }
            else {
                if (actor.getClass().getName().equals(Constants.HERO_CLASS_NAME)){
                    if (isAdjacent((Unit) actor)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    SnapshotArray<Hero> canAttack(Vector2 position){

        SnapshotArray<Hero> heroesICanAttack = new SnapshotArray<Hero>();

        for (Hero hero:map.getHeroes()){
            if (isAdjacent(position, hero)){
                heroesICanAttack.add(hero);
            }
        }

        return heroesICanAttack;

    }

}
