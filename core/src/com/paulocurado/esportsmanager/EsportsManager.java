package com.paulocurado.esportsmanager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.I18NBundle;
import com.paulocurado.esportsmanager.model.Championship;
import com.paulocurado.esportsmanager.model.Contract;
import com.paulocurado.esportsmanager.model.GameSchedule;
import com.paulocurado.esportsmanager.model.Player;
import com.paulocurado.esportsmanager.model.Team;
import com.paulocurado.esportsmanager.model.User;
import com.paulocurado.esportsmanager.screens.BuyScoutScreen;
import com.paulocurado.esportsmanager.screens.ChampionshipScreen;
import com.paulocurado.esportsmanager.screens.HireScreen;
import com.paulocurado.esportsmanager.screens.InitScreen;
import com.paulocurado.esportsmanager.screens.LoadingScreen;
import com.paulocurado.esportsmanager.screens.MainMenuScreen;
import com.paulocurado.esportsmanager.screens.ScoutReportScreen;
import com.paulocurado.esportsmanager.screens.SellPlayersScreen;
import com.paulocurado.esportsmanager.screens.TrainScreen;
import com.paulocurado.esportsmanager.screens.GameScreen;
import com.paulocurado.esportsmanager.screens.LineupScreen;
import com.paulocurado.esportsmanager.screens.PositionsScreen;
import com.paulocurado.esportsmanager.screens.ScoutScreen;


import java.util.ArrayList;

public class EsportsManager extends Game {

    public static final String TITLE = "eSports Team Tycoon";
    public static final float VERSION = 1.0f;
    public static final int V_WIDTH = 640;
    public static final int V_HEIGHT = 960;

    public ArrayList<Player> playerList;
    public ArrayList<Team> teamList;
    public ArrayList<Contract> contractList;

    public User user;
    public GameSchedule schedule;
    public Championship championship;


    public OrthographicCamera camera;
    public SpriteBatch batch;
    public AssetManager assets;
    public I18NBundle bundle;

    public TextureRegion[][] facesOptions;

    public BitmapFont buttonFont;
    public BitmapFont labelFont;
    public BitmapFont labelFontSmall;
    public BitmapFont positionFont;
    public BitmapFont positionSmallFont;
    public BitmapFont labelFontMedium;
    public BitmapFont cleanFont;
    public BitmapFont playerNameFont;

    public AdHandler adHandler;

    public BuyScoutScreen buyScoutScreen;
    public ChampionshipScreen championshipScreen;
    public GameScreen gameScreen;
    public HireScreen hireScreen;
    public InitScreen initScreen;
    public LineupScreen lineupScreen;
    public LoadingScreen loadingScreen;
    public MainMenuScreen mainMenuScreen;
    public PositionsScreen positionsScreen;
    public ScoutReportScreen scoutReportScreen;
    public ScoutScreen scoutScreen;
    public SellPlayersScreen sellPlayersScreen;
    public com.paulocurado.esportsmanager.screens.SimulationScreen simulationScreen;
    public TrainScreen trainScreen;

    public EsportsManager(AdHandler adHandler) {
        this.adHandler = adHandler;
        adHandler.showAds(false);
    }


    @Override
    public void create() {
        assets = new AssetManager();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, V_WIDTH, V_HEIGHT);
        batch = new SpriteBatch();
        initFonts();
        playerList = new ArrayList<Player>();
        teamList = new ArrayList<Team>();
        contractList = new ArrayList<Contract>();

        buyScoutScreen = new BuyScoutScreen(this);
        championshipScreen = new ChampionshipScreen(this);
        gameScreen = new GameScreen(this);
        hireScreen = new HireScreen(this);
        initScreen = new InitScreen(this);
        lineupScreen = new LineupScreen(this);
        loadingScreen = new LoadingScreen(this);
        mainMenuScreen = new MainMenuScreen(this);
        positionsScreen = new PositionsScreen(this);
        scoutReportScreen = new ScoutReportScreen(this);
        scoutScreen = new ScoutScreen(this);
        sellPlayersScreen = new SellPlayersScreen(this);
        //simulationScreen;
        trainScreen = new TrainScreen(this);


        this.setScreen(loadingScreen);

    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        assets.dispose();
        buyScoutScreen.dispose();
        championshipScreen.dispose();
        gameScreen.dispose();
        hireScreen.dispose();
        initScreen.dispose();
        lineupScreen.dispose();
        loadingScreen.dispose();
        mainMenuScreen.dispose();
        positionsScreen.dispose();
        scoutReportScreen.dispose();
        scoutScreen.dispose();
        sellPlayersScreen.dispose();
        trainScreen.dispose();
    }

    private void initFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Neuton-Extrabold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 34;
        parameter.color = Color.WHITE;
        parameter.borderWidth = 3;
        buttonFont = generator.generateFont(parameter);

        FreeTypeFontGenerator generatorLabel = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Neuton-Extrabold.ttf"));
        parameter.size = 30;
        parameter.borderWidth = 2;
        parameter.color = Color.WHITE;
        labelFont = generatorLabel.generateFont(parameter);

        parameter.size = 25;
        parameter.borderWidth = 2;
        parameter.color = Color.WHITE;
        labelFontMedium = generatorLabel.generateFont(parameter);

        FreeTypeFontGenerator generatorLabelSmall = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Neuton-Extrabold.ttf"));
        parameter.size = 22;
        parameter.borderWidth = 0;
        parameter.spaceX = 2;
        parameter.spaceY = 2;
        parameter.color = Color.WHITE;
        labelFontSmall = generatorLabelSmall.generateFont(parameter);

        FreeTypeFontGenerator generatorPosition = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"));
        parameter.size = 24;
        parameter.borderWidth = 2;
        parameter.color = Color.WHITE;
        positionFont = generatorPosition.generateFont(parameter);

        FreeTypeFontGenerator generatorSmallPosition = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Neuton-Extrabold.ttf"));
        parameter.size = 11;
        parameter.borderWidth = 1;
        parameter.color = Color.WHITE;
        positionSmallFont = generatorSmallPosition.generateFont(parameter);

        FreeTypeFontGenerator generatorCleanFont = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Neuton-Extrabold.ttf"));
        parameter.size = 28;
        parameter.borderWidth = 0;
        parameter.color = Color.WHITE;
        cleanFont = generatorCleanFont.generateFont(parameter);

        FreeTypeFontGenerator generatorPlayerName = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"));
        parameter.size = 17;
        parameter.borderWidth = 0;
        parameter.color = Color.WHITE;
        playerNameFont = generatorPlayerName.generateFont(parameter);

        generator.dispose();
        generatorCleanFont.dispose();
        generatorLabel.dispose();
        generatorLabelSmall.dispose();
        generatorPlayerName.dispose();
        generatorPosition.dispose();
        generatorSmallPosition.dispose();

    }

    public Player findPlayerbyId(String playerId) {

        for(int i = 0; i < playerList.size(); i++) {
            if(playerId.equals(playerList.get(i).getId()))
                return playerList.get(i);
        }
        return null;
    }

    public Player findPlayerbyNickName(String NickName) {

        for(int i = 0; i < playerList.size(); i++) {
            if(NickName.equals(playerList.get(i).getNickName()))
                return playerList.get(i);
        }
        return null;
    }
}