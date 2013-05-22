package Items;

import ObjetMap.Entity;
import ObjetMap.ObjetImage;

public class CirkulomaQuartz extends CirkulomaVerre{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7446442499164681313L;

	public CirkulomaQuartz(Entity owner) {
		super(owner);
		setCirkulomaValue(10);
		this.img = new ObjetImage("Items/spriteCirkuloma.png", 100, 100, 200, 200, 1, 0);
		name = "Cirkuloma Quartz";
		
	}

}
