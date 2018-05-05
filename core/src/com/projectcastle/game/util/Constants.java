package com.projectcastle.game.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Ernesto Gonzalez on 12/5/17.
 * Kind of class: ${PACKAGE_NAME}
 */

public class Constants {

    public static final float UNIT_SCALE = 1;
    public static final int WIDTH = 640;
    public static final int HEIGHT = 640;
    public static final String PRINCESS_NAME = "Eirika";
    public static final String PRINCE_NAME = "Christian";
    public static final String COMMANDER_NAME = "Airmanagild";
    public static final String CHARACTERS_ASSET = "Characters/characters_1.png";
    public static final String SELECTED_SPRITE_ASSET = "Maps/SelectedSprite.png";
    public static final int TILE_SIZE = 32;
    public static final int CHARACTER_SIZE = 16;
    public static final int CHARACTER_SCALE = 2;
    public static final String FLAT_SKIN = "UI/skin.json";
    public static final float ACTION_MENU_WIDTH = 70;
    public static final float ACTION_MENU_HEIGHT = 70;
    public static final float ACTION_MENU_BUTTON_WIDTH = 45;
    public static final float ACTION_MENU_BUTTON_PADDING = 5;
    public static final String SELECTED_TILE_SET_NAME = "SelectedTile";
    public static final String CHARACTERS_TILE_LAYER_NAME = "Characters";
    public static final int MOVE_LIMIT = 4;
    public static final int SELECTED_TILE_ID = 0;

    //Turn Message
    public static final int TURN_MESSAGE_WIDTH = 150;
    public static final int TURN_MESSAGE_HEIGHT = 70;
    public static final int TURN_MESSAGE_OFFSET_X = 75;
    public static final int TURN_MESSAGE_OFFSET_Y = 35;

    //Other
    public static final String HERO_CLASS_NAME = "com.projectcastle.game.entities.Hero";
    public static final String ENEMY_CLASS_NAME = "com.projectcastle.game.entities.Enemy";

    //Timer
    public static final int DELAY = 3;

    //Title
    public static final int TITLE_WIDTH = 170;
    public static final int TITLE_HEIGHT = 70;
    public static final int TITLE_OFFSET_X = 85;
    public static final int TITLE_OFFSET_Y = 35;

    //Play button
    public static final int EASY_WIDTH = 100;
    public static final int EASY_HEIGHT = 30;
    public static final int EASY_OFFSET_X = 50;
    public static final int EASY_OFFSET_Y = 15;

    //Game over
    public static final int GAME_OVER_WIDTH = 170;
    public static final int GAME_OVER_HEIGHT = 70;
    public static final int GAME_OVER_OFFSET_X = 85;
    public static final int GAME_OVER_OFFSET_Y = 35;

    //See credits button
    public static final int SEE_CREDITS_WIDTH = 100;
    public static final int SEE_CREDITS_HEIGHT = 30;
    public static final int SEE_CREDITS_OFFSET_X = 50;
    public static final int SEE_CREDITS_OFFSET_Y = 15;

    //Congratulations
    public static final int CONGRATULATIONS_WIDTH = 170;
    public static final int CONGRATULATIONS_HEIGHT = 70;
    public static final int CONGRATULATIONS_OFFSET_X = 85;
    public static final int CONGRATULATIONS_OFFSET_Y = 35;

    //DamageOverlay
    public static final float DAMAGE_FONT_SCALE = 1.5f;

    //Tiled
    public static final int FIRST_GID_OVERWORLD = 97;
    public static final List<Integer> FORBIDDEN_ID_CELLS =
            Collections.unmodifiableList(Arrays.asList(0,1,2,21,23,42,43,44,70,126,127,128,147,149,168,169,170,171,172));

    //Maps
    public static final int EASY_MAP_ID = 1;
    public static final int MEDIUM_MAP_ID = 2;
    public static final int HARD_MAP_ID = 3;
    public static final String TEST_MAP = "Maps/TestMap3.tmx";
    public static final String EASY_MAP = "Maps/EasyMap.tmx";
}
