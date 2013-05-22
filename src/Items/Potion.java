package Items;

import ObjetMap.Entity;
import ObjetMap.ObjetImage;
import bonus.Life;

public class Potion extends Utility{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5664922540983133183L;
	private int capacite;
	public Potion(Entity owner) {
		super(owner);
		capacite = 5;
		this.weight = 5;
		this.name = "Potion de vie mineure";
		this.img = new ObjetImage("Items/potion1.png");
		addBonus(new Life(capacite, owner));
		setUseNumberMax(3);
		maxstackNumber = 99;
		useNumber = getUseNumberMax();
		setDescription("Potion commune dans Histae, on la retrouve dans tous les magasins.");
		
	}
	public void use() {
		super.use();
		this.img = new ObjetImage("Items/potion"+(4-useNumber)+".png");
	}
}
