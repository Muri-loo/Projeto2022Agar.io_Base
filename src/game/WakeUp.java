package game;

public class WakeUp extends Thread{

	Thread p;
	public WakeUp(Thread p) {
		this.p=p;
	}
	@Override
	public void run() {
		try {
			Thread.sleep(2000);
			p.interrupt();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		

	}
}
