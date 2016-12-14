package com.paulocurado.esportsmanager.uielements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.paulocurado.esportsmanager.EsportsManager;
import com.paulocurado.esportsmanager.screens.GameScreen;
import com.paulocurado.esportsmanager.screens.HireScreen;

/**
 * Created by Paulo on 07/12/2016.
 */

public class ErrorDialog extends GameScreenBox {

    private String error;
    private static int MINUMUM_MONTHS = 4;

    public ErrorDialog(EsportsManager mainApp, Skin skin, String path, Screen root) {
        super(mainApp, skin, path, root);
    }

    public String getError() {
        error = "";

        if(((HireScreen)root).getHireDialog().player.getTeamId().equals(mainApp.user.getTeam().getId())) {
            error += mainApp.bundle.get("Error_User_Player_Already_in_Team") + "\n";
        }
        else {
            if (mainApp.user.getTeam().getPlayers().size() >= 5) {
                error += mainApp.bundle.get("Error_User_Team_Full") + "\n";
            }
            if (mainApp.user.getTeam().getBudget() < mainApp.findPlayerbyId(((HireScreen) root).getHireDialog().player.getId()).getCost(mainApp.contractList)) {
                System.out.println(mainApp.findPlayerbyId(((HireScreen) root).getHireDialog().player.getId()).getCost(mainApp.contractList));

                error += mainApp.bundle.get("Error_User_Team_No_Cost_Money") + "\n";
            }

            if (mainApp.user.getTeam().getBudget() < MINUMUM_MONTHS * mainApp.findPlayerbyId(((HireScreen) root).getHireDialog().player.getId()).getSalary(mainApp.contractList)) {

                error += mainApp.bundle.get("Error_User_Team_No_Salary_Money") + "\n";
            }
        }

        ((Label) getActor("errorLabel")).setText(error);

        return error;
    }
}
