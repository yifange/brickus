package edu.jhu.cs.gyifan1.oose;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;

public class MyBrickusBoardPanel extends JPanel {
	private BrickusModel model;
	private JFrame frame;
	private MyBrickusBoardGridPanel[] boardGridPanels;
	private int height, width;
	public void updateSize() {
		int frameHeight = frame.getHeight();
		int frameWidth = frame.getWidth();
		int statusBarHeight = MyBrickusStatusBar.HEIGHT;
		int height = frameHeight - statusBarHeight;
		int width = (int)(frameWidth * 0.6);
		int side = height;
		int border = 0;
		if (width < height) {
			border = height - width;
			side = width;
		}
		setBorder(BorderFactory.createEmptyBorder((int)(border / 2), 0, (int)(border / 2), 0));
		setPreferredSize(new Dimension(side, side));
		
	}
	public void paintBoard() {
		for (int row = 0; row < height; row++)
			for (int col = 0; col < width; col++) {
				MyBrickusBoardGridPanel grid = new MyBrickusBoardGridPanel(col, row);
				grid.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
				boardGridPanels[row * width + col] = grid;  
				add(grid);
			}
	}
	public MyBrickusBoardPanel(MyBrickusFrame frame) {
		this.frame = frame;
		this.model = frame.getModel();
		height = model.getHeight();
		width = model.getWidth();
		GridLayout gridLayout = new GridLayout(height, width);
		setLayout(gridLayout);
		boardGridPanels = new MyBrickusBoardGridPanel[height * width];
		System.out.println(getHeight());
		paintBoard();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		updateSize();
	}
}
