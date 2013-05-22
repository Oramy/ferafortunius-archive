package Items;

import ObjetMap.Entity;
import ObjetMap.ObjetImage;

public class Hache extends Arme {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6553004050018487079L;

	public Hache(Entity owner) {
		super(owner);
		this.weight = 60;
		this.img = new ObjetImage("Items/hache.png");
		name = "Hache";
		setMaxHpBonus(20);
		setTwoHands(true);
		setDescription("Une arme commune et lourde, mais un peu abimée.");
		
	}

}
