package no.helgeby.hangman.gui.painter.animation;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.Timer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import no.helgeby.hangman.gui.painter.CanvasProperties;
import no.helgeby.hangman.gui.painter.GridPainter;
import no.helgeby.hangman.gui.painter.Painter;
import no.helgeby.hangman.gui.painter.PainterListener;

/**
 * Paints a series of buffered images in rapid sequence.
 * <p>
 * This should work fine for low frame rates or simple animations. It is not
 * made for higher frame rates. Too high rate may result in delayed or dropped
 * frames.
 * <p>
 * Its speed is limited by CPU speed and how busy the Spring event-dispatch
 * thread is.
 * <p>
 * Video players often use the graphics card or some kind of hardware
 * acceleration to offload drawing. This implementation use only the CPU.
 */
public abstract class Animation extends Painter {

	private static final Logger log = LogManager.getLogger(Animation.class);

	/**
	 * The current frame number.
	 * <p>
	 * It needs to be thread safe because the animating timer may be updating it at
	 * the same time as another slow drawing task is running.
	 */
	private AtomicInteger currentFrame;
	private List<BufferedImage> frames;
	private GridPainter gridPainter;

	private boolean loop;
	private Timer timer;
	private PainterListener listener;

	private Animator animator;

	/**
	 * Creates an empty animation. Use {@link #createFrame()} to add frames to it.
	 * 
	 * @param frameTime  How long each frame is displayed.
	 * @param properties Image properties.
	 * @param listener   Object to be notified when a frame is painted.
	 */
	public Animation(Duration frameTime, CanvasProperties properties, PainterListener listener) {
		super(properties);
		Objects.requireNonNull(frameTime, "frameTime");
		if (frameTime.toMillis() > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Too long delay.");
		}
		gridPainter = new GridPainter(p);

		this.listener = listener;

		animator = new Animator();
		timer = new Timer((int) frameTime.toMillis(), animator);

		currentFrame = new AtomicInteger();
		frames = new ArrayList<>();
	}

	/**
	 * Sets the listener to be notified when a frame is ready to be painted.
	 * <p>
	 * The current listener is replaced.
	 * 
	 * @param listener Listener to be notified, or null to remove current listener.
	 */
	public void setListener(PainterListener listener) {
		this.listener = listener;
	}

	/**
	 * Starts the animation.
	 * <p>
	 * The first frame is painted immediately and will trigger the listener.
	 */
	public void start() {
		log.info("Starting animation.");

		stage = 0;

		// Paint the first frame to the buffer.
		animator.actionPerformed(null);

		// Start the timer for the rest of the frames.
		timer.start();
	}

	/**
	 * Stops and resets the animation to the beginning.
	 */
	public void stop() {
		timer.stop();
		// TODO: Existing timer task may be running. Delay counter reset until complete?
		resetFrameCounter();
	}

	/**
	 * Paints the current frame.
	 * 
	 * @param g                Canvas to paint on.
	 * @param canvasProperties Scale and size information about the canvas.
	 */
	@Override
	public void paint(Graphics2D g) {
		// Draw current frame.
		BufferedImage frame = frames.get(currentFrame.get());
		g.drawImage(frame, 0, 0, null);

		// The grid is on the top layer because frames are not transparent.
		if (p.isGridEnabled()) {
			gridPainter.paint(g);
		}
	}

	/**
	 * Creates and adds a frame to the animation.
	 * <p>
	 * It is prepared with a white background.
	 * 
	 * @return An object that paints on this frame.
	 */
	public Graphics2D createFrame() {
		Dimension size = p.getSize();
		BufferedImage frame = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = frame.createGraphics();

		prepareDrawing(g);
		g.clearRect(0, 0, size.width, size.height);
		frames.add(frame);

		return g;
	}

	private void nextFrame() {
		// TODO: Does not increment for other thread. Reseach.
		currentFrame.incrementAndGet();
	}

	private void resetFrameCounter() {
		currentFrame.set(0);
	}

	private void notifyListener() {
		if (listener == null) {
			return;
		}
		listener.onPaintComplete();
	}

	private class Animator implements ActionListener {

		private boolean isDrawing;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (isDrawing) {
				// Already drawing another frame that is taking too long. Skip this frame.
				nextFrame();
				return;
			}

			isDrawing = true;
			if (verifyFrameNumber() == false) {
				isDrawing = false;
				return;
			}

			log.info("Animation frame.");

			// Notify the listener that a frame can be painted.
			notifyListener();

			nextFrame();
			verifyFrameNumber();
			isDrawing = false;
		}

		private boolean verifyFrameNumber() {
			if (currentFrame.get() >= frames.size()) {
				resetFrameCounter();
				if (!loop) {
					timer.stop();
				}
				return false;
			}
			return true;
		}

	}

}
