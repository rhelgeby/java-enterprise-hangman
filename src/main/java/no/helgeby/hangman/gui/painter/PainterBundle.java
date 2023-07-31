package no.helgeby.hangman.gui.painter;

import no.helgeby.hangman.gui.painter.animation.WinningManAnimation;
import no.helgeby.hangman.model.GallowsModel;

/**
 * Container that holds references to painters.
 */
public class PainterBundle {

	public GridPainter gridPainter;
	public GallowsPainter gallowsPainter;
	public ManPainter manPainter;
	public WomanPainter womanPainter;
	public ManReleasedPainter manReleasedPainter;

	public WinningManAnimation winningManAnimation;

	public PainterBundle(GallowsModel gallows, CanvasProperties properties, PainterListener listener) {
		gridPainter = new GridPainter(properties);
		gallowsPainter = new GallowsPainter(properties);
		manPainter = new ManPainter(gallows, properties);
		womanPainter = new WomanPainter(gallows, properties);
		manReleasedPainter = new ManReleasedPainter(properties);

		winningManAnimation = new WinningManAnimation(properties, listener);
	}

}
