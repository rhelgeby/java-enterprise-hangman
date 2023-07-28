package no.helgeby.hangman.gui.painter;

import java.awt.Graphics2D;

/**
 * Handles painting of something.
 */
public interface Painter {

	/**
	 * Paints something on the specified graphics.
	 * 
	 * @param g                Canvas to paint on.
	 * @param canvasProperties Scale and size information about the canvas.
	 */
	void paint(Graphics2D g, CanvasProperties properties);

}
