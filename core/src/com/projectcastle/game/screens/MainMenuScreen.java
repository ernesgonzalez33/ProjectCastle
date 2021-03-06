package com.projectcastle.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.projectcastle.game.ProjectCastleGame;
import com.projectcastle.game.util.Constants;
import com.projectcastle.game.util.Enums;

public class MainMenuScreen implements Screen {

    private Stage stage;

    public MainMenuScreen (final ProjectCastleGame game){

        //Setting the camera
        OrthographicCamera camera = new OrthographicCamera();
        Viewport viewport = new FitViewport(Constants.WIDTH, Constants.HEIGHT, camera);
        camera.setToOrtho(false, Constants.WIDTH, Constants.HEIGHT);

        //Setting the stage
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        //Setting the title
        Label title = new Label(Constants.GAME_TITLE, game.skin);
        title.setFontScale(2);
        title.setPosition((Constants.WIDTH / 2) - (title.getWidth() / 2), (Constants.HEIGHT / 2) + 100, Align.center);

        //Setting the debug button
        TextButton debug = new TextButton("Debug", game.skin);
        debug.setPosition((Constants.WIDTH / 2) - Constants.DIFFICULTY_OFFSET_X, (Constants.HEIGHT / 2) - Constants.DIFFICULTY_OFFSET_Y - 50);
        debug.setBounds(debug.getX(), debug.getY(), Constants.DIFFICULTY_WIDTH, Constants.DIFFICULTY_HEIGHT);

        //Setting the easy button
        TextButton easy = new TextButton("Easy", game.skin);
        easy.setPosition((Constants.WIDTH / 2) - Constants.DIFFICULTY_OFFSET_X, (Constants.HEIGHT / 2) - Constants.DIFFICULTY_OFFSET_Y - 100);
        easy.setBounds(easy.getX(), easy.getY(), Constants.DIFFICULTY_WIDTH, Constants.DIFFICULTY_HEIGHT);

        //Setting the medium button
        TextButton medium = new TextButton("Medium", game.skin);
        medium.setPosition((Constants.WIDTH / 2) - Constants.DIFFICULTY_OFFSET_X, (Constants.HEIGHT / 2) - Constants.DIFFICULTY_OFFSET_Y - 150);
        medium.setBounds(medium.getX(), medium.getY(), Constants.DIFFICULTY_WIDTH, Constants.DIFFICULTY_HEIGHT);

        //Setting the hard button
        TextButton hard = new TextButton("Hard", game.skin);
        hard.setPosition((Constants.WIDTH / 2) - Constants.DIFFICULTY_OFFSET_X, (Constants.HEIGHT / 2) - Constants.DIFFICULTY_OFFSET_Y - 200);
        hard.setBounds(hard.getX(), hard.getY(), Constants.DIFFICULTY_WIDTH, Constants.DIFFICULTY_HEIGHT);

        //Adding the actors to the stage
        stage.addActor(title);
        stage.addActor(easy);
        //stage.addActor(debug);
        stage.addActor(medium);
        stage.addActor(hard);

        //Setting the listeners to the buttons
        easy.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameplayScreen(game, Enums.Level.EASY));
            }
        });

        debug.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //game.setScreen(new GameplayScreen(game, Enums.Level.DEBUG));
                game.setScreen(new CreditsScreen(game));
            }
        });

        medium.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameplayScreen(game, Enums.Level.MEDIUM));
            }
        });

        hard.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameplayScreen(game, Enums.Level.HARD));
            }
        });

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }


    @Override
    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
