package game;

import environment.Cell;

public class ThreadAux extends Thread{

	private Cell initialPos;
	private Player p;
	public ThreadAux(Game game,Cell initalPos,Player p) {
	
		this.p=p;
		this.initialPos=initalPos;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(Game.MAX_WAITING_TIME_FOR_MOVE);
			initialPos.PlayerInPositionWake();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
