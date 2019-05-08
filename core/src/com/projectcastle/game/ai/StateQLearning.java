package com.projectcastle.game.ai;

public class StateQLearning {

    public boolean enemyInZone;
    public boolean canAttack;

    public StateQLearning(boolean enemyInZone, boolean canAttack){

        this.canAttack = canAttack;
        this.enemyInZone = enemyInZone;

    }

    public int getStateID(){

        if (canAttack) {
            if (enemyInZone)
                return 0;
            else
                return 1;
        } else {
            if (enemyInZone)
                return 2;
            else
                return 3;
        }

    }

}
