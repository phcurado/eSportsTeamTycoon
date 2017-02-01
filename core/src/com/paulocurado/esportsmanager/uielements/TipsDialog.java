package com.paulocurado.esportsmanager.uielements;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.paulocurado.esportsmanager.EsportsManager;


/**
 * Created by Paulo on 03/12/2016.
 */

public class TipsDialog extends GameScreenBox {
    private String tip;

    private boolean teamPlayerRequirement;


    public TipsDialog(EsportsManager mainApp, Skin skin, String path, Screen root) {
        super(mainApp, skin, path, root);

        tip = "";
        teamPlayerRequirement = false;
    }

    public boolean isTeamPlayerRequirement() {
        if(mainApp.user.getTeam().getPlayers().size() < 5) {
            setTip(mainApp.bundle.get("NoPlayerInform") );
            teamPlayerRequirement = false;
        }
        else {
            teamPlayerRequirement = true;
        }

        return teamPlayerRequirement;
    }

    public void resizeDialogBasedOnText() {
        this.getActor("tipWindow").setSize(this.getActor("tipWindow").getWidth(), this.getActor("tipLabel").getHeight());
    }

    public void setTeamPlayerRequirement(boolean teamPlayerRequirement) {
        this.teamPlayerRequirement = teamPlayerRequirement;
    }



    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        ((Label) this.getActor("tipLabel")).setText(tip);

        this.tip = tip;
    }

    public TipsDialog getDialog() {
        return this;
    }

    public void defaultButtonClick(final Stage newStage) {
        this.getActor("OkButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                Gdx.input.setInputProcessor(newStage);
                getDialog().setVisibility(false);
            }
        });
    }
}
