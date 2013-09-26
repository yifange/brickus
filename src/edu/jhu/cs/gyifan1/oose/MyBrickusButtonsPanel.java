package edu.jhu.cs.gyifan1.oose;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusIllegalMoveEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusListener;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;

public class MyBrickusButtonsPanel extends JPanel {
	private JFrame frame;
	private JButton passButton;
	private JButton verticallyFlipButton, horizontallyFlipButton,
			clockwiseRotateButton, counterClockwiseRotateButton;

	private void addButtonListeners() {
		passButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				BrickusModel model = ((MyBrickusFrame) frame).getModel();
				model.pass(model.getActivePlayer());
			}
		});

		((MyBrickusFrame) frame).getModel().addBrickusListener(
				new BrickusListener() {

					@Override
					public void modelChanged(BrickusEvent event) {
						if (event.isGameOver()) {
							passButton.setEnabled(false);
						}
					}

					@Override
					public void illegalMove(BrickusIllegalMoveEvent event) {
						// TODO Auto-generated method stub

					}
				});
	}

	public MyBrickusButtonsPanel(MyBrickusFrame frame) {
		this.frame = frame;
		passButton = new JButton("Pass");

		JPanel buttonsPanel = new JPanel(new GridLayout(2, 2));
		verticallyFlipButton = new JButton(new ImageIcon(
				"/images/pentobi-flip-vertical.png"));
		horizontallyFlipButton = new JButton("flip");
		clockwiseRotateButton = new JButton("flip");
		counterClockwiseRotateButton = new JButton("flip");
		buttonsPanel.add(verticallyFlipButton);
		buttonsPanel.add(horizontallyFlipButton);
		buttonsPanel.add(clockwiseRotateButton);
		buttonsPanel.add(counterClockwiseRotateButton);
		setLayout(new BorderLayout());
		add(passButton, BorderLayout.SOUTH);
		add(buttonsPanel, BorderLayout.NORTH);
		addButtonListeners();

	}
}
