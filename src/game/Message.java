package game;

import java.io.Serializable;

import gui.GameGuiMain;

public class Message implements Serializable {

	private final Game gui;
	private final PhoneyHumanPlayer p;

	public Message(Game gui, PhoneyHumanPlayer p) {
		this.gui=gui;
		this.p=p;
	}
	
	public Game Game(){
		return gui;
	}
	public PhoneyHumanPlayer Player(){
		return p;
	}

	
}
