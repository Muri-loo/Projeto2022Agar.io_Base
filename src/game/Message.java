package game;

import java.io.Serializable;

import environment.Direction;
import gui.GameGuiMain;

public class Message implements Serializable {

	private final Direction dir;

	public Message(Direction dir) {
		this.dir=dir;
	}

	public Direction getDir(){
		return dir;
	}

	
}
