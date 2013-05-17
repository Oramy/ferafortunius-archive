package Items;

import ObjetMap.Entity;
import ObjetMap.ObjetImage;

public class LanguideSkin extends Item{

	/**
	 * 
	 */
	private static final long serialVersionUID = -342497358528340043L;

	public LanguideSkin(Entity owner) {
		super(owner);
		this.weight = 4;
		maxstackNumber = 50;
		this.img = new ObjetImage("Items/languideSkin.png");
		name = "Peau de languide";
		setDescription("De la peau de languide, elle peut servir comme matériau primaire.");
		
	}

}
