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

//	public static String dirToString(Direction a) {
//		if(a==Direction.UP)
//			return "UP";
//		else if(a==Direction.DOWN)
//			return "DOWN";
//		else if(a==Direction.LEFT)
//			return "LEFT";
//		else if(a==Direction.RIGHT)
//			return "RIGHT";
//		return "NULA";
//	}

	public static Direction stringToDir(String a) {
		if(a.equals("UP"))
			return Direction.UP;
		else if(a.equals("DOWN"))
			return Direction.DOWN;
		else if(a.equals("LEFT"))
			return Direction.LEFT;
		else if(a.equals("RIGHT"))
			return Direction.RIGHT;
		return null;
	}
	
	public static void main(String[] args) {
	System.out.println(Direction.RIGHT);

	}


}
