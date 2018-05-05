package com.projectcastle.game.gameplay;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.projectcastle.game.Map;
import com.projectcastle.game.ProjectCastleGame;
import com.projectcastle.game.entities.Enemy;
import com.projectcastle.game.util.Constants;
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

    public boolean MapTouchDown(Map map, int screenX, int screenY, int pointer, int button){

        if (game.actionMenu.isVisible()){

            //ActionMenu interior
            if (screenX > game.actionMenu.getOriginX() && screenX < map.getViewport().getScreenWidth() - (map.getViewport().getScreenWidth() - game.actionMenu.getWidth() - game.actionMenu.getOriginX())){
                if (screenY > game.actionMenu.getOriginY() && screenY < map.getViewport().getScreenHeight() - (map.getViewport().getScreenHeight() - game.actionMenu.getHeight() - game.actionMenu.getOriginY())){
                    return false;
                } else {
                    game.actionMenu.setVisible(false);
                    game.information.setVisible(false);
                }
            } else {
                game.actionMenu.setVisible(false);
                game.information.setVisible(false);
            }
        } else {
            if (game.turnMessage.isVisible()){
                game.turnMessage.setVisible(false);
            }
            if (game.actionMenu.getCalledBy() == null){
                for (Enemy enemy : map.getEnemies()){
                    if (enemy.isShowingInfo()){
                        enemy.setShowingInfo(false);
                    }
                }
                return false;
            }
            if (game.actionMenu.getCalledBy().getState() == Enums.UnitState.MOVING){
                Vector2 position = map.getTextureTools().tileFinder(screenX, map.getViewport().getScreenHeight() - screenY);
                Vector2 cellPosition = new Vector2(position.x / Constants.TILE_SIZE, position.y / Constants.TILE_SIZE);
                //Verifying move limit
                if (game.actionMenu.getCalledBy().getCanMovePositions().contains(cellPosition)){
                    game.actionMenu.getCalledBy().addAction(Actions.moveTo(position.x, position.y, 2));
                }
                map.clearHighlightedTiles(game.actionMenu.getCalledBy());
                game.actionMenu.getCalledBy().setState(Enums.UnitState.MOVED);
                if (!game.actionMenu.getCalledBy().canAttack(position, map.getStage())){
                    game.actionMenu.getCalledBy().setState(Enums.UnitState.ATTACKED);
                }
            }
        }
        for (Enemy enemy : map.getEnemies()){
            if (enemy.isShowingInfo()){
                enemy.setShowingInfo(false);
            }
        }
        return false;
    }

}
