package game;


import java.util.Observable;
//import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ConcurrentHashMap;

import gui.CountDownLatch;
import environment.Cell;
import environment.Coordinate;
import gui.BoardJComponent;

public class Game extends Observable  {

	public static final int DIMY = 20;
	public static final int DIMX = 20;
	public static final int NUM_PLAYERS = 10;
	private static final int NUM_FINISHED_PLAYERS_TO_END_GAME=3;

	public CountDownLatch endgame = new CountDownLatch(NUM_FINISHED_PLAYERS_TO_END_GAME);

	private boolean GameFinshed=false;

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

	public Cell getCell(Coordinate at) {
		return board[at.x][at.y];
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

	public ConcurrentHashMap<Coordinate,PlayerData>  GetCurrentMap() {
		ConcurrentHashMap<Coordinate,PlayerData> MapaDosPlayers = new  ConcurrentHashMap<Coordinate,PlayerData>();
		for (int x = 0; x < Game.DIMX; x++) 
			for (int y = 0; y < Game.DIMY; y++) {
				Player p= this.getCell(new Coordinate(x,y)).getPlayer();
				if(p!=null){
					MapaDosPlayers.put(new Coordinate(x,y),new PlayerData(p));
				}
			}

		return MapaDosPlayers;
	}

	public void EndGame() {
		this.GameFinshed=true;
	}

	public boolean isOver(){
		return this.GameFinshed;
	}

	public static void main(String[] args) {
		

	}

}
