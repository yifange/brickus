package edu.jhu.cs.gyifan1.oose;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusEvent;
import edu.jhu.cs.gyifan1.oose.GridType;

/**
 * Self implemented BrickusPiece
 * 
 * @author Yifan Ge
 *
 */
public class MyBrickusPiece implements edu.jhu.cs.oose.fall2013.brickus.iface.BrickusPiece {
	/**
	 * Initialize a piece.
	 * @param height	Height of the piece.
	 * @param width		Width of the piece.
	 * @param cornerGrids	2-d array of the corner grids.
	 * @param sideGrids		2-d array of the side grids.
	 * @param brickusModel	The {@link brickusModel} the piece to be attached to.
	 * @throws java.lang.IndexOutOfBoundsException
	 */
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
	
	/**
	 * Flips this Brickus piece horizontally.
	 */
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
	
	/**
	 * Flips this Brickus piece vertically.
	 */
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
	
	/**
	 * Determines the height of this Brickus piece's grid.
	 * 
	 * @return The height of the grid for this piece.
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Determines the width of this Bricus piece's grid.
	 * 
	 * @return The width of the grid for this piece.
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Determines whether or not this Brickus piece has a brick in the specified location of its grid.
	 * @param x	The X coordinate of this Brickus piece's grid.
	 * @param y The Y coordinate of this Brickus piece's grid.
	 * @return	<code>true</code> if this piece has a brick at that location; <code>false</code> if it does not. If the coordinate is out of bounds, the result is <code>false</code>.
	 */
	public boolean isOccupied(int x, int y) throws IndexOutOfBoundsException {
		return pieceGrid[y][x] != null;
	}
	/**
	 * if the grid of the given location is a corner grid
	 * @param x	The X coordinate of this Brickus piece's grid.
	 * @param y The Y coordinate of this Brickus piece's grid.
	 * @return	<code>true</code> if the grid is a CORNER grid.
	 * @throws IndexOutOfBoundsException
	 */
	public boolean isCorner(int x, int y) throws IndexOutOfBoundsException {
		return pieceGrid[y][x] == GridType.CORNER;
	}
	
	/**
	 * if the grid of the given location is a side grid
	 * @param x	The X coordinate of this Brickus piece's grid.
	 * @param y The Y coordinate of this Brickus piece's grid.
	 * @return	<code>true</code> if the grid is a SIDE grid.
	 * @throws IndexOutOfBoundsException
	 */
	public boolean isSide(int x, int y) throws IndexOutOfBoundsException {
		return pieceGrid[y][x] == GridType.SIDE;
	}
	/**
	 * Rotates this Brickus piece 90 degrees clockwise.
	 */
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
	/**
	 * Rotates this Brickus piece 90 degrees counter-clockwise.
	 */
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
	
	/**
	 * Notifies the model of the change of the piece.
	 */
	private void notifyModelPieceChanged() {
		brickusModel.notifyModelChanged(new BrickusEvent(brickusModel, false, false));
	}
private
	GridType[][] pieceGrid;
	int height, width;
	MyBrickusModel brickusModel;
}
