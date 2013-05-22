package Items;

import ObjetMap.Entity;
import ObjetMap.ObjetImage;

public class LittleWoodSword extends Epee{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7430891944346683494L;

	public LittleWoodSword(Entity owner) {
		super(owner);
		this.weight = 10;
		this.img = new ObjetImage("Items/littleWoodSword.png");
		name = "Petite épée en bois";
		setHpGainBonus(2);
		setMaxHpBonus(-5);
		setDescription("Une petite épée conçue pour les enfants, ou les maladroits, mais sa puissance est presque nulle.");
		
	}

}
