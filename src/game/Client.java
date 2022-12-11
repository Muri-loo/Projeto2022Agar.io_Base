package game;

import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import environment.Direction;
import gui.BoardJComponent;
import gui.BoardJComponentClient;


public class Client extends Thread{
	private PrintWriter out;
	private ObjectInputStream in;
	private Socket socket;
	private boolean AWSD;
	private int porto;
	private InetAddress ip;
	BoardJComponent teclado;
	JFrame frame;



	public Client(int porto, InetAddress a,boolean ASWD) {
		this.porto=porto;
		this.AWSD=AWSD;
		this.ip=a;
	}

	public static void main(String[] args) throws UnknownHostException {
		new Client(Server.PORTO,  InetAddress.getByName(null) ,false).run();
	}

	@Override
	public void run() {
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

		in = new ObjectInputStream(socket.getInputStream());

		out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
	}

	void sendMessages() throws IOException, ClassNotFoundException {
		Game a= new Game();
		BoardJComponentClient cliente= new BoardJComponentClient(a, this.AWSD);
		buildGui(cliente);
		while(true){

			Message mensagem = (Message) in.readObject();
			cliente.setJogadores(mensagem.getMapa());
			cliente.repaint();

			if(mensagem.getIsVivo()){
				Direction direction= cliente.getLastPressedDirection();
				System.out.println(Direction.dirToString(direction));
				out.flush();
				out.println(Direction.dirToString(direction));
				cliente.clearLastPressedDirection();
			}
			if(mensagem.getGameIsOver()){
				JOptionPane.showMessageDialog(frame, "Jogo acabou");	
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				return;
			}

		}
	}



	private void buildGui(BoardJComponentClient boardGui) {
		 frame = new JFrame("pcd.io");
		frame.add(boardGui);
		frame.setSize(800,800);
		frame.setLocation(0, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}



}
