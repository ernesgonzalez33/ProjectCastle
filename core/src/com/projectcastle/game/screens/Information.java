package com.projectcastle.game.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.projectcastle.game.entities.Unit;
import com.projectcastle.game.util.Constants;

public class Information extends Window {

    private final static String TAG = Information.class.getName();
    private Unit calledBy; //Variable to know who is calling


    public Information (Skin skin) {

        //Setting the attributes
        super("Information", skin);
        this.setPosition(0, 0);
        this.setBounds(this.getX(), this.getY(), Constants.INFORMATION_WIDTH, Constants.INFORMATION_HEIGHT);
        this.setVisible(false);

    }

    @Override
    public void setVisible(boolean visible) {
        if (!visible){
            this.clearChildren();
        } else {
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
