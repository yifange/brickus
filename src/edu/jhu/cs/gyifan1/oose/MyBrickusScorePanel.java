package edu.jhu.cs.gyifan1.oose;

import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusIllegalMoveEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusListener;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;
import edu.jhu.cs.oose.fall2013.brickus.iface.Player;

/**
 * The panel holds the scores.
 * @author yifan
 *
 */
public class MyBrickusScorePanel extends JPanel {
	private BrickusModel model;
	private Map<Player, JLabel> scoreLabels;
	
	/**
	 * Initialize the score panel.
	 * @param model	The {@link BrickusModel}
	 */
	public MyBrickusScorePanel(final BrickusModel model) {
		this.model = model;
		scoreLabels = new HashMap<Player, JLabel>();
		scoreLabels.put(Player.PLAYER1, new JLabel("0"));
		scoreLabels.put(Player.PLAYER2, new JLabel("0"));
		for (Player player : scoreLabels.keySet()) {
			JLabel label = scoreLabels.get(player);
			label.setForeground(MyBrickusUtils.getPlayerColor(player));
			label.setFont(new Font("Arial", Font.PLAIN, 30));
		}
		add(scoreLabels.get(Player.PLAYER1));
		add(scoreLabels.get(Player.PLAYER2));
		highlightPlayer(model.getActivePlayer());

		model.addBrickusListener(new BrickusListener() {

			@Override
			public void illegalMove(BrickusIllegalMoveEvent event) {
				// nothing to do
			}

			@Override
			public void modelChanged(BrickusEvent event) {
				if (event.isPlayerChanged()) {
					updateScores();
					highlightPlayer(model.getActivePlayer());
					repaint();
				}
				if (event.isGameOver()) {
					updateScores();
					Player player = MyBrickusUtils.getWinner(model);
					highlightPlayer(player);
				}
			}
		});
	}
	
	/**
	 * Highlight one certain player's score on the panel.
	 * @param p 	The player to be highlighted.
	 */
	private void highlightPlayer(Player p) {
		if (p == null) {
			for (Player player : scoreLabels.keySet()) {
				JLabel label = scoreLabels.get(player);
				label.setBorder(BorderFactory.createCompoundBorder(new LineBorder(
						MyBrickusUtils.getPlayerColor(player)), new EmptyBorder(10, 10, 10,
						10)));
			}
		} else {
			for (Player player : scoreLabels.keySet()) {
				JLabel label = scoreLabels.get(player);
				if (p == player)
					label.setBorder(BorderFactory.createCompoundBorder(new LineBorder(
							MyBrickusUtils.getPlayerColor(player)), new EmptyBorder(10, 10,
							10, 10)));
				else
					label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			}
		}
	}
	
	/**
	 * Update the scores according to the model.
	 */
	private void updateScores() {
		for (Player player : scoreLabels.keySet()) {
			scoreLabels.get(player).setText(
					String.valueOf(model.calculateScore(player)));
		}
	}
}
