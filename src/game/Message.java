package game;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import environment.Coordinate;

public class Message implements Serializable {

	private final ConcurrentHashMap<Coordinate,PlayerData> Mapa;

	public Message(ConcurrentHashMap<Coordinate,PlayerData> Mapa) {
		this.Mapa=Mapa;
	}

	public ConcurrentHashMap<Coordinate,PlayerData> getMapa(){
		return Mapa;
	}

	
}
