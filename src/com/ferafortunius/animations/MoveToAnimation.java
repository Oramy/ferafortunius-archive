package com.ferafortunius.animations;

import ObjetMap.ObjetImage;

/**
 * A class who make a move in a period
 * @author Oramy
 *
 */
public class MoveToAnimation implements Runnable {

	protected float x, y, rotation;
	protected long time;
	protected ObjetImage cible; 
	
	public MoveToAnimation(float x, float y, float rotation, long time, ObjetImage cible){
		this.x = x;
		this.y = y;
		this.rotation = rotation;
		this.time = time;
		this.cible = cible;
	}
	@Override
	public void run() {
		long firstTime = System.currentTimeMillis();
		
		long cursor = System.currentTimeMillis();
		long max = firstTime + time;
		
		float realX = cible.getDecalageX();
		float realY = cible.getDecalageY();
		float realRotation = cible.getRotation();
		while(cursor < max){
			float coef = (float)(cursor - firstTime) / (float)time;
			float actualX = (x - realX) * coef;
			float actualY = (y - realY) * coef;
			float actualRotation = (rotation - realRotation) * coef;
			
			cible.setDecalageX((int)actualX);
			cible.setDecalageY((int)actualY);
			cible.setRotation((int)actualRotation);
			cursor = System.currentTimeMillis();
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		cible.setDecalageX((int) (x));
		cible.setDecalageY((int) (y));
	}

}
