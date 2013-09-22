package Items;

import ObjetMap.Entity;
import ObjetMap.ObjetImage;
import bonus.buffs.BuffMini;

public class Potion extends Utility{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5664922540983133183L;
	public Potion(Entity owner) {
		super(owner);
		this.weight = 5;
		this.name = "Potion de vie mineure";
		this.img = new ObjetImage("Items/potion1.png");
		//addBonus(new Life(capacite, owner));
		setUseNumberMax(1);
		maxstackNumber = 99;
		useNumber = getUseNumberMax();
		setDescription("Potion commune dans Histae, on la retrouve dans tous les magasins.");
		
	}
	public void use() {
		this.setUseNumberMax(1);
		this.setUseNumber(1);
		super.use();
		this.img = new ObjetImage("Items/potion"+(4-useNumber)+".png");
		owner.addBonus(new BuffMini(120, owner));
	}
}
