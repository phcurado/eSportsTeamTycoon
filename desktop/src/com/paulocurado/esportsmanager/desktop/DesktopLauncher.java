package com.paulocurado.esportsmanager.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.paulocurado.esportsmanager.EsportsManager;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = EsportsManager.TITLE + " v" + EsportsManager.VERSION;
		config.width = EsportsManager.V_WIDTH;
		config.height = EsportsManager.V_HEIGHT;
		new LwjglApplication(new EsportsManager(new com.paulocurado.esportsmanager.AdHandler() {
			@Override
			public void showAds(boolean show) {

			}
		}), config);
	}
}
