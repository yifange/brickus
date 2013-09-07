package edu.jhu.cs.gyifan1.oose;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusEvent;

public class MyBrickusPiece implements edu.jhu.cs.oose.fall2013.brickus.iface.BrickusPiece {
	
	public MyBrickusPiece(int height, int width, int[][] occupiedGrids, MyBrickusModel brickusModel) throws java.lang.IndexOutOfBoundsException {
		this.height = height;
		this.width = width;
		this.pieceGrid = new boolean[height][width];
		this.brickusModel = brickusModel;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				this.pieceGrid[i][j] = false;				
			}
		}
		for (int i = 0; i < occupiedGrids.length; i++) {
			int x = occupiedGrids[i][0];
			int y = occupiedGrids[i][1];
			this.pieceGrid[x][y] = true;
		}
	}
	
	public void flipHorizontally() {
		for (int i = 0; i < height / 2; i++) {
			for (int j = 0; j < width; j++) {
				pieceGrid[i][j] = pieceGrid[i][j] ^ pieceGrid[height - i - 1][j];
				pieceGrid[height - i - 1][j] = pieceGrid[i][j] ^ pieceGrid[height - i -1][j];
				pieceGrid[i][j] = pieceGrid[i][j] ^ pieceGrid[height - i - 1][j];
			}
		}
		notifyModelPieceChanged();
	}
	public void flipVertically() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width / 2; j++) {
				pieceGrid[i][j] = pieceGrid[i][j] ^ pieceGrid[i][width - j - 1];
				pieceGrid[i][width - j - 1] = pieceGrid[i][j] ^ pieceGrid[i][width - j - 1];
				pieceGrid[i][j] = pieceGrid[i][j] ^ pieceGrid[i][width - j - 1];
			}
		}
		notifyModelPieceChanged();
	}
	public int getHeight() {
		return height;
	}
	public int getWidth() {
		return width;
	}
	public boolean isOccupied(int x, int y) throws java.lang.IndexOutOfBoundsException {
		return pieceGrid[y][x];
	}
	public void rotateClockwise() {
		height = height + width;
		width = height - width;
		height = height - width;
		boolean[][] newPieceGrid = new boolean[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				newPieceGrid[i][j] = pieceGrid[width - j - 1][i];
			}
		}
		pieceGrid = newPieceGrid;
		// leave garbage collection to do the rest
		notifyModelPieceChanged();
	}
	public void rotateCounterClockwise() {
		height = height + width;
		width = height - width;
		height = height - width;
		boolean[][] newPieceGrid = new boolean[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				newPieceGrid[i][j] = pieceGrid[j][height - i - 1];
			}
		}
		pieceGrid = newPieceGrid;
		// leave garbage collection to do the rest
		notifyModelPieceChanged();
	}
	
	private void notifyModelPieceChanged() {
		brickusModel.notifyModelChanged(new BrickusEvent(brickusModel, false, false));
	}
private
	boolean[][] pieceGrid;
	int height, width;
	MyBrickusModel brickusModel;
}
