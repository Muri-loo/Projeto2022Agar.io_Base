package game;

import environment.Cell;

public class ThreadAux extends Thread{
	
	private Game game;
	private Cell initalPos;
	public ThreadAux(Game game,Cell initalPos) {
		this.game=game;
		this.initalPos=initalPos;
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(Game.MAX_WAITING_TIME_FOR_MOVE);
			if(initalPos.isOcupied())game.unlockPlayerCell();
			return;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
