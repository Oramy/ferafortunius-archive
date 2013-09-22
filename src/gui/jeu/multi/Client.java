package gui.jeu.multi;

import gui.Text;
import gui.jeu.Jeu;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client{
	private Jeu jeu;
	public Client(Jeu jeu){
		this.setJeu(jeu);
		Socket socket;
		try {
		
				socket = new Socket(InetAddress.getByName("localhost"),2009);	
				socket.setSoTimeout(10000);
				if(!jeu.getOptionsFen().getIp().getInput().getContenu().equals(""))
					socket = new Socket(InetAddress.getByName(jeu.getOptionsFen().getIp().getInput().getContenu()),2009);	
				
				InputStream sortie = socket.getInputStream();
		        ObjectInputStream ois = new ObjectInputStream(sortie); 
		        OutputStream entree = socket.getOutputStream();
		        ObjectOutputStream oos = new ObjectOutputStream(entree);
		        jeu.addDialog(new Text("Vous êtes connecté à " + socket.getInetAddress(), jeu.getDialogBar()));
		        Thread t = new Thread(new CClientUpdater(this, socket, oos,ois));
		        t.start();
		       
		}catch (UnknownHostException e) {
			
			e.printStackTrace();
		}catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	/**
	 * @return the jeu
	 */
	public Jeu getJeu() {
		return jeu;
	}
	/**
	 * @param jeu the jeu to set
	 */
	public void setJeu(Jeu jeu) {
		this.jeu = jeu;
	}

}
