package com.paulocurado.esportsmanager.uielements.scout;

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
import com.paulocurado.esportsmanager.screens.ScoutReportScreen;
import com.paulocurado.esportsmanager.uielements.GameScreenBox;
import com.paulocurado.esportsmanager.model.Position;

/**
 * Created by phcur on 07/01/2017.
 */

public class HireScoutedPlayerDialog extends GameScreenBox {
    protected Player player;

    public HireScoutedPlayerDialog(EsportsManager mainApp, Skin skin, String path, Screen root) {
        super(mainApp, skin, path, root);
        buttonsClick();
    }

    public void setUpDialog(Player player) {
        this.player = player;

        ((Label) getActor("NickNameLabel")).setWrap(false);
        ((Label) getActor("NickNameLabel")).setText(player.getNickName());
        ((Image) getActor("faceImage")).setDrawable(player.createPlayerFace(
                mainApp.facesOptions,
                ((ScoutReportScreen)root).getStage().getViewport()).getDrawable());


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
        this.getActor("HireButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {

                if(!((ScoutReportScreen) root).getErrorScoutDialog().getError().equals("")) {
                    ((ScoutReportScreen) root).getErrorScoutDialog().setVisibility(true);
                }
                else if (mainApp.user.getTeam().getPlayers().size() >= 5) {

                    ((ScoutReportScreen) root).getTipsDialog().setVisibility(true);
                    ((ScoutReportScreen) root).getTipsDialog().setTip(mainApp.bundle.get("Transfer_Sell_Players"));
                    ((ScoutReportScreen) root).getTipsDialog().getActor("OkButton").clearListeners();
                    ((ScoutReportScreen) root).getTipsDialog().getActor("OkButton").addListener((new ClickListener() {
                        public void clicked(InputEvent e, float x, float y) {
                            ((ScoutReportScreen) root).getTipsDialog().setVisibility(false);
                            mainApp.setScreen(mainApp.sellPlayersScreen);
                        }
                    }));

                }

                else {
                    ((ScoutReportScreen) root).getConfirmContractScoutDialog().setVisibility(true);
                }
            }
        });

        this.getActor("backButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                setVisibility(false);
                Gdx.input.setInputProcessor(((ScoutReportScreen)root).getStage());
            }
        });
    }

}
