package bonus;

import gui.jeu.Jeu;
import ObjetMap.Entity;

public abstract class Buff extends Bonus{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 
	 * Le chemin de l'image du buff
	 */
	protected String buffImage;
	/**
	 * Durée du buff, en secondes.
	 */
	private int length;
	/**
	 * Le temps qu'il reste avant la disparition du buff, en secondes.
	 */
	private int restingTime;
	/**
	 * Le maximum d'accumulation du buff
	 */
	protected int maxStackNumber; 
	
	/**
	 * Le temps de la dernière MAJ
	 */
	protected transient long lastMaj;
	public Buff(int length, Entity o) {
		super(o);
		init(length);
	}
	/**
	 * Méthode de réinitialisation
	 * @param length la durée du buff
	 */
	private void init(int length){
		setRestingTime(length);
		this.length = length;
		lastMaj = System.currentTimeMillis();
	}
	/**
	 * @param amount le temps à enlever
	 */
	private void decreaseRestingTime(int amount){
		setRestingTime(getRestingTime() - amount);
	}
	/**
	 *  Met à jour le buff, doit être activé à chaque secondes.
	 *  @param lastUpdateInterval donne le nombre de secondes passées depuis la dernière MAJ des buffs.
	 */
	public void update(Jeu jeu){
		long refreshTime =  System.currentTimeMillis() - 1000;
		if(lastMaj < refreshTime){
			decreaseRestingTime((int)((float)(refreshTime - lastMaj) / 1000f + 1));
			buffEffect();
			lastMaj = System.currentTimeMillis();
		}
		if(! isValid()){
			endBuffEffect();
			getCible().getBonus().remove(this);
		}
	}
	/**
	 * Buffs child methods
	 * 
	 */
	public abstract void buffEffect();
	public abstract void endBuffEffect();
	/* -------------------
	 * Getters and Setters
	 * -------------------
	 */
	public boolean isValid(){
		boolean valid = false;
		if(getRestingTime() > 0)
			valid = true;
		return valid;
	}
	public String getBuffImage() {
		return buffImage;
	}
	public void setBuffImage(String buffImage) {
		this.buffImage = buffImage;
	}
	public int getRestingTime() {
		return restingTime;
	}
	public void setRestingTime(int restingTime) {
		this.restingTime = restingTime;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
}
