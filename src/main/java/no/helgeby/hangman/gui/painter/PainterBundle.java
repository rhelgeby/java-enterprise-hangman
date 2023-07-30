package no.helgeby.hangman.gui.painter;

import no.helgeby.hangman.gui.painter.animation.WinningManAnimation;
import no.helgeby.hangman.model.GallowsModel;

/**
 * Container that holds references to painters.
 */
public class PainterBundle {

	public GallowsPainter gallowsPainter;
	public ManPainter manPainter;
	public WomanPainter womanPainter;
	public GridPainter gridPainter;

	public WinningManAnimation winningManAnimation;

	public PainterBundle(GallowsModel gallows, CanvasProperties properties, PainterListener listener) {
		gallowsPainter = new GallowsPainter(properties);
		manPainter = new ManPainter(gallows, properties);
		womanPainter = new WomanPainter(gallows, properties);
		gridPainter = new GridPainter(properties);

		winningManAnimation = new WinningManAnimation(properties, listener);
	}

}
