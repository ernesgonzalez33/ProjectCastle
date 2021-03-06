package com.projectcastle.game.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.projectcastle.game.entities.Unit;
import com.projectcastle.game.util.Constants;
import com.projectcastle.game.util.Enums;

/**
 * Created by Ernesto Gonzalez on 3/12/17.
 * Kind of class: ${PACKAGE_NAME}
 */

public class ActionMenu extends Window {

    private Unit calledBy; //Variable to know who is calling


    public ActionMenu (Skin skin) {

        //Setting the attributes
        super("ActionMenu", skin);
        this.setPosition(0 ,0);
        this.setBounds(this.getX(), this.getY(), Constants.ACTION_MENU_WIDTH, Constants.ACTION_MENU_HEIGHT);
        this.setVisible(false);

        //Initializing the labels
        TextButton move = new TextButton("Move", skin);
        TextButton attack = new TextButton("Attack", skin);

        //Setting the rows
        this.add(move).width(Constants.ACTION_MENU_BUTTON_WIDTH).pad(Constants.ACTION_MENU_BUTTON_PADDING);
        this.row();
        this.add(attack).width(Constants.ACTION_MENU_BUTTON_WIDTH);

        //Setting the inputs
        move.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                calledBy.getMap().game.information.setVisible(false);
                if (calledBy.getState() == Enums.UnitState.MOVED || calledBy.getState() == Enums.UnitState.ATTACKED){
                    setVisible(false);
                } else {
                    calledBy.getMap().highlightTilesToMove(calledBy);
                    calledBy.setState(Enums.UnitState.MOVING);
                    setVisible(false);
                }
            }
        });

        attack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                calledBy.getMap().game.information.setVisible(false);
                if (calledBy.getState() == Enums.UnitState.ATTACKED){
                    setVisible(false);
                } else {
                    calledBy.setState(Enums.UnitState.ATTACKING);
                    setVisible(false);
                }
            }
        });

    }

    public Unit getCalledBy() {
        return calledBy;
    }

    public void setCalledBy(Unit calledBy) {
        this.calledBy = calledBy;
    }

}
