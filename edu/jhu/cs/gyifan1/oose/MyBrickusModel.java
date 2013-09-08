package edu.jhu.cs.gyifan1.oose;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusIllegalMoveEvent;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusListener;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;
import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusPiece;
import edu.jhu.cs.oose.fall2013.brickus.iface.Player;
import edu.jhu.cs.gyifan1.oose.BrickusIllegalMoveException;

public class MyBrickusModel implements BrickusModel {
	public MyBrickusModel() {
		brickusListeners = new ArrayList<BrickusListener>();
		activePlayer = Player.PLAYER1;
		height = 16; 
		width = 16;
		board = new Player[height][width];
		pieces = new HashMap<Player, List<BrickusPiece>>();
		pieces.put(Player.PLAYER1, brickusPieceSet());
		pieces.put(Player.PLAYER2, brickusPieceSet());
		
		scores = new HashMap<Player, Integer>();
		scores.put(Player.PLAYER1, 0);
		scores.put(Player.PLAYER2, 0);
	}
	public void addBrickusListener(BrickusListener listener) {
		for (Iterator<BrickusListener> i = brickusListeners.iterator(); i.hasNext();) {
			BrickusListener currentListener = (BrickusListener)i.next();
			if (currentListener == listener) {
				return;
			}
		}
		brickusListeners.add(listener);
	}
	
	public int calculateScore(Player player) {
		return scores.get(player);
	}
	
	public Player getActivePlayer() {
		return activePlayer;
	}
	public Player getContents(int x, int y) throws IndexOutOfBoundsException {
		return board[y][x];
	}
	
	public int getHeight() {
		return height;
	}
	public List<BrickusPiece> getPieces(Player player) {
		return pieces.get(player);
	}
	
	public int getWidth() {
		return width;
	}
	public void pass(Player player) {
		// XXX game over?????
		if (passed)
			notifyModelChanged(new BrickusEvent(this, false, true));
		else {
			passed = true;
			changePlayer();
			notifyModelChanged(new BrickusEvent(this, true, false));
		}
	}
	// XXX the document says the method throws BrickusIllegalMoveEvent, while it is not inherited from Throwable
	public void placePiece(Player player, int x, int y, BrickusPiece piece) {
		// XXX placePiece
		try {
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
	
	public void removeBrickusListener(BrickusListener listener) {
		brickusListeners.remove(listener);
	}
	public void notifyModelChanged(BrickusEvent event) {
		for (Iterator<BrickusListener> i = brickusListeners.iterator(); i.hasNext();) {
			BrickusListener currentListener = (BrickusListener)i.next();
			currentListener.modelChanged(event);
		}
	}
	public void notifyIllegalMove(BrickusIllegalMoveEvent event) {
		for (Iterator<BrickusListener> i = brickusListeners.iterator(); i.hasNext();) {
			BrickusListener currentListener = (BrickusListener)i.next();
			currentListener.illegalMove(event);
		}
	}
	private void addScoreForPiece(Player player, BrickusPiece piece) {
		int score = scores.get(player);
		scores.put(player, score + getPieceScore(piece));
	}
	private int getPieceScore(BrickusPiece piece) {
		int score = 0;
		for (int i = 0; i < piece.getWidth(); i++)
			for (int j = 0; j < piece.getHeight(); j++)
				if (piece.isOccupied(i, j))
					score++;
		return score;
	}
	private void removePiece(Player player, BrickusPiece piece) {
		pieces.get(player).remove(piece);
	}
	private void changePlayer() {
		Player[] players = Player.values();
		activePlayer = (activePlayer == players[0]) ? players[1] : players[0];
	}
	private boolean validatePiecesNoOverlap(int x, int y, BrickusPiece piece) {
		for (int i = 0; i < piece.getWidth(); i++) {
			for (int j = 0; j < piece.getHeight(); j++) {
				try {
					if (piece.isOccupied(i, j) && getContents(x + i, y + j) != null) {
						return false;
					}
				} catch (IndexOutOfBoundsException e) {
					// ignore
				}
			}
		}
		return true;
	}
	private boolean validatePieceOnBoard(int x, int y, BrickusPiece piece) {
		if ((x >= 0) && (x + piece.getWidth() <= width) && (y >= 0) && (y + piece.getHeight() <= height))
			return true;
		return false;
	}
	private boolean validatePiecesSameColorDiagonal(Player player, int x, int y, BrickusPiece piece) {
		if (calculateScore(player) == 0)
			return true;
		else {
			for (int i = 0; i < piece.getWidth(); i++)
				for (int j = 0; j < piece.getHeight(); j++)
					if (((MyBrickusPiece) piece).isCorner(i, j) && checkDiagonal(player, x + i, y + j))
						return true;
			return false;
		}
	}
	private boolean checkDiagonal(Player player, int x, int y) {
		try {
			if (getContents(x - 1, y - 1) == player)
				return true;
		} catch (IndexOutOfBoundsException e) {
			// ignore
		}
		
		try {
			if (getContents(x - 1, y + 1) == player)
				return true;
		} catch (IndexOutOfBoundsException e) {
			// ignore
		}
		try {
			if (getContents(x + 1, y - 1) == player)
				return true;
		} catch (IndexOutOfBoundsException e) {
			// ignore
		}
		try {
			if (getContents(x + 1, y + 1) == player)
				return true;
		} catch (IndexOutOfBoundsException e) {
			// ignore
		}
		return false;
	}
	private boolean checkOrthogonal(Player player, int x, int y) {
		try {
			if (getContents(x - 1, y) == player)
				return false;
		} catch (IndexOutOfBoundsException e) {
			// ignore
		}
		
		try {
			if (getContents(x + 1, y) == player)
				return false;
		} catch (IndexOutOfBoundsException e) {
			// ignore
		}
		try {
			if (getContents(x, y - 1) == player)
				return false;
		} catch (IndexOutOfBoundsException e) {
			// ignore
		}
		try {
			if (getContents(x, y + 1) == player)
				return false;
		} catch (IndexOutOfBoundsException e) {
			// ignore
		}
		return true;
	}
	private boolean validatePiecesSameColorNoSideTouch(Player player, int x, int y, BrickusPiece piece) {
		for (int i = 0; i < piece.getWidth(); i++)
			for (int j = 0; j < piece.getHeight(); j++)
				if (piece.isOccupied(i, j) && !checkOrthogonal(player, x + i, y + j))
					return false;
		return true;
	}
	private boolean validateFirstPieceInCorner(Player player, int x, int y, BrickusPiece piece) {
		
		if (calculateScore(player) == 0)
			if (!(((x == 0) && (y == 0) && piece.isOccupied(0, 0)) || 
					((x == width - piece.getWidth()) && (y == 0) && piece.isOccupied(piece.getWidth() - 1, 0)) || 
					((x == 0) && (y == height - piece.getHeight()) && piece.isOccupied(0, piece.getHeight() - 1)) ||
					((x == width - piece.getWidth()) && (y == height - piece.getHeight()) && piece.isOccupied(piece.getWidth() - 1, piece.getHeight() - 1))))
				return false;
		return true;
	}
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
