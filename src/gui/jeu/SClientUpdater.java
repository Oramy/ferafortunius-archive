package gui.jeu;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Level.ChunkMap;
import ObjetMap.Entity;




public class SClientUpdater implements Runnable {
	private Socket client;
	private Serveur serveur;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Entity persoClient;
	public SClientUpdater(Socket client, Serveur serveur, ObjectOutputStream oos, ObjectInputStream ois) {
		this.client = client;
		this.serveur = serveur;
		this.oos = oos;
		this.ois = ois;
	}
	public void syncCaracs(Entity p){
		persoClient.setPosX(p.getPosX());
		persoClient.setPosY(p.getPosY());
		persoClient.setPosZ(p.getPosZ());
		persoClient.setChunkX(p.getChunkX());
		persoClient.setChunkY(p.getChunkY());
		persoClient.setChunkZ(p.getChunkZ());
		persoClient.setImage(p.getImage());
		
		if(!serveur.getJeu().getCarte().getChunk(persoClient).getContenu().contains(persoClient)){
			serveur.getJeu().getCarte().getChunks()[0][0][0].addContenu(persoClient);
		}
	}
	public void sendMessage(ObjMessage obj){
		try {
			oos.writeObject(obj);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
	    try {
	    	ChunkMap s = serveur.getJeu().getCarte().clone();
			oos.writeObject(s);
			oos.flush();
    		
			do{
				Entity p  = (Entity) ois.readObject();	
				
				if(persoClient == null)
					persoClient = p;
	
				syncCaracs(p);
				
				ObjMessage player = new ObjMessage(serveur.getJeu().getPlayer(), 'u');
				sendMessage(player);
				
			}while(client.isConnected());
			serveur.getJeu().addDialog("Un client s'est déconnecté");
			client.close();
	    } catch (IOException e) {
	    	serveur.getJeu().addDialog(e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			serveur.getJeu().addDialog(e.getMessage());
			e.printStackTrace();
		}	
	}

}

