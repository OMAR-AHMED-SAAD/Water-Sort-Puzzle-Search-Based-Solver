package code;

public class State {
	private Bottle[] bottles;

	public State(Bottle[] bottles) {
		this.bottles = bottles;
	}

	public boolean isGoal() {
		for (Bottle bottle : bottles)
			if (!bottle.isAllLayersSame())
				return false;

		return true;
	}

	public State getCopy() {
		Bottle[] newBottles = new Bottle[bottles.length];
		for (int i = 0; i < bottles.length; i++)
			newBottles[i] = new Bottle(new String(bottles[i].toString()));
		return new State(newBottles);
	}

	public State getNewState(int i, int j) {
		if (i == j || !bottles[i].isPourValid(bottles[j]))
			return null;
		State newState = this.getCopy();
		newState.bottles[i].pourTo(newState.bottles[j]);
		return newState;

	}
}
