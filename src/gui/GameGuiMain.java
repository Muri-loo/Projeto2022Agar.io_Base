package gui;

import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import game.BotPlayer;
import game.Game;
import game.PhoneyHumanPlayer;
import game.PlayerDos;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GameGuiMain implements Observer, Serializable {
	private JFrame frame = new JFrame("pcd.io");
	private BoardJComponent boardGui;
	private Game game;

	public GameGuiMain() {
		super();
		game = new Game();
		game.addObserver(this);
		buildGui();

	}
	public Game getGame() {
		return game;
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

//		PhoneyHumanPlayer a =new PhoneyHumanPlayer(1, game,(byte)1);
//		a.start();
//		//
//		//	
//		//	
//		PlayerDos d= new PlayerDos(2,game,(byte)1);
//		d.start();

		
		ExecutorService pool = Executors.newFixedThreadPool(200);
		for(int i=0; i<99; i++){
			BotPlayer f =new BotPlayer(i,game);
			pool.submit(f);
		}
		try {
			game.endgame.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			game.EndGame();
			System.out.println(game.playersInGame());
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
