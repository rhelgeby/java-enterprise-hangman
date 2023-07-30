package no.helgeby.hangman.gui.painter;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import no.helgeby.hangman.model.GallowsModel;

/**
 * Draws a man according to the gallows state.
 */
public class ManPainter extends GallowsPersonPainter {

	public ManPainter(GallowsModel gallows, CanvasProperties properties) {
		super(gallows, properties);
	}

	@Override
	public void paint(Graphics2D g) {
		paintUpperBody(g);

		int scaledHeadSize = (int) (headSize * scale);
		int bodyMid = scaledHeadSize / 2;
		int scaledDropLength = gallows.isDead() ? (int) (dropLength * scale) : 0;

		stage = gallows.getStage();

		if (stage >= 6) {
			// Body.
			x1 = 400 * scale + bodyMid + offsetX;
			y1 = 175 * scale + scaledHeadSize + scaledDropLength + offsetY;
			x2 = 400 * scale + bodyMid + offsetX;
			y2 = 275 * scale + scaledHeadSize + scaledDropLength + offsetY;
			g.draw(new Line2D.Float(x1, y1, x2, y2));
		}

		if (stage >= 7) {
			// Left leg.
			x1 = 400 * scale + bodyMid + offsetX;
			y1 = 275 * scale + scaledHeadSize + scaledDropLength + offsetY;
			x2 = 350 * scale + bodyMid + offsetX;
			y2 = 375 * scale + scaledHeadSize + scaledDropLength + offsetY;
			g.draw(new Line2D.Float(x1, y1, x2, y2));
		}

		if (stage >= 8) {
			// Right leg.
			x1 = 400 * scale + bodyMid + offsetX;
			y1 = 275 * scale + scaledHeadSize + scaledDropLength + offsetY;
			x2 = 450 * scale + bodyMid + offsetX;
			y2 = 375 * scale + scaledHeadSize + scaledDropLength + offsetY;
			g.draw(new Line2D.Float(x1, y1, x2, y2));
		}
	}

}
