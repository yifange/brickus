package edu.jhu.cs.gyifan1.oose;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusPiece;

/**
 * The model for the piece selection.
 * @author yifan
 *
 */
public class MyBrickusPieceSelectionModel {
	private BrickusPiece selectedPiece;
	
	/**
	 * Initialize the model.
	 */
	public MyBrickusPieceSelectionModel() {
		clearSelection();
	}
	
	/**
	 * Clear current piece selection.
	 */
	public void clearSelection() {
		selectedPiece = null;
	}

	/**
	 * Get the currently selected piece.
	 * @return	the currently selected piece.
	 */
	public BrickusPiece getSelectedPiece() {
		return selectedPiece;
	}
	
	/**
	 * Set the current selected piece.
	 * @param piece	The currently selected piece.
	 */
	public void setSelectedPiece(BrickusPiece piece) {
		selectedPiece = piece;
	}
}
