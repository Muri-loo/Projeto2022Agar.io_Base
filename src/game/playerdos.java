package game;

import environment.Cell;
import environment.Coordinate;
import gui.BoardJComponent;

public class playerdos extends Player {

	public playerdos(int id, Game game) {
		super(id, game);
	}
	public playerdos(int id, Game game,byte a) {
		super(id, game,a);
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
