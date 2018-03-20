package com.projectcastle.game.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.projectcastle.game.ProjectCastleGame;
import com.projectcastle.game.screens.TemplateScreen;
import com.projectcastle.game.util.Enums;

/**
 * Created by Ernesto Gonzalez on 25/2/18.
 * Kind of class: gameplay
 */

public class InputProcessorHelp {

    private final ProjectCastleGame game;

    private static final String TAG = InputProcessorHelp.class.getName();

    public InputProcessorHelp(final ProjectCastleGame game){
        this.game = game;

    }

    public boolean ScreenTouchDown(TemplateScreen screen, int screenX, int screenY, int pointer, int button){

        if (game.actionMenu.isVisible()){

            if (screenX > game.actionMenu.getOriginX() && screenX < screen.getViewport().getScreenWidth() - (screen.getViewport().getScreenWidth() - game.actionMenu.getWidth() - game.actionMenu.getOriginX())){
                if (screenY > game.actionMenu.getOriginY() && screenY < screen.getViewport().getScreenHeight() - (screen.getViewport().getScreenHeight() - game.actionMenu.getHeight() - game.actionMenu.getOriginY())){
                    return false;
                } else {
                    game.actionMenu.setVisible(false);
                    if (game.actionMenu.getCalledBy().getState() != Enums.UnitState.IDLE){
                        game.actionMenu.getCalledBy().setState(Enums.UnitState.IDLE);
                        Gdx.app.log(TAG, "Unit " + game.actionMenu.getCalledBy().getName() + " state is now " + game.actionMenu.getCalledBy().getState());
                    }
                }
            } else {
                game.actionMenu.setVisible(false);
                if (game.actionMenu.getCalledBy().getState() != Enums.UnitState.IDLE){
                    game.actionMenu.getCalledBy().setState(Enums.UnitState.IDLE);
                    Gdx.app.log(TAG, "Unit " + game.actionMenu.getCalledBy().getName() + " state is now " + game.actionMenu.getCalledBy().getState());
                }
            }

        } else {

            if (game.actionMenu.getCalledBy().getState() == Enums.UnitState.MOVING){
                Vector2 position = screen.getTextureTools().tileFinder(screenX, screen.getViewport().getScreenHeight() - screenY);
                game.actionMenu.getCalledBy().addAction(Actions.moveTo(position.x, position.y, 2)); //TODO: (opcional) Que los personajes no se muevan en diagonal
                game.actionMenu.getCalledBy().setState(Enums.UnitState.IDLE);
            }
        }
        return false;
    }

}