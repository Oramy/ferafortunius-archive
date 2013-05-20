package Items;

import java.io.Serializable;
import java.util.ArrayList;

import ObjetMap.Entity;



public abstract class Equipment  implements Serializable, Cloneable{/**
	 * 
	 */
	protected static final long serialVersionUID = 1L;
	private ArrayList<EquipmentItem> contents;
	protected Entity owner;
	public Equipment(Entity owner){
		this.owner = owner;
		setContents(new ArrayList<EquipmentItem>());
	}
	public Equipment clone(){
		Equipment o = null;
		try {
			// On récupère l'instance à renvoyer par l'appel de la 
			// méthode super.clone()
			o = (Equipment) super.clone();
		} catch(CloneNotSupportedException cnse) {
			// Ne devrait jamais arriver car nous implémentons 
			// l'interface Cloneable
			cnse.printStackTrace(System.err);
		}
		o.owner = owner;
		o.contents = new ArrayList<EquipmentItem>();
		for(EquipmentItem equip  : contents){
			o.contents.add((EquipmentItem) equip.clone());
		}
		return o;
	}
	public void addEquipment(EquipmentItem equip){
		if(!getContents().contains(equip)){
			owner.setHpgain(owner.getHpgain() + equip.getHpGainBonus());
			owner.setMpgain(owner.getMpgain() + equip.getMpGainBonus());
			owner.setMaxHp(owner.getMaxHp() + equip.getMaxHpBonus());
			owner.setMaxMp(owner.getMaxMp() + equip.getMaxMpBonus());
			getContents().add(equip);
		}
	}
	public void removeEquipment(EquipmentItem equip){
		if(getContents().contains(equip)){
			owner.setHpgain(owner.getHpgain() - equip.getHpGainBonus());
			owner.setMpgain(owner.getMpgain() - equip.getMpGainBonus());
			owner.setMaxHp(owner.getMaxHp() - equip.getMaxHpBonus());
			owner.setMaxMp(owner.getMaxMp() - equip.getMaxMpBonus());
			getContents().remove(equip);
		}
	}
	public abstract void equip(EquipmentItem equip);
	public abstract void unequip(EquipmentItem equip);
	/**
	 * @return the owner
	 */
	public Entity getOwner() {
		return owner;
	}
	/**
	 * @param owner the owner to set
	 */
	public void setOwner(Entity owner) {
		this.owner = owner;
	}
	/**
	 * @return the contents
	 */
	public ArrayList<EquipmentItem> getContents() {
		return contents;
	}
	/**
	 * @param contents the contents to set
	 */
	public void setContents(ArrayList<EquipmentItem> contents) {
		this.contents = contents;
	}

}
