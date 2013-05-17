package Items;

import java.io.Serializable;
import java.util.ArrayList;

import ObjetMap.Entity;



public class Inventory implements Serializable, Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Entity owner;
	private ArrayList<Item> contents;
	private int maxWeight;
	private int weight;
	private boolean locked;
	private long cirkulomas;
	public Inventory(Entity owner){
		init();
		this.owner = owner;
	}
	public Inventory(Entity owner, int weight){
		init();
		this.owner = owner;
		this.weight = weight;
	}
	@SuppressWarnings("unchecked")
	public Inventory clone(){
		Inventory o = null;
		try {
			// On récupère l'instance à renvoyer par l'appel de la 
			// méthode super.clone()
			o = (Inventory) super.clone();
		} catch(CloneNotSupportedException cnse) {
			// Ne devrait jamais arriver car nous implémentons 
			// l'interface Cloneable
			cnse.printStackTrace(System.err);
		}
		o.owner = owner;
		if(contents != null)
		o.contents = (ArrayList<Item>) contents.clone();
		return o;
	}
	private void init() {
		setCirkulomas(0);
		weight = 0;
		maxWeight = 0;
		contents =  new ArrayList<Item>();
		locked = true;
		owner = null;
	}
	
	/**
	 * @return the owner
	 */
	public Entity getOwner() {
		return owner;
	}
	/**
	 * @param owner the owner to set
	 */
	public void setOwner(Entity owner) {
		this.owner = owner;
	}
	public ArrayList<Item> getContents(){
		return contents;
		
	}
	public void addContent(Item item){
		if(weight + item.getWeight() <= maxWeight){
			contents.add(item);
			weight += item.getWeight();
			if(item instanceof CirkulomaVerre){
				cirkulomas += ((CirkulomaVerre)item).getCirkulomaValue();
			}
		}
	}
	public void removeContent(Item item){
		contents.remove(item);
		weight -= item.getWeight();
		if(item instanceof CirkulomaVerre){
			cirkulomas -= ((CirkulomaVerre)item).getCirkulomaValue();
		}
	}
	/**
	 * @return the maxWeight
	 */
	public int getMaxWeight() {
		return maxWeight;
	}
	/**
	 * @param maxWeight the maxWeight to set
	 */
	public void setMaxWeight(int maxWeight) {
		this.maxWeight = maxWeight;
	}
	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}
	/**
	 * @return the cirkulomas
	 */
	public long getCirkulomas() {
		return cirkulomas;
	}
	/**
	 * @param cirkulomas the cirkulomas to set
	 */
	public void setCirkulomas(long cirkulomas) {
		this.cirkulomas = cirkulomas;
	}
	/**
	 * @return the locked
	 */
	public boolean isLocked() {
		return locked;
	}
	/**
	 * @param locked the locked to set
	 */
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
}
