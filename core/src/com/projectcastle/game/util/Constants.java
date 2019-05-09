package com.projectcastle.game.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Ernesto Gonzalez on 12/5/17.
 * Kind of class: ${PACKAGE_NAME}
 */

public class Constants {

    //Game Title
    public static final String GAME_TITLE = "Overworld Tactics";

    //Characters
    public static final String PRINCESS_NAME = "Eirika";
    public static final String PRINCE_NAME = "Christian";
    public static final String COMMANDER_NAME = "Airmanagild";
    static final String CHARACTERS_ASSET = "Characters/characters_1.png";
    public static final int CHARACTER_SCALE = 2;
    public static final int MOVE_LIMIT = 4;

    //Map
    public static final float UNIT_SCALE = 1;
    public static final int WIDTH = 640;
    public static final int HEIGHT = 640;
    static final String SELECTED_SPRITE_ASSET = "Maps/SelectedSprite.png";
    public static final int SELECTED_TILE_ID = 0;
    public static final int TILE_SIZE = 32;
    public static final int EASY_MAP_ID = 1;
    public static final int MEDIUM_MAP_ID = 2;
    public static final int HARD_MAP_ID = 3;
    public static final int EASY_MAP_ID_2 = 4;
    public static final int MEDIUM_MAP_ID_2 = 5;
    public static final int HARD_MAP_ID_2 = 6;
    static final String TEST_MAP = "Maps/TestMap3.tmx";
    static final String EASY_MAP = "Maps/EasyMap.tmx";
    static final String MEDIUM_MAP = "Maps/MediumMap.tmx";
    static final String HARD_MAP = "Maps/HardMap.tmx";
    static final String EASY_MAP_2 = "Maps/EasyMap2.tmx";
    static final String MEDIUM_MAP_2 = "Maps/MediumMap2.tmx";
    static final String HARD_MAP_2 = "Maps/HardMap2.tmx";

    //UI
    public static final String FLAT_SKIN = "UI/skin.json";

    //ActionMenu
    public static final float ACTION_MENU_WIDTH = 70;
    public static final float ACTION_MENU_HEIGHT = 70;
    public static final float ACTION_MENU_BUTTON_WIDTH = 45;
    public static final float ACTION_MENU_BUTTON_PADDING = 5;

    //Turn Message
    public static final int TURN_MESSAGE_WIDTH = 150;
    public static final int TURN_MESSAGE_HEIGHT = 70;
    public static final int TURN_MESSAGE_OFFSET_X = 75;
    public static final int TURN_MESSAGE_OFFSET_Y = 35;

    //Other
    public static final String HERO_CLASS_NAME = "com.projectcastle.game.entities.Hero";
    public static final String ENEMY_CLASS_NAME = "com.projectcastle.game.entities.Enemy";
    public static final String HERO_AGENT_CLASS_NAME = "com.projectcastle.game.ai.HeroAgent";
    public static final double ATTACK_CHANCE = 0.85;

    //Timer
    public static final int DELAY = 0;

    //Difficulty buttons
    public static final int DIFFICULTY_WIDTH = 100;
    public static final int DIFFICULTY_HEIGHT = 30;
    public static final int DIFFICULTY_OFFSET_X = 50;
    public static final int DIFFICULTY_OFFSET_Y = 15;

    //Game over
    public static final int GAME_OVER_WIDTH = 170;
    public static final int GAME_OVER_HEIGHT = 70;
    public static final int GAME_OVER_OFFSET_X = 85;
    public static final int GAME_OVER_OFFSET_Y = 35;

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

    //Learning
    public static final int STATES = 4;
    public static final int ACTIONS = 27;
    public static final int MAX_EPISODES = 100;
    public static final double LEARNING_RATE = 0.4;
    public static final double DISCOUNT_RATE = 0.99;
    public static final int MAX_EXPLORATION_RATE = 1;
    public static final double MIN_EXPLORATION_RATE = 0.01;
    public static final double EXPLORATION_DECAY_RATE = 0.001;


}
