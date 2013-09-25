package edu.jhu.cs.gyifan1.oose;


import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusIllegalMoveEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusListener;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;
import edu.jhu.cs.oose.fall2013.brickus.iface.Player;


public class MyBrickusStatusBar extends JPanel {
	private JLabel messageLabel;
	private MyBrickusFrame frame;
	public final static int HEIGHT = 20;
	public void updateSize() {
		setPreferredSize(new Dimension(frame.getWidth(), HEIGHT));
	}
	public MyBrickusStatusBar(final MyBrickusFrame frame) {
		this.frame = frame;
		updateSize();
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBorder(new BevelBorder(BevelBorder.LOWERED));
		messageLabel = new JLabel();
		messageLabel.setHorizontalAlignment(SwingConstants.LEFT);
		add(messageLabel);
		frame.getModel().addBrickusListener(new BrickusListener() {
			
			@Override
			public void modelChanged(BrickusEvent event) {
				if (event.isPlayerChanged()) {
					updateMessage(frame.getModel().getActivePlayer().toString() + "'s turn.");
					repaint();
				}
				if (event.isGameOver()) {
					BrickusModel model = frame.getModel();
					int score1 = model.calculateScore(Player.PLAYER1);
					int score2 = model.calculateScore(Player.PLAYER2);
					if (score1 > score2)
						updateMessage(Player.PLAYER1.toString() + "wins.");
					else if (score1 < score2)
						updateMessage(Player.PLAYER2.toString() + "wins.");
					else
						updateMessage("Game ties.");
						
						
				}
			}
			
			@Override
			public void illegalMove(BrickusIllegalMoveEvent event) {
				updateMessage(event.getMessage());
				repaint();
			}
		});
	}
	private void updateMessage(String text) {
		messageLabel.setText(text);		
	}
}
