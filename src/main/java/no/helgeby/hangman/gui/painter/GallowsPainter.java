package no.helgeby.hangman.gui.painter;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

/**
 * Paints the empty gallows without rope.
 */
public class GallowsPainter extends Painter {

	private static final float GALLOW_THICKNESS = THICK_LINE;

	public GallowsPainter(CanvasProperties properties) {
		super(properties);
	}

	@Override
	public void paint(Graphics2D g) {
		prepareDrawing(g);
		g.setStroke(new BasicStroke(GALLOW_THICKNESS * scale));

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
