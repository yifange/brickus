package edu.jhu.cs.gyifan1.oose;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusPiece;

public class MyBrickusPieceSelectionModel {
	private BrickusPiece selectedPiece;

	public BrickusPiece getSelectedPiece() {
		return selectedPiece;
	}

	public void setSelectedPiece(BrickusPiece piece) {
		selectedPiece = piece;
	}

	public void clearSelection() {
		selectedPiece = null;
	}

	public MyBrickusPieceSelectionModel() {
		selectedPiece = null;
	}
}
