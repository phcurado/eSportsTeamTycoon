package com.paulocurado.esportsmanager.uielements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.paulocurado.esportsmanager.EsportsManager;
import com.paulocurado.esportsmanager.model.Player;
import com.paulocurado.esportsmanager.model.Position;
import com.paulocurado.esportsmanager.screens.GameScreen;
import com.paulocurado.esportsmanager.screens.HireScreen;
import com.paulocurado.esportsmanager.screens.ScoutReportScreen;

/**
 * Created by Paulo on 05/12/2016.
 */

public class PlayerHireDialog extends  GameScreenBox {
    protected Player player;

    public PlayerHireDialog(EsportsManager mainApp, Skin skin, String path, Screen root) {
        super(mainApp, skin, path, root);

        this.getActor("HireButton").setVisible(false);
        this.getActor("backButton").setX(getActor("HirePlayerBox").getX() + getActor("HirePlayerBox").getWidth() / 2 -
                getActor("backButton").getWidth() / 2);
        buttonsClick();

    }

    public void setUpDialog(Player player) {
        this.player = player;

        ((Label) getActor("NickNameLabel")).setWrap(false);
        ((Label) getActor("NickNameLabel")).setText(player.getNickName());
        ((Image) getActor("faceImage")).setDrawable(player.createPlayerFace(mainApp.facesOptions,
                mainApp.gameScreen.gamePort).getDrawable());


        ((Label) getActor("FarmNumberLabel")).setText(Integer.toString(player.getFarm() ));
        ((Label) getActor("FightNumberLabel")).setText(Integer.toString(player.getFighting() ));
        ((Label) getActor("IndependenceNumberLabel")).setText(Integer.toString(player.getIndependency() ));
        ((Label) getActor("RotationNumberLabel")).setText(Integer.toString(player.getRotation() ));
        ((Label) getActor("SupportNumberLabel")).setText(Integer.toString(player.getSupport() ));


        ((Label) getActor("CarryAbilityLabel")).setText(player.hability(Position.CARRY) );
        ((Label) getActor("MidAbilityLabel")).setText(player.hability(Position.MID) );
        ((Label) getActor("OfflaneAbilityLabel")).setText(player.hability(Position.OFFLANE) );
        ((Label) getActor("Supp4AbilityLabel")).setText(player.hability(Position.SUPP4) );
        ((Label) getActor("Supp5AbilityLabel")).setText(player.hability(Position.SUPP5) );

        ((Label) getActor("CostNumberLabel")).setText(Integer.toString(player.getCost(mainApp.contractList)) );
        ((Label) getActor("SalaryNumberLabel")).setText(Integer.toString(player.getSalary(mainApp.contractList)) );


        ((Label) getActor("CostNumberLabel")).setAlignment(Align.right);
        ((Label) getActor("SalaryNumberLabel")).setAlignment(Align.right);

    }

    private void buttonsClick() {

        this.getActor("backButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                setVisibility(false);
                Gdx.input.setInputProcessor(((HireScreen)root).getStage());
            }
        });
    }

}
