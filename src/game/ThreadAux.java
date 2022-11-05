package game;

import environment.Cell;

public class ThreadAux extends Thread{

	private Game game;
	private Cell initialPos;
	private Player p;
	public ThreadAux(Game game,Cell initalPos,Player p) {
		this.game=game;
		this.p=p;
		this.initialPos=initalPos;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(Game.MAX_WAITING_TIME_FOR_MOVE);
			if(initialPos.isOcupied() && !(initialPos.getPlayer().equals(p)) )
				game.unlockPlayerCell();
			return;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
