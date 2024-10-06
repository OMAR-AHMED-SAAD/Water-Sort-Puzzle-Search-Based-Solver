package code;

public class State {
	Bottle[] bottles;
	int costFromParent;

	public State(Bottle[] bottles) {
		this.bottles = bottles;
	}

	public State(String stateDescription) {
		String[] state = stateDescription.split(";");
		int numOfBottles = Integer.parseInt(state[0]);
		Bottle[] bottles = new Bottle[numOfBottles];
		for (int i = 0; i < numOfBottles; i++)
			bottles[i] = new Bottle(state[i + 2]);
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
		newState.costFromParent = newState.bottles[i].pourTo(newState.bottles[j]);
		return newState;

	}

//	 get the number of layers remaining to move for each bottle to be of the same color
	public int getHeuristic1() {
		int h = 0;

		for (Bottle bottle : bottles)
			h += bottle.layersRemToSameColor();

		return h;
	}

	public int getHeuristic2() {
		int h = 0;
		for (Bottle bottle : bottles)
			if (!bottle.isAllLayersSame())
				h++;
		return h;
	}

	@Override
	public boolean equals(Object obj) {
		State state = (State) obj;
		for (int i = 0; i < bottles.length; i++)
			if (!bottles[i].equals(state.bottles[i]))
				return false;
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		for (int i = 0; i < bottles.length; i++)
			hash += bottles[i].hashCode() * (i + 1) * 31;
		return hash;
	}

	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < bottles.length; i++)
			str += "Bottle" + (i + 1) + ": " + bottles[i].toString() + " ";
		return str;
	}

}
