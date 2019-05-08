package com.projectcastle.game.ai;

public class StateQLearning {

    public boolean enemyInZone;
    public boolean canAttack;

    public StateQLearning(boolean enemyInZone, boolean canAttack){

        this.canAttack = canAttack;
        this.enemyInZone = enemyInZone;

    }

}
