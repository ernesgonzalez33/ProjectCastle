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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.projectcastle.game.ProjectCastleGame;
import com.projectcastle.game.util.Constants;
import com.projectcastle.game.util.Enums;

public class MainMenuScreen implements Screen {

    private ProjectCastleGame game;
    private static final String TAG = MainMenuScreen.class.getName();

    OrthographicCamera camera;
    Viewport viewport;
    Stage stage;
    Label title;
    TextButton easy;

    public MainMenuScreen (final ProjectCastleGame game){

        this.game = game;

        //Setting the camera
        camera = new OrthographicCamera();
        viewport = new FitViewport(Constants.WIDTH, Constants.HEIGHT, camera);
        camera.setToOrtho(false, Constants.WIDTH, Constants.HEIGHT);

        //Setting the stage
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        //Setting the title
        title = new Label("Project Castle", game.skin);
        title.setFontScale(2);
        title.setPosition((Constants.WIDTH / 2) - Constants.TITLE_OFFSET_X, (Constants.HEIGHT / 2) - Constants.TITLE_OFFSET_Y + 100);
        title.setBounds(title.getX(), title.getY(), Constants.TITLE_WIDTH, Constants.TITLE_HEIGHT);

        //Setting the easy button
        easy = new TextButton("Easy", game.skin);
        easy.setPosition((Constants.WIDTH / 2) - Constants.EASY_OFFSET_X, (Constants.HEIGHT / 2) - Constants.EASY_OFFSET_Y - 100);
        easy.setBounds(easy.getX(), easy.getY(), Constants.EASY_WIDTH, Constants.EASY_HEIGHT);

        //Adding the actors to the stage
        stage.addActor(title);
        stage.addActor(easy);

        //Setting the listener to the button
        easy.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameplayScreen(game, Enums.Level.EASY));
            }
        });

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
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
