package edu.jhu.cs.gyifan1.oose;

/**
 * The grid type. For a piece, there are two types of grids, CORNER and SIDE.
 * When checking if the piece has same color pieces connected diagonally, only the CORNER
 * grids are needed to be considered.
 * 
 * @author Yifan Ge
 *
 */
public enum GridType {
	CORNER, SIDE
}
