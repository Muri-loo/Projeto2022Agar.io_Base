package gui;

import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import game.BotPlayer;
import game.Game;
import game.PhoneyHumanPlayer;
import game.playerdos;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GameGuiMain implements Observer {
	private JFrame frame = new JFrame("pcd.io");
	private BoardJComponent boardGui;
	private Game game;

	public GameGuiMain() {
		super();
		game = new Game();
		game.addObserver(this);
		buildGui();

	}

	private void buildGui() {
		boardGui = new BoardJComponent(game);
		frame.add(boardGui);
		game.setTeclado(boardGui);


		frame.setSize(800,800);
		frame.setLocation(0, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void init() throws InterruptedException  {
		frame.setVisible(true);

		// Demo players, should be deleted
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		PhoneyHumanPlayer a =new PhoneyHumanPlayer(90, game,(byte)1);
//		game.addPlayerToGame(a);
//		a.start();
//
//	
//	
////
//		playerdos d= new playerdos(18,game,(byte)1);
//		game.addPlayerToGame(d);
//		d.start();

		

		 ExecutorService pool = Executors.newFixedThreadPool(70);
		 for(int i=0; i<45; i++){
				BotPlayer b= new BotPlayer(i,game);
				game.addPlayerToGame(b);
				pool.submit(b);
			}

		
		try {
			game.endgame.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			 pool.shutdownNow();
			JOptionPane.showMessageDialog(frame, "Jogo acabou");	
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		}
		
	}

	@Override
	public void update(Observable o, Object arg) {
		boardGui.repaint();
	}

	public static void main(String[] args) throws InterruptedException {
		GameGuiMain game = new GameGuiMain();
		game.init();

	}

}
