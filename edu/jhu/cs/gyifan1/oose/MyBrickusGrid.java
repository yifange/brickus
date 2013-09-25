package edu.jhu.cs.gyifan1.oose;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MyBrickusGrid extends JPanel {
	private int layoutHeight, layoutWidth;
	private Color[] fillColors;
	private Color[] borderColors;
	
	public Point getGridLocation(int x, int y) {
		Point origin = getLocation();
		int gridWidth = getWidth() / layoutWidth;
		int gridHeight = getHeight() / layoutHeight;
		Point point = new Point();
		point.setLocation((x - origin.x) / gridWidth, (y - origin.y) / gridHeight);
		return point;
	}
	public MyBrickusGrid(int width, int height) {
		this.layoutHeight = height;
		this.layoutWidth = width;
		fillColors = new Color[layoutHeight * layoutWidth];
		borderColors = new Color[layoutHeight * layoutWidth];
	}
	public void setFillColor(int x, int y, Color color) {
		fillColors[y * layoutWidth + x] = color;
	}
	public void setBorderColor(int x, int y, Color color) {
		borderColors[y * layoutWidth + x] = color;
	}
	public Color getFillColor(int x, int y) {
		return fillColors[y * layoutWidth + x];
	}
	public void paintComponent(Graphics g) {
		Point point = getLocation();
		int x = point.x;
		int y = point.y;
		int gridWidth = getWidth() / layoutWidth;
		int gridHeight = getHeight() / layoutHeight;
		for (int row = 0; row < layoutHeight; row++)
			for (int col = 0; col < layoutWidth; col++) {
				Color fillColor = fillColors[row * layoutWidth + col];
				if (fillColor != null) {
					g.setColor(fillColors[row * layoutWidth + col]);
					g.fillRect(x + col * gridWidth, y + row * gridHeight, gridWidth, gridHeight);
				}
				g.setColor(borderColors[row * layoutWidth + col]);
				g.drawRect(x + col * gridWidth, y + row * gridHeight, gridWidth, gridHeight);
			}
	}
}
