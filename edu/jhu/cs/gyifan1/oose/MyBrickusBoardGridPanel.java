package edu.jhu.cs.gyifan1.oose;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;
import edu.jhu.cs.oose.fall2013.brickus.iface.Player;

public class MyBrickusBoardGridPanel extends JPanel {
	private int x, y;
	private Set<MyBrickusMouseLocationListener> listeners;
	public MyBrickusBoardGridPanel(int x, int y) {
		this.x = x;
		this.y = y;
		listeners = new HashSet<MyBrickusMouseLocationListener>();
		setUnoccupied();
		addMouseListener(new MouseHandler());
	}
	public void addListener(MyBrickusMouseLocationListener listener) {
		listeners.add(listener);
	}
	public void removeListener(MyBrickusMouseLocationListener listener) {
		listeners.remove(listener);
	}
	public void notifyMouseEnteredLocation() {
		for (MyBrickusMouseLocationListener listener : listeners) {
			listener.mouseEnteredLocation(x, y);
		}
	}
	public void notifyMouseClickedLocation() {
		for (MyBrickusMouseLocationListener listener : listeners) {
			listener.mouseClickedLocation(x, y);
		}
	}
	public int getBoardX() {
		return x;
	}
	public int getBoardY() {
		return y;
	}
	
	public void setHovered(Player player) {
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		setBackground(MyBrickusUtils.getPlayerTransparentColor(player));
	}
	
	public void setOccupied(Player player) {
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		setBackground(MyBrickusUtils.getPlayerColor(player));
	}
	public void setUnoccupied() {
		setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		setBackground(null);
	}
	private class MouseHandler extends MouseAdapter {
		public void mouseEntered(MouseEvent event) {
			notifyMouseEnteredLocation();
		}
		public void mouseClicked(MouseEvent event) {
			notifyMouseClickedLocation();
		}
	}
	
}
