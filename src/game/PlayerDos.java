package game;

import java.io.Serializable;

import environment.Cell;
import environment.Coordinate;
import gui.BoardJComponent;

public class PlayerDos extends Player implements Serializable {

	public PlayerDos(int id, Game game) {
		super(id, game);
	}
	
	public PlayerDos(int id, Game game,byte a) {
		super(id, game,a);
	}

	@Override
	public boolean isHumanPlayer() {
		return true;
	}

	@Override
	public  void move() throws InterruptedException {
		BoardJComponent key = game.getTeclado();
		if(key.getLastPressedDirectionDos()==null)
			return;
		Cell celulaPlayer=super.getCurrentCell();
		Coordinate novaCoordenada=celulaPlayer.getPosition().translate(key.getLastPressedDirectionDos().getVector());
		if(canMove(novaCoordenada)){
			game.getCell(novaCoordenada).setPlayer(this);;
			key.clearLastPressedDirectionDos();;
		}
	}
}
