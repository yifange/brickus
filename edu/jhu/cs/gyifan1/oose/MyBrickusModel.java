
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

public class MyBrickusModel implements BrickusModel {
	public MyBrickusModel() {
		brickusListeners = new ArrayList<BrickusListener>();
		activePlayer = Player.PLAYER1;
		height = 16; 
		width = 16;
		board = new Player[height][width];
		
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
	public Player getContents(int x, int y) throws java.lang.IndexOutBoundsException {
		return board[x][y];
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
		Player[] players = Player.values();
		activePlayer = (activePlayer == players[0]) ? players[1] : players[0];
		// XXX game over?????
		notifyModelChanged(new BrickusEvent(this, true, false));
	}
	// XXX the document says the method throws BrickusIllegalMoveEvent, while it is not inherited from Throwable
	public void placePiece(Player player, int x, int y, BrickusPiece piece) {
		// XXX placePiece
		notifyModelChanged(new BrickusEvent(this, true, false));
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
	
private
	List<BrickusListener> brickusListeners;
	Player activePlayer;
	Player[][] board;
	HashMap<Player, List<BrickusPiece>> pieces;
	int width, height;
}