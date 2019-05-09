package com.projectcastle.game.ai;

import com.badlogic.gdx.Gdx;
import com.projectcastle.game.util.Constants;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class QTable {

    double [][] qTable;
    int states;
    int actions;
    public int rewards[];
    public boolean win[];

    public QTable (int states, int actions){

        qTable = new double[states][actions];
        this.states = states;
        this.actions = actions;
        rewards = new int[Constants.MAX_EPISODES];
        win = new boolean[Constants.MAX_EPISODES];

        for (int ii = 0; ii < states; ii++){
            for (int jj = 0; jj < actions; jj++){
                qTable[ii][jj] = 0.0f;
            }
        }


    }

    public void printQTable (){

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("/Users/ernestogonzalezchacon/Documents/TFG/ProjectCastle/Docs/qTable.txt"));

            for (int i = 0; i < this.states; i++) {
                for (int j = 0; j < this.actions; j++) {
                    bw.write(this.qTable[i][j] + " ");
                }
                bw.newLine();
            }
            bw.newLine();
            for (int ii = 0; ii < rewards.length; ii++){
                bw.write(rewards[ii] + " ");
            }
            bw.newLine();
            for (int ii = 0; ii < win.length; ii++){
                bw.write(win[ii] + " ");
            }
            bw.flush();
        } catch (IOException e) {}

    }

}
