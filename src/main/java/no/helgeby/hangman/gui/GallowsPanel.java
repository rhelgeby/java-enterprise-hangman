package no.helgeby.hangman.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Objects;

import javax.swing.JPanel;

import no.helgeby.hangman.event.EmptyGameEventListener;
import no.helgeby.hangman.gui.painter.PainterManager;
import no.helgeby.hangman.model.GallowsModel;
import no.helgeby.hangman.model.GameModel;

public class GallowsPanel extends JPanel {

	private static final long serialVersionUID = 4402590040687631095L;

	private static final float SCALE = 1.0f;

	// Offset is not scaled. Adjust as necessary.
	private static final int OFFSET_X = -75;
	private static final int OFFSET_Y = -75;

	private GallowsModel gallows;

	private PainterManager painterManager;

	public GallowsPanel(GameModel model) {
		Objects.requireNonNull(model, "model");
		this.gallows = model.gallowsModel();

		Dimension size = new Dimension(450, 450);
		scale(size);
		setPreferredSize(size);

		painterManager = new PainterManager(size, gallows);
		painterManager.setScale(SCALE);
		painterManager.setOffsetX(OFFSET_X);
		painterManager.setOffsetY(OFFSET_Y);

		// Schedule a repaint whenever a buffer is ready.
		painterManager.setListener(() -> {
			repaint();
		});

		// Paint the initial drawing.
		painterManager.paint();
		repaint();

		model.addListener(new ModelListener());
	}

	private static void scale(Dimension d) {
		d.width = (int) (d.width * SCALE);
		d.height = (int) (d.height * SCALE);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(painterManager.getImage(), 0, 0, null);
	}

	private class ModelListener extends EmptyGameEventListener {

		@Override
		public void gameWon() {
			painterManager.paint();
		}

		@Override
		public void guessMade(char key) {
			painterManager.paint();
		}

		@Override
		public void newGame() {
			painterManager.paint();
		}

	}
}
