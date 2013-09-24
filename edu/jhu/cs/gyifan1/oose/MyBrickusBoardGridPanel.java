package edu.jhu.cs.gyifan1.oose;

import javax.swing.JPanel;

public class MyBrickusBoardGridPanel extends JPanel {
	private int x, y;
	public MyBrickusBoardGridPanel(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public int getBoardX() {
		return x;
	}
	public int getBoardY() {
		return y;
	}
	
}
