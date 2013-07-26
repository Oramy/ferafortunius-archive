package bonus;

import bonus.buffs.Buff;
import ObjetMap.Entity;

public class MaxLife extends Buff{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int amount;
	public MaxLife(Entity o) {
		super(50, o);
		amount = 5;
		buffImage = "GUI/Buff/button.png";
	}

	@Override
	public void buffEffect() {
		
	}

	@Override
	public void effect() {
		getCible().setMaxHp((int)Math.ceil(getCible().getMaxHp() * (100f+(float)amount) / 100f));
	}

	@Override
	public void endBuffEffect() {
		getCible().setMaxHp((int)Math.floor(getCible().getMaxHp() / (100f+(float)amount) * 100f));
	}

}
