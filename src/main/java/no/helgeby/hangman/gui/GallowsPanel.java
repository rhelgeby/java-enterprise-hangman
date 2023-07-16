package no.helgeby.hangman.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import no.helgeby.hangman.event.EmptyGameEventListener;
import no.helgeby.hangman.model.GallowsModel;
import no.helgeby.hangman.model.GameModel;

public class GallowsPanel extends JPanel {

	private static final long serialVersionUID = 4402590040687631095L;

	private static final int HEAD_SIZE = 50;
	private static final float GALLOW_THICKNESS = 5;
	private static final float DRAWING_THICKNESS = 2;

	private float scale = 1.0f;

	/**
	 * How far to fall when dropping dead, in pixels before scaling.
	 */
	private int shiftSize = 50;

	// Offset is not scaled. Adjust as necessary.
	private int offsetX = -75;
	private int offsetY = -75;

	private boolean drawGrid = false;

	private BufferedImage image;
	private Graphics2D buffer;

	private GallowsModel gallows;

	public GallowsPanel(GameModel model) {
		Objects.requireNonNull(model, "model");
		this.gallows = model.gallowsModel();

		int width = (int) (450 * scale);
		int height = (int) (450 * scale);
		setPreferredSize(new Dimension(width, height));

		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		buffer = image.createGraphics();

		model.addListener(new ModelListener());

		draw();
	}

	private void draw() {
		paintBuffer();
		SwingUtilities.invokeLater(() -> {
			repaint();
		});
	}

	private void drawGallows(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(GALLOW_THICKNESS * scale));

		float x1;
		float y1;
		float x2;
		float y2;

		// Gallows
		x1 = 100 * scale + offsetX;
		y1 = 500 * scale + offsetY;
		x2 = 300 * scale + offsetX;
		y2 = 500 * scale + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));

		x1 = 200 * scale + offsetX;
		y1 = 500 * scale + offsetY;
		x2 = 200 * scale + offsetX;
		y2 = 100 * scale + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));

		x1 = 200 * scale + offsetX;
		y1 = 100 * scale + offsetY;
		x2 = 500 * scale + offsetX;
		y2 = 100 * scale + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));

		x1 = 300 * scale + offsetX;
		y1 = 100 * scale + offsetY;
		x2 = 200 * scale + offsetX;
		y2 = 200 * scale + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));

		x1 = 150 * scale + offsetX;
		y1 = 500 * scale + offsetY;
		x2 = 200 * scale + offsetX;
		y2 = 450 * scale + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));

		x1 = 250 * scale + offsetX;
		y1 = 500 * scale + offsetY;
		x2 = 200 * scale + offsetX;
		y2 = 450 * scale + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));
	}

	private void drawPerson(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(DRAWING_THICKNESS * scale));

		int stage = gallows.getStage();
		boolean isDead = gallows.isDead();

		int headSize = (int) (50 * scale);
		int headMid = headSize / 2;
		int shiftY = isDead ? (int) (shiftSize * scale) : 0;

		float x1;
		float y1;
		float x2;
		float y2;

		if (stage >= 1) {
			// Rope.
			x1 = 400 * scale + headMid + offsetX;
			y1 = 100 * scale + offsetY;
			x2 = 400 * scale + headMid + offsetX;
			y2 = 175 * scale - (headSize / 2) + shiftY + offsetY;
			g.draw(new Line2D.Float(x1, y1, x2, y2));
		}

		if (stage >= 2) {
			// Head.
			x1 = 400 * scale + offsetX;
			y1 = 150 * scale + shiftY + offsetY;
			g.drawOval((int) x1, (int) y1, headSize, headSize);
		}

		if (stage >= 2 && isDead) {
			// Face.
			x1 = 410 * scale + offsetX;
			y1 = 165 * scale + shiftY + offsetY;
			x2 = 400 * scale + headSize - (10 * scale) + offsetX;
			y2 = 130 * scale + headSize + shiftY + offsetY;
			g.draw(new Line2D.Float(x1, y1, x2, y2));
			x1 = 410 * scale + offsetX;
			y1 = 130 * scale + headSize + shiftY + offsetY;
			x2 = 400 * scale + headSize - (10 * scale) + offsetX;
			y2 = 165 * scale + shiftY + offsetY;
			g.draw(new Line2D.Float(x1, y1, x2, y2));
		}

		if (stage >= 3) {
			// Neck.
			x1 = 400 * scale + headMid + offsetX;
			y1 = 150 * scale + headSize + shiftY + offsetY;
			x2 = 400 * scale + headMid + offsetX;
			y2 = 175 * scale + headSize + shiftY + offsetY;
			g.draw(new Line2D.Float(x1, y1, x2, y2));
		}

		if (stage >= 4) {
			// Left arm.
			x1 = 400 * scale + headMid + offsetX;
			y1 = 175 * scale + headSize + shiftY + offsetY;
			x2 = 350 * scale + headMid + offsetX;
			y2 = 225 * scale + headSize + shiftY + offsetY;
			g.draw(new Line2D.Float(x1, y1, x2, y2));
		}

		if (stage >= 5) {
			// Right arm.
			x1 = 400 * scale + headMid + offsetX;
			y1 = 175 * scale + headSize + shiftY + offsetY;
			x2 = 450 * scale + headMid + offsetX;
			y2 = 225 * scale + headSize + shiftY + offsetY;
			g.draw(new Line2D.Float(x1, y1, x2, y2));
		}

		if (gallows.isWoman()) {
			drawWoman(g);
		} else {
			drawMan(g);
		}
	}

	private void drawMan(Graphics2D g) {
		int headSize = (int) (HEAD_SIZE * scale);
		int bodyMid = headSize / 2;
		int shiftY = gallows.isDead() ? (int) (shiftSize * scale) : 0;

		float x1;
		float y1;
		float x2;
		float y2;

		int stage = gallows.getStage();

		if (stage >= 6) {
			// Body.
			x1 = 400 * scale + bodyMid + offsetX;
			y1 = 175 * scale + headSize + shiftY + offsetY;
			x2 = 400 * scale + bodyMid + offsetX;
			y2 = 275 * scale + headSize + shiftY + offsetY;
			g.draw(new Line2D.Float(x1, y1, x2, y2));
		}

		if (stage >= 7) {
			// Left leg.
			x1 = 400 * scale + bodyMid + offsetX;
			y1 = 275 * scale + headSize + shiftY + offsetY;
			x2 = 350 * scale + bodyMid + offsetX;
			y2 = 375 * scale + headSize + shiftY + offsetY;
			g.draw(new Line2D.Float(x1, y1, x2, y2));
		}

		if (stage >= 8) {
			// Right leg.
			x1 = 400 * scale + bodyMid + offsetX;
			y1 = 275 * scale + headSize + shiftY + offsetY;
			x2 = 450 * scale + bodyMid + offsetX;
			y2 = 375 * scale + headSize + shiftY + offsetY;
			g.draw(new Line2D.Float(x1, y1, x2, y2));
		}
	}

	private void drawWoman(Graphics2D g) {
		int headSize = (int) (50 * scale);
		int bodyHorizontalMid = headSize / 2;
		int shiftY = gallows.isDead() ? (int) (shiftSize * scale) : 0;

		float x1;
		float y1;
		float x2;
		float y2;

		int stage = gallows.getStage();

		// Body.
		if (stage >= 6) {
			x1 = 400 * scale + bodyHorizontalMid + offsetX;
			y1 = 175 * scale + headSize + shiftY + offsetY;
			x2 = 375 * scale + bodyHorizontalMid + offsetX;
			y2 = 325 * scale + headSize + shiftY + offsetY;
			g.draw(new Line2D.Float(x1, y1, x2, y2));
		}
		if (stage >= 7) {
			x1 = 400 * scale + bodyHorizontalMid + offsetX;
			y1 = 175 * scale + headSize + shiftY + offsetY;
			x2 = 425 * scale + bodyHorizontalMid + offsetX;
			y2 = 325 * scale + headSize + shiftY + offsetY;
			g.draw(new Line2D.Float(x1, y1, x2, y2));
		}
		if (stage >= 8) {
			x1 = 400 * scale + offsetX;
			y1 = 325 * scale + headSize + shiftY + offsetY;
			x2 = 450 * scale + offsetX;
			y2 = 325 * scale + headSize + shiftY + offsetY;
			g.draw(new Line2D.Float(x1, y1, x2, y2));
		}

		if (stage >= 9) {
			// Left leg.
			x1 = 390 * scale + bodyHorizontalMid + offsetX;
			y1 = 325 * scale + headSize + shiftY + offsetY;
			x2 = 390 * scale + bodyHorizontalMid + offsetX;
			y2 = 350 * scale + headSize + shiftY + offsetY;
			g.draw(new Line2D.Float(x1, y1, x2, y2));
		}

		if (stage >= 10) {
			// Right leg.
			x1 = 410 * scale + bodyHorizontalMid + offsetX;
			y1 = 325 * scale + headSize + shiftY + offsetY;
			x2 = 410 * scale + bodyHorizontalMid + offsetX;
			y2 = 350 * scale + headSize + shiftY + offsetY;
			g.draw(new Line2D.Float(x1, y1, x2, y2));
		}
	}

	private void paintBuffer() {
		buffer.setBackground(Color.WHITE);
		buffer.clearRect(0, 0, image.getWidth(), image.getHeight());

		if (drawGrid) {
			// Draw grid at the bottom layer.
			drawGrid(buffer);
		}

		drawGallows(buffer);
		drawPerson(buffer);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}

	private void drawGrid(Graphics2D g) {
		int width = image.getWidth();
		int height = image.getHeight();
		int gridSize = 10;

		g.setStroke(new BasicStroke(1.0f));

		for (int x = 0; x < width; x += gridSize) {
			for (int y = 0; y < height; y += gridSize) {
				if (x % 100 == 0 && y % 100 == 0) {
					g.setColor(Color.RED);
					g.fillRect(x, y, 3, 3);
				} else {
					g.setColor(Color.BLUE);
					g.drawRect(x, y, 1, 1);
				}
			}
		}
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
