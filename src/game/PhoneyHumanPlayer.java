package game;

import environment.Cell;
import environment.Coordinate;
import environment.Direction;
import gui.BoardJComponent;

/**
 * Class to demonstrate a player being added to the game.
 * @author luismota
 *
 */
public class PhoneyHumanPlayer extends Player {
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
	public  void move() {
		BoardJComponent key = game.getTeclado();
		if(key.getLastPressedDirection()==null)
			return;
		Cell celulaPlayer=super.getCurrentCell();
		Coordinate novaCoordenada=celulaPlayer.getPosition().translate(key.getLastPressedDirection().getVector());
		if(canMove(novaCoordenada)){
			celulaPlayer.ClearCell();
			game.getCell(novaCoordenada).setPlayer(this);;
			key.clearLastPressedDirection();
		}
	}










}
