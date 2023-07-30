package no.helgeby.hangman.gui.painter.animation;

import java.awt.Graphics2D;
import java.time.Duration;

import no.helgeby.hangman.gui.painter.CanvasProperties;
import no.helgeby.hangman.gui.painter.GallowsPainter;
import no.helgeby.hangman.gui.painter.PainterListener;

public class WinningManAnimation extends Animation {

	private GallowsPainter gallowsPainter;

	public WinningManAnimation(CanvasProperties properties, PainterListener listener) {
		super(Duration.ofSeconds(1), properties, listener);
		gallowsPainter = new GallowsPainter();
		createFrames();
	}

	// TODO: Paint character being released.

	private void createFrames() {
		frame1();
		frame2();
		frame3();
	}

	private void frame1() {
		Graphics2D g = createFrame();
		gallowsPainter.paint(g, properties);
		g.drawOval(10, 10, 100, 100);
	}

	private void frame2() {
		Graphics2D g = createFrame();
		gallowsPainter.paint(g, properties);
		g.drawOval(20, 20, 100, 100);
	}

	private void frame3() {
		Graphics2D g = createFrame();
		gallowsPainter.paint(g, properties);
		g.drawOval(30, 30, 100, 100);
	}

}
