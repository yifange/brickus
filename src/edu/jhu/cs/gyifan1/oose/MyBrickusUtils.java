package edu.jhu.cs.gyifan1.oose;

import java.awt.Color;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;
import edu.jhu.cs.oose.fall2013.brickus.iface.Player;

public class MyBrickusUtils {
	public static Color getOverlappingColor() {
		return new Color(1, 0, 1, (float) 0.6);
	}

	public static Color getPlayerColor(Player player) {
		if (player == Player.PLAYER1)
			return Color.red;
		else
			return Color.blue;
	}

	public static Color getPlayerTransparentColor(Player player) {
		if (player == Player.PLAYER1)
			return new Color(1, 0, 0, (float) 0.3);
		else
			return new Color(0, 0, 1, (float) 0.3);
	}

	public static Player getWinner(BrickusModel model) {
		int score1 = model.calculateScore(Player.PLAYER1);
		int score2 = model.calculateScore(Player.PLAYER2);
		if (score1 > score2)
			return Player.PLAYER1;
		else if (score2 > score1)
			return Player.PLAYER2;
		else
			return null;
	}
}
