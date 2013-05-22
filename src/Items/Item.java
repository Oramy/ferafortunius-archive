package Items;

import java.io.Serializable;

import org.newdawn.slick.Graphics;

import ObjetMap.Entity;
import ObjetMap.ObjetImage;


public abstract class Item implements Serializable, Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected ItemType type;
	protected int weight;
	protected int maxstackNumber;
	
	protected Entity owner;
	
	protected ObjetImage img;
	protected String name;
	protected String description;
	protected String descriptionPath;
	
	protected boolean autoGet;
	protected int autoGetRange;
	public Item(Entity owner){
		setMaxstackNumber(1);
		setAutoGet(false);
		this.owner = owner;
		type = ItemType.Basic;
		name = "";
		autoGetRange = 10;
		setDescriptionPath("");
		setDescription("");
		weight = 0;
		img = new ObjetImage("",0,0,0,0,0,0);
		
	}
	public Item clone(){
		Item o = null;
		try {
			// On récupère l'instance à renvoyer par l'appel de la 
			// méthode super.clone()
			o = (Item) super.clone();
		} catch(CloneNotSupportedException cnse) {
			// Ne devrait jamais arriver car nous implémentons 
			// l'interface Cloneable
			cnse.printStackTrace(System.err);
		}
		o.owner = owner;
		return o;
		
	}
	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}
	public void draw(Graphics g){
		
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
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

	/**
	 * @return the name
	 */
	public String getName() {
		String name = ItemDescriptionRessources.loadName(getDescriptionPath());
		if(!name.equals(""))
			return name;
		else
			return this.name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the img
	 */
	public ObjetImage getImg() {
		return img;
	}

	/**
	 * @param img the img to set
	 */
	public void setImg(ObjetImage img) {
		this.img = img;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		String description = ItemDescriptionRessources.loadDescription(getDescriptionPath());
		if(!description.equals(""))
			return description;
		else
			return this.description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the maxstackNumber
	 */
	public int getMaxstackNumber() {
		return maxstackNumber;
	}
	/**
	 * @param maxstackNumber the maxstackNumber to set
	 */
	public void setMaxstackNumber(int maxstackNumber) {
		this.maxstackNumber = maxstackNumber;
	}
	/**
	 * @return the autoGet
	 */
	public boolean isAutoGet() {
		return autoGet;
	}
	/**
	 * @param autoGet the autoGet to set
	 */
	public void setAutoGet(boolean autoGet) {
		this.autoGet = autoGet;
	}
	/**
	 * @return the autoGetRange
	 */
	public int getAutoGetRange() {
		return autoGetRange;
	}
	/**
	 * @param autoGetRange the autoGetRange to set
	 */
	public void setAutoGetRange(int autoGetRange) {
		this.autoGetRange = autoGetRange;
	}
	/**
	 * @return the descriptionPath
	 */
	public String getDescriptionPath() {
		return descriptionPath;
	}
	/**
	 * @param descriptionPath the descriptionPath to set
	 */
	public void setDescriptionPath(String descriptionPath) {
		this.descriptionPath = descriptionPath;
	}
	public ItemType getType(){
		if(type == null)
			type = ItemType.Basic;
		return type;
	}
	public void setType(ItemType type){
		this.type = type;
	}
	
}
