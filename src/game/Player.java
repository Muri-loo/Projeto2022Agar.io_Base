package game;



import environment.Cell;
import environment.Coordinate;
import environment.Direction;
import gui.BoardJComponent;
import gui.CountDownLatch;

/**
 * Represents a player.
 * @author luismota
 *
 */
public abstract class Player extends Thread {


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
		originalStrength=(byte)( (Math.random()*game.MAX_INITIAL_STRENGTH)+1);
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

	public abstract void move();

	protected boolean canMove(Coordinate point) {
		if(point.x	>= game.DIMX || point.y  >= game.DIMY || point.x<0 || point.y<0)
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
		
		synchronized( Player.class){
			if( player.isDone() || this.isDone() ) return;
//			System.out.println("TEM LUTAAAA");
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
	}

	@Override
	public void run() {
		try {

			//Ao iniciar Thread pela primeira vez tenta inserir o player no jogo.
			game.getRandomCell().setPlayerInGame(this);
//			game.getCell(new Coordinate(6,6)).setPlayerInGame(this);;
			while(true){
				if(Thread.interrupted() || this.isDone() ) return;
				move();
				game.notifyChange();
				Thread.sleep(game.REFRESH_INTERVAL*originalStrength);
			}
		} catch (InterruptedException e) {
			//System.out.println("Fui Morto: "+this);
		}
	}

}
