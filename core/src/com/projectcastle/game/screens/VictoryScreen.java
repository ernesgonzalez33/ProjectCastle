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
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.projectcastle.game.ProjectCastleGame;
import com.projectcastle.game.util.Constants;

public class VictoryScreen implements Screen {

    private ProjectCastleGame game;
    private static final String TAG = GameOverScreen.class.getName();

    OrthographicCamera camera;
    Viewport viewport;
    Stage stage;
    Label congratulations;
    TextButton seeCredits;

    public VictoryScreen (final ProjectCastleGame game){

        this.game = game;

        //Setting the camera
        camera = new OrthographicCamera();
        viewport = new FitViewport(Constants.WIDTH, Constants.HEIGHT, camera);
        camera.setToOrtho(false, Constants.WIDTH, Constants.HEIGHT);

        //Setting the stage
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        //Setting the game over text
        congratulations = new Label("CONGRATULATIONS!", game.skin);
        congratulations.setFontScale(2);
        congratulations.setPosition((Constants.WIDTH / 2) - Constants.CONGRATULATIONS_OFFSET_X - 65, (Constants.HEIGHT / 2) - Constants.CONGRATULATIONS_OFFSET_Y + 100);
        congratulations.setBounds(congratulations.getX(), congratulations.getY(), Constants.CONGRATULATIONS_WIDTH, Constants.CONGRATULATIONS_HEIGHT);

        //Adding the actors to the stage
        stage.addActor(congratulations);

        //Timer to credits
        game.timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                game.setScreen(new CreditsScreen(game));
            }
        }, 5);

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
