package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
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
				if(game.isOver())return;
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
		BufferedReader in;
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
			//			  

			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}

		//SO PARA 1 CLIENTE
		private void serve() throws IOException, InterruptedException, ClassNotFoundException {
			PhoneyHumanPlayer jogador = new PhoneyHumanPlayer(ids++,game);
			jogador.start();

			while (true) {
				//ENVIAR MAPA
				out.writeObject(new Message(game.GetCurrentMap(),jogador.isAlive(),game.isOver()));
				//RECEBER DIREÇÕES SO SE TIVER VIVO
				if(jogador.isAlive()){
					String direction =  in.readLine();
					jogador.ToMove(Direction.stringToDir(direction));
				}
				if(game.isOver()) return;
				out.flush();
				Thread.sleep(Game.REFRESH_INTERVAL);
			}
		}


		//	private class SendOutPut extends Thread{
		//		
		//	}


	}
}
