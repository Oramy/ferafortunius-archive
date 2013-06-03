package Items;

import java.util.ArrayList;

import ObjetMap.Entity;
import ObjetMap.ObjetImageList;

public class EquipmentItem extends Item {
	/**
	 * 
	 */
	private static final long serialVersionUID =  1L;
	protected int maxMpBonus;
	protected int maxHpBonus;
	protected int hpGainBonus;
	protected int mpGainBonus;
	protected EquipmentType equipType;
	
	private ArrayList<ObjetImageList> images;
	public EquipmentItem(Entity owner) {
		super(owner);
		setMaxMpBonus(0);
		setMaxHpBonus(0);
		setMpGainBonus(0);
		setHpGainBonus(0);
		this.type = ItemType.Equipment;
		this.equipType = EquipmentType.Basic;
		
		images = new ArrayList<ObjetImageList>();
//		ObjetImage i = new ObjetImage("img");
//		i.setAlias("alias de l'image à remplacer");
//		i.setParentAlias("alias de l'image à remplacer");
//		images.put("liste où l'image doit être remplacée ", i);
	}
	/**
	 * @return the mpMaxBonus
	 */
	public int getMaxMpBonus() {
		return maxMpBonus;
	}
	/**
	 * @param mpMaxBonus the mpMaxBonus to set
	 */
	public void setMaxMpBonus(int mpMaxBonus) {
		this.maxMpBonus = mpMaxBonus;
	}
	/**
	 * @return the hpMaxBonus
	 */
	public int getMaxHpBonus() {
		return maxHpBonus;
	}
	/**
	 * @param hpMaxBonus the hpMaxBonus to set
	 */
	public void setMaxHpBonus(int hpMaxBonus) {
		this.maxHpBonus = hpMaxBonus;
	}
	/**
	 * @return the hpGainBonus
	 */
	public int getHpGainBonus() {
		return hpGainBonus;
	}
	/**
	 * @param hpGainBonus the hpGainBonus to set
	 */
	public void setHpGainBonus(int hpGainBonus) {
		this.hpGainBonus = hpGainBonus;
	}
	/**
	 * @return the mpGainBonus
	 */
	public int getMpGainBonus() {
		return mpGainBonus;
	}
	/**
	 * @param mpGainBonus the mpGainBonus to set
	 */
	public void setMpGainBonus(int mpGainBonus) {
		this.mpGainBonus = mpGainBonus;
	}
	public EquipmentType getEquipmentType(){
		return equipType;
	}
	public ArrayList<ObjetImageList> getImages() {
		return images;
	}
	public void setImages(ArrayList<ObjetImageList> images) {
		this.images = images;
	}
}
