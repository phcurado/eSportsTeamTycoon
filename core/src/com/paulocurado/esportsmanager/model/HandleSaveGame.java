package com.paulocurado.esportsmanager.model;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.paulocurado.esportsmanager.EsportsManager;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;


/**
 * Created by Paulo on 14/12/2016.
 */

public class HandleSaveGame {

    public void saveGame(EsportsManager mainApp) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();


        Writer writerPlayer = Gdx.files.local("Players.json").writer(false);
        String players = gson.toJson(mainApp.playerList);
        try {
            writerPlayer.write(players);
            writerPlayer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Writer writerTeam = Gdx.files.local("Teams.json").writer(false);
        String teams = gson.toJson(mainApp.teamList);
        try {
            writerTeam.write(teams);
            writerTeam.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Writer writerContract = Gdx.files.local("Contracts.json").writer(false);
        String contracts = gson.toJson(mainApp.contractList);
        try {
            writerContract.write(contracts);
            writerContract.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Writer writerUser = Gdx.files.local("User.json").writer(false);
        String user = gson.toJson(mainApp.user);
        try {
            writerUser.write(user);
            writerUser.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadGame(EsportsManager mainApp){
        Gson gson = new GsonBuilder().create();

        Reader readerPlayer = Gdx.files.local("Players.json").reader();
        mainApp.playerList.clear();
        mainApp.playerList.addAll((ArrayList<Player>) gson.fromJson(readerPlayer,
                new TypeToken<ArrayList<Player>>() {
                }.getType()));

        Reader readerTeam = Gdx.files.local("Teams.json").reader();
        mainApp.teamList.clear();
        mainApp.teamList.addAll((ArrayList<Team>) gson.fromJson(readerTeam,
                new TypeToken<ArrayList<Team>>() {
                }.getType()));

        Reader readerContract = Gdx.files.local("Contracts.json").reader();
        mainApp.contractList.clear();
        mainApp.contractList.addAll((ArrayList<Contract>) gson.fromJson(readerContract,
                new TypeToken<ArrayList<Contract>>() {
                }.getType()));

        Reader readerUser = Gdx.files.local("User.json").reader();
        mainApp.user = gson.fromJson(readerUser, User.class);

        JsonParser json = new JsonParser();
        Object arrayTeams = json.parse(Gdx.files.local("Teams.json").reader());
        JsonArray jsonArray = (JsonArray) arrayTeams;

        for(int i = 0; i < mainApp.teamList.size(); i++) {
            JsonObject jsonObject = (JsonObject)jsonArray.get(i);
            if(jsonObject.get("playersId").getAsJsonArray().size() != 0) {
                for(int j = 0; j < jsonObject.get("playersId").getAsJsonArray().size(); j++) {
                    if(!jsonObject.get("playersId").getAsJsonArray().get(j).isJsonNull()) {
                        mainApp.teamList.get(i).getPlayers().add(mainApp.findPlayerbyId(jsonObject.get("playersId").getAsJsonArray().get(j).getAsString()));
                    }
                }
            }


        }

        mainApp.user.setTeam(mainApp.teamList.get(mainApp.teamList.size() - 1) );

    }
}
