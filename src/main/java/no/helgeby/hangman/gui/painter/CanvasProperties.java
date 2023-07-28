package no.helgeby.hangman.gui.painter;

import java.awt.Dimension;
import java.util.Objects;

/**
 * Holds scale and sizing information about a canvas.
 */
public class CanvasProperties {

	public static final float DEFAULT_SCALE = 1.0f;

	public static final Dimension DEFAULT_SIZE = new Dimension(450, 450);

	public static final int DEFAULT_OFFSET_X = -75;
	public static final int DEFAULT_OFFSET_Y = -75;

	private float scale;
	private Dimension size;

	// Offset is not scaled.
	private int offsetX;
	private int offsetY;

	/**
	 * Creates default properties.
	 */
	public CanvasProperties() {
		this(DEFAULT_SCALE, DEFAULT_SIZE, DEFAULT_OFFSET_X, DEFAULT_OFFSET_Y);
	}

	/**
	 * Creates properties.
	 *
	 * @param scale   The scale to paint in.
	 * @param size    Canvas size. Must not be null.
	 * @param offsetX Number of pixels the image should be shifted to the right,
	 *                after scaling.
	 * @param offsetY Number of pixels the image should be shifted down, after
	 *                scaling.
	 */
	public CanvasProperties(float scale, Dimension size, int offsetX, int offsetY) {
		this.scale = scale;
		this.size = Objects.requireNonNull(size, "size");
		rescale(size);
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

	public float getScale() {
		return scale;
	}

	public Dimension getSize() {
		return size;
	}

	public int getOffsetX() {
		return offsetX;
	}

	public int getOffsetY() {
		return offsetY;
	}

	private void rescale(Dimension d) {
		d.width = (int) (d.width * scale);
		d.height = (int) (d.height * scale);
	}
}
