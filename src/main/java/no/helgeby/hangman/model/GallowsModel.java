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
	private boolean isWoman = false;

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
		} else if (isWoman) {
			return stage >= LAST_WOMMAN_STAGE;
		} else {
			return stage >= LAST_MAN_STAGE;
		}
	}

	public boolean isWoman() {
		return isWoman;
	}

	public void setWoman(boolean isWoman) {
		this.isWoman = isWoman;
		reset();
	}

	public int getStage() {
		return stage;
	}

	public void reset() {
		stage = 0;
		isFree = false;
	}

}
