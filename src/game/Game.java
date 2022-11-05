package game;


import java.util.Observable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import environment.Cell;
import environment.Coordinate;
import gui.BoardJComponent;

public class Game extends Observable {

	public static final int DIMY = 30;
	public static final int DIMX = 30;
	private static final int NUM_PLAYERS = 90;
	private static final int NUM_FINISHED_PLAYERS_TO_END_GAME=3;

	public CountDownLatch endGame = new CountDownLatch(3);
	
	private Lock l = new ReentrantLock();
	private Condition PlayerInPosition = l.newCondition();
	
	public static final long REFRESH_INTERVAL = 400;
	public static final double MAX_INITIAL_STRENGTH = 3;
	public static final long MAX_WAITING_TIME_FOR_MOVE = 2000;
	public static final long INITIAL_WAITING_TIME = 10000;

	
	private BoardJComponent teclado;


	public BoardJComponent getTeclado() {
		return teclado;
	}
	public void setTeclado(BoardJComponent teclado) {
		this.teclado = teclado;
	}

	protected Cell[][] board;

	public Game() {
		board = new Cell[Game.DIMX][Game.DIMY];

		for (int x = 0; x < Game.DIMX; x++) 
			for (int y = 0; y < Game.DIMY; y++) 
				board[x][y] = new Cell(new Coordinate(x, y),this);
	}

	/** 
	 * @param player 
	 * @throws InterruptedException 
	 */
	public void addPlayerToGame(Player player) throws InterruptedException {
		l.lock();
//		Cell initialPos=getRandomCell();
		Cell initialPos=getCell(new Coordinate(2,2));
		while(initialPos.isOcupied()) PlayerInPosition.await();
		initialPos.setPlayer(player);
		
		// To update GUI
		notifyChange();
		l.unlock();
	}
	
	public void unlockPlayerCell(){
		l.lock();
		PlayerInPosition.signalAll();
		l.unlock();
	}
	

	public Cell getCell(Coordinate at) {
		return board[at.x][at.y];
	}

	public void fight(Player player1,Player player2) {
		byte winnerStrength = (byte)Math.min(player1.getCurrentStrength()+player2.getCurrentStrength(),10);
		Cell CelulaP1= player1.getCurrentCell();
		Cell CelulaP2= player2.getCurrentCell();
		if(player1.getCurrentStrength()==player2.getCurrentStrength()){
			if( (int)((Math.random()*2)+1)>1){
				//Player1Ganha
				player1.setStrength(winnerStrength);
				player2.killPlayer();

			}else{
				//PLayer2Ganha
				player2.setStrength(winnerStrength);
				player1.killPlayer();
			}
		}else if(player1.getCurrentStrength()>player2.getCurrentStrength()){
			//PLayer1Ganha
			player1.setStrength(winnerStrength);
			player2.killPlayer();

		}else{
			//Player2Ganha
			player2.setStrength(winnerStrength);
			player1.killPlayer();
		}
	}


	/**	
	 * Updates GUI. Should be called anytime the game state changes
	 */
	public void notifyChange() {
		setChanged();
		notifyObservers();
	}

	public Cell getRandomCell() {
		Cell newCell=getCell(new Coordinate((int)(Math.random()*Game.DIMX),(int)(Math.random()*Game.DIMY)));
		return newCell; 
	}



}
