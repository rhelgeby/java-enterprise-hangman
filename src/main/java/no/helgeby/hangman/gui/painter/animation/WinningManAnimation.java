package no.helgeby.hangman.gui.painter.animation;

import java.awt.Graphics2D;
import java.time.Duration;

import no.helgeby.hangman.gui.painter.CanvasProperties;
import no.helgeby.hangman.gui.painter.GallowsPainter;
import no.helgeby.hangman.gui.painter.ManReleasedPainter;
import no.helgeby.hangman.gui.painter.PainterListener;

public class WinningManAnimation extends Animation {

	private GallowsPainter gallowsPainter;
	private ManReleasedPainter manReleasedPainter;

	public WinningManAnimation(CanvasProperties properties, PainterListener listener) {
		super(Duration.ofSeconds(1), properties, listener);
		gallowsPainter = new GallowsPainter(p);
		manReleasedPainter = new ManReleasedPainter(p);

		createFrames();
	}

	private void createFrames() {
		frame1();
		frame2();
		frame3();
		frame4();
		frame5();
		frame6();
		frame7();
		frame8();
	}

	private Graphics2D prepareFrame() {
		Graphics2D g = createFrame();
		gallowsPainter.paint(g);
		return g;
	}

	private void frame1() {
		// Start with a man on the gallows.

		Graphics2D g = prepareFrame();
		manReleasedPainter.stage = 0;
		manReleasedPainter.paint(g);
	}

	private void frame2() {
		// TODO: Another person show up.

		Graphics2D g = prepareFrame();
		manReleasedPainter.stage = 0;
		manReleasedPainter.paint(g);
	}

	private void frame3() {
		// TODO: Person shoots the rope.

		Graphics2D g = prepareFrame();
		manReleasedPainter.stage = 1;
		manReleasedPainter.paint(g);
	}

	private void frame4() {
		// TODO: Person shoots the man.

		Graphics2D g = prepareFrame();
		manReleasedPainter.stage = 2;
		manReleasedPainter.paint(g);
	}

	private void frame5() {
		// TODO: Man falls down on the ground.

		Graphics2D g = prepareFrame();
		manReleasedPainter.stage = 3;
		manReleasedPainter.paint(g);
	}

	private void frame6() {
		// TODO: Person start walking out of the frame.

		Graphics2D g = prepareFrame();
		manReleasedPainter.stage = 3;
		manReleasedPainter.paint(g);
	}

	private void frame7() {
		// TODO: Person leaves the frame.

		Graphics2D g = prepareFrame();
		manReleasedPainter.stage = 3;
		manReleasedPainter.paint(g);
	}

	private void frame8() {
		// TODO: Dead body on the ground.

		Graphics2D g = prepareFrame();
		manReleasedPainter.stage = 3;
		manReleasedPainter.paint(g);
	}
}
