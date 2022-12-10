package game;

import java.awt.Frame;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JFrame;

import gui.BoardJComponent;


public class Client {
	private ObjectInputStream inObjectReader;
	private PrintWriter out;
	private Socket socket;
	private boolean AWSD;
	private int porto;
	private InetAddress ip;
	BoardJComponent teclado;
	


	public Client(int porto, InetAddress a,boolean ASWD) {
		this.porto=porto;
		this.AWSD=AWSD;
		this.ip=a;
	}

	public static void main(String[] args) throws UnknownHostException {
		new Client(Server.PORTO,  InetAddress.getByName(null) ,true).runClient();
	}

	public void runClient() {
		try {
			connectToServer();
			sendMessages();
		} catch (IOException | ClassNotFoundException  e) {// ERRO...
		} finally {//a fechar...
			try {
				socket.close();
			} catch (IOException e) {//... 
			}
		}
	}

	void connectToServer() throws IOException {
		socket = new Socket(ip, porto);
		System.out.println("Socket:" + socket);

		inObjectReader = new ObjectInputStream(socket.getInputStream());
		out = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(socket.getOutputStream())),
				true);
	}

	void sendMessages() throws IOException, ClassNotFoundException {
		Scanner in = new Scanner(System.in);
		int i=0;
		while(true){
			Message a = (Message)inObjectReader.readObject();
			teclado =  new BoardJComponent(a.Game());
			if(i==0){
				buildGui(teclado);
				
				i++;
			}else
				teclado.repaint();
			teclado.getLastPressedDirection();
			teclado.clearLastPressedDirection();


		}
		//		out.println("FIM");
	}


	private void buildGui(BoardJComponent boardGui) {
		JFrame frame = new JFrame("pcd.io");
		frame.add(boardGui);
		frame.setSize(800,800);
		frame.setLocation(0, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}



}
