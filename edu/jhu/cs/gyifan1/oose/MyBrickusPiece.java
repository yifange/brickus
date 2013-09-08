package edu.jhu.cs.gyifan1.oose;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusEvent;
import edu.jhu.cs.gyifan1.oose.GridType;

public class MyBrickusPiece implements edu.jhu.cs.oose.fall2013.brickus.iface.BrickusPiece {
	
	public MyBrickusPiece(int height, int width, int[][] cornerGrids, int[][] sideGrids, MyBrickusModel brickusModel) throws java.lang.IndexOutOfBoundsException {
		this.height = height;
		this.width = width;
		this.pieceGrid = new GridType[height][width];
		this.brickusModel = brickusModel;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				this.pieceGrid[i][j] = null;				
			}
		}
		for (int i = 0; i < cornerGrids.length; i++) {
			int x = cornerGrids[i][0];
			int y = cornerGrids[i][1];
			this.pieceGrid[x][y] = GridType.CORNER;
		}
		for (int i = 0; i < sideGrids.length; i++) {
			int x = sideGrids[i][0];
			int y = sideGrids[i][1];
			this.pieceGrid[x][y] = GridType.SIDE;
		}
	}
	
	public void flipHorizontally() {
		for (int i = 0; i < height / 2; i++) {
			for (int j = 0; j < width; j++) {
				GridType temp = pieceGrid[i][j];
				pieceGrid[i][j] = pieceGrid[height - i - 1][j];
				pieceGrid[height - i - 1][j] = temp;
			}
		}
		notifyModelPieceChanged();
	}
	public void flipVertically() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width / 2; j++) {
				GridType temp = pieceGrid[i][j];
				pieceGrid[i][j] = pieceGrid[i][width - j - 1];
				pieceGrid[i][width - j - 1] = temp;
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
	public boolean isOccupied(int x, int y) throws IndexOutOfBoundsException {
		return pieceGrid[y][x] != null;
	}
	public boolean isCorner(int x, int y) throws IndexOutOfBoundsException {
		return pieceGrid[y][x] == GridType.CORNER;
	}
	public boolean isSide(int x, int y) throws IndexOutOfBoundsException {
		return pieceGrid[y][x] == GridType.SIDE;
	}
	
	public void rotateClockwise() {
		height = height + width;
		width = height - width;
		height = height - width;
		GridType[][] newPieceGrid = new GridType[height][width];
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
		GridType[][] newPieceGrid = new GridType[height][width];
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
	GridType[][] pieceGrid;
	int height, width;
	MyBrickusModel brickusModel;
}
