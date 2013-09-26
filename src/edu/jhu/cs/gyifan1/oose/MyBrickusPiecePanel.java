package edu.jhu.cs.gyifan1.oose;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusPiece;
import edu.jhu.cs.oose.fall2013.brickus.iface.Player;

public class MyBrickusPiecePanel extends MyBrickusGrid {
	private BrickusPiece piece;
	private Player player;
	private Color color;
	private int biasX, biasY;
	private final static int LAYOUT_COLS = 5, LAYOUT_ROWS = 5;
	private MyBrickusPieceSelectionModel selectionModel;
	private BrickusModel model;
	private Set<MyBrickusPieceSelectionChangeListener> listeners;

	public BrickusPiece getPiece() {
		return piece;
	}

	private void draw() {
		boolean selected = (piece == selectionModel.getSelectedPiece());
		for (int row = 0; row < LAYOUT_ROWS; row++)
			for (int col = 0; col < LAYOUT_COLS; col++) {
				setBorderColor(col, row, Color.black);
				setFillColor(col, row, MyBrickusUtils.getPlayerColor(player));
				if (piece.isOccupied(col - biasX, row - biasY)) {
					setFillColor(col, row, MyBrickusUtils.getPlayerColor(player));
				} else {
					if (selected)
						setFillColor(col, row, Color.yellow);
					else
						setFillColor(col, row, Color.white);

				}
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

	public MyBrickusPiecePanel(BrickusPiece piece, Player player,
			BrickusModel model, MyBrickusPieceSelectionModel selectionModel) {
		super(LAYOUT_COLS, LAYOUT_ROWS);
		this.piece = piece;
		this.player = player;
		this.model = model;
		this.color = MyBrickusUtils.getPlayerColor(player);
		this.selectionModel = selectionModel;

		listeners = new HashSet<MyBrickusPieceSelectionChangeListener>();
		biasX = (LAYOUT_COLS - piece.getWidth()) / 2;
		biasY = (LAYOUT_ROWS - piece.getHeight()) / 2;
		// setLayout(new GridLayout(LAYOUT_ROWS, LAYOUT_COLS));
		addMouseListener(new MouseHandler());
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
