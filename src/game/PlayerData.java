package game;

import java.io.Serializable;

import environment.Coordinate;

public class PlayerData implements Serializable {


	public Coordinate cord;
	public byte strength;
	public boolean human;
	public int id;
	
	public PlayerData(Coordinate cord, byte strength, boolean human, int id) {
		super();
		this.cord = cord;
		this.strength = strength;
		this.human = human;
		this.id = id;
	}

	public PlayerData(Player p) {
		this.cord = p.getCurrentCell().getPosition();
		this.strength = p.getCurrentStrength();
		this.human = p.isHumanPlayer();
		this.id=p.getIdentification();
	}
	
}
