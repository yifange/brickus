package edu.jhu.cs.gyifan1.oose;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusPiece;

public class MyBrickusPiecePanel extends JPanel {
	private BrickusPiece piece;
	private Color color;
	private JPanel[] pieceGridPanels; 
	private int biasX, biasY;
	private final static int LAYOUT_COLS = 5, LAYOUT_ROWS = 5;
	private MyBrickusPieceSelectionModel selectionModel;
	private List<MyBrickusPieceSelectionChangeListener> listeners;
	public BrickusPiece getPiece() {
		return piece;
	}
	private void draw() {
		removeAll();
		revalidate();
		for (int row = 0; row < LAYOUT_ROWS; row++)
			for (int col = 0; col < LAYOUT_COLS; col++) {
				JPanel gridPanel = pieceGridPanels[row * LAYOUT_COLS + col];
				gridPanel.setBackground(color);
				if (piece.isOccupied(col - biasX, row - biasY)) {
					gridPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
					gridPanel.setOpaque(true);
				} else {
					gridPanel.setBorder(BorderFactory.createEmptyBorder());
					gridPanel.setOpaque(false);
				}
				add(gridPanel);
			}
	}
	public void addListeners(MyBrickusPieceSelectionChangeListener listener) {
		
	}
	
	public MyBrickusPiecePanel(BrickusPiece piece, Color color, MyBrickusPieceSelectionModel selectionModel) {
		
		this.piece = piece;
		this.color = color;
		this.selectionModel = selectionModel;
		
		biasX = (LAYOUT_COLS - piece.getWidth()) / 2;
		biasY = (LAYOUT_ROWS - piece.getHeight()) / 2;
		pieceGridPanels = new JPanel[LAYOUT_COLS * LAYOUT_ROWS];
		setLayout(new GridLayout(LAYOUT_ROWS, LAYOUT_COLS));
		addMouseListener(new MouseHandler());
		for (int row = 0; row < LAYOUT_ROWS; row++)
			for (int col = 0; col < LAYOUT_COLS; col++) {
				JPanel gridPanel = new JPanel();
				pieceGridPanels[row * LAYOUT_COLS + col] = gridPanel;
			}
		draw();	
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw();
	}
	
	private class MouseHandler extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			selectionModel.setSelectedPiece(piece);
			//TODO: selectedPieceChanged
		}
	}
}
