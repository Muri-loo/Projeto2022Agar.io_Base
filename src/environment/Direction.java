package environment;

import java.io.Serializable;

public enum Direction implements Serializable {
	UP(0,-1),DOWN(0,1),LEFT(-1,0),RIGHT(1,0);
	private Coordinate vector;

	Direction(int x, int y) {
		vector=new Coordinate(x, y);
	}

	public Coordinate getVector() {
		return vector;
	}
	
	public static Direction RandomVector() {
		switch((int)((Math.random()*4)+1)) {
		case 1:
			return UP;
		case 2:
			return DOWN;
		case 3:
			return LEFT;
		}
		return RIGHT;
	}



}
