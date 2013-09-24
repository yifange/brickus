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
	private MyBrickusPieceSelectionModel selectionModel;
	
	public final static int LAYOUT_ROWS = 3, LAYOUT_COLS = 7;
	public void updateSize() {
		int frameWidth = frame.getWidth();
		int width = (int)(frameWidth * 0.4);
		System.out.println("updateSize");
		setPreferredSize(new Dimension(width, (int)(width * LAYOUT_ROWS / LAYOUT_COLS)));
	}
	private void draw() {
		List<BrickusPiece> pieces = model.getPieces(player);
		removeAll();
		revalidate();
		for (int i = 0; i < LAYOUT_ROWS * LAYOUT_COLS; i++) {
			BrickusPiece piece = pieces.get(i);
			piecePanels[i] = new MyBrickusPiecePanel(piece, color, selectionModel);
			if (model.getActivePlayer() == player && selectionModel.getSelectedPiece() == piece) {
				piecePanels[i].setBackground(Color.gray);
			}
			add(piecePanels[i]);
		}
	}
	public MyBrickusPieceTray(MyBrickusFrame frame, Player player) {
		this.frame = frame;
		this.model = frame.getModel();
		this.selectionModel = frame.getSelectionModel();
		this.player = player;
		color = MyBrickusUtilities.getPlayerColor(player);
		piecePanels = new MyBrickusPiecePanel[LAYOUT_ROWS * LAYOUT_COLS];
		updateSize();
		setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(20, 20, 20, 20), new BevelBorder(BevelBorder.LOWERED)));
		setLayout(new GridLayout(LAYOUT_ROWS, LAYOUT_COLS));
		draw();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		updateSize();
		draw();
	}
}