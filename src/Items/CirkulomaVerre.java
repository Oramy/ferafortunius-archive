package Items;

import ObjetMap.Entity;
import ObjetMap.ObjetImage;

public class CirkulomaVerre extends Item{
	/**
	 * 
	 */
	protected static final long serialVersionUID = -4479020422859590017L;
	private long cirkulomaValue;
	public CirkulomaVerre(Entity owner) {
		super(owner);
		this.weight = 0;
		setCirkulomaValue(1);
		maxstackNumber = 999999;
		this.img = new ObjetImage("Items/spriteCirkuloma.png", 100, 100, 200, 200, 0, 0);
		name = "Cirkuloma Verre";
		setDescription("La monnaie de Fera Fortunius.");
		setAutoGet(true);
		setAutoGetRange(30);
		
	}
	/**
	 * @return the cirkulomaValue
	 */
	public long getCirkulomaValue() {
		return cirkulomaValue;
	}
	/**
	 * @param cirkulomaValue the cirkulomaValue to set
	 */
	public void setCirkulomaValue(int cirkulomaValue) {
		this.cirkulomaValue = cirkulomaValue;
	}

}
