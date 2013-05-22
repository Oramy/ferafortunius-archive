package bonus;

import ObjetMap.Entity;

public class BuffRegenLife extends Buff{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BuffRegenLife(Entity o) {
		super(20, o);
		buffImage = "GUI/Buff/regenlife.png";
	}

	@Override
	public void effect() {
		
	}

	@Override
	public void buffEffect() {
		getCible().addBonus(new Life(10, getCible()));
	}

	@Override
	public void endBuffEffect() {
		// TODO Auto-generated method stub
		
	}

}
