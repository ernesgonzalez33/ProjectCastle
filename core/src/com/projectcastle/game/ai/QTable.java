package com.projectcastle.game.ai;

import com.badlogic.gdx.Gdx;

public class QTable {

    float [][] qTable;

    public QTable (int states, int actions){

        qTable = new float[states][actions];

        for (int ii = 0; ii < states - 1; ii++){
            for (int jj = 0; jj < actions - 1; jj++){
                qTable[ii][jj] = 0.0f;
            }
        }


        //Imprimir QTable
//        for (int ii = 0; ii < states - 1; ii++){
//            for (int jj = 0; jj < actions - 1; jj++){
//                Gdx.app.log("QTable", String.valueOf(qTable[ii][jj]));
//            }
//        }

    }

}
