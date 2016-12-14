package com.paulocurado.esportsmanager.uielements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.paulocurado.esportsmanager.EsportsManager;
import com.paulocurado.esportsmanager.model.Player;
import com.paulocurado.esportsmanager.model.Team;
import com.paulocurado.esportsmanager.model.UsefulFunctions;
import com.paulocurado.esportsmanager.screens.HireScreen;

/**
 * Created by Paulo on 07/12/2016.
 */

public class ConfirmContractDialog extends GameScreenBox {
    public ConfirmContractDialog(EsportsManager mainApp, Skin skin, String path, Screen root) {
        super(mainApp, skin, path, root);

        buttonsClick();
    }

    private void buttonsClick() {
        this.getActor("YesButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                transferLogic();
                mainApp.setScreen(root);
            }
        });

        this.getActor("NoButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                Gdx.input.setInputProcessor(((HireScreen)root).getHireDialog().getStage());
                setVisibility(false);
            }
        });
    }

    private void transferLogic() {
        Team team = ((HireScreen)root).getHireDialog().player.playerTeam(mainApp.teamList);
        UsefulFunctions usefulFunctions = new UsefulFunctions(mainApp);


        //Primeiro é verificado se o player tem time e caso tenha o player deve ser removido do seu time atual para a contratação, também ve se o player não já está no time
        if(((HireScreen)root).getHireDialog().player.hasTeam(mainApp.teamList)) {
            //remove o player do time e contrata um novo
            ((HireScreen)root).getHireDialog().player.playerTeam(mainApp.teamList).getPlayers().remove(((HireScreen)root).getHireDialog().player);
            Player recomendedPlayer = team.recomendedPlayer(mainApp.playerList); //pega uma referência do player recomendado
            team.getPlayers().add(recomendedPlayer ); //adiciona ele
            recomendedPlayer.setTeamId(team.getId()); // muda seu id para o time correspondente
            usefulFunctions.createNewContract(team, recomendedPlayer, recomendedPlayer.getRecomendedCost(), recomendedPlayer.getRecomendedSalary());

            ((HireScreen) root).getHireDialog().player.setTeamId(mainApp.user.getTeam().getId());
            mainApp.user.getTeam().getPlayers().add(((HireScreen) root).getHireDialog().player);
            mainApp.user.getTeam().setBudget(mainApp.user.getTeam().getBudget() - ((HireScreen) root).getHireDialog().player.getCost(mainApp.contractList));

            usefulFunctions.changePlayerContract(mainApp.user.getTeam(), ((HireScreen) root).getHireDialog().player);
        }
        else if(!((HireScreen)root).getHireDialog().player.hasTeam(mainApp.teamList) ) {
                //Id do time antigo do player alterado pro novo
                ((HireScreen) root).getHireDialog().player.setTeamId(mainApp.user.getTeam().getId());
                //player contratado
                mainApp.user.getTeam().getPlayers().add(((HireScreen) root).getHireDialog().player);
            usefulFunctions.createNewContract(mainApp.user.getTeam(), ((HireScreen) root).getHireDialog().player, ((HireScreen) root).getHireDialog().player.getRecomendedCost(),
            ((HireScreen) root).getHireDialog().player.getRecomendedSalary());
        }


        mainApp.teamList.get(0).setUpPlayers();
        for(int i = 0; i < 5; i ++) {
            System.out.println(mainApp.teamList.get(0).getPlayers().get(i).getNickName());
        }




        //TODO fazer a transferencia e pensar em fazer uma classe que tenha funções de find
        //por enquanto isso aqui faz a transferência pro time, removendo o jogador do time antigo porém tem que fazer depois
        // algo que atualize as variáveis que relacionam jogador e time como o ID do time na classe jogador


    }
}
