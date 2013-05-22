package Items;

import ObjetMap.Entity;
import ObjetMap.ObjetImage;

public class CirkulomaSaphir extends CirkulomaVerre{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6292801973487451387L;

	public CirkulomaSaphir(Entity owner) {
		super(owner);
		setCirkulomaValue(50);
		this.img = new ObjetImage("Items/spriteCirkuloma.png", 100, 100, 200, 200, 2, 0);
		name = "Cirkuloma Saphir";
		
	}

}
