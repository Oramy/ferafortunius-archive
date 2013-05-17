package Items;

import ObjetMap.Entity;
import ObjetMap.ObjetImage;

public class Plume extends Item {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7407368450141616047L;

	public Plume(Entity owner) {
		super(owner);
		this.weight = 1;
		maxstackNumber = 50;
		this.img = new ObjetImage("Items/plume.png");
		name = "Plume";
		setDescription("Une petite plume commune, elle pourra vous servir si vous êtes un grand écrivain !");
		
	}

}
