package edu.jhu.cs.gyifan1.oose;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusPiece;
/**
 * The panel holding the Brickus game board.
 * @author yifan
 *
 */
public class MyBrickusBoardPanel extends MyBrickusGrid {
	private class MouseHandler extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent event) {
			Point point = getGridLocation(event.getX(), event.getY());
			BrickusPiece piece = selectionModel.getSelectedPiece();
			// left click?
			if (SwingUtilities.isLeftMouseButton(event) && piece != null
					&& point != null) {
				// place piece and repaint the board
				model.placePiece(model.getActivePlayer(), point.x, point.y, piece);
				repaint();
			}
			event.getComponent().getParent().dispatchEvent(event);

		}

		@Override
		public void mouseEntered(MouseEvent event) {
			mouseIn = true;
		}

		@Override
		public void mouseExited(MouseEvent event) {
			mouseIn = false;
			repaint();
		}

		@Override
		public void mouseMoved(MouseEvent event) {
			Point point = getGridLocation(event.getX(), event.getY());
			// if the user has selected some piece and the mouse is in the board, get the grid location of the mouse
			if (selectionModel.getSelectedPiece() != null && point != null) {
				cursorX = point.x;
				cursorY = point.y;
				repaint();
			}
		}
	}
	private int cursorX, cursorY; // the grid location of the mouse
	private JFrame frame;
	private int height, width; // height/width of the board
	private BrickusModel model;
	private boolean mouseIn; // whether mouse in the board

	private MyBrickusPieceSelectionModel selectionModel;
	
	/**
	 * Initialize the board. 
	 * @param frame		The main frame
	 */
	public MyBrickusBoardPanel(MyBrickusFrame frame) {
		super(frame.getModel().getWidth(), frame.getModel().getHeight());
		this.frame = frame;
		model = frame.getModel();
		selectionModel = frame.getSelectionModel();
		height = model.getHeight();
		width = model.getWidth();
		createBoard();
		MouseHandler mouseHandler = new MouseHandler();
		addMouseListener(mouseHandler);
		addMouseMotionListener(mouseHandler);
	}
	/**
	 * Draw an empty board.
	 */
	public void createBoard() {
		for (int row = 0; row < height; row++)
			for (int col = 0; col < width; col++) {
				setBorderColor(col, row, Color.black);
			}
	}
	
	/**
	 * Paint the placed pieces & the piece to be placed.
	 */
	private void paintBoard() {
		paintPlacedPieces();
		if (mouseIn)
			paintHoveringPieces();
	}
	
	/**
	 * Paint the board and update size of the board.
	 */
	@Override
	public void paintComponent(Graphics g) {
		paintBoard();
		updateSize();
		super.paintComponent(g);
	}
	
	/**
	 * Paint the piece to placed at the position of the mouse cursor.
	 */
	private void paintHoveringPieces() {
		if (selectionModel.getSelectedPiece() != null) {
			BrickusPiece piece = selectionModel.getSelectedPiece();
			for (int y = 0; y < piece.getHeight(); y++)
				for (int x = 0; x < piece.getWidth(); x++) {
					if (piece.isOccupied(x, y)) {
						Color color;
						Color currentColor = getFillColor(cursorX + x, cursorY + y);
						if (currentColor != Color.white
								&& currentColor != MyBrickusUtils.getPlayerColor(model
										.getActivePlayer()))
							color = MyBrickusUtils.getOverlappingColor();
						else
							color = MyBrickusUtils.getPlayerTransparentColor(model
									.getActivePlayer());
						setFillColor(cursorX + x, cursorY + y, color);
					}
				}
		}
	}
	
	/**
	 * Paint placed pieces on the board.
	 */
	private void paintPlacedPieces() {
		for (int row = 0; row < height; row++)
			for (int col = 0; col < width; col++) {
				if (model.getContents(col, row) != null) {
					setFillColor(col, row,
							MyBrickusUtils.getPlayerColor(model.getContents(col, row)));
				} else {
					setFillColor(col, row, Color.white);
				}
			}
	}
	
	/**
	 * Update the size of board according to the size of the window.
	 */
	public void updateSize() {
		int frameHeight = frame.getContentPane().getHeight();
		int frameWidth = frame.getContentPane().getWidth();
		int statusBarHeight = MyBrickusStatusBar.HEIGHT;
		int height = frameHeight - statusBarHeight;
		int width = (int) ((frameWidth - 60) * 0.6);
		int side = height;
		int border = 0;
		if (width < height) {
			side = width;
			border = (height - width) / 2;
		}
		setLocation(10, border);
		setSize(side, side);

	}
}
