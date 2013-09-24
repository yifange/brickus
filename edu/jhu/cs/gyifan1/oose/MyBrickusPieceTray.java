package edu.jhu.cs.gyifan1.oose;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusPiece;
import edu.jhu.cs.oose.fall2013.brickus.iface.Player;

public class MyBrickusPieceTray extends JPanel {
	private BrickusModel model;
	private JFrame frame;
	private MyBrickusPiecePanel[] piecePanels;
	private Player player;
	private Color color;
	
	public final static int LAYOUT_ROWS = 3, LAYOUT_COLS = 7;
	public void updateSize() {
		setPreferredSize(new Dimension(350, 150));
	}
	private void draw() {
		List<BrickusPiece> pieces = model.getPieces(player);
		removeAll();
		revalidate();
		for (int i = 0; i < LAYOUT_ROWS * LAYOUT_COLS; i++) {
			BrickusPiece piece = pieces.get(i);
			piecePanels[i] = new MyBrickusPiecePanel(piece, color);
			add(piecePanels[i]);
		}
	}
	public MyBrickusPieceTray(BrickusModel model, JFrame frame, Player player) {
		this.model = model;
		this.frame = frame;
		this.player = player;
		if (player == Player.PLAYER1)
			color = Color.red;
		else
			color = Color.blue;
		piecePanels = new MyBrickusPiecePanel[LAYOUT_ROWS * LAYOUT_COLS];
		updateSize();
		setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(20, 20, 20, 20), new BevelBorder(BevelBorder.LOWERED)));
		setLayout(new GridLayout(LAYOUT_ROWS, LAYOUT_COLS));
		draw();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw();
	}
}
