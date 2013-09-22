package gui.jeu.multi;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class AccepterClients implements Runnable {

	private ServerSocket socketserver;
	private Socket socket;
	private Serveur serveur;
	//private int nbrclient = 0;
	public AccepterClients(ServerSocket s, Serveur serveur){
		socketserver = s;
		this.serveur = serveur;
	}

	public void run() {
		try {
			while(true){
				socket = socketserver.accept(); // Un client se connecte on l'accepte
				socket.setSoTimeout(0);
				//nbrclient++;
				Thread t = new Thread(new Authentification(socket, serveur));
				t.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}