package game;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import environment.Coordinate;

public class Message implements Serializable {

	private final ConcurrentHashMap<Coordinate,PlayerData> Mapa;
	private final boolean isVivo;

	public Message(ConcurrentHashMap<Coordinate,PlayerData> Mapa,boolean isVivo) {
		this.Mapa=Mapa;
		this.isVivo=isVivo;
	}

	public ConcurrentHashMap<Coordinate,PlayerData> getMapa(){
		return Mapa;
	}

	public boolean getIsVivo() {
		return isVivo;
	}
	
}
