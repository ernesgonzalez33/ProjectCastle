package com.projectcastle.game.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * Created by ernestogonzalezchacon on 3/12/17.
 */

//TODO: Meter los label y las cosas en el menú

public class ActionMenu extends Table {

    Skin skin;

    public ActionMenu (Skin skin) {

        super();
        this.skin = skin;
        this.setPosition(0 ,0);
        this.setVisible(false);

    }

//    Label move = new Label("Move", skin);
//    Label attack;

}
