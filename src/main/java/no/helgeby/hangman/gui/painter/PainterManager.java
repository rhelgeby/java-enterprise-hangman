package no.helgeby.hangman.gui.painter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import no.helgeby.hangman.gui.PainterListener;
import no.helgeby.hangman.model.DrawingType;
import no.helgeby.hangman.model.GallowsModel;

/**
 * Holds painters and delegates the painting task.
 * <p>
 * The painters are drawing onto a buffer with the same size as the component
 * passed. When painting is done a listener is notified.
 */
public class PainterManager {

	// TODO: Listen to the game model?

	private BufferedImage image;
	private Graphics2D g;

	private float scale = 1.0f;

	// Offset is not scaled.
	private int offsetX;
	private int offsetY;

	/**
	 * Allows multiple painters for drawing various things on the buffer.
	 */
	private List<Painter> stack;

	private boolean drawGrid = false;

	private GallowsPainter gallowsPainter;
	private ManPainter manPainter;
	private WomanPainter womanPainter;
	private GridPainter gridPainter;

	private PainterListener listener;
	private GallowsModel gallows;

	public PainterManager(Dimension size, GallowsModel gallows) {
		Objects.requireNonNull(size, "size");
		this.gallows = Objects.requireNonNull(gallows, "gallows");

		image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
		g = image.createGraphics();

		stack = new ArrayList<>();

		gallowsPainter = new GallowsPainter();
		manPainter = new ManPainter(gallows);
		womanPainter = new WomanPainter(gallows);
		gridPainter = new GridPainter(size.width, size.height);
	}

	public void paint() {
		reset();

		// TODO: Figure out what to paint and add it to the stack.

		if (gallows.isFree()) {
			// TODO: Draw a winning person.
		} else {
			if (gallows.getCurrentDrawingType() == DrawingType.WOMAN) {
				paintWoman();
			} else {
				paintMan();
			}
		}

		paintBuffer();
	}

	/**
	 * Paints whatever currently is on the painter stack and notifies the listener
	 * when done.
	 */
	private void paintBuffer() {
		for (Painter p : stack) {
			p.paint(g, scale, offsetX, offsetY);
		}
		listener.onPaintComplete();
	}

	public void paintGallows() {
		reset();
		paintBuffer();
	}

	public void paintMan() {
		reset();
		stack.add(manPainter);
		paintBuffer();
	}

	public void paintWoman() {
		reset();
		stack.add(womanPainter);
		paintBuffer();
	}

	public void reset() {
		stack.clear();

		g.setBackground(Color.WHITE);
		g.clearRect(0, 0, image.getWidth(), image.getHeight());

		// Make sure the grid is always painted if enabled.
		if (drawGrid) {
			stack.add(gridPainter);
		}

		// The gallows is always visible.
		stack.add(gallowsPainter);
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public void setOffsetX(int offsetX) {
		this.offsetX = offsetX;
	}

	public void setOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}

	public void setListener(PainterListener listener) {
		this.listener = listener;
	}

}
