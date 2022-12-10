package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Client {
	private BufferedReader in;
	private PrintWriter out;
	private Socket socket;
	private boolean AWSD;
	private int porto;
	private InetAddress ip;


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
		} catch (IOException e) {// ERRO...
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
	
		in = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		out = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(socket.getOutputStream())),
				true);
	}

	void sendMessages() throws IOException {
		Scanner in = new Scanner(System.in);
		while(true){
			String i = in.nextLine();
			out.println( i);
			//				String str = in.readLine();
			//				System.out.println(str);

		}
		//		out.println("FIM");
	}



}
