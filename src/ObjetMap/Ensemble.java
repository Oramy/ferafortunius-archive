package ObjetMap;

import java.io.Serializable;
import java.util.ArrayList;

import Level.ChunkMap;

public class Ensemble implements Serializable, Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<ObjetMap> contenu;
	private String nom;
	private transient int posX,  posY, posZ;
	
	public Ensemble clone(){
		 Ensemble o = null;
		 try {
			o = (Ensemble) super.clone();
		 } catch(CloneNotSupportedException cnse) {
			cnse.printStackTrace(System.err);
		 }
		 o.setNom(nom);
		 o.contenu = new ArrayList<ObjetMap>();
		 for(int i = 0, c = contenu.size(); i < c; i++){
			 o.contenu.add(contenu.get(i).clone());
		 }
		 return o;
	}
	public Ensemble(ArrayList<ObjetMap> contenu){
		this.setContenu(contenu);
		this.setNom("");
	}
	public Ensemble(){
		this.setContenu(new ArrayList<ObjetMap>());
		this.setNom("");
		
	}
	public Ensemble(ArrayList<ObjetMap> contenu2, String nom2) {
		this.setContenu(contenu2);
		this.setNom(nom2);
	}
	public static Ensemble convertMap(ChunkMap c){
		return new Ensemble(c.getChunks()[0][0][0].getContenu(), c.getNom());
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public int getPosZ() {
		return posZ;
	}
	public void setPosZ(int posZ) {
		this.posZ = posZ;
	}
	public ArrayList<ObjetMap> getContenu() {
		return contenu;
	}
	public void setContenu(ArrayList<ObjetMap> contenu) {
		this.contenu = contenu;
	}
}
