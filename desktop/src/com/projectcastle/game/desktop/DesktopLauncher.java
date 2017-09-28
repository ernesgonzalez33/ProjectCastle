package com.projectcastle.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.projectcastle.game.ProjectCastleGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "ProjectCastle";
		config.width = 640;
		config.height = 640;
		new LwjglApplication(new ProjectCastleGame(), config);
	}
}
