package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import gui.GameGuiMain;


public class Server {
	
	public Game game;
	public static final int PORTO = 8080;
	ObjectOutputStream objToSend;

	public void OpenServer() throws IOException {
		ServerSocket ss = new ServerSocket(PORTO);
		game= new Game();
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
		
		private int ids=0;

		public DealWithClient(Socket socket) throws IOException {
			doConnections(socket);
		}

		private BufferedReader in;
		private PrintWriter out;

		@Override
		public void run() {
				try {
					serve();
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}
		
		}

		void doConnections(Socket socket) throws IOException {
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			objToSend = new ObjectOutputStream(socket.getOutputStream());


//			out = new PrintWriter(new BufferedWriter(
//					new OutputStreamWriter(socket.getOutputStream())),
//					true);
		}
		private void serve() throws IOException, InterruptedException {
			PhoneyHumanPlayer jogador = new PhoneyHumanPlayer(ids++,game);
			while (true) {
				objToSend.writeObject(new Message(game, jogador));
			
				//receberalgodaparteDele
				
				
				Thread.sleep(Game.REFRESH_INTERVAL);
			}
		}
	}







}
