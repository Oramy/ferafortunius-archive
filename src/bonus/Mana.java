package bonus;

import org.newdawn.slick.Color;

import ObjetMap.Entity;


public class Mana extends SimpleBonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Mana(int amount, Entity o) {
		super(amount, o);
		if(getCible().getHp() == getCible().getMaxHp()){
			getCible().getBonus().remove(this);
		}
		if(amount > 0){
			color = new Color(0,0,255,255);
			setText("+"+amount+" MP");
		}
		else{
			color = new Color(120,120,255,255);
			setText(amount+" MP");
		}
	}

	@Override
	public void effect() {
		getCible().increaseMp(amount);
	}

}
