package edu.jhu.cs.gyifan1.oose;

import java.awt.Color;

import edu.jhu.cs.oose.fall2013.brickus.iface.Player;

public class MyBrickusUtils {
	public static Color getPlayerColor(Player player) {
		if (player == Player.PLAYER1)
			return Color.red;
		else
			return Color.blue;
	}
	public static Color getPlayerTransparentColor(Player player) {
		if (player == Player.PLAYER1)
			return new Color(1, 0, 0, (float)0.5);
		else
			return new Color(0, 0, 1, (float)0.5);
	}
}
