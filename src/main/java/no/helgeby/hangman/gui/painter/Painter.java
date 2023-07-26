package no.helgeby.hangman.gui.painter;

import java.awt.Graphics2D;

/**
 * Handles painting of something.
 */
public interface Painter {

	/**
	 * Paints something on the specified graphics.
	 * 
	 * @param g       Canvas to paint on.
	 * @param scale   The scale to paint in.
	 * @param offsetX Number of pixels the image should be shifted to the right,
	 *                after scaling.
	 * @param offsetY Number of pixels the image should be shifted down, after
	 *                scaling.
	 */
	void paint(Graphics2D g, float scale, int offsetX, int offsetY);

}
