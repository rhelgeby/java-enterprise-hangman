package no.helgeby.hangman.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

import javax.swing.JPanel;
import no.helgeby.hangman.event.EmptyGameEventListener;
import no.helgeby.hangman.gui.painter.GallowsPainter;
import no.helgeby.hangman.gui.painter.GridPainter;
import no.helgeby.hangman.gui.painter.ManPainter;
import no.helgeby.hangman.gui.painter.WomanPainter;
import no.helgeby.hangman.model.Difficulty;
import no.helgeby.hangman.model.DrawingType;
import no.helgeby.hangman.model.GallowsModel;
import no.helgeby.hangman.model.GameModel;

public class GallowsPanel extends JPanel {

	private static final long serialVersionUID = 4402590040687631095L;

	private float scale = 1.0f;

	// Offset is not scaled. Adjust as necessary.
	private int offsetX = -75;
	private int offsetY = -75;

	private boolean drawGrid = false;

	private BufferedImage image;
	private Graphics2D buffer;

	private GallowsModel gallows;
	private GallowsPainter gallowsPainter;
	private ManPainter manPainter;
	private WomanPainter womanPainter;
	private GridPainter gridPainter;

	public GallowsPanel(GameModel model) {
		Objects.requireNonNull(model, "model");
		this.gallows = model.gallowsModel();

		int width = (int) (450 * scale);
		int height = (int) (450 * scale);
		setPreferredSize(new Dimension(width, height));

		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		buffer = image.createGraphics();

		gallowsPainter = new GallowsPainter();
		manPainter = new ManPainter(gallows);
		womanPainter = new WomanPainter(gallows);
		gridPainter = new GridPainter(width, height);

		model.addListener(new ModelListener());

		draw();
	}

	private void draw() {
		paintBuffer();
		repaint();
	}

	private void drawPerson(Graphics2D g) {
		if (gallows.getCurrentDrawingType() == DrawingType.WOMAN) {
			womanPainter.paint(g, scale, offsetX, offsetY);
		} else {
			manPainter.paint(g, scale, offsetX, offsetY);
		}
	}

	private void paintBuffer() {
		buffer.setBackground(Color.WHITE);
		buffer.clearRect(0, 0, image.getWidth(), image.getHeight());

		if (drawGrid) {
			gridPainter.paint(buffer, scale, offsetX, offsetY);
		}

		gallowsPainter.paint(buffer, scale, offsetX, offsetY);
		drawPerson(buffer);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}

	private class ModelListener extends EmptyGameEventListener {

		@Override
		public void gameWon() {
			draw();
			// TODO: Draw a winning character.
		}

		@Override
		public void guessMade(char key) {
			draw();
		}

		@Override
		public void newGame() {
			draw();
		}

	}
}
