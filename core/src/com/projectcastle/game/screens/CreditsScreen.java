package com.projectcastle.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.projectcastle.game.ProjectCastleGame;
import com.projectcastle.game.util.Constants;

public class CreditsScreen implements Screen {

    private ProjectCastleGame game;
    private static final String TAG = VictoryScreen.class.getName();

    OrthographicCamera camera;
    Viewport viewport;
    Stage stage;
    Label credits;
    Label developer;
    Label myName;
    Label sprites;
    Label spritesCreators;
    Label tutored;
    Label tutor;

    public CreditsScreen (final ProjectCastleGame game){

        this.game = game;

        //Setting the camera
        camera = new OrthographicCamera();
        viewport = new FitViewport(Constants.WIDTH, Constants.HEIGHT, camera);
        camera.setToOrtho(false, Constants.WIDTH, Constants.HEIGHT);

        //Setting the stage
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        //Setting the credits
        credits = new Label("Credits", game.skin);
        credits.setFontScale(2);
        credits.setPosition(250 , (Constants.HEIGHT / 2) + 250);

        developer = new Label("Developed by", game.skin);
        developer.setFontScale(1.5f);
        developer.setPosition(credits.getX() - 18, credits.getY() - 50);

        myName = new Label("Ernesto Gonzalez Chacon", game.skin);
        myName.setPosition(credits.getX() - 25, credits.getY() - 100);

        sprites = new Label("Sprites by", game.skin);
        sprites.setFontScale(1.5f);
        sprites.setPosition(credits.getX(), credits.getY() - 150);

        spritesCreators = new Label("Zabin, Daneeklu, Jetrel, Hyptosis, Redshrike, Bertram, Daniel Cook, Saphy and Sharm", game.skin);
        spritesCreators.setPosition(45, credits.getY() - 200);

        tutored = new Label("Tutored by", game.skin);
        tutored.setFontScale(1.5f);
        tutored.setPosition(credits.getX(), credits.getY() - 250);

        tutor = new Label("Daniel Borrajo Millan", game.skin);
        tutor.setPosition(credits.getX() - 10, credits.getY() - 300);

        stage.addActor(credits);
        stage.addActor(developer);
        stage.addActor(myName);
        stage.addActor(sprites);
        stage.addActor(spritesCreators);
        stage.addActor(tutored);
        stage.addActor(tutor);

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
