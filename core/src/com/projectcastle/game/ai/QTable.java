package com.projectcastle.game.ai;

import com.badlogic.gdx.Gdx;
import com.projectcastle.game.util.Constants;

import java.io.*;

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
            BufferedWriter bw = new BufferedWriter(new FileWriter(Constants.Q_TABLE_FILE));

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

    public void createFromFile (){

        try {
            BufferedReader br = new BufferedReader(new FileReader(Constants.Q_TABLE_FILE));

            for (int i = 0; i < this.states; i++){
                    String aux = br.readLine();
                    String auxArray [] = new String[this.actions];
                    auxArray = aux.split(" ");
                    for (int j = 0; j < this.actions; j++){
                        double auxDouble = Double.parseDouble(auxArray[j]);
                        this.qTable[i][j] = auxDouble;
                    }
            }

        } catch (IOException e) {}

    }

}
