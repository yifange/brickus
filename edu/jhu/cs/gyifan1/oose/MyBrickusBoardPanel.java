package edu.jhu.cs.gyifan1.oose;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusPiece;

public class MyBrickusBoardPanel extends MyBrickusGrid {
	private BrickusModel model;
	private MyBrickusPieceSelectionModel selectionModel;
	private JFrame frame;
//	private MyBrickusBoardGridPanel[] boardGridPanels;
	private int height, width;
	private boolean mouseInBoard;
	public void updateSize() {
		int frameHeight = frame.getContentPane().getHeight();
		int frameWidth = frame.getContentPane().getWidth();
		int statusBarHeight = MyBrickusStatusBar.HEIGHT;
		int height = frameHeight - statusBarHeight;
		int width = (int)(frameWidth * 0.6);
		int side = height;
		if (width < height) {
			side = width;
		}
		setPreferredSize(new Dimension(side, side));
		setSize(side, side);
		
	}
	public void paintBoard() {
		for (int row = 0; row < height; row++)
			for (int col = 0; col < width; col++) {
				if (model.getContents(col, row) != null) {
					setFillColor(col, row, MyBrickusUtils.getPlayerColor(model.getContents(col, row)));
				} else {
					setFillColor(col, row, null);
				}
			}
	}
	public void createBoard() {
		for (int row = 0; row < height; row++) 
			for (int col = 0; col < width ; col++) {
				setBorderColor(col, row, Color.black);
			}
	}
	public MyBrickusBoardPanel(MyBrickusFrame frame) {
		super(frame.getModel().getWidth(), frame.getModel().getHeight());
//		setPadding(20, 20, 20, 20);
		this.frame = frame;
		model = frame.getModel();
		selectionModel = frame.getSelectionModel();
		height = model.getHeight();
		width = model.getWidth();
		createBoard();
		addMouseListener(new MouseHandler());
	}
	public void paintComponent(Graphics g) {
		paintBoard();
		updateSize();
		super.paintComponent(g);
	}
	
	
	
	
	private class MouseHandler extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			Point point = getGridLocation(event.getX(), event.getY());
			BrickusPiece piece = selectionModel.getSelectedPiece();
			if (piece != null) {
				model.placePiece(model.getActivePlayer(), point.x, point.y, piece);
				System.out.println("placePiece");
				repaint();
			}
		}
		public void mouseEntered(MouseEvent event) {
			mouseInBoard = true;
			System.out.println("in");
			repaint();
		}
		public void mouseExited(MouseEvent event) {
			mouseInBoard = false;
			System.out.println("out");
			repaint();
		}
	}
}
