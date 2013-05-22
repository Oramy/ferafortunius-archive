package Items;

import ObjetMap.Entity;
import ObjetMap.ObjetImage;

public class GrassSword extends Epee{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3427246213137803342L;

	public GrassSword(Entity owner) {
		super(owner);
		this.weight = 13;
		this.img = new ObjetImage("Items/grassSword.png");
		name = "Grass Sword";
		setMaxHpBonus(10);
		setDescription("Faite d'herbe, cette épée est aussi coupante que le meilleur sabre mais aussi légère que de l'herbe. Elle est rare et peu d'artisans savent la fabriquer.");
		
	}

}
