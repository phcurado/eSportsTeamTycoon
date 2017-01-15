package com.paulocurado.esportsmanager.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.paulocurado.esportsmanager.EsportsManager;
import com.paulocurado.esportsmanager.model.Player;
import com.paulocurado.esportsmanager.uielements.ConfirmContractDialog;
import com.paulocurado.esportsmanager.uielements.ErrorDialog;
import com.paulocurado.esportsmanager.uielements.PlayerHireDialog;
import com.paulocurado.esportsmanager.uielements.TipsDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * Created by Paulo on 03/12/2016.
 */

public class HireScreen implements Screen{

    private final EsportsManager mainApp;

    private Viewport gamePort;

    private Stage stage;
    private Skin skin;

    private Table container;
    private TextButton backButton;

    private PlayerHireDialog hireDialog;
    private TipsDialog tipsDialog;
    private ErrorDialog errorDialog;
    private ConfirmContractDialog confirmContractDialog;

    public HireScreen(final EsportsManager mainApp) {
        this.mainApp = mainApp;
        gamePort = new FitViewport(mainApp.V_WIDTH , mainApp.V_HEIGHT, mainApp.camera);
        stage = new Stage(gamePort, mainApp.batch);

    }

    @Override
    public void show() {
        System.out.println("Hire Screen");
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        this.skin = new Skin();
        this.skin.addRegions(mainApp.assets.get("ui/ui.atlas", TextureAtlas.class));
        this.skin.add("button-font", mainApp.buttonFont);
        this.skin.add("label-font", mainApp.labelFont);
        this.skin.add("label-small-font", mainApp.labelFontSmall);
        this.skin.add("position-font", mainApp.positionFont);
        this.skin.add("position-small-font", mainApp.positionSmallFont);
        this.skin.add("label-medium-font", mainApp.labelFontMedium);
        this.skin.add("label-clean-font", mainApp.cleanFont);
        this.skin.add("playerName-font", mainApp.playerNameFont);
        this.skin.load(Gdx.files.internal("ui/ui.json"));


        hireDialog = new PlayerHireDialog(mainApp, skin, "ui/HirePlayerBox.json", this);
        tipsDialog = new TipsDialog(mainApp, skin, "ui/informationBox.json", this);
        errorDialog = new ErrorDialog(mainApp, skin, "ui/ErrorBox.json", this);
        confirmContractDialog = new ConfirmContractDialog(mainApp, skin, "ui/ConfirmationBox.json", this);


        tipLogic();
        errorDialogButtonsClick();


        Table table = new Table();
        Table tableTitle = new Table();


        ArrayList<Player> playerOrganizeByRank = new ArrayList<Player>();

        playerOrganizeByRank.addAll(mainApp.playerList);
        Collections.sort(playerOrganizeByRank, new Comparator<Player>() {
            @Override
            public int compare(Player player1, Player player2) {
                return player2.getRank() - player1.getRank();
            }
        });



        for(int i = 0; i < mainApp.playerList.size(); i ++) {

            final Label playerNickName_Light = new Label(playerOrganizeByRank.get(i).getNickName(), skin, "tableLabel_Light");
            final Label playerNickName_Dark = new Label(playerOrganizeByRank.get(i).getNickName(), skin, "tableLabel_Dark");

            Label playerRank_Light = new Label(Integer.toString(playerOrganizeByRank.get(i).getRank()), skin, "tableLabel_Light");
            Label playerRank_Dark = new Label(Integer.toString(playerOrganizeByRank.get(i).getRank()), skin, "tableLabel_Dark");

            Label position_Light = new Label(playerOrganizeByRank.get(i).bestPosition(), skin, "tableLabel_Light");
            Label position_Dark = new Label(playerOrganizeByRank.get(i).bestPosition(), skin, "tableLabel_Dark");


            if(i == 0) {
                table.row().fillX();
                table.add(new Label(mainApp.bundle.get("Nickname"), skin, "tableLabelTitle2") ).expandX();
                table.add(new Label(mainApp.bundle.get("Team"), skin, "tableLabelTitle2") ).expandX();
                table.add(new Label(mainApp.bundle.get("Position"), skin, "tableLabelTitle2") ).expandX();
                table.add(new Label(mainApp.bundle.get("Rank"), skin, "tableLabelTitle2") ).expandX();
            }


            String team = "-----------";
            for(int j = 0; j < mainApp.teamList.size(); j ++) {
                for(int k = 0; k < mainApp.teamList.get(j).getPlayers().size(); k ++) {
                    if(i%2 == 0) {
                        if (mainApp.teamList.get(j).getPlayers().get(k).getNickName().equals(playerNickName_Light.getText().toString())) {
                            team = mainApp.teamList.get(j).getName();
                        }
                    }
                    else {
                        if (mainApp.teamList.get(j).getPlayers().get(k).getNickName().equals(playerNickName_Dark.getText().toString())) {
                            team = mainApp.teamList.get(j).getName();
                        }
                    }

                }
            }


            Label teamName_Light = new Label(team, skin, "tableLabel_Light");
            Label teamName_Dark = new Label(team, skin, "tableLabel_Dark");

            table.row().fillX();


            if(i%2 == 0) {
                table.add(playerNickName_Light);
                table.add(teamName_Light).expandX();
                table.add(position_Light).expandX();
                table.add(playerRank_Light);

            }
            else {
                table.add(playerNickName_Dark);
                table.add(teamName_Dark).expandX();
                table.add(position_Dark).expandX();
                table.add(playerRank_Dark);
            }


            playerNickName_Light.addListener(new ClickListener() {
                public void clicked(InputEvent e, float x, float y) {
                    hireDialog.setUpDialog(mainApp.findPlayerbyNickName(playerNickName_Light.getText().toString()) );
                    hireDialog.setVisibility(true);
                }
            });
            playerNickName_Dark.addListener(new ClickListener() {
                public void clicked(InputEvent e, float x, float y) {
                    hireDialog.setUpDialog(mainApp.findPlayerbyNickName(playerNickName_Dark.getText().toString()) );
                    hireDialog.setVisibility(true);
                }
            });

        }




        ScrollPane playersScrollPane = new ScrollPane(table, skin, "default");
        playersScrollPane.setupOverscroll(0, 0, 0);
        playersScrollPane.setSize(gamePort.getWorldWidth(), gamePort.getWorldHeight());

        container = new Table();
        container.add(tableTitle).expandX().fillX();
        container.row();
        container.add(playersScrollPane).expandX().fillX().padBottom(10);


        Image background = new Image(mainApp.assets.get("img/images.atlas", TextureAtlas.class).findRegion("inGameFloor") );
        background.setSize(gamePort.getWorldWidth(), gamePort.getWorldHeight());

        container.setSize(gamePort.getWorldWidth(), gamePort.getWorldHeight());

        setBackButton();

        container.row().expandX();
        container.add(backButton).fillX();


        stage.addActor(background);
        stage.addActor(container);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        mainApp.batch.setProjectionMatrix(stage.getCamera().combined);
        stage.getCamera().update();
        stage.getViewport().apply();


        stage.draw();
        hireDialog.draw();
        tipsDialog.draw();
        errorDialog.draw();
        confirmContractDialog.draw();

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
    }

    public void setBackButton() {
        backButton = new TextButton(mainApp.bundle.get("Back"), skin, "default");

        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                mainApp.setScreen(mainApp.gameScreen);
            }
        });

    }

    public void tipLogic() {
        tipsDialog.defaultButtonClick(stage);


        if(mainApp.user.isHireScreenFirstTime()) {
            tipsDialog.setTip(mainApp.bundle.get("hireTip"));
            Gdx.input.setInputProcessor(tipsDialog.getStage());
            tipsDialog.setVisibility(true);
            mainApp.user.setHireScreenFirstTime(false);
        }
        else{
            tipsDialog.setVisibility(false);
        }
    }

    public void errorDialogButtonsClick() {
        errorDialog.getActor("OkButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                Gdx.input.setInputProcessor(hireDialog.getStage());
                errorDialog.setVisibility(false);

            }
        });

    }

    public Stage getStage() { return stage; }

    public ErrorDialog getErrorDialog() {
        return errorDialog;
    }

    public PlayerHireDialog getHireDialog() {
        return hireDialog;
    }

    public ConfirmContractDialog getConfirmContractDialog() {
        return confirmContractDialog;
    }
}
