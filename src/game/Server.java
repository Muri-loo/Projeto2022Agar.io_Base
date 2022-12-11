package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import environment.Direction;
import gui.GameGuiMain;


public class Server {

	public GameGuiMain jogo;
	public Game game;
	public static final int PORTO = 8080;

	private int ids=0;



	public void OpenServer() throws IOException {
		ServerSocket ss = new ServerSocket(PORTO);
		jogo= new GameGuiMain();
		game= jogo.getGame();
		jogo.start();

		try {
			while(true){
				Socket socket = ss.accept();
				System.out.println("Conexão feita");
				new DealWithClient(socket).start();
			}			
		} finally {
			ss.close();
		}
	}

	public static void main(String[] args) {
		try {
			new Server().OpenServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	public class DealWithClient extends Thread {
		ObjectInputStream in;
		ObjectOutputStream out;


		public DealWithClient(Socket socket) throws IOException {
			doConnections(socket);
		}


		@Override
		public void run() {
			try {
				serve();
			} catch (IOException | InterruptedException | ClassNotFoundException e) {
				e.printStackTrace();
			}

		}

		void doConnections(Socket socket) throws IOException {

			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(socket.getInputStream());
		}
		
		
		private void serve() throws IOException, InterruptedException, ClassNotFoundException {
			System.out.println("ENTROU PARA COLOCAR NO JOGO");
			PhoneyHumanPlayer jogador = new PhoneyHumanPlayer(ids++,game);
			System.out.println(jogador);

			jogador.start();
			while (true) {
				out.writeObject(new Message(game.GetCurrentMap()));
				Direction direction = (Direction) in.readObject();
				jogador.ToMove(direction);
				out.flush();
				Thread.sleep(Game.REFRESH_INTERVAL);
			}
		}


		//	private class SendOutPut extends Thread{
		//		
		//	}


	}
}
