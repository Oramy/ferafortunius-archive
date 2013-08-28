package gui.jeu.multi;


import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;


public class Authentification implements Runnable{
	private Socket client;
	private InputStream in;
	private OutputStream out;
	private Serveur serveur;
	public Authentification(Socket client, Serveur serveur){
		this.client = client;
		this.serveur = serveur;
	}
	@Override
	public void run() {
			
			try {
				out = client.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(out);
				
				in = client.getInputStream();
			    ObjectInputStream ois = new ObjectInputStream(in); 
			    serveur.getJeu().addDialog("Un client s'est connecté ! ");
			   Thread t = new Thread(new SClientUpdater(client, serveur, oos, ois));
				t.start();
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		
	}
	
}