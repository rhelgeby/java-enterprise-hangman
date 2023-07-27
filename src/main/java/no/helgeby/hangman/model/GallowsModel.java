package no.helgeby.hangman.model;

public class GallowsModel {

	/**
	 * The last valid stage for drawing a man.
	 */
	public static final int LAST_MAN_STAGE = 9;

	/**
	 * The last valid stage for drawing a woman.
	 */
	public static final int LAST_WOMMAN_STAGE = 11;

	private int stage = 0;
	private boolean isFree = false;
	private DrawingType currentDrawingType;
	private DrawingType nextDrawingType;

	public GallowsModel() {
		currentDrawingType = DrawingType.MAN;
	}

	public void nextStage() {
		if (isDead() || isFree) {
			// Game finished.
			return;
		}
		stage++;
	}

	public void free() {
		isFree = true;
	}

	public boolean isDead() {
		if (isFree) {
			return false;
		} else if (currentDrawingType == DrawingType.WOMAN) {
			return stage >= LAST_WOMMAN_STAGE;
		} else {
			return stage >= LAST_MAN_STAGE;
		}
	}

	public DrawingType getCurrentDrawingType() {
		return currentDrawingType;
	}

	public void setNextDrawingType(DrawingType drawingType) {
		nextDrawingType = drawingType;
	}

	public int getStage() {
		return stage;
	}

	public void reset() {
		stage = 0;
		isFree = false;
		if (nextDrawingType != null) {
			currentDrawingType = nextDrawingType;
		}
	}

}
