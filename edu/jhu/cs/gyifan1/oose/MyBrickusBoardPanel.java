package edu.jhu.cs.gyifan1.oose;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusPiece;

public class MyBrickusBoardPanel extends JPanel {
	private BrickusModel model;
	private MyBrickusPieceSelectionModel selectionModel;
	private JFrame frame;
	private MyBrickusBoardGridPanel[] boardGridPanels;
	private int height, width;
	private boolean mouseInBoard;
	public void updateSize() {
		int frameHeight = frame.getHeight();
		int frameWidth = frame.getWidth();
		int statusBarHeight = MyBrickusStatusBar.HEIGHT;
		int height = frameHeight - statusBarHeight;
		int width = (int)(frameWidth * 0.6);
		int side = height;
		int border = 0;
		if (width < height) {
			border = height - width;
			side = width;
		}
		setBorder(BorderFactory.createEmptyBorder((int)(border / 2), 0, (int)(border / 2), 0));
		setPreferredSize(new Dimension(side, side));
		
	}
	public void paintBoard() {
		for (int row = 0; row < height; row++)
			for (int col = 0; col < width; col++) {
				MyBrickusBoardGridPanel grid = boardGridPanels[row * width + col];
				if (model.getContents(col, row) != null) {
					grid.setOccupied(model.getContents(col, row));
				} else {
					grid.setUnoccupied();
				}
			}
	}
	public void createBoard() {
		for (int row = 0; row < height; row++)
			for (int col = 0; col < width; col++) {
				MyBrickusBoardGridPanel grid = new MyBrickusBoardGridPanel(col, row);
				boardGridPanels[row * width + col] = grid;
				
				grid.addListener(new MyBrickusMouseLocationListener() {
					@Override
					public void mouseEnteredLocation(int x, int y) {
//						System.out.println(mouseInBoard);
						if (mouseInBoard) {
							
							BrickusPiece piece = selectionModel.selectedPiece;
							for (int i = 0; i < piece.getHeight(); y++)
								for (int j = 0; j < piece.getWidth(); x++) {
									if (piece.isOccupied(j, x) && model.getContents(x + j, y + i) == null)
										boardGridPanels[(y + i) * width + (x + j)].setHovered(model.getActivePlayer());
								}
						}
						repaint();
					}

					@Override
					public void mouseClickedLocation(int x, int y) {
						model.placePiece(model.getActivePlayer(), x, y, selectionModel.getSelectedPiece());
					}
				});
				add(grid);
			}
		
	}
	public MyBrickusBoardPanel(MyBrickusFrame frame) {
		this.frame = frame;
		this.model = frame.getModel();
		this.selectionModel = frame.getSelectionModel();
		height = model.getHeight();
		width = model.getWidth();
		GridLayout gridLayout = new GridLayout(height, width);
		setLayout(gridLayout);
		boardGridPanels = new MyBrickusBoardGridPanel[height * width];
		createBoard();
		addMouseListener(new MouseHandler());
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		updateSize();
		paintBoard();
	}
	
	
	
	
	private class MouseHandler extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			System.out.println("clicked");
		}
		public void mouseMoved(MouseEvent event) {
			System.out.println("moving");
		}
		public void mouseEntered(MouseEvent event) {
			mouseInBoard = true;
			System.out.println("in");
			repaint();
		}
		public void mouseExited(MouseEvent event) {
			mouseInBoard = false;
			System.out.println("out");
			repaint();
		}
	}
}
