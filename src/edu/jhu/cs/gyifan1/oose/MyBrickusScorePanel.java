package edu.jhu.cs.gyifan1.oose;

import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusIllegalMoveEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusListener;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;
import edu.jhu.cs.oose.fall2013.brickus.iface.Player;

public class MyBrickusScorePanel extends JPanel {
	private Map<Player, JLabel> scoreLabels;
	private BrickusModel model;
	private void updateScores() {
		System.out.println("he");
		for (Player player : scoreLabels.keySet()) {
			scoreLabels.get(player).setText(String.valueOf(model.calculateScore(player)));
		}
	}
	public MyBrickusScorePanel(BrickusModel model) {
		this.model = model;
		scoreLabels = new HashMap<Player, JLabel>();
		scoreLabels.put(Player.PLAYER1, new JLabel("0"));
		scoreLabels.put(Player.PLAYER2, new JLabel("0"));
		for (Player player : scoreLabels.keySet()) {
			JLabel label = scoreLabels.get(player);
			label.setForeground(MyBrickusUtils.getPlayerColor(player));
			label.setFont(new Font("Arial", Font.PLAIN, 30));
			label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		}
		add(scoreLabels.get(Player.PLAYER1));
		add(scoreLabels.get(Player.PLAYER2));
		
//		updateScores();
		model.addBrickusListener(new BrickusListener() {
			
			@Override
			public void modelChanged(BrickusEvent event) {
				if (event.isPlayerChanged() || event.isGameOver()) {
					updateScores();
					repaint();
				}
			}
			
			@Override
			public void illegalMove(BrickusIllegalMoveEvent event) {
				// nothing to do
			}
		});
	}
}
