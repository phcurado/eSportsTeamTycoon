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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.paulocurado.esportsmanager.EsportsManager;
import com.paulocurado.esportsmanager.uielements.NewGameDialog;

/**
 * Created by Paulo on 09/11/2016.
 */

public class MainMenuScreen implements Screen {
    private EsportsManager mainApp;

    private Viewport gamePort;

    private Stage stage;
    private Skin skin;

    private Image logo;
    private Image background;
    private TextButton buttonPlay;
    private TextButton buttonAbout;
    private TextButton buttonExit;
    private NewGameDialog newGameDialog;

    public static float BUTTON_SPACING = 20f;
    public static float BUTTON_SIZE = 230f;


    public MainMenuScreen(EsportsManager mainApp) {
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



        newGameDialog = new NewGameDialog(mainApp, skin);


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
        newGameDialog.render(delta);
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
        buttonPlay = new TextButton(mainApp.bundle.get("startButton"), skin, "default");
        buttonAbout = new TextButton(mainApp.bundle.get("aboutButton"), skin, "default");
        buttonExit = new TextButton(mainApp.bundle.get("exitButton"), skin, "default");

        buttonPlay.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                newGameDialog.visible = true;

            }
        });

        buttonExit.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                Gdx.app.exit();
            }
        });


        Table table = new Table();

        table.add(buttonPlay).width(BUTTON_SIZE).height(BUTTON_SIZE * buttonPlay.getHeight() / buttonPlay.getWidth()).padBottom(BUTTON_SPACING);
        table.row().fillX();
        table.add(buttonAbout).width(BUTTON_SIZE).height(BUTTON_SIZE * buttonPlay.getHeight() / buttonPlay.getWidth()).padBottom(BUTTON_SPACING);
        table.row().fillX();
        table.add(buttonExit).width(BUTTON_SIZE).height(BUTTON_SIZE * buttonPlay.getHeight() / buttonPlay.getWidth()).padBottom(BUTTON_SPACING);

        table.setPosition(gamePort.getWorldWidth() / 2 - table.getWidth() / 2, logo.getY() - table.getHeight() / 2 - 250 );


        stage.addActor(table);

    }
}
