package bonus.buffs;

import ObjetMap.Entity;

public class BuffMini extends Buff{

	public BuffMini(int length, Entity o) {
		super(length, o);
		buffImage = "GUI/Buff/regenlife.png";
	}

	@Override
	public void buffEffect() {
		// TODO Auto-generated method stub
		
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
