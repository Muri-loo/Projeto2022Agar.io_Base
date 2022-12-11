package environment;

import java.io.Serializable;

public class Coordinate implements Serializable{
	public final int x;
	public final int y;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	@Override
	public boolean equals(Object otherObject) {
		Coordinate other = (Coordinate) otherObject;
		return other.x==this.x && other.y ==this.y;
	}
	
	public double distanceTo(Coordinate other) {
		double dx = y - other.y;
		double dy = x - other.x;
		return Math.sqrt(dx * dx + dy * dy);
	}
	
//	public Coordinate addCordinates(Coordinate a) {
//		return new Coordinate(a.x+this.x,this.y+y);
//	}

	@Override
	public int hashCode() {
		return this.x*this.y;
	}
	
	public Coordinate translate(Coordinate vector) {
		return new Coordinate(x+vector.x, y+vector.y);
	}
}
