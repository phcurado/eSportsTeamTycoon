package com.paulocurado.esportsmanager.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.paulocurado.esportsmanager.EsportsManager;
import com.paulocurado.esportsmanager.uielements.NewGameDialog;

/**
 * Created by Paulo on 09/11/2016.
 */

public class StartScreen implements Screen {
    private EsportsManager mainApp;

    private Viewport gamePort;

    private Stage stage;
    private Skin skin;

    private Image logo;
    private Image background;
    private TextButton buttonPlay;
    private TextButton buttonAbout;
    private TextButton buttonExit;
    private NewGameDialog dialog;

    public static float BUTTON_SPACING = 10f;
    public static float BUTTON_SIZE = 100f;


    public StartScreen(EsportsManager mainApp) {
        this.mainApp = mainApp;


        gamePort = new FitViewport(mainApp.V_WIDTH , mainApp.V_HEIGHT , mainApp.camera);
        stage = new Stage(gamePort, mainApp.batch);
        Gdx.input.setInputProcessor(stage);


        this.skin = new Skin();
        this.skin.addRegions(mainApp.assets.get("ui/ui.atlas", TextureAtlas.class));
        this.skin.add("default-font", mainApp.font);
        this.skin.load(Gdx.files.internal("ui/ui.json"));


        initBackground();


        initButtons();
    }

    @Override
    public void show() {
        System.out.println("Start Screen");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mainApp.batch.setProjectionMatrix(stage.getCamera().combined);


        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
        skin.dispose();

    }

    public void initBackground() {
        background = new Image(mainApp.assets.get("img/startmenubackground.png", Texture.class));
        background.setSize(gamePort.getWorldWidth(), gamePort.getWorldHeight());
        stage.addActor(background);


        logo = new Image(mainApp.assets.get("img/logo.png", Texture.class));
        logo.setSize(gamePort.getWorldWidth() - 25, gamePort.getWorldWidth()*logo.getHeight()/logo.getWidth());
        logo.setPosition(gamePort.getWorldWidth() / 2 - logo.getWidth() / 2, gamePort.getWorldHeight() - logo.getHeight() - 25);
        stage.addActor(logo);

    }

    public void initButtons() {
        buttonPlay = new TextButton("START", skin, "default");
        buttonAbout = new TextButton("ABOUT", skin, "default");
        buttonExit = new TextButton("EXIT", skin, "default");

        buttonPlay.setSize(BUTTON_SIZE, BUTTON_SIZE * buttonPlay.getHeight() / buttonPlay.getWidth() );
        buttonAbout.setSize(BUTTON_SIZE , BUTTON_SIZE * buttonPlay.getHeight() / buttonPlay.getWidth() );
        buttonExit.setSize(BUTTON_SIZE , BUTTON_SIZE * buttonPlay.getHeight() / buttonPlay.getWidth() );


        buttonExit.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                Gdx.app.exit();
            }
        });


        Table table = new Table();

        table.add(buttonPlay).width(BUTTON_SIZE).height(BUTTON_SIZE * buttonPlay.getHeight() / buttonPlay.getWidth()).padBottom(BUTTON_SPACING);
        table.row();
        table.add(buttonAbout).width(BUTTON_SIZE).height(BUTTON_SIZE * buttonPlay.getHeight() / buttonPlay.getWidth()).padBottom(BUTTON_SPACING);
        table.row();
        table.add(buttonExit).width(BUTTON_SIZE).height(BUTTON_SIZE * buttonPlay.getHeight() / buttonPlay.getWidth()).padBottom(BUTTON_SPACING);

        table.setPosition(gamePort.getWorldWidth() / 2 - table.getWidth() / 2, gamePort.getWorldHeight() / 2 - table.getHeight() / 2 - 80 );


        stage.addActor(table);

    }
}
