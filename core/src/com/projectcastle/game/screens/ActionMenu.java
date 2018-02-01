package com.projectcastle.game.screens;

import com.badlogic.gdx.Gdx;
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

//TODO: Si llamo al ActionMenu desde otra unidad, la anterior tiene que pasar a Idle

public class ActionMenu extends Window {

    private final static String TAG = ActionMenu.class.getName();
    private TextButton move;
    private TextButton attack;
    private Unit calledBy; //Variable to know who is calling


    public ActionMenu (Skin skin) {

        //Setting the attributes
        super("ActionMenu", skin);
        this.setPosition(0 ,0);
        this.setBounds(this.getX(), this.getY(), Constants.ACTION_MENU_WIDTH, Constants.ACTION_MENU_HEIGHT);
        this.setVisible(false);

        //Initializing the labels
        move = new TextButton("Move", skin);
        attack = new TextButton("Attack", skin);

        //Setting the rows
        this.add(move);
        this.row();
        this.add(attack);

        //Setting the inputs ---> No parece funcionar aquí
        move.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log(TAG, "Move touched by " + calledBy.getName());
                calledBy.setState(Enums.UnitState.MOVING);
                Gdx.app.log(TAG, "State is now " + calledBy.getState());
                setVisible(false);
            }
        });

        attack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log(TAG, "Attack touched by " + calledBy.getName());
                calledBy.setState(Enums.UnitState.ATTACKING);
                Gdx.app.log(TAG, "State is now " + calledBy.getState());
                setVisible(false);
            }
        });

    }

    Unit getCalledBy() {
        return calledBy;
    }

    public void setCalledBy(Unit calledBy) {
        this.calledBy = calledBy;
    }

}
