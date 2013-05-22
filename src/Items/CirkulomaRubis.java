package Items;

import ObjetMap.Entity;
import ObjetMap.ObjetImage;

public class CirkulomaRubis extends CirkulomaVerre{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7047356202641415072L;

	public CirkulomaRubis(Entity owner) {
		super(owner);
		setCirkulomaValue(10000);
		this.img = new ObjetImage("Items/spriteCirkuloma.png", 100, 100, 200, 200, 5, 0);
		name = "Cirkuloma Rubis";
		
	}

}

