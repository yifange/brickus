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

/**
 * The panel holding one Brickus piece.
 * @author yifan
 *
 */
public class MyBrickusPiecePanel extends MyBrickusGrid {
	private class MouseHandler extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent event) {
			if (player == model.getActivePlayer()) {
				selectionModel.setSelectedPiece(piece);
				notifyPieceSelectionChanged();
			}
		}
	}
	private final static int LAYOUT_COLS = 5, LAYOUT_ROWS = 5; // The layout height and width, which are 5
	private int biasX, biasY; // Used to make the piece shown in the center of the panel.
	private Set<MyBrickusPieceSelectionChangeListener> listeners;
	private BrickusModel model;
	private BrickusPiece piece;
	private Player player;

	private MyBrickusPieceSelectionModel selectionModel;
	
	/**
	 * Initialize a piece panel.
	 * 
	 * @param piece		The piece in the panel.
	 * @param player	Which player this piece belongs to.
	 * @param model		The {@link BrickusModel}
	 * @param selectionModel The {@link MyBrickusPieceSelectionModel}
	 */
	public MyBrickusPiecePanel(BrickusPiece piece, Player player,
			BrickusModel model, MyBrickusPieceSelectionModel selectionModel) {
		super(LAYOUT_COLS, LAYOUT_ROWS);
		this.piece = piece;
		this.player = player;
		this.model = model;
		this.selectionModel = selectionModel;

		listeners = new HashSet<MyBrickusPieceSelectionChangeListener>();
		biasX = (LAYOUT_COLS - piece.getWidth()) / 2;
		biasY = (LAYOUT_ROWS - piece.getHeight()) / 2;
		addMouseListener(new MouseHandler());
	}
	
	/**
	 * Add listener to the piece panel.
	 * @param listener		The listener to be added.
	 */
	public void addListener(MyBrickusPieceSelectionChangeListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * Draw the piece. Highlight the piece if selected.
	 */
	private void draw() {
		boolean selected = (piece == selectionModel.getSelectedPiece());
		boolean active = (player == model.getActivePlayer());
		for (int row = 0; row < LAYOUT_ROWS; row++)
			for (int col = 0; col < LAYOUT_COLS; col++) {
				setBorderColor(col, row, Color.black);
				if (piece.isOccupied(col - biasX, row - biasY)) {
					Color color;
					if (active)
						color = MyBrickusUtils.getPlayerColor(player);
					else
						color = MyBrickusUtils.getDisabledColor();
					setFillColor(col, row, color);
				} else {
					if (selected)
						setFillColor(col, row, Color.yellow);
					else
						setFillColor(col, row, Color.white);

				}
			}
	}
	
	/**
	 * Get the piece the panel holds.
	 * @return	the piece the panel holds.
	 */
	public BrickusPiece getPiece() {
		return piece;
	}
	
	/**
	 * Fire an event, notifying the piece selection has changed.
	 */
	public void notifyPieceSelectionChanged() {
		for (MyBrickusPieceSelectionChangeListener listener : listeners) {
			listener.pieceSelectionChanged(selectionModel);
		}
	}
	
	/**
	 * Paint the piece panel.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw();
	}
	
	/**
	 * Remove a certain listener.
	 * @param listener		The listener to be removed.
	 */
	public void removeListener(MyBrickusPieceSelectionChangeListener listener) {
		listeners.remove(listener);
	}
}
