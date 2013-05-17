package Items;

import ObjetMap.Entity;

public class HumanEquipment extends Equipment{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5357710248724977149L;
	private Arme leftArm;
	private Arme rightArm;
	private Casque casque;
	public HumanEquipment(Entity owner){
		super(owner);
		leftArm = rightArm =  null;
	}
	@Override
	public void equip(EquipmentItem equip) {
		if(equip instanceof Arme){
			if(!((Arme) equip).isTwoHands()){
				if(leftArm == null){
					leftArm = (Arme) equip;
					addEquipment(leftArm);
				}
				else if(rightArm == null){
					rightArm = (Arme) equip;
					addEquipment(rightArm);
				}
				else if(getContents().contains(leftArm) && getContents().contains(rightArm)){
					unequip(leftArm);
					equip(equip);
				}
			}else{
				if(leftArm != null){
					if(getContents().contains(leftArm)){
						unequip(leftArm);
					}
				}
				if(rightArm != null){
					if(getContents().contains(rightArm)){
						unequip(rightArm);
					}
				}
				if(leftArm == null && rightArm == null){
					leftArm = (Arme) equip;
					rightArm = (Arme) equip;
					addEquipment(leftArm);
				}
				
			}
		}
		if(equip instanceof Casque){
			if(casque == null){
				casque = (Casque) equip;
				addEquipment(casque);
			}
			else if(getContents().contains(casque)){
				unequip(casque);
				equip(equip);
			}
		}
	}

	@Override
	public void unequip(EquipmentItem equip) {
		removeEquipment(equip);
		if(equip instanceof Arme){
			if(leftArm != null){
				if(leftArm.equals(equip)){
					leftArm = null;
				}
			}
			if(rightArm != null){
				if(rightArm.equals(equip)){
					rightArm = null;
				}
			}
		}
	}

	/**
	 * @return the leftArm
	 */
	public Arme getLeftArm() {
		return leftArm;
	}

	/**
	 * @param leftArm the leftArm to set
	 */
	public void setLeftArm(Arme leftArm) {
		this.leftArm = leftArm;
	}

	/**
	 * @return the rightArm
	 */
	public Arme getRightArm() {
		return rightArm;
	}

	/**
	 * @param rightArm the rightArm to set
	 */
	public void setRightArm(Arme rightArm) {
		this.rightArm = rightArm;
	}
	/**
	 * @return the casque
	 */
	public Casque getCasque() {
		return casque;
	}
	/**
	 * @param casque the casque to set
	 */
	public void setCasque(Casque casque) {
		this.casque = casque;
	}

}
