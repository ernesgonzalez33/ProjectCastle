package com.projectcastle.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.projectcastle.game.ProjectCastleGame;
import com.projectcastle.game.util.Constants;

public class GameOverScreen implements Screen {

    private Stage stage;

    public GameOverScreen (final ProjectCastleGame game){

        //Setting the camera
        OrthographicCamera camera = new OrthographicCamera();
        Viewport viewport = new FitViewport(Constants.WIDTH, Constants.HEIGHT, camera);
        camera.setToOrtho(false, Constants.WIDTH, Constants.HEIGHT);

        //Setting the stage
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        //Setting the game over text
        Label gameOver = new Label("Game Over", game.skin);
        gameOver.setFontScale(2);
        gameOver.setPosition((Constants.WIDTH / 2) - Constants.GAME_OVER_OFFSET_X, (Constants.HEIGHT / 2) - Constants.GAME_OVER_OFFSET_Y + 100);
        gameOver.setBounds(gameOver.getX(), gameOver.getY(), Constants.GAME_OVER_WIDTH, Constants.GAME_OVER_HEIGHT);

        //Adding the actors to the stage
        stage.addActor(gameOver);


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
