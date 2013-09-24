package edu.jhu.cs.gyifan1.oose;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;

public class MyBrickusBoardPanel extends JPanel {
	private BrickusModel model;
	private JFrame frame;
	private JPanel[] boardGridPanels;
	private int height, width;
	public void updateSize() {
		int frameHeight = frame.getHeight();
		int statusBarHeight = MyBrickusStatusBar.HEIGHT;
		setPreferredSize(new Dimension(frameHeight - statusBarHeight, frameHeight - statusBarHeight));
		
	}
	public void paintBoard() {
		for (int row = 0; row < height; row++)
			for (int col = 0; col < width; col++) {
				JPanel grid = new JPanel();
				grid.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
				boardGridPanels[row * width + col] = grid;  
				add(grid);
			}
	}
	public MyBrickusBoardPanel(BrickusModel model, JFrame frame) {
		this.model = model;
		this.frame = frame;
		height = model.getHeight();
		width = model.getWidth();
		GridLayout gridLayout = new GridLayout(height, width);
		setLayout(gridLayout);
		boardGridPanels = new JPanel[height * width];
		System.out.println(getHeight());
		paintBoard();
	}
}
