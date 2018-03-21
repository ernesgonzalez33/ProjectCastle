package com.projectcastle.game.screens;


import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.projectcastle.game.util.Constants;
import com.projectcastle.game.util.Enums;

public class TurnMessage extends Window {


    private final static String TAG = ActionMenu.class.getName();

    public TurnMessage (Skin skin) {

        //Setting the attributes
        super("Turn", skin);
        this.setPosition(Constants.WIDTH / 2, Constants.HEIGHT / 2);
        this.setBounds(this.getX(), this.getY(), Constants.TURN_MESSAGE_WIDTH, Constants.TURN_MESSAGE_HEIGHT);
        this.setVisible(false);


    }

    public void setTurn(Enums.Turn activeTurn) {

        if (activeTurn == Enums.Turn.PLAYER){
            this.add(new Label("Player's turn", this.getSkin()));
            this.setVisible(true);
        } else {
            this.add(new Label("Enemy's turn", this.getSkin()));
            this.setVisible(true);
        }

    }
}
