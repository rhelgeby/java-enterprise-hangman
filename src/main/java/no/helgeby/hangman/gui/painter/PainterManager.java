package no.helgeby.hangman.gui.painter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import no.helgeby.hangman.gui.painter.animation.Animation;
import no.helgeby.hangman.gui.painter.animation.WinningManAnimation;
import no.helgeby.hangman.model.DrawingType;
import no.helgeby.hangman.model.GallowsModel;

/**
 * Holds painters and delegates the painting task.
 * <p>
 * The painters are drawing onto a buffer with the same size as the component
 * passed. When painting is done a listener is notified.
 */
public class PainterManager {

	private static final Logger log = LogManager.getLogger(PainterManager.class);

	private BufferedImage image;
	private Graphics2D g;
	private CanvasProperties properties;

	/**
	 * Allows multiple painters for drawing various things on the buffer.
	 */
	private List<Painter> stack;

	private GallowsPainter gallowsPainter;
	private ManPainter manPainter;
	private WomanPainter womanPainter;
	private GridPainter gridPainter;

	private PainterListener listener;
	private GallowsModel gallows;

	private Animation currentAnimation;
	private WinningManAnimation winningManAnimation;

	public PainterManager(CanvasProperties properties, GallowsModel gallows) {
		this.properties = Objects.requireNonNull(properties, "properties");
		this.gallows = Objects.requireNonNull(gallows, "gallows");
		Dimension size = properties.getSize();

		image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
		g = image.createGraphics();

		stack = new ArrayList<>();

		gallowsPainter = new GallowsPainter();
		manPainter = new ManPainter(gallows);
		womanPainter = new WomanPainter(gallows);
		gridPainter = new GridPainter();

		AnimationListener animationListener = new AnimationListener();
		winningManAnimation = new WinningManAnimation(properties, animationListener);
	}

	public void paint() {
		reset();

		if (gallows.isFree()) {
			// TODO: Figure out which animation to start.
			startWinningManAnimation();
		} else {
			if (gallows.getCurrentDrawingType() == DrawingType.WOMAN) {
				paintWoman();
			} else {
				paintMan();
			}
		}
	}

	/**
	 * Paints whatever currently is on the painter stack and notifies the listener
	 * when done.
	 */
	private void paintBuffer() {
		log.debug("Painting buffer.");
		for (Painter p : stack) {
			p.paint(g, properties);
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

	public void startWinningManAnimation() {
		reset();
		currentAnimation = winningManAnimation;
		winningManAnimation.start();
	}

	public void setDrawGrid(boolean drawGrid) {
		properties.setGrid(drawGrid);
	}

	public void reset() {
		stack.clear();

		g.setBackground(Color.WHITE);
		g.clearRect(0, 0, image.getWidth(), image.getHeight());

		// Make sure the grid is always painted if enabled.
		if (properties.isGridEnabled()) {
			stack.add(gridPainter);
		}

		// The gallows is always visible.
		stack.add(gallowsPainter);

		// Stop any animations if any.
		if (currentAnimation != null) {
			currentAnimation.stop();
			currentAnimation = null;
		}
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setListener(PainterListener listener) {
		this.listener = listener;
	}

	private class AnimationListener implements PainterListener {
		@Override
		public void onPaintComplete() {
			log.debug("Animation frame complete.");
			stack.add(currentAnimation);
			paintBuffer();
		}
	}

}
