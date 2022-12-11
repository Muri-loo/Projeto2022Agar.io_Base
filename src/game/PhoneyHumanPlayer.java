package game;

import java.io.Serializable;

import environment.Cell;
import environment.Coordinate;
import environment.Direction;
import gui.BoardJComponent;

/**
 * Class to demonstrate a player being added to the game.
 * @author luismota
 *
 */
public class PhoneyHumanPlayer extends Player implements Serializable {

	Direction ToMove=null;

	public PhoneyHumanPlayer(int id, Game game, byte strength) {
		super(id, game, strength);
	}
	public PhoneyHumanPlayer(int id, Game game) {
		super(id, game);
	}

	public boolean isHumanPlayer() {
		return true;
	}

	@Override
	public  void move() throws InterruptedException {
		if(ToMove==null)
			return;
		Cell celulaPlayer=super.getCurrentCell();

		Coordinate novaCoordenada=celulaPlayer.getPosition().translate(ToMove.getVector());

		if(canMove(novaCoordenada)){
			game.getCell(novaCoordenada).setPlayer(this);
		}
		this.ToMove=null;
	}
	
	public void ToMove(Direction b) {
		this.ToMove=b;
	}









}
