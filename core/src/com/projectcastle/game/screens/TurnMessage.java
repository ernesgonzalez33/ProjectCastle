package com.projectcastle.game.screens;


import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.Timer;
import com.projectcastle.game.util.Constants;
import com.projectcastle.game.util.Enums;

public class TurnMessage extends Window {

    private Timer turnTimer;

    public TurnMessage (Skin skin) {

        //Setting the attributes
        super("Turn", skin);
        this.setPosition((Constants.WIDTH / 2) - Constants.TURN_MESSAGE_OFFSET_X, (Constants.HEIGHT / 2) - Constants.TURN_MESSAGE_OFFSET_Y);
        this.setBounds(this.getX(), this.getY(), Constants.TURN_MESSAGE_WIDTH, Constants.TURN_MESSAGE_HEIGHT);
        this.setVisible(false);
        turnTimer = new Timer();

    }

    public void setTurn(Enums.Turn activeTurn) {

        if (activeTurn == Enums.Turn.PLAYER){
            this.add(new Label("Player's turn", this.getSkin()));
            this.setVisible(true);
        } else {
            this.add(new Label("Enemy's turn", this.getSkin()));
            this.setVisible(true);
            turnTimer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    setVisible(false);
                }
            }, Constants.DELAY);
        }

    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (!visible){
            SnapshotArray<Actor> actors = this.getChildren();
            for (Actor child : actors){
                child.remove();
            }
        }
    }
}
