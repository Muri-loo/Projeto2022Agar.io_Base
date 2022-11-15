package game;

import environment.Cell;
import environment.Coordinate;
import environment.Direction;
import gui.BoardJComponent;

public class BotPlayer extends Player {

	public BotPlayer(int id, Game game, byte strength) {
		super(id, game, strength);
		// TODO Auto-generated constructor stub
	}
	public BotPlayer(int id, Game game) {
		super(id, game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isHumanPlayer() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void move() {
		Cell celulaPlayer=super.getCurrentCell();
		Coordinate novaCoordenada=celulaPlayer.getPosition().translate(Direction.RandomVector().getVector());
		if(canMove(novaCoordenada)){
			celulaPlayer.ClearCell();
			game.getCell(novaCoordenada).setPlayer(this);
		}
	}

}
