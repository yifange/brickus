package edu.jhu.cs.gyifan1.oose;


import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;


public class MyBrickusStatusBar extends JPanel {
	private JLabel messageLabel;
	private JFrame frame;
	public final static int HEIGHT = 20;
	public void updateSize() {
		setPreferredSize(new Dimension(frame.getWidth(), HEIGHT));
	}
	public MyBrickusStatusBar(JFrame frame) {
		this.frame = frame;
		updateSize();
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBorder(new BevelBorder(BevelBorder.LOWERED));
		messageLabel = new JLabel();
		messageLabel.setHorizontalAlignment(SwingConstants.LEFT);
		add(messageLabel);
	}
	public void updateMessage(String text) {
		messageLabel.setText(text);		
	}
}
