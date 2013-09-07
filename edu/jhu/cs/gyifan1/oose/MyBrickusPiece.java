package edu.jhu.cs.gyifan1.oose;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusEvent;

public class MyBrickusPiece {
	
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
	
	void flipHorizontally() {
		for (int i = 0; i < height / 2; i++) {
			for (int j = 0; j < width; j++) {
				boolean temp;
				temp = pieceGrid[i][j];
				pieceGrid[i][j] = pieceGrid[height - i][j];
				pieceGrid[height - i][j] = temp;
			}
		}
		notifyModelPieceChanged();
	}
	void flipVertically() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width / 2; j++) {
				boolean temp;
				temp = pieceGrid[i][j];
				pieceGrid[i][j] = pieceGrid[i][width - j];
				pieceGrid[i][width - j] = temp;
			}
		}
		notifyModelPieceChanged();
	}
	int getHeight() {
		return height;
	}
	int getWidth() {
		return width;
	}
	boolean isOccupied(int x, int y) throws java.lang.IndexOutOfBoundsException {
		return pieceGrid[x][y];
	}
	void rotateClockwise() {
		// XXX
		notifyModelPieceChanged();
	}
	void rotateCounterClockwise() {
		// XXX
		notifyModelPieceChanged();
	}
private
	boolean[][] pieceGrid;
	int height, width;
	MyBrickusModel brickusModel;
	void notifyModelPieceChanged() {
		brickusModel.notifyModelChanged(new BrickusEvent(brickusModel, false, false));
	}
}
