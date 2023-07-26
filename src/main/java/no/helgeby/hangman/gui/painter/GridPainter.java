package no.helgeby.hangman.gui.painter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Paints a dotted grid.
 */
public class GridPainter implements Painter {

	private int width;
	private int height;

	public GridPainter(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void paint(Graphics2D g, float scale, int offsetX, int offsetY) {
		int gridSize = 10;

		g.setStroke(new BasicStroke(1.0f));

		for (int x = 0; x < width; x += gridSize) {
			for (int y = 0; y < height; y += gridSize) {
				if (x % 100 == 0 && y % 100 == 0) {
					g.setColor(Color.RED);
					g.fillRect(x, y, 3, 3);
				} else {
					g.setColor(Color.BLUE);
					g.drawRect(x, y, 1, 1);
				}
			}
		}
	}

}
