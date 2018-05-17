package com.projectcastle.game.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.projectcastle.game.ProjectCastleGame;
import com.projectcastle.game.entities.Unit;
import com.projectcastle.game.util.Constants;

public class Information extends Window {

    private final static String TAG = Information.class.getName();
    private Unit calledBy; //Variable to know who is calling
    private ProjectCastleGame game;


    public Information (Skin skin, ProjectCastleGame game) {

        //Setting the attributes
        super("Information", skin);
        this.setVisible(false);
        this.game = game;

    }

    @Override
    public void setVisible(boolean visible) {
        if (!visible){
            this.clearChildren();
        } else {
            if (game.getMapCont() == 0){
                this.setPosition(0, 0);
            }
            if (game.getMapCont() == 1){
                this.setPosition(Constants.WIDTH - this.getWidth(), 0);
            }
            this.add(new Label("Name: " + getCalledBy().getName(), getSkin()));
            this.row();
            this.add(new Label("Attack: " + getCalledBy().getAttack(), getSkin()));
            this.row();
            this.add(new Label("Defense: " + getCalledBy().getDefense(), getSkin()));
            this.row();
            this.add(new Label("Health: " + getCalledBy().getHealth(), getSkin()));
        }
        super.setVisible(visible);
    }

    public Unit getCalledBy() {
        return calledBy;
    }

    public void setCalledBy(Unit calledBy) {
        this.calledBy = calledBy;
    }


}
