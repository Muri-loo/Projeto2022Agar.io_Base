package game;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import environment.Coordinate;

public class Message implements Serializable {

	private final ConcurrentHashMap<Coordinate,PlayerData> Mapa;
	private final boolean isVivo;
	private final boolean GameIsOver;

	public Message(ConcurrentHashMap<Coordinate,PlayerData> Mapa,boolean isVivo, boolean GameIsOver) {
		this.Mapa=Mapa;
		this.isVivo=isVivo;
		this.GameIsOver=GameIsOver;
	}

	public ConcurrentHashMap<Coordinate,PlayerData> getMapa(){
		return Mapa;
	}

	public boolean getIsVivo() {
		return isVivo;
	}
	
	public boolean getGameIsOver() {
		return GameIsOver;
	}
	
	
}
