package no.helgeby.hangman.gui.painter;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class ManReleasedPainter extends Painter {

	public ManReleasedPainter(CanvasProperties properties) {
		super(properties);
	}

	@Override
	public void paint(Graphics2D g) {
		switch (stage) {
		case 0:
			stage0(g, p);
			break;
		case 1:
			stage1(g, p);
			break;
		case 2:
			stage2(g, p);
			break;
		case 3:
			stage3(g, p);
			break;
		}
	}

	private void stage0(Graphics2D g, CanvasProperties p) {
		// Start with a man on the gallows.

		prepareDrawing(g);

		// Rope.
		x1 = 400 * scale + headMid + offsetX;
		y1 = 100 * scale + offsetY;
		x2 = 400 * scale + headMid + offsetX;
		y2 = 175 * scale - (headSize / 2) + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));

		// Head.
		x1 = 400 * scale + offsetX;
		y1 = 150 * scale + offsetY;
		g.drawOval((int) x1, (int) y1, headSize, headSize);

		// Neck.
		x1 = 400 * scale + headMid + offsetX;
		y1 = 150 * scale + headSize + offsetY;
		x2 = 400 * scale + headMid + offsetX;
		y2 = 175 * scale + headSize + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));

		// Left arm.
		x1 = 400 * scale + headMid + offsetX;
		y1 = 175 * scale + headSize + offsetY;
		x2 = 350 * scale + headMid + offsetX;
		y2 = 225 * scale + headSize + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));

		// Right arm.
		x1 = 400 * scale + headMid + offsetX;
		y1 = 175 * scale + headSize + offsetY;
		x2 = 450 * scale + headMid + offsetX;
		y2 = 225 * scale + headSize + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));

		// Body.
		x1 = 400 * scale + headMid + offsetX;
		y1 = 175 * scale + headSize + offsetY;
		x2 = 400 * scale + headMid + offsetX;
		y2 = 275 * scale + headSize + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));

		// Left leg.
		x1 = 400 * scale + headMid + offsetX;
		y1 = 275 * scale + headSize + offsetY;
		x2 = 350 * scale + headMid + offsetX;
		y2 = 375 * scale + headSize + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));

		// Right leg.
		x1 = 400 * scale + headMid + offsetX;
		y1 = 275 * scale + headSize + offsetY;
		x2 = 450 * scale + headMid + offsetX;
		y2 = 375 * scale + headSize + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));
	}

	private void stage1(Graphics2D g, CanvasProperties p) {
		// Man is on the ground with arms in the air.

		prepareDrawing(g);

		// Rope.
		x1 = 400 * scale + headMid + offsetX;
		y1 = 100 * scale + offsetY;
		x2 = 400 * scale + headMid + offsetX;
		y2 = 175 * scale - (headSize / 2) + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));

		// Head.
		x1 = 400 * scale + offsetX;
		y1 = 225 * scale + offsetY;
		g.drawOval((int) x1, (int) y1, headSize, headSize);

		// Neck.
		x1 = 400 * scale + headMid + offsetX;
		y1 = 225 * scale + headSize + offsetY;
		x2 = 400 * scale + headMid + offsetX;
		y2 = 250 * scale + headSize + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));

		// Left arm.
		x1 = 400 * scale + headMid + offsetX;
		y1 = 250 * scale + headSize + offsetY;
		x2 = 350 * scale + headMid + offsetX;
		y2 = 200 * scale + headSize + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));

		// Right arm.
		x1 = 400 * scale + headMid + offsetX;
		y1 = 250 * scale + headSize + offsetY;
		x2 = 450 * scale + headMid + offsetX;
		y2 = 200 * scale + headSize + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));

		// Body.
		x1 = 400 * scale + headMid + offsetX;
		y1 = 225 * scale + headSize + offsetY;
		x2 = 400 * scale + headMid + offsetX;
		y2 = 350 * scale + headSize + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));

		// Left leg.
		x1 = 400 * scale + headMid + offsetX;
		y1 = 350 * scale + headSize + offsetY;
		x2 = 350 * scale + headMid + offsetX;
		y2 = 450 * scale + headSize + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));

		// Right leg.
		x1 = 400 * scale + headMid + offsetX;
		y1 = 350 * scale + headSize + offsetY;
		x2 = 450 * scale + headMid + offsetX;
		y2 = 450 * scale + headSize + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));
	}

	private void stage2(Graphics2D g, CanvasProperties p) {
		// Man is falling straight down.

		prepareDrawing(g);

		// Rope.
		x1 = 400 * scale + headMid + offsetX;
		y1 = 100 * scale + offsetY;
		x2 = 400 * scale + headMid + offsetX;
		y2 = 175 * scale - (headSize / 2) + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));

		// Head.
		x1 = 400 * scale + offsetX;
		y1 = 325 * scale + offsetY;
		g.drawOval((int) x1, (int) y1, headSize, headSize);

		// Face.
		x1 = 410 * scale + offsetX;
		y1 = 340 * scale + offsetY;
		x2 = 400 * scale + headSize - (10 * scale) + offsetX;
		y2 = 305 * scale + headSize + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));
		x1 = 410 * scale + offsetX;
		y1 = 305 * scale + headSize + offsetY;
		x2 = 400 * scale + headSize - (10 * scale) + offsetX;
		y2 = 340 * scale + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));

		// Neck.
		x1 = 400 * scale + headMid + offsetX;
		y1 = 325 * scale + headSize + offsetY;
		x2 = 400 * scale + headMid + offsetX;
		y2 = 350 * scale + headSize + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));

		// Left arm.
		x1 = 400 * scale + headMid + offsetX;
		y1 = 350 * scale + headSize + offsetY;
		x2 = 350 * scale + headMid + offsetX;
		y2 = 300 * scale + headSize + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));

		// Right arm.
		x1 = 400 * scale + headMid + offsetX;
		y1 = 350 * scale + headSize + offsetY;
		x2 = 450 * scale + headMid + offsetX;
		y2 = 300 * scale + headSize + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));

		// Body.
		x1 = 400 * scale + headMid + offsetX;
		y1 = 325 * scale + headSize + offsetY;
		x2 = 400 * scale + headMid + offsetX;
		y2 = 425 * scale + headSize + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));

		// Left leg bending.
		x1 = 400 * scale + headMid + offsetX;
		y1 = 425 * scale + headSize + offsetY;
		x2 = 325 * scale + headMid + offsetX;
		y2 = 450 * scale + headSize + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));
		x1 = 375 * scale + headMid + offsetX;
		y1 = 450 * scale + headSize + offsetY;
		x2 = 325 * scale + headMid + offsetX;
		y2 = 450 * scale + headSize + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));

		// Right leg bending.
		x1 = 400 * scale + headMid + offsetX;
		y1 = 425 * scale + headSize + offsetY;
		x2 = 475 * scale + headMid + offsetX;
		y2 = 450 * scale + headSize + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));
		x1 = 475 * scale + headMid + offsetX;
		y1 = 450 * scale + headSize + offsetY;
		x2 = 425 * scale + headMid + offsetX;
		y2 = 450 * scale + headSize + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));
	}

	private void stage3(Graphics2D g, CanvasProperties p) {
		// Man is dead on the ground.
		prepareDrawing(g);

		// Rope.
		x1 = 400 * scale + headMid + offsetX;
		y1 = 100 * scale + offsetY;
		x2 = 400 * scale + headMid + offsetX;
		y2 = 175 * scale - (headSize / 2) + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));

		// Head.
		x1 = 350 * scale + offsetX;
		y1 = 400 * scale + offsetY;
		g.drawOval((int) x1, (int) y1, headSize, headSize);

		// Face.
		x1 = 360 * scale + offsetX;
		y1 = 415 * scale + offsetY;
		x2 = 350 * scale + headSize - (10 * scale) + offsetX;
		y2 = 375 * scale + headSize + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));
		x1 = 360 * scale + offsetX;
		y1 = 380 * scale + headSize + offsetY;
		x2 = 350 * scale + headSize - (10 * scale) + offsetX;
		y2 = 410 * scale + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));

		// Neck.
		x1 = 375 * scale + headMid + offsetX;
		y1 = 400 * scale + headSize + offsetY;
		x2 = 365 * scale + headMid + offsetX;
		y2 = 390 * scale + headSize + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));

		// Left arm.
		x1 = 375 * scale + headMid + offsetX;
		y1 = 400 * scale + headSize + offsetY;
		x2 = 330 * scale + headMid + offsetX;
		y2 = 415 * scale + headSize + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));

		// Right arm.
		x1 = 375 * scale + headMid + offsetX;
		y1 = 400 * scale + headSize + offsetY;
		x2 = 425 * scale + headMid + offsetX;
		y2 = 410 * scale + headSize + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));

		// Body.
		x1 = 400 * scale + headMid + offsetX;
		y1 = 425 * scale + headSize + offsetY;
		x2 = 375 * scale + headMid + offsetX;
		y2 = 400 * scale + headSize + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));

		// Left leg bending.
		x1 = 400 * scale + headMid + offsetX;
		y1 = 425 * scale + headSize + offsetY;
		x2 = 325 * scale + headMid + offsetX;
		y2 = 450 * scale + headSize + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));
		x1 = 375 * scale + headMid + offsetX;
		y1 = 450 * scale + headSize + offsetY;
		x2 = 325 * scale + headMid + offsetX;
		y2 = 450 * scale + headSize + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));

		// Right leg bending.
		x1 = 400 * scale + headMid + offsetX;
		y1 = 425 * scale + headSize + offsetY;
		x2 = 475 * scale + headMid + offsetX;
		y2 = 450 * scale + headSize + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));
		x1 = 475 * scale + headMid + offsetX;
		y1 = 450 * scale + headSize + offsetY;
		x2 = 425 * scale + headMid + offsetX;
		y2 = 450 * scale + headSize + offsetY;
		g.draw(new Line2D.Float(x1, y1, x2, y2));
	}

}
