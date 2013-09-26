package edu.jhu.cs.gyifan1.oose;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;
/**
 * A grid-layout panel implementation. Support set/get fill color and border color for a grid with given x/y positions.
 * @author yifan
 *
 */
public class MyBrickusGrid extends JPanel {
	private Color[] borderColors;
	private Color[] fillColors;
	private int layoutHeight, layoutWidth;

	/**
	 * 
	 * @param width		The grid width of the panel.
	 * @param height	The grid height of the panel 
	 */
	public MyBrickusGrid(int width, int height) {
		this.layoutHeight = height;
		this.layoutWidth = width;
		fillColors = new Color[layoutHeight * layoutWidth];
		borderColors = new Color[layoutHeight * layoutWidth];
	}
	/**
	 * Get the fill color of given grid position.
	 * @param x		x location
	 * @param y		y location
	 * @return		Fill color
	 */
	public Color getFillColor(int x, int y) {
		try {
			Color color = fillColors[y * layoutWidth + x];
			return color;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	/**
	 * Get the grid location for given screen position.
	 * @param x		x-coordinate (screen position)
	 * @param y		y-coordinate (screen position)
	 * @return		The grid location. Return <code>null</code> if the position is out of the panel.
	 */
	public Point getGridLocation(int x, int y) {
		Point origin = getLocation();
		int gridWidth = getWidth() / layoutWidth;
		int gridHeight = getHeight() / layoutHeight;
		Point point = new Point();
		int gx = (x - origin.x) / gridWidth;
		int gy = (y - origin.y) / gridHeight;
		if (gx >= 0 && gx < layoutWidth && gy >= 0 && gy < layoutHeight) {
			point.setLocation(gx, gy);
			return point;
		} else {
			return null;
		}
	}
	
	/**
	 * Paint the panel.
	 */
	@Override
	public void paintComponent(Graphics g) {
		int gridWidth = getWidth() / layoutWidth;
		int gridHeight = getHeight() / layoutHeight;
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		for (int row = 0; row < layoutHeight; row++)
			for (int col = 0; col < layoutWidth; col++) {
				Color fillColor = fillColors[row * layoutWidth + col];
				if (fillColor != null)
					g.setColor(fillColors[row * layoutWidth + col]);
				else
					g.setColor(Color.white);
				g.fillRect(col * gridWidth, row * gridHeight, gridWidth,
						gridHeight);
				g.setColor(borderColors[row * layoutWidth + col]);
				g.drawRect(col * gridWidth, row * gridHeight, gridWidth,
						gridHeight);
			}
	}
	
	/**
	 * Set border color for given grid location.
	 * @param x		x-location
	 * @param y		y-location
	 * @param color	The color to be set
	 */
	public void setBorderColor(int x, int y, Color color) {
		if (x >= 0 && x < layoutWidth && y >= 0 && y < layoutHeight)
			borderColors[y * layoutWidth + x] = color;
	}
	
	/**
	 * Set fill color for given grid location.
	 * @param x		x-location
	 * @param y		y-location
	 * @param color	The color to be set
	 */
	public void setFillColor(int x, int y, Color color) {
		if (x >= 0 && x < layoutWidth && y >= 0 && y < layoutHeight)
			fillColors[y * layoutWidth + x] = color;
	}
}
