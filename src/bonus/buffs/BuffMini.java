package bonus.buffs;

import ObjetMap.Entity;

public class BuffMini extends Buff{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BuffMini(int length, Entity o) {
		super(length, o);
		buffImage = "GUI/Buff/regenlife.png";
	}

	@Override
	public void buffEffect() {
		
	}

	@Override
	public void endBuffEffect() {
		this.getCible().extend(2f);
	}

	@Override
	public void effect() {
		this.getCible().reduce(2f);
	}

}
