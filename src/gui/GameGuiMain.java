package gui;

import java.util.Observable;
import java.util.Observer;

import game.BotPlayer;
import game.Game;
import game.PhoneyHumanPlayer;
import game.playerdos;

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
		PhoneyHumanPlayer a =new PhoneyHumanPlayer(1, game, (byte)1);
		game.addPlayerToGame(a);
		a.start();

//				BotPlayer b= new BotPlayer(4,game);
//				game.addPlayerToGame(b);
//				b.start();
//				BotPlayer c= new BotPlayer(12,game);
//				game.addPlayerToGame(c);
//				c.start();
		playerdos d= new playerdos(18,game);
		game.addPlayerToGame(d);
		d.start();

//		game.addPlayerToGame(new PhoneyHumanPlayer(9, game));		
//		game.addPlayerToGame(new PhoneyHumanPlayer(2, game));
//		game.addPlayerToGame(new PhoneyHumanPlayer(3, game));
//		game.addPlayerToGame(new PhoneyHumanPlayer(12, game));
//		game.addPlayerToGame(new PhoneyHumanPlayer(13, game));
//		game.addPlayerToGame(new PhoneyHumanPlayer(14, game));
//		game.addPlayerToGame(new PhoneyHumanPlayer(153, game));
//		game.addPlayerToGame(new PhoneyHumanPlayer(123, game));
//		game.addPlayerToGame(new PhoneyHumanPlayer(453, game));
//		game.addPlayerToGame(new PhoneyHumanPlayer(24, game));
//		game.addPlayerToGame(new PhoneyHumanPlayer(34, game));
//		game.addPlayerToGame(new PhoneyHumanPlayer(35, game));
//		game.addPlayerToGame(new PhoneyHumanPlayer(36, game));
//		game.addPlayerToGame(new PhoneyHumanPlayer(37, game));
//		game.addPlayerToGame(new PhoneyHumanPlayer(38, game));
		try {
			game.endGame.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Acabou Jogo Caraio");
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
