package environment;

import java.io.Serializable;
import java.util.Comparator;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import game.BotPlayer;
import game.Game;
import game.Player;
import game.WakeUp;

public class Cell implements Comparator<Cell>, Serializable{
	private Coordinate position;
	private Player player=null;

	private Lock l = new ReentrantLock();

	private Condition PlayerInPosition = l.newCondition();


	public Cell(Coordinate position,Game g) {
		super();
		this.position = position;
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
	public  void setPlayerInGame(Player player)  {
		l.lock();
		if(isOcupied()) 
			System.out.println("Quero inserir o jogador: "+player+" na cela: "+this+" Onde se encontra o jogador: "+getPlayer());
		//INICIA O TIMER DE DOIS SEGUNDOS
		WakeUp timer=new WakeUp(Thread.currentThread());
		timer.start();
		//CASO CELA ESTEJA OCUPADA ENTRA EM ESPERA
		while(this.isOcupied()){
			try {
				PlayerInPosition.await();
			} catch (InterruptedException e) {
				l.unlock();
				return;
			}
		}
		//INSERE O PLAYER NA CELA DESEJADA
		this.player = player;
		l.unlock();
	}



	public  void setPlayer(Player player) throws InterruptedException {
		Cell playerCell=player.getCurrentCell();

		//IMPEDIR QUE EXISTA DEAD LOCK BLOQUEANDO DOIS OBJETOS SEMPRE A MESMA ORDEM
		if(compare(this,playerCell)>1){
			l.lock(); 
			playerCell.l.lock();
		}else{
			playerCell.l.lock();
			l.lock();
		}
		//Se cela tiver ocupada
		if(isOcupied()){
			//Se o player na cela esta morto ou ja acabou o jogo.
			if(this.player.isDone() && player instanceof BotPlayer){
				//Incia timer que apos 2 segundos ira enviar interrupt para argumento
				WakeUp timer=new WakeUp(Thread.currentThread());
				timer.start();
				playerCell.l.unlock();
				l.unlock();
				synchronized(player){
					while(timer.isAlive())
						player.wait();
				}
			}
			getPlayer().fight(player);

		}else{
			player.getCurrentCell().ClearCell();
			this.player=player;
		}

		playerCell.l.unlock();
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
