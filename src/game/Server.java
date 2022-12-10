package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

	public static final int PORTO = 8080;


	public void OpenServer() throws IOException {
		ServerSocket ss = new ServerSocket(PORTO);
		try {
			while(true){
				Socket socket = ss.accept();
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
//			out = new PrintWriter(new BufferedWriter(
//					new OutputStreamWriter(socket.getOutputStream())),
//					true);
		}
		private void serve() throws IOException, InterruptedException {
			while (true) {
				String str = in.readLine();
//				if (str.equals("FIM"))
//					break;
//				System.out.println("Eco:" + str);
				//out.println(str);
				Thread.sleep(Game.REFRESH_INTERVAL);
			}
		}
	}







}
