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
	private boolean isDead = false; // TODO: Refactor to stages. isDead should mean a certain stage is reached.
	private boolean isWoman = false;

	// TODO: Is character free. Use a flag for a freed character?

	public void nextStage() {
		if (isDead) {
			// Already marked dead.
			return;
		}
		stage++;
		if (isDead()) {
			isDead = true;
		}
	}

	public boolean isDead() {
		if (isDead) {
			return true;
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
		isDead = false;
	}

}
