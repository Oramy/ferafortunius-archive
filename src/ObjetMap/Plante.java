package ObjetMap;

import java.util.ArrayList;

public class Plante extends ObjetMap {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2577494640431307237L;

	public Plante(int chunkX, int chunkY, int chunkZ, int posX, int posY,
			int posZ) {
		super(chunkX, chunkY, chunkZ, posX, posY, posZ);
		setNom("Pierre");
		setSizeX(12);
		setSizeY(12);
		setSizeZ(12);
		decalageX = 6;
		decalageY = -2;
		image = new ArrayList<ObjetImage>();
		image.add(new ObjetImage("ObjetMap/Plante/plante.png", 19, 26, 190, 262, 0, 0));
		
	}
	@Override
	protected ObjetMap newMe(int chunkX, int chunkY, int chunkZ, int posX,
			int posY, int posZ) {
		
		return null;
	}

}
