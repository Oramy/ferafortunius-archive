package gui.jeu.multi;

import gui.jeu.Jeu;

import java.awt.Point;
import java.io.IOException;
import java.net.ServerSocket;


public class Serveur {
	private Point toTransfer;
	private Jeu jeu;
	public Serveur(Jeu jeu){
		this.jeu = jeu;
		ServerSocket socket;
		toTransfer = new Point(0,0);

		try {
			socket = new ServerSocket(2009);
			Thread t = new Thread(new AccepterClients(socket, this));
			t.start();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	/**
	 * @return the toTransfer
	 */
	public Point getToTransfer() {
		return toTransfer;
	}
	/**
	 * @param toTransfer the toTransfer to set
	 */
	public void setToTransfer(Point toTransfer) {
		this.toTransfer = toTransfer;
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