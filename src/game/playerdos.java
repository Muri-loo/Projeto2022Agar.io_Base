package game;

import environment.Cell;
import environment.Coordinate;
import gui.BoardJComponent;

public class PlayerDos extends Player {

	public PlayerDos(int id, Game game) {
		super(id, game);
	}

	@Override
	public boolean isHumanPlayer() {
		return true;
	}

	@Override
	public  void move() {
		BoardJComponent key = game.getTeclado();
		if(key.lastPressedDirectiondos()==null)
			return;
		Cell celulaPlayer=super.getCurrentCell();
		Coordinate novaCoordenada=celulaPlayer.getPosition().translate(key.lastPressedDirectiondos().getVector());
		if(canMove(novaCoordenada)){
			celulaPlayer.ClearCell();
			game.getCell(novaCoordenada).setPlayer(this);;
			key.clearlastPressedDirectiondos();
		}
	}
}
