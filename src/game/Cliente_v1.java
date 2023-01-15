package game;

import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

public class Cliente_v1 {

	PrintWriter out;
	ObjectInputStream in;
	InetAddress serverip;
	Socket SocketServidor;
	BoardJComponent teclado;
	JFrame frame;

	public static void main(String[] args) {
		new Cliente_v1().iniciarConexao();
	}


	private void iniciarConexao() {
		try {
			serverip = InetAddress.getByName(null);
			SocketServidor = new Socket(serverip,8080);
			System.out.println("Cliente ligado ao Servidor");

			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(SocketServidor.getOutputStream())), true);
			in = new ObjectInputStream(SocketServidor.getInputStream());

			System.out.println("Conexão ao servidor feita");
			this.IniciarJogo();

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void IniciarJogo() throws ClassNotFoundException, IOException {
		Game a= new Game();
		BoardJComponentClient cliente= new BoardJComponentClient(a, false);
		buildGui(cliente);
		
		while(true) {
			while(true){
				Message mensagem = (Message) in.readObject();
				cliente.setJogadores(mensagem.getMapa());
				cliente.repaint();

				if(mensagem.getIsVivo()){
					Direction direction = cliente.getLastPressedDirection();
					
					if( !(direction==null)) {
						System.out.println("in");
						out.flush();
						out.println(direction);
						cliente.clearLastPressedDirection();
					}

					
				}
				if(mensagem.getGameIsOver()){
					JOptionPane.showMessageDialog(frame, "Jogo acabou");	
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
					return;
				}
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
