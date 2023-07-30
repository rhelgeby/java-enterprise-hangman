package no.helgeby.hangman.gui.painter.animation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Objects;

import no.helgeby.hangman.gui.painter.CanvasProperties;
import no.helgeby.hangman.gui.painter.Painter;

/**
 * Provides some basic utilities for the painter.
 */
public abstract class AbstractPainter implements Painter {

	public static final int DEFAULT_HEAD_SIZE = 50;

	public static final int THIN_LINE = 2;
	public static final int THICK_LINE = 5;

	// A cache of properties. Makes it convenient for the painter.
	protected CanvasProperties p;
	protected int stage;
	protected float scale;
	protected int offsetX;
	protected int offsetY;

	protected int headSize;
	protected int headMid;

	// Common temporary variables. The painter is responsible for using these.
	protected float x1;
	protected float y1;
	protected float x2;
	protected float y2;
	protected float r;

	public AbstractPainter(CanvasProperties properties) {
		this(0, properties);
	}

	public AbstractPainter(int stage, CanvasProperties properties) {
		this.stage = stage;
		this.p = Objects.requireNonNull(properties, "properties");
		cacheProperties();
	}

	public void prepareDrawing(Graphics2D g) {
		// Update cache.
		cacheProperties();

		g.setColor(Color.BLACK);
		g.setBackground(Color.WHITE);
		g.setStroke(new BasicStroke(Math.max(THIN_LINE * scale, 1)));
	}

	public void cacheProperties() {
		scale = p.getScale();
		offsetX = p.getOffsetX();
		offsetY = p.getOffsetY();
		headSize = (int) (DEFAULT_HEAD_SIZE * scale);
		headMid = headSize / 2;
	}

}
