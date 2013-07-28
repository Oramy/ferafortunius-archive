package Items;

import java.util.ArrayList;

import ObjetMap.Entity;
import bonus.Bonus;

public class Utility extends Item{
	/**
	 * 
	 */
	private static final long serialVersionUID = 750382835391952161L;
	protected int useNumber;
	protected int useNumberMax;
	protected String effectDescription;
	private ArrayList<Bonus> bonus;
	public Utility(Entity owner) {
		super(owner);
		setUseNumberMax(0);
		useNumber = getUseNumberMax();
		setEffectDescription("");
		setBonus(new ArrayList<Bonus>());
		
	}
	public void use() {
		owner.addBonus(getBonus());
		setUseNumber(getUseNumber() - 1);
		if(useNumber <= 0){
			owner.getInventaire().removeContent(this);
		}
	
	}
	public void addBonus(Bonus bonus){
		effectDescription += bonus.getText();
		this.getBonus().add(bonus);
	}
	public void removeBonus(Bonus bonus){
		effectDescription.replace(bonus.getText(), "");
		this.getBonus().remove(bonus);
	}
	/**
	 * @return the useNumberMax
	 */
	public int getUseNumberMax() {
		return useNumberMax;
	}
	/**
	 * @param useNumberMax the useNumberMax to set
	 */
	public void setUseNumberMax(int useNumberMax) {
		this.useNumberMax = useNumberMax;
	}
	/**
	 * @return the useNumber
	 */
	public int getUseNumber() {
		return useNumber;
	}
	/**
	 * @param useNumber the useNumber to set
	 */
	public void setUseNumber(int useNumber) {
		this.useNumber = useNumber;
	}
	/**
	 * @return the effectDescription
	 */
	public String getEffectDescription() {
		return effectDescription;
	}
	/**
	 * @param effectDescription the effectDescription to set
	 */
	public void setEffectDescription(String effectDescription) {
		this.effectDescription = effectDescription;
	}
	public ArrayList<Bonus> getBonus() {
		if(bonus == null)
			bonus = new ArrayList<Bonus>();
		return bonus;
	}
	public void setBonus(ArrayList<Bonus> bonus) {
		this.bonus = bonus;
	}
}
