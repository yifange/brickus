package edu.jhu.cs.gyifan1.oose;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusIllegalMoveEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusListener;
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
	private JButton passButton;
	public final static int DEFAULT_WIDTH = 860, DEFAULT_HEIGHT = 500,
			MIN_WIDTH = 860, MIN_HEIGHT = 500;

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
		rightPanel.add(pieceTrayHolderPanel, BorderLayout.NORTH);
		rightPanel.add(passButton, BorderLayout.SOUTH);
		rightPanel.add(new MyBrickusScorePanel(model), BorderLayout.CENTER);
		rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		passButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				model.pass(model.getActivePlayer());
			}
		});
		add(rightPanel, BorderLayout.EAST);
	}
	private void addMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Game");
		JMenuItem newGameMenuItem = new JMenuItem("New game");
		JMenuItem exitMenuItem = new JMenuItem("Exit");
	  exitMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
	  newGameMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				newGame();
			}
		});
		menu.add(newGameMenuItem);
		menu.add(exitMenuItem);
		menuBar.add(menu);
		setJMenuBar(menuBar);
	}
	private void newGame() {
		getContentPane().removeAll();
		getContentPane().revalidate();
		getContentPane().repaint();
		model = new StandardBrickusModel();
		selectionModel = new MyBrickusPieceSelectionModel();
		statusBar = new MyBrickusStatusBar(this);
		boardPanel = new MyBrickusBoardPanel(this);
		passButton = new JButton("Pass");
		pieceTrays = new HashMap<Player, MyBrickusPieceTray>();
		pieceTrays
				.put(Player.PLAYER1, new MyBrickusPieceTray(this, Player.PLAYER1));
		pieceTrays
				.put(Player.PLAYER2, new MyBrickusPieceTray(this, Player.PLAYER2));
		addComponents();
		pack();
		MouseHandler mouseHandler = new MouseHandler();
		getContentPane().addMouseListener(mouseHandler);
		getContentPane().addMouseWheelListener(mouseHandler);
		updateComponentSize();
		model.addBrickusListener(new BrickusListener() {
			
			@Override
			public void modelChanged(BrickusEvent event) {
				if (event.isGameOver())
					passButton.setEnabled(false);
			}
			
			@Override
			public void illegalMove(BrickusIllegalMoveEvent event) {
				
			}
		});
		
	}
	public MyBrickusFrame() {
		super("Brickus");
		setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addMenu();
		newGame();
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
