package edu.jhu.cs.gyifan1.oose;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;


import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;
import edu.jhu.cs.oose.fall2013.brickus.iface.Player;
import edu.jhu.cs.oose.fall2013.brickus.model.StandardBrickusModel;

public class MyBrickusFrame extends javax.swing.JFrame {
	private BrickusModel model;
	private MyBrickusPieceSelectionModel selectionModel;
	private MyBrickusStatusBar statusBar;
	private MyBrickusBoardPanel boardPanel;
	private Map<Player, MyBrickusPieceTray> pieceTrays;
	public final static int DEFAULT_WIDTH = 780, DEFAULT_HEIGHT = 500, MIN_WIDTH = 780, MIN_HEIGHT = 500;

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
		add(boardPanel, BorderLayout.CENTER);
		JPanel rightPanel = new JPanel(new BorderLayout());
		JPanel pieceTrayHolderPanel = new JPanel(new BorderLayout());
		pieceTrayHolderPanel.add(pieceTrays.get(Player.PLAYER1), BorderLayout.NORTH);
		pieceTrayHolderPanel.add(pieceTrays.get(Player.PLAYER2), BorderLayout.SOUTH);
		rightPanel.add(pieceTrayHolderPanel, BorderLayout.NORTH);
		add(rightPanel, BorderLayout.EAST);
	}
	public MyBrickusFrame() {
		super("Brickus");
		this.model = new StandardBrickusModel();
		this.selectionModel = new MyBrickusPieceSelectionModel();
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		statusBar = new MyBrickusStatusBar(this);
		boardPanel = new MyBrickusBoardPanel(this);
		pieceTrays = new HashMap<Player, MyBrickusPieceTray>();
		pieceTrays.put(Player.PLAYER1, new MyBrickusPieceTray(this, Player.PLAYER1));
		pieceTrays.put(Player.PLAYER2, new MyBrickusPieceTray(this, Player.PLAYER2));
		setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addComponents();
		pack();
		updateComponentSize();
	}
}
