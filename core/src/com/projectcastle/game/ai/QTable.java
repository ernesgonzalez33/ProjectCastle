package com.projectcastle.game.ai;

import com.badlogic.gdx.Gdx;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class QTable {

    float [][] qTable;
    int states;
    int actions;

    public QTable (int states, int actions){

        qTable = new float[states][actions];
        this.states = states;
        this.actions = actions;

        for (int ii = 0; ii < states - 1; ii++){
            for (int jj = 0; jj < actions - 1; jj++){
                qTable[ii][jj] = 0.0f;
            }
        }


    }

    public void printQTable (){

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("/Users/ernestogonzalezchacon/Documents/TFG/ProjectCastle/Docs/qTable.txt"));

            for (int i = 0; i < this.states - 1; i++) {
                for (int j = 0; j < this.actions - 1; j++) {
                    bw.write(this.qTable[i][j] + " ");
                }
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {}

    }

}
