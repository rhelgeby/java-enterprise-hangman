package no.helgeby.hangman.gui;

import java.awt.Graphics;
import java.util.Objects;

import javax.swing.JPanel;

import no.helgeby.hangman.event.EmptyGameEventListener;
import no.helgeby.hangman.gui.painter.CanvasProperties;
import no.helgeby.hangman.gui.painter.PainterManager;
import no.helgeby.hangman.model.GallowsModel;
import no.helgeby.hangman.model.GameModel;

public class GallowsPanel extends JPanel {

	private static final long serialVersionUID = 4402590040687631095L;

	private GallowsModel gallows;

	private PainterManager painterManager;

	public GallowsPanel(GameModel model) {
		Objects.requireNonNull(model, "model");
		this.gallows = model.gallowsModel();

		CanvasProperties canvasProperties = new CanvasProperties();
		setPreferredSize(canvasProperties.getSize());

		painterManager = new PainterManager(canvasProperties, gallows);

		// Schedule a repaint whenever a buffer is ready.
		painterManager.setListener(() -> {
			repaint();
		});

		// Paint the initial drawing.
		painterManager.paint();
		repaint();

		model.addListener(new ModelListener());
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
