package edu.jhu.cs.gyifan1.oose;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusPiece;
import edu.jhu.cs.oose.fall2013.brickus.iface.Player;

public class MyBrickusPiecePanel extends JPanel {
	private BrickusPiece piece;
	private Player player;
	private Color color;
	private JPanel[] pieceGridPanels; 
	private int biasX, biasY;
	private final static int LAYOUT_COLS = 5, LAYOUT_ROWS = 5;
	private MyBrickusPieceSelectionModel selectionModel;
	private BrickusModel model;
	private Set<MyBrickusPieceSelectionChangeListener> listeners;
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
	public void removeListener(MyBrickusPieceSelectionChangeListener listener) {
		listeners.remove(listener);
	}
	public void addListener(MyBrickusPieceSelectionChangeListener listener) {
		listeners.add(listener);
	}
	public void notifyPieceSelectionChanged() {
		for (MyBrickusPieceSelectionChangeListener listener : listeners) {
			listener.pieceSelectionChanged(selectionModel);
		}
	}
	public MyBrickusPiecePanel(BrickusPiece piece, Player player, BrickusModel model, MyBrickusPieceSelectionModel selectionModel) {
		
		this.piece = piece;
		this.player = player;
		this.model = model;
		this.color = MyBrickusUtils.getPlayerColor(player);
		this.selectionModel = selectionModel;
		
		listeners = new HashSet<MyBrickusPieceSelectionChangeListener>();
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
			if (player == model.getActivePlayer()) {
				selectionModel.setSelectedPiece(piece);
				notifyPieceSelectionChanged();
			}
		}
	}
}
