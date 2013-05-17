package Items;

import ObjetMap.Entity;

public class Arme extends EquipmentItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4717824312271452475L;
	private boolean twoHands;
	public Arme(Entity owner) {
		super(owner);
		setTwoHands(false);
		
	}
	/**
	 * @return the twoHands
	 */
	public boolean isTwoHands() {
		return twoHands;
	}
	/**
	 * @param twoHands the twoHands to set
	 */
	public void setTwoHands(boolean twoHands) {
		this.twoHands = twoHands;
	}

}
