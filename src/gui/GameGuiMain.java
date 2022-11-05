package gui;

import java.util.Observable;
import java.util.Observer;

import game.BotPlayer;
import game.Game;
import game.PhoneyHumanPlayer;

import javax.swing.JFrame;

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
		
		
//		
//		BotPlayer b= new BotPlayer(4,game, (byte)4);
//		game.addPlayerToGame(b);
//		b.start();
		
		

		PhoneyHumanPlayer a =new PhoneyHumanPlayer(1, game, (byte)5);

		game.addPlayerToGame(a);
		a.start();
		
		game.addPlayerToGame(new PhoneyHumanPlayer(9, game, (byte)2));
	
		
		
		game.addPlayerToGame(new PhoneyHumanPlayer(2, game, (byte)2));
		game.addPlayerToGame(new PhoneyHumanPlayer(3, game, (byte)1));
		
		try {
			game.endGame.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
