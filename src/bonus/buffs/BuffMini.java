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
		Thread t = new Thread(new Runnable(){

			@Override
			public void run() {
				for(int i = 0; i < 3; i ++){
					getCible().extend(1.1f);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		});
		t.start();
	}

	@Override
	public void effect() {
		Thread t = new Thread(new Runnable(){

			@Override
			public void run() {
				for(int i = 0; i < 3; i ++){
					getCible().reduce(1.1f);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		});
		t.start();
	}

}
