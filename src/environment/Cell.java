package environment;

import java.util.Comparator;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import game.Game;
import game.Player;
import game.ThreadAux;

public class Cell implements Comparator<Cell>{
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
		if(isOcupied()) System.out.println("Quero inserir o jogador: "+player+" na cela: "+this+" Onde se encontra o jogador: "+getPlayer());
		//INICIA O TIMER DE DOIS SEGUNDOS
		Thread timer= new ThreadAux(game,this,player);
		timer.start();
		//CASO CELA ESTEJA OCUPADA ENTRA EM ESPERA
		while(this.isOcupied()){
			PlayerInPosition.await();
			if(!timer.isAlive() && this.isOcupied()){
				System.out.println("Passaram-se Dois segundos e jogador vai ser recolocado:"+player);
				l.unlock();
				game.getRandomCell().setPlayerInGame(player);
				return; 
			}
		}
		//INSERE O PLAYER NA CELA DESEJADA
		this.player = player;
		l.unlock();
	}



	public  void setPlayer(Player player){
		Cell playerCell=player.getCurrentCell();
		//IMPEDIR QUE EXISTA DEAD LOCK BLOQUEANDO DOIS OBJETOS SEMPRE A MESMA ORDEM
		if(compare(this,playerCell)>1){
			l.lock(); 
			playerCell.l.lock();
		}else{
			playerCell.l.lock();
			l.lock();
		}

		if(isOcupied()){
			getPlayer().fight(player);
		}else{
			player.getCurrentCell().ClearCell();
			this.player=player;
		}

		playerCell.l.unlock();
		l.unlock();
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

	@Override
	public int compare(Cell o1, Cell o2) {
		if(o1.getPosition().x>o2.getPosition().x || o1.getPosition().y>o2.getPosition().y)
			return 1;
		else if(o1.getPosition().y<o2.getPosition().y || o1.getPosition().y<o2.getPosition().y )
			return -1;
		else
			return 0;
	}





}
