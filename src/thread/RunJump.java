package thread;

import gui.jeu.Jeu;
import Level.ChunkMap;
import ObjetMap.ObjetMap;

public class RunJump implements Runnable {

	private int jumpSize;
	private int jumpTime;
	ObjetMap o;
	private ChunkMap carte;
	private Jeu jeu;
	/**
	 * @param jeu the actual window used
	 * @param chunkPersoX the actual X chunk position
	 * @param chunkPersoY the actual Y chunk position
	 * @param chunkPersoZ the actual Z chunk position
	 * @param posX the actual X position
	 * @param posX the actual Y position
	 * @param posX the actual Z position
	 * @param jumpSize the jumpSize
	 * @param jumpTime the jumpTime
	 */
	public RunJump(Jeu jeu, ChunkMap c, ObjetMap o, int jumpSize, int jumpTime, int id) {
		
		this.carte = c;
		this.jeu = jeu;
		this.jumpSize = jumpSize;
		this.jumpTime = jumpTime;
		this.o = o;
	}

	@Override
	public void run() {
		
		o.setFly(true);
		for(int i = 0; i < jumpSize; i ++){
			try {
				carte.deplacement(o, 0, 0, 1, jeu);
				Thread.sleep(jumpTime / jumpSize / 2);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			} 
		}
		int ancZ = o.getPosZ();
		carte.deplacement(o, 0, 0, -1, jeu);
		while(ancZ != o.getPosZ()){
			try{	
				Thread.sleep(jumpTime / jumpSize / 2);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			} 
			ancZ = o.getPosZ();
			carte.deplacement(o, 0, 0, -1, jeu);
		}
		o.setFly(false);
	}

}
