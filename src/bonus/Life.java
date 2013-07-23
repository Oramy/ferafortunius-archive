package bonus;

import org.newdawn.slick.Color;

import ObjetMap.Entity;


public class Life extends SimpleBonus{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Life(int amount, Entity o){
		super(amount, o);
		if(getCible().getHp() == getCible().getMaxHp() && amount > 0){
			getCible().getBonus().remove(this);
		}
		if(amount > 0){
			color = new Color(0,255,0,255);
			setText("+"+amount+" HP");
		}
		else{
			color = new Color(255,0,0,255);
			setText(amount+" HP");
		}
	}
	public void effect(){
		getCible().increaseHp(amount);
	}
}
