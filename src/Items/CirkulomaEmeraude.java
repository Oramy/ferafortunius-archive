package Items;

import ObjetMap.Entity;
import ObjetMap.ObjetImage;

public class CirkulomaEmeraude extends CirkulomaVerre{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5861584000427030427L;

	public CirkulomaEmeraude(Entity owner) {
		super(owner);
		setCirkulomaValue(1000);
		this.img = new ObjetImage("Items/spriteCirkuloma.png", 100, 100, 200, 200, 4, 0);
		name = "Cirkuloma Emeraude";
	}

}
