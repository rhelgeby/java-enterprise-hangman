package no.helgeby.hangman.gui.painter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.Objects;

import no.helgeby.hangman.model.GallowsModel;

/**
 * Draws the upper body of a person.
 */
public abstract class PersonPainter implements Painter {

	public static final int DEFAULT_HEAD_SIZE = 50;
	public static final int DEFAULT_DROP_LENGTH = 50;
	public static final float DEFAULT_DRAWING_THICKNESS = 2;

	/**
	 * How long the rope is when dropping dead, in pixels before scaling.
	 */
	protected int dropLength = DEFAULT_DROP_LENGTH;

	/**
	 * The diameter of the head, in pixels before scaling.
	 */
	protected int headSize = DEFAULT_HEAD_SIZE;

	/**
	 * The width of the lines drawn, in pixels.
	 */
	protected float drawingThickness = DEFAULT_DRAWING_THICKNESS;

	protected GallowsModel gallows;

	public PersonPainter(GallowsModel gallows) {
		this.gallows = Objects.requireNonNull(gallows, "gallows");
	}

	/**
	 * Draws the upper body of a person.
	 */
	public void paintUpperBody(Graphics2D g, float scale, int offsetX, int offsetY) {
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(drawingThickness * scale));

		int stage = gallows.getStage();
		boolean isDead = gallows.isDead();

		int headSize = (int) (50 * scale);
		int headMid = headSize / 2;
		int shiftY = isDead ? (int) (dropLength * scale) : 0;

		float x1;
		float y1;
		float x2;
		float y2;

		if (stage >= 1) {
			// Rope.
			x1 = 400 * scale + headMid + offsetX;
			y1 = 100 * scale + offsetY;
			x2 = 400 * scale + headMid + offsetX;
			y2 = 175 * scale - (headSize / 2) + shiftY + offsetY;
			g.draw(new Line2D.Float(x1, y1, x2, y2));
		}

		if (stage >= 2) {
			// Head.
			x1 = 400 * scale + offsetX;
			y1 = 150 * scale + shiftY + offsetY;
			g.drawOval((int) x1, (int) y1, headSize, headSize);
		}

		if (stage >= 2 && isDead) {
			// Face.
			x1 = 410 * scale + offsetX;
			y1 = 165 * scale + shiftY + offsetY;
			x2 = 400 * scale + headSize - (10 * scale) + offsetX;
			y2 = 130 * scale + headSize + shiftY + offsetY;
			g.draw(new Line2D.Float(x1, y1, x2, y2));
			x1 = 410 * scale + offsetX;
			y1 = 130 * scale + headSize + shiftY + offsetY;
			x2 = 400 * scale + headSize - (10 * scale) + offsetX;
			y2 = 165 * scale + shiftY + offsetY;
			g.draw(new Line2D.Float(x1, y1, x2, y2));
		}

		if (stage >= 3) {
			// Neck.
			x1 = 400 * scale + headMid + offsetX;
			y1 = 150 * scale + headSize + shiftY + offsetY;
			x2 = 400 * scale + headMid + offsetX;
			y2 = 175 * scale + headSize + shiftY + offsetY;
			g.draw(new Line2D.Float(x1, y1, x2, y2));
		}

		if (stage >= 4) {
			// Left arm.
			x1 = 400 * scale + headMid + offsetX;
			y1 = 175 * scale + headSize + shiftY + offsetY;
			x2 = 350 * scale + headMid + offsetX;
			y2 = 225 * scale + headSize + shiftY + offsetY;
			g.draw(new Line2D.Float(x1, y1, x2, y2));
		}

		if (stage >= 5) {
			// Right arm.
			x1 = 400 * scale + headMid + offsetX;
			y1 = 175 * scale + headSize + shiftY + offsetY;
			x2 = 450 * scale + headMid + offsetX;
			y2 = 225 * scale + headSize + shiftY + offsetY;
			g.draw(new Line2D.Float(x1, y1, x2, y2));
		}
	}

}
