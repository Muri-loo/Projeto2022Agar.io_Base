package game;


public class WakeUp extends Thread {

	Thread p;
	public WakeUp(Thread p) {
		this.p=p;
	}
	@Override
	public void run() {
		try {
			Thread.sleep(Game.MAX_WAITING_TIME_FOR_MOVE);
			p.interrupt();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		

	}
}
