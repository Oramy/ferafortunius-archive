package bonus;

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
		getCible().setMaxHp(getCible().getMaxHp() * (100+amount) / 100);
	}

	@Override
	public void endBuffEffect() {
		getCible().setMaxHp(getCible().getMaxHp() / (100+amount) * 100);
	}

}
