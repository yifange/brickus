package edu.jhu.cs.gyifan1.oose;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusPiece;

public class MyBrickusPieceSelectionModel {
	BrickusPiece selectedPiece;
	public BrickusPiece getSelectedPiece() {
		return selectedPiece;
	}
	public void setSelectedPiece(BrickusPiece piece) {
		selectedPiece = piece;
	}
	public MyBrickusPieceSelectionModel() {
		selectedPiece = null;
	}
}