package edu.jhu.cs.gyifan1.oose;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusIllegalMoveEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusListener;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;
import edu.jhu.cs.oose.fall2013.brickus.iface.Player;

/**
 * The status bar panel
 * @author yifan
 *
 */
public class MyBrickusStatusBar extends JPanel {
	public final static int HEIGHT = 20;
	private MyBrickusFrame frame;
	private JLabel messageLabel;
	
	/**
	 * Initialize the status bar
	 * @param frame
	 */
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
			public void illegalMove(BrickusIllegalMoveEvent event) {
				updateMessage(event.getMessage());
				repaint();
			}

			@Override
			public void modelChanged(BrickusEvent event) {
				if (event.isGameOver()) {
					BrickusModel model = frame.getModel();
					Player winner = MyBrickusUtils.getWinner(model);
					if (winner == null)
						updateMessage("Game ties.");
					else
						updateMessage(winner.toString() + " wins.");
				} else if (event.isPlayerChanged()) {
					updateMessage(frame.getModel().getActivePlayer().toString()
							+ "'s turn.");
					repaint();
				}
			}
		});
	}
	/**
	 * Update the message in the status bar.
	 * @param text
	 */
	private void updateMessage(String text) {
		messageLabel.setText(text);
	}
	
	/**
	 * Update the size of the bar.
	 */
	public void updateSize() {
		setPreferredSize(new Dimension(frame.getWidth(), HEIGHT));
	}
}
