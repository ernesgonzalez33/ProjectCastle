package com.projectcastle.game.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.projectcastle.game.util.Constants;

/**
 * Created by ernestogonzalezchacon on 3/12/17.
 */

//TODO: Darle color al menú

public class ActionMenu extends Table {

    Skin skin;
    Label move;
    Label attack;

    public ActionMenu (Skin skin) {

        //Setting the attributes
        super();
        this.skin = skin;
        this.setPosition(0 ,0);
        this.setVisible(false);
        this.setWidth(Constants.TABLE_WIDTH);
        this.setHeight(Constants.TABLE_HEIGHT);

        //Initializing the labels
        move = new Label("Move", skin);
        attack = new Label("Attack", skin);

        //Setting the rows
        this.add(move);
        this.row();
        this.add(attack);

        //Setting the background
//        this.setBackground("default");

    }

}
