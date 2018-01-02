package com.projectcastle.game.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

/**
 * Created by ernestogonzalezchacon on 3/12/17.
 */

//TODO: Darle color al menú

public class ActionMenu extends Window {

    Label move;
    Label attack;

    public ActionMenu (Skin skin) {

        //Setting the attributes
        super("ActionMenu", skin);
        this.setPosition(0 ,0);
        this.setVisible(false);

        //Initializing the labels
        move = new Label("Move", skin);
        attack = new Label("Attack", skin);

        //Setting the rows
        this.add(move);
        this.row();
        this.add(attack);


    }

}
