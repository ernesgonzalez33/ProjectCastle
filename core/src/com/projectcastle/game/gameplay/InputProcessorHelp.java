package com.projectcastle.game.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.Viewport;
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

            if (screenX > game.actionMenu.getOriginX() && screenX < screen.viewport.getScreenWidth() - (screen.viewport.getScreenWidth() - game.actionMenu.getWidth() - game.actionMenu.getOriginX())){
                if (screenY > game.actionMenu.getOriginY() && screenY < screen.viewport.getScreenHeight() - (screen.viewport.getScreenHeight() - game.actionMenu.getHeight() - game.actionMenu.getOriginY())){
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
                game.actionMenu.getCalledBy().addAction(Actions.moveTo(screenX, screen.viewport.getScreenHeight() - screenY, 2)); //TODO: arreglar para que se fije en los tiles
                game.actionMenu.getCalledBy().setState(Enums.UnitState.IDLE);
            }
        }
        return false;
    }

}
