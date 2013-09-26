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

public class MyBrickusBoardPanel extends MyBrickusGrid {
	private BrickusModel model;
	private MyBrickusPieceSelectionModel selectionModel;
	private JFrame frame;
	private boolean mouseIn;
	// private MyBrickusBoardGridPanel[] boardGridPanels;
	private int height, width;
	private int cursorX, cursorY;

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
		// setPreferredSize(new Dimension(side, side));
		setLocation(10, border);
		setSize(side, side);

	}

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

	private void paintHoveringPieces() {
		if (selectionModel.getSelectedPiece() != null) {
			BrickusPiece piece = selectionModel.getSelectedPiece();
			// System.out.println("height: " + piece.getHeight());
			// System.out.println("width: " + piece.getWidth());
			for (int y = 0; y < piece.getHeight(); y++)
				for (int x = 0; x < piece.getWidth(); x++) {
					if (piece.isOccupied(x, y)) {
						// System.out.println(cursorX + x + " " + (cursorY + y));
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

	private void paintBoard() {
		paintPlacedPieces();
		if (mouseIn)
			paintHoveringPieces();
	}

	public void createBoard() {
		for (int row = 0; row < height; row++)
			for (int col = 0; col < width; col++) {
				setBorderColor(col, row, Color.black);
			}
	}

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

	public void paintComponent(Graphics g) {
		paintBoard();
		updateSize();
		super.paintComponent(g);
	}

	private class MouseHandler extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			Point point = getGridLocation(event.getX(), event.getY());
			BrickusPiece piece = selectionModel.getSelectedPiece();
			if (SwingUtilities.isLeftMouseButton(event) && piece != null
					&& point != null) {
				model.placePiece(model.getActivePlayer(), point.x, point.y, piece);
				// System.out.println("placePiece");
				repaint();
			}
			event.getComponent().getParent().dispatchEvent(event);

		}

		public void mouseEntered(MouseEvent event) {
			mouseIn = true;
		}

		public void mouseExited(MouseEvent event) {
			mouseIn = false;
			repaint();
		}

		public void mouseMoved(MouseEvent event) {
			Point point = getGridLocation(event.getX(), event.getY());
			if (selectionModel.getSelectedPiece() != null && point != null) {
				cursorX = point.x;
				cursorY = point.y;
				// System.out.println("cursorX: " + cursorX + "cursorY:" + cursorY);
				repaint();
			}
		}
	}
}
