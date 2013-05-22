package Items;

import ObjetMap.Entity;
import ObjetMap.ObjetImage;

public class FireShield extends Bouclier {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8285128234579161542L;

	public FireShield(Entity owner) {
		super(owner);
		this.weight = 20;
		this.img = new ObjetImage("Items/fireShield.png");
		name = "Fire shield";
		setDescription("Un bouclier vous protégeant particulièrement contre le feu.");
		
	}

}
