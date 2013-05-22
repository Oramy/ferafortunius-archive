package Items;

import ObjetMap.Entity;
import ObjetMap.ObjetImage;

public class MoonBow extends Arc{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3457717432687791266L;

	public MoonBow(Entity owner) {
		super(owner);
		this.weight = 25;
		this.img = new ObjetImage("Items/moonBow.png");
		name = "Moon Bow";
		setMaxMpBonus(20);
		setDescription("Un arc mystique connu de tous les archers et à manier avec précaution. Il amplifie grandement la vitesse des flèches.");
		
	}

}
