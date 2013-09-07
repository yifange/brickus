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
		int score = 0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (board[i][j] == player) {
					score++;
				}
			}
		}
		return score;
	}
	
	public Player getActivePlayer() {
		return activePlayer;
	}
	public Player getContents(int x, int y) throws java.lang.IndexOutOfBoundsException {
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
		changePlayer();
		notifyModelChanged(new BrickusEvent(this, true, false));
	}
	// XXX the document says the method throws BrickusIllegalMoveEvent, while it is not inherited from Throwable
	public void placePiece(Player player, int x, int y, BrickusPiece piece) {
		// XXX placePiece
		try {
			validateMove(player, x, y, piece);
			for (int i = 0; i < piece.getHeight(); i++)
				for (int j = 0; j < piece.getWidth(); j++)
					if (piece.isOccupied(j, i))
						board[y + i][x + j] = player;
			removePiece(player, piece);
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
	private void removePiece(Player player, BrickusPiece piece) {
		pieces.get(player).remove(piece);
	}
	private void changePlayer() {
		Player[] players = Player.values();
		activePlayer = (activePlayer == players[0]) ? players[1] : players[0];
	}
	private boolean validatePiecesNoOverlap(int x, int y, BrickusPiece piece) {
		for (int i = 0; i < piece.getHeight(); i++) {
			for (int j = 0; j < piece.getWidth(); j++) {
				if (piece.isOccupied(j, i) && getContents(y + j, x + i) != null) {
					return false;
				}
			}
		}
		return true;
	}
	private boolean validatePieceOnBoard(int x, int y, BrickusPiece piece) {
		return false;
	}
	private boolean validatePiecesSameColorDiagonal(Player player, int x, int y, BrickusPiece piece) {
		return false;
	}
	private boolean validatePiecesSameColorNoSideTouch(Player player, int x, int y, BrickusPiece piece) {
		return false;
	}
	private boolean validateFirstPieceInCorner(Player player, int x, int y, BrickusPiece piece) {
		return false;
	}
	private void validateMove(Player player, int x, int y, BrickusPiece piece) throws BrickusIllegalMoveException {
		if (validatePiecesNoOverlap(x, y, piece))
			throw new BrickusIllegalMoveException(new String("Pieces may not be placed on top of other pieces."));
		if (validatePieceOnBoard(x, y, piece))
			throw new BrickusIllegalMoveException(new String("A placed peice must be entirely on the game board."));
		if (validatePiecesSameColorDiagonal(player, x, y, piece)) 
			throw new BrickusIllegalMoveException(new String("Pieces must touch pieces of the same color diagonally."));
		if (validatePiecesSameColorNoSideTouch(player, x, y, piece))
			throw new BrickusIllegalMoveException(new String("Pieces may not be placed with sides touching pieces of the same color."));
		if (validateFirstPieceInCorner(player, x, y, piece))
			throw new BrickusIllegalMoveException(new String("A player's first piece must be placed in a corner."));
	}
	private List<BrickusPiece> brickusPieceSet() {
		List<BrickusPiece> pieceSet = new ArrayList<BrickusPiece>();
		pieceSet.add(new MyBrickusPiece(3, 3, new int[][] {{0, 1}, {1, 0}, {1, 1}, {1, 2}, {2, 1}}, this));
		pieceSet.add(new MyBrickusPiece(3, 3, new int[][] {{0, 0}, {1, 0}, {1, 1}, {1, 2}, {2, 1}}, this));
		pieceSet.add(new MyBrickusPiece(3, 3, new int[][] {{0, 0}, {0, 1}, {0, 2}, {1, 1}, {2, 1}}, this));
		pieceSet.add(new MyBrickusPiece(2, 4, new int[][] {{0, 0}, {0, 1}, {0, 2}, {0, 3}, {1, 3}}, this));
		pieceSet.add(new MyBrickusPiece(1, 5, new int[][] {{0, 0}, {0, 1}, {0, 2}, {0, 3}, {0, 4}}, this));
		pieceSet.add(new MyBrickusPiece(3, 3, new int[][] {{0, 0}, {0, 1}, {1, 1}, {2, 1}, {2, 2}}, this));
		pieceSet.add(new MyBrickusPiece(3, 2, new int[][] {{0, 0}, {0, 1}, {1, 1}, {2, 0}, {2, 1}}, this));
		pieceSet.add(new MyBrickusPiece(3, 2, new int[][] {{0, 0}, {0, 1}, {1, 0}, {1, 1}, {2, 1}}, this));
		pieceSet.add(new MyBrickusPiece(3, 3, new int[][] {{0, 0}, {1, 0}, {1, 1}, {2, 1}, {2, 2}}, this));
		pieceSet.add(new MyBrickusPiece(2, 4, new int[][] {{0, 0}, {0, 1}, {1, 1}, {1, 2}, {1, 3}}, this));
		pieceSet.add(new MyBrickusPiece(2, 4, new int[][] {{0, 0}, {0, 1}, {0, 2}, {0, 3}, {1, 1}}, this));
		pieceSet.add(new MyBrickusPiece(3, 3, new int[][] {{0, 0}, {0, 1}, {0, 2}, {1, 2}, {2, 2}}, this));
		pieceSet.add(new MyBrickusPiece(1, 4, new int[][] {{0, 0}, {0, 1}, {0, 2}, {0, 3}}, this));
		pieceSet.add(new MyBrickusPiece(2, 3, new int[][] {{0, 0}, {0, 1}, {0, 2}, {1, 2}}, this));
		pieceSet.add(new MyBrickusPiece(2, 3, new int[][] {{0, 0}, {0, 1}, {0, 2}, {1, 1}}, this));
		pieceSet.add(new MyBrickusPiece(2, 3, new int[][] {{0, 0}, {0, 1}, {1, 1}, {1, 2}}, this));
		pieceSet.add(new MyBrickusPiece(2, 2, new int[][] {{0, 0}, {0, 1}, {1, 0}, {1, 1}}, this));
		pieceSet.add(new MyBrickusPiece(1, 3, new int[][] {{0, 0}, {0, 1}, {0, 2}}, this));
		pieceSet.add(new MyBrickusPiece(2, 2, new int[][] {{0, 0}, {0, 1}, {1, 1}}, this));
		pieceSet.add(new MyBrickusPiece(1, 2, new int[][] {{0, 0}, {0, 1}}, this));
		pieceSet.add(new MyBrickusPiece(1, 1, new int[][] {{0, 0}}, this));
		return pieceSet;
	}
private
	List<BrickusListener> brickusListeners;
	Player activePlayer;
	Player[][] board;
	HashMap<Player, List<BrickusPiece>> pieces;
	int width, height;
}
