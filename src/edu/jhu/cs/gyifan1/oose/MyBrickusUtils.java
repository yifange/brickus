package edu.jhu.cs.gyifan1.oose;

import java.awt.Color;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;
import edu.jhu.cs.oose.fall2013.brickus.iface.Player;

/**
 * Some useful functions.
 * @author yifan
 *
 */
public class MyBrickusUtils {
	/**
	 * The color used to indicate piece overlapping.
	 * @return	The color used to indicate piece overlapping.
	 */
	public static Color getOverlappingColor() {
		return new Color((float)1, (float)1, 0);
	}
	
	/**
	 * Get the color used when the piece cannot be selected.
	 * @return color used when the piece cannot be selected.
	 */
	public static Color getDisabledColor() {
		return Color.gray;
	}
	/**
	 * For a given player, get the color. 
	 * @param player	The player for whom to get the color.
	 * @return				The color for the player.
	 */
	public static Color getPlayerColor(Player player) {
		if (player == Player.PLAYER1)
			return new Color((float)1, (float)0.14, (float)0.24);
		else
			return new Color((float)0.01, (float)0.56, (float)0.61);
	}
	/**
	 * For a given player, get the transparent color.
	 * @param player 	The player for whom to get the color.
	 * @return				The color for the player.
	 */
	public static Color getPlayerTransparentColor(Player player) {
		if (player == Player.PLAYER1)
			return new Color(1, (float)0.14, (float)0.24, (float)0.3);
		else
			return new Color((float)0.01, (float)0.56, (float)0.61, (float) 0.3);
	}
	/**
	 * Get the winner.
	 * @param model	The {@BrickusModel}
	 * @return	The winner. Return <code>null</code> if tied.
	 */
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
