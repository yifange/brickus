package edu.jhu.cs.gyifan1.oose;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JPanel;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusIllegalMoveEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusListener;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusPiece;
import edu.jhu.cs.oose.fall2013.brickus.iface.Player;

/**
 * The tray panel to holds all the pieces for one player.
 * 
 * @author yifan
 *
 */
public class MyBrickusPieceTray extends JPanel {
	public final static int LAYOUT_ROWS = 3, LAYOUT_COLS = 7;
	private Color color;
	private MyBrickusFrame frame;
	private MyBrickusPiecePanel[] piecePanels;
	private Player player;

	private MyBrickusPieceSelectionModel selectionModel;
	
	/**
	 * Initialize the piece tray.
	 * @param frame
	 * @param player
	 */
	public MyBrickusPieceTray(MyBrickusFrame frame, Player player) {
		this.frame = frame;
		this.selectionModel = frame.getSelectionModel();
		this.player = player;
		color = MyBrickusUtils.getPlayerColor(player);
		piecePanels = new MyBrickusPiecePanel[LAYOUT_ROWS * LAYOUT_COLS];
		updateSize();
		// Set to grid layout
		setLayout(new GridLayout(LAYOUT_ROWS, LAYOUT_COLS));
		frame.getModel().addBrickusListener(new BrickusListener() {

			@Override
			public void illegalMove(BrickusIllegalMoveEvent arg0) {
				// Nothing to do.
			}

			@Override
			public void modelChanged(BrickusEvent event) {
				// When the brickus model changes, redraw all the pieces.
				redrawPieces();
				// If player changes or the game is over, clear the piece selection.
				if (event.isPlayerChanged() || event.isGameOver())
					selectionModel.clearSelection();
				repaint();
			}
		});
		redrawPieces();
	}

	/**
	 * Paint the tray. Highlight selected.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		updateSize();
		updateSelected();
	}
	
	/**
	 * Redraw all the pieces.
	 */
	private void redrawPieces() {
		BrickusModel model = frame.getModel();
		List<BrickusPiece> pieces = model.getPieces(player);
		removeAll();
		revalidate();
		for (int i = 0; i < pieces.size(); i++) {
			BrickusPiece piece = pieces.get(i);
			piecePanels[i] = new MyBrickusPiecePanel(piece, player, frame.getModel(),
					selectionModel);
			add(piecePanels[i]);
			piecePanels[i].addListener(new MyBrickusPieceSelectionChangeListener() {
				@Override
				public void pieceSelectionChanged(
						MyBrickusPieceSelectionModel selectionModel) {
					repaint();
				}
			});
		}
		// Add empty panels for padding.
		for (int i = 0; i < LAYOUT_ROWS * LAYOUT_COLS - pieces.size(); i++) {
			add(new JPanel());
		}
	}
	
	/**
	 * Highlight the selected pieces.
	 */
	private void updateSelected() {
		BrickusModel model = frame.getModel();
		List<BrickusPiece> pieces = model.getPieces(player);
		for (int i = 0; i < pieces.size(); i++) {
			piecePanels[i].repaint();
		}
	}
	/**
	 * Update the size according to the window size.
	 */
	public void updateSize() {
		int frameWidth = frame.getWidth();
		int width = (int) (frameWidth * 0.4);
		setPreferredSize(new Dimension(width,
				width * LAYOUT_ROWS / LAYOUT_COLS));
	}
}
