package Items;

import ObjetMap.Entity;
import ObjetMap.ObjetImage;

public class CirkulomaDiamant extends CirkulomaVerre{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8958932515873111728L;

	public CirkulomaDiamant(Entity owner) {
		super(owner);
		setCirkulomaValue(100);
		this.img = new ObjetImage("Items/spriteCirkuloma.png", 100, 100, 200, 200, 3, 0);
		name = "Cirkuloma Diamant";
		
	}

}
