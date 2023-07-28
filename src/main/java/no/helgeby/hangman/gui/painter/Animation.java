package no.helgeby.hangman.gui.painter;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.time.Duration;
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

	public Animation(Duration frameTime, Dimension size, PainterListener listener) {
		Objects.requireNonNull(frameTime, "frameTime");
		if (frameTime.toMillis() > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Too long delay.");
		}
		Objects.requireNonNull(size, "size");
		this.listener = listener;

		animator = new Animator();
		timer = new Timer((int) frameTime.toMillis(), animator);

		image1 = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
		g1 = image1.createGraphics();
		image2 = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
		g2 = image2.createGraphics();

		currentFrame = new AtomicInteger();
	}

	public abstract List<BufferedImage> getFrames();

	public void setListener(PainterListener listener) {
		this.listener = listener;
	}

	public void start() {
		log.info("Starting animation.");

		// Paint the first frame to the buffer.
		animator.actionPerformed(null);

		// Start the timer for the rest of the frames.
		timer.start();
	}

	public void stop() {
		timer.stop();
		// TODO: Existing timer task may be running. Delay counter reset until complete?
		resetFrameCounter();
	}

	public synchronized BufferedImage getCurrentImage() {
		return secondBufferActive ? image1 : image2;
	}

	@Override
	public void paint(Graphics2D g, float scale, int offsetX, int offsetY) {
		// Draw current frame.
		BufferedImage frame = getFrames().get(currentFrame.get());
		g.drawImage(frame, 0, 0, null);
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
			if (currentFrame.get() >= getFrames().size()) {
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
