package edu.jhu.cs.gyifan1.oose;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusPiece;
import edu.jhu.cs.oose.fall2013.brickus.iface.Player;
import edu.jhu.cs.oose.fall2013.brickus.model.StandardBrickusModel;

public class MyBrickusFrame extends javax.swing.JFrame {
	private BrickusModel model;
	private MyBrickusPieceSelectionModel selectionModel;
	private MyBrickusStatusBar statusBar;
	private MyBrickusBoardPanel boardPanel;
	private Map<Player, MyBrickusPieceTray> pieceTrays;
	public final static int DEFAULT_WIDTH = 780, DEFAULT_HEIGHT = 500,
			MIN_WIDTH = 780, MIN_HEIGHT = 500;

	private void updateComponentSize() {
		boardPanel.updateSize();
		statusBar.updateSize();
	}

	public BrickusModel getModel() {
		return model;
	}

	public MyBrickusPieceSelectionModel getSelectionModel() {
		return selectionModel;
	}

	private void addComponents() {
		add(statusBar, BorderLayout.SOUTH);
		add(boardPanel, BorderLayout.WEST);
		JPanel rightPanel = new JPanel(new BorderLayout());
		JPanel pieceTrayHolderPanel = new JPanel(new BorderLayout());
		pieceTrayHolderPanel
				.add(pieceTrays.get(Player.PLAYER1), BorderLayout.NORTH);
		pieceTrayHolderPanel
				.add(pieceTrays.get(Player.PLAYER2), BorderLayout.SOUTH);
		JPanel pieceDisplayButtonsHolderPanel = new JPanel(new BorderLayout());
		JPanel buttonsPanel = new MyBrickusButtonsPanel(this);
		// pieceDisplayButtonsHolderPanel.add(new MyBrickusPieceDisplayPanel(),
		// BorderLayout.WEST);
		pieceDisplayButtonsHolderPanel.add(buttonsPanel, BorderLayout.EAST);
		rightPanel.add(pieceDisplayButtonsHolderPanel, BorderLayout.NORTH);
		rightPanel.add(pieceTrayHolderPanel, BorderLayout.SOUTH);
		add(rightPanel, BorderLayout.EAST);
	}

	public MyBrickusFrame() {
		super("Brickus");
		model = new StandardBrickusModel();
		selectionModel = new MyBrickusPieceSelectionModel();
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		statusBar = new MyBrickusStatusBar(this);
		boardPanel = new MyBrickusBoardPanel(this);
		pieceTrays = new HashMap<Player, MyBrickusPieceTray>();
		pieceTrays
				.put(Player.PLAYER1, new MyBrickusPieceTray(this, Player.PLAYER1));
		pieceTrays
				.put(Player.PLAYER2, new MyBrickusPieceTray(this, Player.PLAYER2));
		setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addComponents();
		pack();
		// model.addBrickusListener(new BrickusListener() {
		//
		// @Override
		// public void modelChanged(BrickusEvent event) {
		// if (event.isGameOver()) {
		// passButton.setEnabled(false);
		// }
		// }
		//
		// @Override
		// public void illegalMove(BrickusIllegalMoveEvent event) {
		// // TODO Auto-generated method stub
		//
		// }
		// });
		MouseHandler mouseHandler = new MouseHandler();
		getContentPane().addMouseListener(mouseHandler);
		getContentPane().addMouseWheelListener(mouseHandler);
		updateComponentSize();
	}

	private class MouseHandler extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			BrickusPiece piece = selectionModel.getSelectedPiece();
			if (piece != null && SwingUtilities.isRightMouseButton(event))
				if (event.isShiftDown()) {
					piece.flipHorizontally();
				} else {
					piece.flipVertically();
				}
		}

		public void mouseWheelMoved(MouseWheelEvent event) {
			BrickusPiece piece = selectionModel.getSelectedPiece();
			if (piece != null) {
				if (event.getUnitsToScroll() > 1)
					piece.rotateClockwise();
				else if (event.getUnitsToScroll() < -1)
					piece.rotateCounterClockwise();
			}
		}
	}
}
