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
    TextButton returnMain;

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
        //credits.setWidth(credits.getWidth() * 2);
        credits.setPosition((Constants.WIDTH / 2) - (credits.getWidth() / 2), (Constants.HEIGHT / 2) + 250, Align.center);

        developer = new Label("Developed by", game.skin);
        developer.setFontScale(1.5f);
        developer.setWidth(developer.getWidth() * 1.5f);
        developer.setPosition((Constants.WIDTH / 2) - (developer.getWidth() / 2), credits.getY() - 50);

        myName = new Label("Ernesto Gonzalez Chacon", game.skin);
        myName.setPosition((Constants.WIDTH / 2) - (myName.getWidth() / 2), credits.getY() - 100);

        sprites = new Label("Sprites by", game.skin);
        sprites.setFontScale(1.5f);
        sprites.setWidth(sprites.getWidth() * 1.5f);
        sprites.setPosition((Constants.WIDTH / 2) - (sprites.getWidth() / 2), credits.getY() - 150);

        spritesCreators = new Label("Zabin, Daneeklu, Jetrel, Hyptosis, Redshrike, Bertram, Daniel Cook, Saphy and Sharm", game.skin);
        spritesCreators.setPosition((Constants.WIDTH / 2) - (spritesCreators.getWidth() / 2), credits.getY() - 200);

        tutored = new Label("Tutored by", game.skin);
        tutored.setFontScale(1.5f);
        tutored.setWidth(tutored.getWidth() * 1.5f);
        tutored.setPosition((Constants.WIDTH / 2) - (tutored.getWidth() / 2), credits.getY() - 250);

        tutor = new Label("Daniel Borrajo Millan", game.skin);
        tutor.setPosition((Constants.WIDTH / 2) - (tutor.getWidth() / 2), credits.getY() - 300);

        returnMain = new TextButton("Back to Main Menu", game.skin);
        returnMain.setPosition((Constants.WIDTH / 2) - (returnMain.getWidth() / 2), credits.getY() - 400);
        returnMain.setBounds(returnMain.getX(), returnMain.getY(), returnMain.getWidth(), returnMain.getHeight());

        stage.addActor(credits);
        stage.addActor(developer);
        stage.addActor(myName);
        stage.addActor(sprites);
        stage.addActor(spritesCreators);
        stage.addActor(tutored);
        stage.addActor(tutor);
        stage.addActor(returnMain);

        //Setting the listeners to the buttons
        returnMain.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
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

