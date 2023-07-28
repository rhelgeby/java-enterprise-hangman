package no.helgeby.hangman.gui.painter;

import java.awt.Color;
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

/**
 * Paints a series of buffered images in rapid sequence.
 * <p>
 * This should work fine for low frame rates or simple animations. It is not
 * made for higher frame rates. Too high rate may result in delayed or dropped
 * frames.
 * <p>
 * Its speed is limited by how busy the Spring event-dispatch thread is, and how
 * fast the CPU can read the image from a buffer to paint on a component.
 * <p>
 * Video players often use the graphics card or some kind of hardware
 * acceleration to offload drawing. This implementation use only the CPU.
 */
public abstract class Animation implements Painter {

	private static final Logger log = LogManager.getLogger(Animation.class);

	/**
	 * The current frame number.
	 * <p>
	 * It needs to be thread safe because the animating timer may be updating it at
	 * the same time as another slow drawing task is running.
	 */
	private AtomicInteger currentFrame;
	protected Dimension size;
	protected CanvasProperties properties;
	private List<BufferedImage> frames;

	private boolean loop;
	private Timer timer;
	private PainterListener listener;

	/**
	 * Whether the second buffer is ready to draw on.
	 * <p>
	 * Double buffering reduces the chance of an incomplete drawing being rendered.
	 */
	private boolean secondBufferActive;

	private BufferedImage image1;
	private Graphics2D g1;
	private BufferedImage image2;
	private Graphics2D g2;
	private Animator animator;

	/**
	 * Creates an empty animation. Use {@link #createFrame()} to add frames to it.
	 * 
	 * @param frameTime  How long each frame is displayed.
	 * @param properties Image properties.
	 * @param listener   Object to be notified when a frame is painted.
	 */
	public Animation(Duration frameTime, CanvasProperties properties, PainterListener listener) {
		Objects.requireNonNull(frameTime, "frameTime");
		if (frameTime.toMillis() > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Too long delay.");
		}
		this.properties = Objects.requireNonNull(properties, "properties");
		size = properties.getSize();

		this.listener = listener;

		animator = new Animator();
		timer = new Timer((int) frameTime.toMillis(), animator);

		image1 = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
		g1 = image1.createGraphics();
		image2 = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
		g2 = image2.createGraphics();

		currentFrame = new AtomicInteger();
		frames = new ArrayList<>();
	}

	/**
	 * Sets the listener to be notified when a frame is done.
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

	public synchronized BufferedImage getCurrentImage() {
		return secondBufferActive ? image1 : image2;
	}

	/**
	 * Paints the current frame.
	 * 
	 * @param g       Canvas to paint on.
	 * @param scale   The scale to paint in.
	 * @param offsetX Number of pixels the image should be shifted to the right,
	 *                after scaling.
	 * @param offsetY Number of pixels the image should be shifted down, after
	 *                scaling.
	 */
	@Override
	public void paint(Graphics2D g, float scale, int offsetX, int offsetY) {
		// Draw current frame.
		BufferedImage frame = frames.get(currentFrame.get());
		g.drawImage(frame, 0, 0, null);
	}

	/**
	 * Creates and adds a frame to the animation.
	 * <p>
	 * It is prepared with a white background.
	 * 
	 * @return An object that paints on this frame.
	 */
	public Graphics2D createFrame() {
		BufferedImage frame = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = frame.createGraphics();

		g.setBackground(Color.WHITE);
		g.setColor(Color.BLACK);
		g.clearRect(0, 0, size.width, size.height);

		frames.add(frame);
		return g;
	}

	private synchronized Graphics2D getBuffer() {
		Graphics2D g = secondBufferActive ? g1 : g2;
		secondBufferActive = !secondBufferActive;
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

			log.info("Drawing animation frame.");

			// Note: It is not necessary to clear the buffer. It will draw the image over
			// the old one. The frames are not transparent.

			Graphics2D g = getBuffer();
			paint(g, 0, 0, 0);

			nextFrame();
			verifyFrameNumber();

			isDrawing = false;
			notifyListener();
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
