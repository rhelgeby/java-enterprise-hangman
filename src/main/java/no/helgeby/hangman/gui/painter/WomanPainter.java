package no.helgeby.hangman.gui.painter;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import no.helgeby.hangman.model.GallowsModel;

/**
 * Draws a woman according to the gallows state.
 */
public class WomanPainter extends GallowsPersonPainter {

	public WomanPainter(GallowsModel gallows, CanvasProperties properties) {
		super(gallows, properties);
	}

	@Override
	public void paint(Graphics2D g) {
		paintUpperBody(g);

		int scaledHeadSize = (int) (headSize * scale);
		int bodyHorizontalMid = scaledHeadSize / 2;
		int scaledDropLength = gallows.isDead() ? (int) (dropLength * scale) : 0;

		stage = gallows.getStage();

		// Body.
		if (stage >= 6) {
			x1 = 400 * scale + bodyHorizontalMid + offsetX;
			y1 = 175 * scale + scaledHeadSize + scaledDropLength + offsetY;
			x2 = 375 * scale + bodyHorizontalMid + offsetX;
			y2 = 325 * scale + scaledHeadSize + scaledDropLength + offsetY;
			g.draw(new Line2D.Float(x1, y1, x2, y2));
		}
		if (stage >= 7) {
			x1 = 400 * scale + bodyHorizontalMid + offsetX;
			y1 = 175 * scale + scaledHeadSize + scaledDropLength + offsetY;
			x2 = 425 * scale + bodyHorizontalMid + offsetX;
			y2 = 325 * scale + scaledHeadSize + scaledDropLength + offsetY;
			g.draw(new Line2D.Float(x1, y1, x2, y2));
		}
		if (stage >= 8) {
			x1 = 400 * scale + offsetX;
			y1 = 325 * scale + scaledHeadSize + scaledDropLength + offsetY;
			x2 = 450 * scale + offsetX;
			y2 = 325 * scale + scaledHeadSize + scaledDropLength + offsetY;
			g.draw(new Line2D.Float(x1, y1, x2, y2));
		}

		if (stage >= 9) {
			// Left leg.
			x1 = 390 * scale + bodyHorizontalMid + offsetX;
			y1 = 325 * scale + scaledHeadSize + scaledDropLength + offsetY;
			x2 = 390 * scale + bodyHorizontalMid + offsetX;
			y2 = 350 * scale + scaledHeadSize + scaledDropLength + offsetY;
			g.draw(new Line2D.Float(x1, y1, x2, y2));
		}

		if (stage >= 10) {
			// Right leg.
			x1 = 410 * scale + bodyHorizontalMid + offsetX;
			y1 = 325 * scale + scaledHeadSize + scaledDropLength + offsetY;
			x2 = 410 * scale + bodyHorizontalMid + offsetX;
			y2 = 350 * scale + scaledHeadSize + scaledDropLength + offsetY;
			g.draw(new Line2D.Float(x1, y1, x2, y2));
		}
	}

}
