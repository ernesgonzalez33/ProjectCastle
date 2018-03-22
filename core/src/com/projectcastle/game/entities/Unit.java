package com.projectcastle.game.entities;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.projectcastle.game.screens.ActionMenu;
import com.projectcastle.game.screens.TemplateScreen;
import com.projectcastle.game.util.Constants;
import com.projectcastle.game.util.Enums;

import java.util.ArrayList;

/**
 * Created by Ernesto Gonzalez on 12/5/17.
 * Kind of class: ${PACKAGE_NAME}
 */

public class Unit extends Actor implements Steerable<Vector2> {

    public final static String TAG = Unit.class.getName();
    private int attack;
    private int defense;
    private String name;
    private int health;
    private TextureRegion region;
    private Enums.UnitState state;
    private int moveLimit;
    private TemplateScreen screen;
    private ArrayList<Vector2> canMovePositions;
    Vector2 position;
    Vector2 linearVelocity;
    float angularVelocity;
    float boundingRadius;
    boolean tagged;

    float maxLinearSpeed = 100;
    float maxLinearAcceleration = 200;
    float maxAngularSpeed = 5;
    float maxAngularAcceleration = 10;

    boolean independentFacing;

    SteeringBehavior<Vector2> steeringBehavior;


    Unit(float positionX, float positionY, int attack, int defense, String name, final int health, TextureRegion region, final ActionMenu actionMenu, int moveLimit, TemplateScreen screen) {

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
        this.screen = screen;
        this.canMovePositions = new ArrayList<Vector2>();
        this.independentFacing = true;

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(this.region, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    void setStatsAfterAttack (Unit attackingUnit, Unit defendingUnit){

        //Setting health
        defendingUnit.setHealth(defendingUnit.getHealth() - (attackingUnit.getAttack() - defendingUnit.getDefense()));

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

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        this.position = new Vector2(x, y);
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    private int getAttack() {
        return attack;
    }

    private void setAttack(int attack) {
        this.attack = attack;
    }

    private int getDefense() {
        return defense;
    }

    private void setDefense(int defense) {
        this.defense = defense;
    }

    int getHealth() {
        return health;
    }

    private void setHealth(int health) {
        this.health = health;
    }

    public Enums.UnitState getState() {
        return state;
    }

    public void setState(Enums.UnitState state) {

        this.state = state;
        if (state == Enums.UnitState.ATTACKED)
            screen.verifyTurnChange();

    }

    public int getMoveLimit() { return moveLimit; }

    public void setMoveLimit(int moveLimit) { this.moveLimit = moveLimit; }

    public Unit getThis() { return this; }

    public TextureRegion getRegion() {
        return region;
    }

    public void setRegion(TextureRegion region) {
        this.region = region;
    }

    public TemplateScreen getScreen() {
        return screen;
    }

    public void setScreen(TemplateScreen screen) {
        this.screen = screen;
    }

    public ArrayList<Vector2> getCanMovePositions() {
        return canMovePositions;
    }

    public void setCanMovePositions(ArrayList<Vector2> canMovePositions) {
        this.canMovePositions = canMovePositions;
    }

    public boolean isAdjacent(Unit attackedUnit){

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


    /**
     * Returns the vector indicating the linear velocity of this Steerable.
     */
    @Override
    public Vector2 getLinearVelocity() {
        return null;
    }

    /**
     * Returns the float value indicating the the angular velocity in radians of this Steerable.
     */
    @Override
    public float getAngularVelocity() {
        return 0;
    }

    /**
     * Returns the bounding radius of this Steerable.
     */
    @Override
    public float getBoundingRadius() {
        return 0;
    }

    /**
     * Returns {@code true} if this Steerable is tagged; {@code false} otherwise.
     */
    @Override
    public boolean isTagged() {
        return false;
    }

    /**
     * Tag/untag this Steerable. This is a generic flag utilized in a variety of ways.
     *
     * @param tagged the boolean value to set
     */
    @Override
    public void setTagged(boolean tagged) {

    }

    /**
     * Returns the threshold below which the linear speed can be considered zero. It must be a small positive value near to zero.
     * Usually it is used to avoid updating the orientation when the velocity vector has a negligible length.
     */
    @Override
    public float getZeroLinearSpeedThreshold() {
        return 0;
    }

    /**
     * Sets the threshold below which the linear speed can be considered zero. It must be a small positive value near to zero.
     * Usually it is used to avoid updating the orientation when the velocity vector has a negligible length.
     *
     * @param value
     */
    @Override
    public void setZeroLinearSpeedThreshold(float value) {

    }

    /**
     * Returns the maximum linear speed.
     */
    @Override
    public float getMaxLinearSpeed() {
        return 0;
    }

    /**
     * Sets the maximum linear speed.
     *
     * @param maxLinearSpeed
     */
    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {

    }

    /**
     * Returns the maximum linear acceleration.
     */
    @Override
    public float getMaxLinearAcceleration() {
        return 0;
    }

    /**
     * Sets the maximum linear acceleration.
     *
     * @param maxLinearAcceleration
     */
    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {

    }

    /**
     * Returns the maximum angular speed.
     */
    @Override
    public float getMaxAngularSpeed() {
        return 0;
    }

    /**
     * Sets the maximum angular speed.
     *
     * @param maxAngularSpeed
     */
    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {

    }

    /**
     * Returns the maximum angular acceleration.
     */
    @Override
    public float getMaxAngularAcceleration() {
        return 0;
    }

    /**
     * Sets the maximum angular acceleration.
     *
     * @param maxAngularAcceleration
     */
    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {

    }

    /**
     * Returns the vector indicating the position of this location.
     */
    @Override
    public Vector2 getPosition() {
        return null;
    }

    /**
     * Returns the float value indicating the orientation of this location. The orientation is the angle in radians representing
     * the direction that this location is facing.
     */
    @Override
    public float getOrientation() {
        return 0;
    }

    /**
     * Sets the orientation of this location, i.e. the angle in radians representing the direction that this location is facing.
     *
     * @param orientation the orientation in radians
     */
    @Override
    public void setOrientation(float orientation) {

    }

    /**
     * Returns the angle in radians pointing along the specified vector.
     *
     * @param vector the vector
     */
    @Override
    public float vectorToAngle(Vector2 vector) {
        return 0;
    }

    /**
     * Returns the unit vector in the direction of the specified angle expressed in radians.
     *
     * @param outVector the output vector.
     * @param angle     the angle in radians.
     * @return the output vector for chaining.
     */
    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        return null;
    }

    /**
     * Creates a new location.
     * <p>
     * This method is used internally to instantiate locations of the correct type parameter {@code T}. This technique keeps the API
     * simple and makes the API easier to use with the GWT backend because avoids the use of reflection.
     *
     * @return the newly created location.
     */
    @Override
    public Location<Vector2> newLocation() {
        return null;
    }

}
