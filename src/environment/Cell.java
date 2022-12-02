package environment;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import game.Game;
import game.Player;
import game.ThreadAux;

public class Cell {
	private Coordinate position;
	private Game game;
	private Player player=null;

	private Lock l = new ReentrantLock();
	private Condition PlayerInPosition = l.newCondition();

	public Cell(Coordinate position,Game g) {
		super();
		this.position = position;
		this.game=g;
	}

	public Coordinate getPosition() {
		return position;
	}

	public boolean isOcupied() {
		return player!=null;
	}


	public Player getPlayer() {
		return player;
	}



	//METODO PARA INICIALIZAR PLAYERS NO INICIO DO JOGO
	public  void setPlayerInGame(Player player) throws InterruptedException {
		l.lock();
		if(isOcupied()) 
			System.out.println("Quero inserir o jogador: "+player+" na cela: "+this+" Onde se encontra o jogador: "+getPlayer());
		//INICIA O TIMER DE DOIS SEGUNDOS
		Thread timer= new ThreadAux(game,this,player);
		timer.start();
		//CASO CELA ESTEJA OCUPADA ENTRA EM ESPERA
		while(this.isOcupied()){
			PlayerInPosition.await();
			if(!timer.isAlive() && this.isOcupied()){
				System.out.println("Passaram-se Dois segundos e a timer morreu");
				l.unlock();
				game.getRandomCell().setPlayerInGame(player);
				return; 
			}
		}
		//INSERE O PLAYER NA CELA DESEJADA
		this.player = player;
		l.unlock();
	}



	public synchronized void setPlayer(Player player){
		if(isOcupied()){
			getPlayer().fight(player);
		}else{
			player.getCurrentCell().ClearCell();
			this.player=player;
		}
	}


	public void PlayerInPositionWake() {
		l.lock();
		PlayerInPosition.signalAll();
		l.unlock();
	}

	public void ClearCell() {
		l.lock();
		player=null;
		PlayerInPosition.signalAll();
		l.unlock();

	}

	@Override
	public String toString() {
		return "Celula com x="+position.x+" y= "+position.y;
	}



}
