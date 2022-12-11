package game;



import java.io.Serializable;

import environment.Cell;
import environment.Coordinate;

/**
 * Represents a player.
 * @author luismota
 *
 */
public abstract class Player extends Thread implements Serializable{


	protected  Game game;

	private int id;

	private byte currentStrength;
	protected byte originalStrength;

	// TODO: get player position from data in game
	public Cell getCurrentCell() {
		for(int i=0; i<game.board.length; i++){
			for(int j=0; j<game.board[i].length; j++){
				Cell celula = game.getCell(new Coordinate(i,j));
				if(celula.getPlayer()!=null && celula.getPlayer().equals(this)){
					return celula;
				}
			}
		}
		return null;
	}

	public Player(int id, Game game, byte strength) {
		super();
		this.id = id;
		this.game=game;
		currentStrength=strength;
		originalStrength=strength;
	}

	public Player(int id, Game game) {
		super();
		this.id = id;
		this.game=game;
		originalStrength=(byte)( (Math.random()*Game.MAX_INITIAL_STRENGTH)+1);
		currentStrength=originalStrength;
	}

	public abstract boolean isHumanPlayer();

	@Override
	public String toString() {
		return "Player [id=" + id + ", currentStrength=" + currentStrength + ", getCurrentCell()=" + getCurrentCell()
		+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public byte getCurrentStrength() {
		return currentStrength;
	}

	public void setStrength(Byte currentStrength){
		this.currentStrength=currentStrength;
	}


	public int getIdentification() {
		return id;
	}

	public abstract void move() throws InterruptedException;


	protected boolean canMove(Coordinate point) {
		if(point.x	>= Game.DIMX || point.y  >= Game.DIMY || point.x<0 || point.y<0)
			return false;
		return true;
	}


	public boolean isDone(){
		return this.currentStrength==0 || this.currentStrength==10;
	}

	public void killPlayer() {
		this.interrupt();
		this.setStrength((byte)0);
	}

	public  void fight(Player player) {
		if( player.isDone() || this.isDone() ) return;

		byte winnerStrength = (byte)Math.min(this.getCurrentStrength()+player.getCurrentStrength(),10);
		if(this.getCurrentStrength()==player.getCurrentStrength()){

			if( (int)((Math.random()*2)+1)>1){
				//thisGanha
				this.setStrength(winnerStrength);
				player.killPlayer();
			}else{
				//playerGanha
				player.setStrength(winnerStrength);
				this.killPlayer();
			}

		}else if(this.getCurrentStrength()>player.getCurrentStrength()){
			//thisGanha
			this.setStrength(winnerStrength);
			player.killPlayer();
		}else{
			//playerGanha
			player.setStrength(winnerStrength);
			this.killPlayer();
		}
		if(winnerStrength==10)	game.endgame.countDown(); 
	}


	//Ao iniciar Thread pela primeira vez tenta inserir o player no jogo.



	@Override
	public void run() {

		while(!(this.isDone() || game.isOver()) ){
			try {
				if(this.getCurrentCell()==null)	
					//					game.getCell(new Coordinate(6,6)).setPlayerInGame(this);
					game.getRandomCell().setPlayerInGame(this);
				else
					move();

				game.notifyChange();
				if(this instanceof BotPlayer)
					Thread.sleep(Game.REFRESH_INTERVAL*originalStrength);
			} catch (InterruptedException e) {
				if(this.isDone() || game.isOver())return;
			}
		}

	}

}
