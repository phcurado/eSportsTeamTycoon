package com.paulocurado.esportsmanager.model;

import com.paulocurado.esportsmanager.EsportsManager;

import java.util.ArrayList;

/**
 * Created by Paulo on 13/12/2016.
 */

public class UsefulFunctions {
    private EsportsManager mainApp;

    public UsefulFunctions(EsportsManager mainApp) {
        this.mainApp = mainApp;
    }


    public void getPlayerCost() {
        //TODO make this
    }

    public void changePlayerContract(Team team, Player player){
        for(int i = 0; i < mainApp.contractList.size(); i++) {
            if(mainApp.contractList.get(i).getPlayerId().equals(player.getId())) {
                mainApp.contractList.get(i).setTeamId(team.getId());
            }
        }

    }
    public void createNewContract(Team team, Player player, int cost, int salary) {
        Contract contract = new Contract();
        contract.setId(newContractID());
        contract.setTeamId(team.getId());
        contract.setPlayerId(player.getId());
        contract.setTransferFee(cost);
        contract.setSalary(salary);

        mainApp.contractList.add(contract);
    }

    public String newContractID() {
        long contractId;
        long newId = 1;
        for (int i = 0; i < mainApp.contractList.size(); i++) {
            for (int j = 0; j < mainApp.contractList.size(); j++) {
                contractId = Integer.parseInt(mainApp.contractList.get(j).getId().split("_")[1]);
                if (newId == contractId)
                    newId++;

            }
        }
        String calculateID = Long.toString(newId);
        String zeros = "";
        for( int i = 0; i < 6 - calculateID.length(); i++) {
            zeros = zeros + "0";
        }

        return "CONTRACT_" + zeros + calculateID;
    }

    public Team findTeamById(String teamId) {
        for(int i = 0; i < mainApp.teamList.size(); i++) {
            if(teamId.equals(mainApp.teamList.get(i).getId()))
                return mainApp.teamList.get(i);
        }
        return null;
    }

    public ArrayList<Team> getTeamsByTier(int tier) {
        ArrayList<Team> team = new ArrayList<Team>();
        for(int i = 0; i < mainApp.teamList.size(); i++) {
            if(mainApp.teamList.get(i).getTier() == tier) {
                team.add(mainApp.teamList.get(i));
            }
        }
        return team;
    }
}
