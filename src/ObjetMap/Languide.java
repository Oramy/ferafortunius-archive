package ObjetMap;

import gui.jeu.Jeu;

public class Languide extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Languide(int chunkX, int chunkY, int chunkZ, int posX, int posY,
			int posZ) {
		super(chunkX, chunkY, chunkZ, posX, posY, posZ);
		
	}
	public void update(Jeu jeu){
		super.update(jeu);
	}
	@Override
	protected ObjetMap newMe(int chunkX, int chunkY, int chunkZ, int posX,
			int posY, int posZ) {
		
		return null;
	}

}
