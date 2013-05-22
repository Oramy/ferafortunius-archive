package gui.jeu;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import Level.ChunkMap;
import ObjetMap.Entity;
import ObjetMap.ObjetMap;
import ObjetMap.Perso;




public class CClientUpdater implements Runnable{
	private Client client;
	private Socket serveur;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	private ArrayList<ObjMessage> serverMessages;
	public CClientUpdater(Client client, Socket serveur, ObjectOutputStream oos, ObjectInputStream ois){
		this.client = client;
		this.oos = oos;
		this.ois = ois;
		this.serveur = serveur;
		this.serverMessages = new ArrayList<ObjMessage>();
		
	}
	public void syncCaracs(Perso p){
		client.getJeu().getPlayer().setPosX(p.getPosX());
		client.getJeu().getPlayer().setPosY(p.getPosY());
		client.getJeu().getPlayer().setPosZ(p.getPosZ());
		client.getJeu().getPlayer().setChunkX(p.getChunkX());
		client.getJeu().getPlayer().setChunkY(p.getChunkY());
		client.getJeu().getPlayer().setChunkZ(p.getChunkZ());
		client.getJeu().getPlayer().setImage(p.getImage());
	}
	public void updateData(ObjetMap o){
		int id  = removeDatCoords(o);
		addData(o, id);
	}
	public void addData(ObjetMap o){
		client.getJeu().getCarte().getChunk(o).addContenu(o);
	}
	public void addData(ObjetMap o, int id){
		client.getJeu().getCarte().getChunk(o).addContenu(o, id);
	}
	public void removeData(ObjetMap o){
		client.getJeu().getCarte().getChunk(o).getContenu().remove(o);
	}
	public int removeDatCoords(ObjetMap o){
		ObjetMap similar = client.getJeu().getCarte().getChunk(o).getContenu(o.getPosX(), o.getPosY(), o.getPosZ());
		int id = client.getJeu().getCarte().getChunk(o).getContenu().indexOf(similar);
		removeData(similar);
		return id;
	}
	public void executeMessage(ObjMessage mail){
		if(mail.getAction() == 'u')
			updateData(mail.getObject());
		if(mail.getAction() == 'a')
			addData(mail.getObject());
		if(mail.getAction() == 'r')
			removeData(mail.getObject());
	}
	public void executeMessages(){
		for(int i = 0, c = serverMessages.size(); i < c; i++)
		{
			executeMessage(serverMessages.get(i));
			serverMessages.remove(i);
		}
	}
	public void getServerMessages(){
		try {
			ObjMessage message;
			message = (ObjMessage)(ois.readObject());
			serverMessages.add(message);			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void run() {
		try {
			Entity p = client.getJeu().getPlayer();
			ChunkMap c = ((ChunkMap) ois.readObject());
			c.getChunk(p).addContenu(p);
			client.getJeu().setCarte(c);
			client.getJeu().getPanneauDuJeu().setCarte(c);
			
			while(serveur.isConnected()){
					Entity pclone = (Entity) p.clone();
					oos.writeObject(pclone);
					oos.flush();
					try {
						Thread.sleep(16);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					getServerMessages();
					executeMessages();
					
			}
			serveur.close();
		} catch (IOException e1) {
			client.getJeu().addDialog(e1.getMessage());
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			client.getJeu().addDialog(e1.getMessage());
			e1.printStackTrace();
		}
  
	}

}