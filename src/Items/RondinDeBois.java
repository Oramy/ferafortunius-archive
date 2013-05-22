package Items;

import ObjetMap.Entity;
import ObjetMap.ObjetImage;

public class RondinDeBois extends Item {

	/**
	 * 
	 */
	private static final long serialVersionUID = -767499699512252705L;

	public RondinDeBois(Entity owner) {
		super(owner);
		this.weight = 5;
		maxstackNumber = 50;
		this.img = new ObjetImage("Items/bois.png");
		name = "Rondin de bois";
		setDescription("Bois, matériel essentiel du charpentier.");
		
	}


}
