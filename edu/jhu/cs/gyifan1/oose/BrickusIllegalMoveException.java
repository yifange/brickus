package edu.jhu.cs.gyifan1.oose;

/**
 * Illegal move exception. Contains the description of the move.
 * @author Yifan Ge
 *
 */
public class BrickusIllegalMoveException extends Exception {
	public BrickusIllegalMoveException(String message) {
		// call the constructor of the super class (Exception)
		super(message);
	}
}
