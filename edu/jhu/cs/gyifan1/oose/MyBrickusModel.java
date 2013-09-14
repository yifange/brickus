package edu.jhu.cs.gyifan1.oose;
import java.util.ArrayList;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import edu.jhu.cs.oose.fall2013.brickus.iface.*;
import edu.jhu.cs.gyifan1.oose.BrickusIllegalMoveException;

/**
 * Self implemented Brickus model
 * @author	Yifan Ge
 */
public class MyBrickusModel implements BrickusModel {
	/**
	 * Constructor to initialize the Brickus model instance.
	 */
	public MyBrickusModel() {
		brickusListeners = new ArrayList<BrickusListener>();
		activePlayer = Player.PLAYER1;
		height = 16; 
		width = 16;
		board = new Player[height][width];
		
		// Use a hashmap to store the pieces of the two players
		pieces = new HashMap<Player, List<BrickusPiece>>();
		pieces.put(Player.PLAYER1, brickusPieceSet());
		pieces.put(Player.PLAYER2, brickusPieceSet());
		
		// Use a hashmap to store the scores of the two players
		scores = new HashMap<Player, Integer>();
		scores.put(Player.PLAYER1, 0);
		scores.put(Player.PLAYER2, 0);
	}
	/**
	 * Adds a listener to this {@link BrickusModel}. 
	 * The provided listener will be called whenever pertinent events are generated on the model
	 * 
	 * @param listener	The {@link BrickusListener} to add to this {@link BrickusModel}
	 */
	public void addBrickusListener(BrickusListener listener) {
		for (Iterator<BrickusListener> i = brickusListeners.iterator(); i.hasNext();) {
			BrickusListener currentListener = (BrickusListener)i.next();
			// check if this listener has been added to the model
			if (currentListener == listener) {
				return;
			}
		}
		brickusListeners.add(listener);
	}
	/**
	 * Calculates the score for the provided player.
	 * The player's score is equal to the number of squares he or she has placed on the board.
	 * Note that this should be the number of squares, not the number of pieces; that is,
	 * the plus-shaped piece would be worth five points.
	 * 
	 * @param player	The player for whom to calculate a score.
	 * 
	 * @return				The player's current score.
	 */
	public int calculateScore(Player player) {
		return scores.get(player);
	}
	
	/**
	 * Indicate the player whose turn it is. If the game is over, this method returns <code>null</code>.
	 * 
	 * @return				The player who is currently moving.
	 */
	public Player getActivePlayer() {
		return activePlayer;
	}
	
	/**
	 * Determines the contents of the cell with the given location.
	 * 
	 * @param	x				The X coordinate of the location.
	 * @param y				The Y coordinate of the location.
	 * 
	 * @return				The contents of that cell in terms of the player who owns a brick there.
	 * 								If no brick is at that location, <code>null</code> is returned.
	 * 
	 * @throws java.lang.IndexOutOfBoundsExcepton	If the provided X or Y coordinate is out of range.
	 */
	public Player getContents(int x, int y) throws IndexOutOfBoundsException {
		return board[y][x];
	}
	
	/**
	 * Establishes the height of the Brickus board.
	 * 
	 * @return			The height of the Brickus board.	
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Determines which pieces the specified player has remaining. 
	 * The list returned by this method cannot be modified by the caller. 
	 * 
	 * @param player	An object identifying the player in question.
	 * 
	 * @return 				The pieces the player has yet to place.
	 */
	public List<BrickusPiece> getPieces(Player player) {
		return Collections.unmodifiableList(pieces.get(player));
	}
	
	/**
	 * Establishes the width of the Brickus board.
	 * 
	 * @return The width of the Brickus board.
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Indicates that the player has chosen to pass. 
	 * This method fires an event reflecting that the active player has changed, 
	 * complete with game over flag if all players have passed. 
	 * This method must not be called after the game has ended 
	 * (that is, after a BrickusEvent has been issued declaring the game to be over).
	 * 
	 * @param player	The player who is passing.
	 */
	public void pass(Player player) {
		if (passed) {
			// If the other player has passed, end the game.
			activePlayer = null;
			notifyModelChanged(new BrickusEvent(this, false, true));
		} else {
			// Set the passed flag and change player.
			passed = true;
			changePlayer();
			notifyModelChanged(new BrickusEvent(this, true, false));
		}
	}
	
	/**
	 * Indicates that the active player is attempting to place the provided piece. 
	 * The piece's grid's upper-left corner should be positioned at the specified location. 
	 * This method in particular is obligated to report an appropriate event to all listeners of the model; 
	 * it must either report a movement (when the move is legal) or an illegal move (when it is not).
	 * 
	 * @param player	The player for whom the piece is to be placed.
	 * @param x				The X coordinate of the piece's grid on the Brickus board.
	 * @param y				The Y coordinate of the piece's grid on the Brickus board.
	 * @param piece		The piece which is being placed.
	 */
	public void placePiece(Player player, int x, int y, BrickusPiece piece) {
		try {
			// may throw the exception containing the message describing the illegal move.
			validateMove(player, x, y, piece);
			for (int i = 0; i < piece.getWidth(); i++)
				for (int j = 0; j < piece.getHeight(); j++)
					if (piece.isOccupied(i, j))
						board[y + j][x + i] = player;
			removePiece(player, piece);
			addScoreForPiece(player, piece);
			passed = false;
			changePlayer();
			notifyModelChanged(new BrickusEvent(this, true, false));
		} catch (BrickusIllegalMoveException e) {
			notifyIllegalMove(new BrickusIllegalMoveEvent(e.getMessage()));
		}
	}
	/**
	 * Removes a listener from this {@link BrickusModel}. 
	 * The removed listener will no longer be called when events occur on this model.
	 * 
	 * @param listener	The {@link BrickusListener} to remove from this {@link BrickusModel}
	 */
	public void removeBrickusListener(BrickusListener listener) {
		brickusListeners.remove(listener);
	}
	
	/**
	 * Notify all the listeners a model changed event.
	 * @param event		The {@link BrickusEvent} to notify the listeners.
	 */
	public void notifyModelChanged(BrickusEvent event) {
		for (Iterator<BrickusListener> i = brickusListeners.iterator(); i.hasNext();) {
			BrickusListener currentListener = (BrickusListener)i.next();
			currentListener.modelChanged(event);
		}
	}
	
	/**
	 * Notify all the listeners a illegal move event.
	 * @param event		The {@link BrickusIllegalMoveEvent} to notify the listeners.
	 */
	public void notifyIllegalMove(BrickusIllegalMoveEvent event) {
		for (Iterator<BrickusListener> i = brickusListeners.iterator(); i.hasNext();) {
			BrickusListener currentListener = (BrickusListener)i.next();
			currentListener.illegalMove(event);
		}
	}
	
	/**
	 * Add the score of a give piece to a player.
	 * @param player	The player to gain the score.
	 * @param piece		The piece to calculate score for.
	 */
	private void addScoreForPiece(Player player, BrickusPiece piece) {
		int score = scores.get(player);
		scores.put(player, score + getPieceScore(piece));
	}
	/**
	 * Count the number of blocks to get the score for a given piece
	 * 
	 * @param piece		The piece to score
	 * @return				The score of the given piece
	 */
	private int getPieceScore(BrickusPiece piece) {
		int score = 0;
		for (int i = 0; i < piece.getWidth(); i++)
			for (int j = 0; j < piece.getHeight(); j++)
				if (piece.isOccupied(i, j))
					score++;
		return score;
	}
	
	/**
	 * Remove a piece from a player's piece set.
	 * 
	 * @param player	The player for whom to remove the piece.
	 * @param piece		The piece to remove.
	 */
	private void removePiece(Player player, BrickusPiece piece) {
		pieces.get(player).remove(piece);
	}
	
	/**
	 * Change the active player. 
	 */
	private void changePlayer() {
		Player[] players = Player.values();
		activePlayer = (activePlayer == players[0]) ? players[1] : players[0];
	}
	
	/**
	 * Validate if the piece to place is on top of other pieces.
	 * 
	 * @param x
	 * @param y
	 * @param piece
	 * @return  Whether the piece to place is on top of other pieces.
	 */
	private boolean validatePiecesNoOverlap(int x, int y, BrickusPiece piece) {
		for (int i = 0; i < piece.getWidth(); i++) {
			for (int j = 0; j < piece.getHeight(); j++) {
				if ((piece.isOccupied(i, j) && (x + i >= 0) && (x + i <= width - 1) && (y + j >= 0) && (y + j <= height - 1) && getContents(x + i, y + j) != null))
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Validate if the piece is placed wholly on the board.
	 * @param x		X coordinate of the location.
	 * @param y		Y coordinate of the location.
	 * @param piece	The piece to be placed on the board.
	 * @return 	Whether the piece is placed wholly on the board.
	 */
	private boolean validatePieceOnBoard(int x, int y, BrickusPiece piece) {
		if ((x >= 0) && (x + piece.getWidth() <= width) && (y >= 0) && (y + piece.getHeight() <= height))
			return true;
		return false;
	}
	
	/**
	 * Validate if the piece to be placed touches some same color pieces diagonally. 
	 * @param player	The player for whom the piece is to be placed.
	 * @param x				X coordinate of the location.
	 * @param y				Y coordinate of the location.
	 * @param piece		The piece to be placed on the board.
	 * @return				Whether the piece touches some same color piece diagonally.
	 */
	private boolean validatePiecesSameColorDiagonal(Player player, int x, int y, BrickusPiece piece) {
		if (calculateScore(player) == 0)
			return true;
		else {
			for (int i = 0; i < piece.getWidth(); i++)
				for (int j = 0; j < piece.getHeight(); j++)
					// Only needs to check to girds that have been marked as CORNER
					if (((MyBrickusPiece) piece).isCorner(i, j) && checkDiagonal(player, x + i, y + j))
						return true;
			return false;
		}
	}
	
	/**
	 * Check the diagonal grids of a given location. 
	 * @param player	The player for whom a piece is to be placed.
	 * @param x				X coordinate of the location.
	 * @param y				Y coordinate of the location.
	 * @return				Whether a same color gird exists in one of the diagonal grids.
	 */
	private boolean checkDiagonal(Player player, int x, int y) {
		if (((x - 1 >= 0) && (y - 1 >= 0) && (getContents(x - 1, y - 1) == player)) ||
				((x - 1 >= 0) && (y + 1 <= height - 1) && (getContents(x - 1, y + 1) == player)) ||
				((x + 1 <= width - 1) && (y - 1 >= 0) && (getContents(x + 1, y - 1) == player)) ||
				((x + 1 <= width - 1) && (y + 1 <= height - 1) && (getContents(x + 1, y + 1) == player))) 
			return true;
		else
			return false;
	}
	
	/**
	 * Check the orthogonal grids of a given location. 
	 * @param player	The player for whom a piece is to be placed.
	 * @param x				X coordinate of the location.
	 * @param y				Y coordinate of the location.
	 * @return				Whether no same color girds exist in the orthogonal grids.
	 */
	private boolean checkOrthogonal(Player player, int x, int y) {
		if (((x - 1 >= 0) && (getContents(x - 1, y) == player)) ||
				((x + 1 <= width - 1) && (getContents(x + 1, y) == player)) ||
				((y - 1 >= 0) && (getContents(x, y - 1) == player)) ||
				((y + 1 <= height - 1) && (getContents(x, y + 1) == player)))
			return false;
		else
			return true;
	}
	
	/**
	 * Validate if the piece has sides touching same color pieces.
	 * 
	 * @param player	The player for whom the piece is to be placed.
	 * @param x				X coordinate of the location.
	 * @param y				Y coordinate of the location.
	 * @param piece		The piece to be placed.
	 * @return				Whether the piece has side touching same color pieces.
	 */
	private boolean validatePiecesSameColorNoSideTouch(Player player, int x, int y, BrickusPiece piece) {
		for (int i = 0; i < piece.getWidth(); i++)
			for (int j = 0; j < piece.getHeight(); j++)
				if (piece.isOccupied(i, j) && !checkOrthogonal(player, x + i, y + j))
					return false;
		return true;
	}
	
	/**
	 * Validate if the first piece of the player is placed in the corner of the board.
	 * @param player	The player for whom the piece to be placed.
	 * @param x				X coordinate of the location.
	 * @param y				Y coordinate of the location.
	 * @param piece		The piece to be placed.
	 * @return				Whether the first piece is placed in the corner.
	 */
	private boolean validateFirstPieceInCorner(Player player, int x, int y, BrickusPiece piece) {
		if (calculateScore(player) == 0)
			if (!(((x == 0) && (y == 0) && piece.isOccupied(0, 0)) || 
					((x == width - piece.getWidth()) && (y == 0) && piece.isOccupied(piece.getWidth() - 1, 0)) || 
					((x == 0) && (y == height - piece.getHeight()) && piece.isOccupied(0, piece.getHeight() - 1)) ||
					((x == width - piece.getWidth()) && (y == height - piece.getHeight()) && piece.isOccupied(piece.getWidth() - 1, piece.getHeight() - 1))))
				return false;
		return true;
	}
	
	/**
	 * Validate the player's move
	 * @param player	The player for whom the move to be validated.
	 * @param x				X coordinate of the location.
	 * @param y				Y coordinate of the location.
	 * @param piece		The piece to be placed.
	 * @throws BrickusIllegalMoveException
	 */
	private void validateMove(Player player, int x, int y, BrickusPiece piece) throws BrickusIllegalMoveException {
		if (!validateFirstPieceInCorner(player, x, y, piece))
			throw new BrickusIllegalMoveException(new String("A player's first piece must be placed in a corner."));
		if (!validatePiecesNoOverlap(x, y, piece))
			throw new BrickusIllegalMoveException(new String("Pieces may not be placed on top of other pieces."));
		if (!validatePieceOnBoard(x, y, piece))
			throw new BrickusIllegalMoveException(new String("A placed piece must be entirely on the game board."));
		if (!validatePiecesSameColorDiagonal(player, x, y, piece)) 
			throw new BrickusIllegalMoveException(new String("Pieces must touch pieces of the same color diagonally."));
		if (!validatePiecesSameColorNoSideTouch(player, x, y, piece))
			throw new BrickusIllegalMoveException(new String("Pieces may not be placed with sides touching pieces of the same color."));
	}
	/**
	 * Generate the set pieces.
	 * @return <code>List<BrickusPiece></code> contains the starting pieces.
	 */
	private List<BrickusPiece> brickusPieceSet() {
		List<BrickusPiece> pieceSet = new ArrayList<BrickusPiece>();
		pieceSet.add(new MyBrickusPiece(3, 3, new int[][] {{0, 1}, {1, 0}, {1, 2}, {2, 1}}, new int[][] {{1, 1}}, this));
		pieceSet.add(new MyBrickusPiece(3, 3, new int[][] {{0, 0}, {1, 0}, {1, 2}, {2, 1}}, new int[][] {{1, 1}}, this));
		pieceSet.add(new MyBrickusPiece(3, 3, new int[][] {{0, 0}, {0, 2}, {2, 1}}, new int[][] {{0, 1}, {1, 1}}, this));
		pieceSet.add(new MyBrickusPiece(2, 4, new int[][] {{0, 0}, {0, 3}, {1, 3}}, new int[][] {{0, 1}, {0, 2}}, this));
		pieceSet.add(new MyBrickusPiece(1, 5, new int[][] {{0, 0}, {0, 4}}, new int[][] {{0, 1}, {0, 2}, {0, 3}}, this));
		pieceSet.add(new MyBrickusPiece(3, 3, new int[][] {{0, 0}, {0, 1}, {2, 1}, {2, 2}}, new int[][] {{1, 1}}, this));
		pieceSet.add(new MyBrickusPiece(3, 2, new int[][] {{0, 0}, {0, 1}, {2, 0}, {2, 1}}, new int[][] {{1, 1}}, this));
		pieceSet.add(new MyBrickusPiece(3, 2, new int[][] {{0, 0}, {0, 1}, {1, 0}, {2, 1}}, new int[][] {{1, 1}}, this));
		pieceSet.add(new MyBrickusPiece(3, 3, new int[][] {{0, 0}, {1, 0}, {1, 1}, {2, 1}, {2, 2}}, new int[0][0], this));
		pieceSet.add(new MyBrickusPiece(2, 4, new int[][] {{0, 0}, {0, 1}, {1, 1}, {1, 3}}, new int[][] {{1, 2}}, this));
		pieceSet.add(new MyBrickusPiece(2, 4, new int[][] {{0, 0}, {0, 3}, {1, 1}}, new int[][] {{0, 1}, {0, 2}}, this));
		pieceSet.add(new MyBrickusPiece(3, 3, new int[][] {{0, 0}, {0, 2}, {2, 2}}, new int[][] {{0, 1}, {1, 2}}, this));
		pieceSet.add(new MyBrickusPiece(1, 4, new int[][] {{0, 0}, {0, 3}}, new int[][] {{0, 1}, {0, 2}}, this));
		pieceSet.add(new MyBrickusPiece(2, 3, new int[][] {{0, 0}, {0, 2}, {1, 2}}, new int[][] {{0, 1}}, this));
		pieceSet.add(new MyBrickusPiece(2, 3, new int[][] {{0, 0}, {0, 2}, {1, 1}}, new int[][] {{0, 1}}, this));
		pieceSet.add(new MyBrickusPiece(2, 3, new int[][] {{0, 0}, {0, 1}, {1, 1}, {1, 2}}, new int[0][0], this));
		pieceSet.add(new MyBrickusPiece(2, 2, new int[][] {{0, 0}, {0, 1}, {1, 0}, {1, 1}}, new int[0][0], this));
		pieceSet.add(new MyBrickusPiece(1, 3, new int[][] {{0, 0}, {0, 2}}, new int[][] {{0, 1}}, this));
		pieceSet.add(new MyBrickusPiece(2, 2, new int[][] {{0, 0}, {0, 1}, {1, 1}}, new int[0][0], this));
		pieceSet.add(new MyBrickusPiece(1, 2, new int[][] {{0, 0}, {0, 1}}, new int[0][0], this));
		pieceSet.add(new MyBrickusPiece(1, 1, new int[][] {{0, 0}}, new int[0][0], this));
		return pieceSet;
	}
private
	List<BrickusListener> brickusListeners;
	Player activePlayer;
	Player[][] board;
	HashMap<Player, List<BrickusPiece>> pieces;
	HashMap<Player, Integer> scores;
	int width, height;
	boolean passed;
}
