package no.helgeby.hangman.gui.painter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

/**
 * Paints the empty gallows without rope.
 */
public class GallowsPainter implements Painter {

	private static final float GALLOW_THICKNESS = 5;

	@Override
	public void paint(Graphics2D g, float scale, int offsetX, int offsetY) {
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(GALLOW_THICKNESS * scale));

		float x1;
		float y1;
		float x2;
		float y2;

		x1 = 100 * scale + offsetX;
		y1 = 500 * scale + offsetY;
		x2 = 300 * scale + offsetX;
		y2 = 500 * scale + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));

		x1 = 200 * scale + offsetX;
		y1 = 500 * scale + offsetY;
		x2 = 200 * scale + offsetX;
		y2 = 100 * scale + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));

		x1 = 200 * scale + offsetX;
		y1 = 100 * scale + offsetY;
		x2 = 500 * scale + offsetX;
		y2 = 100 * scale + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));

		x1 = 300 * scale + offsetX;
		y1 = 100 * scale + offsetY;
		x2 = 200 * scale + offsetX;
		y2 = 200 * scale + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));

		x1 = 150 * scale + offsetX;
		y1 = 500 * scale + offsetY;
		x2 = 200 * scale + offsetX;
		y2 = 450 * scale + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));

		x1 = 250 * scale + offsetX;
		y1 = 500 * scale + offsetY;
		x2 = 200 * scale + offsetX;
		y2 = 450 * scale + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));
	}

}
