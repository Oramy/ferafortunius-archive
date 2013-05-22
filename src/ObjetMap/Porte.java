package ObjetMap;

import java.util.ArrayList;

public class Porte extends ObjetMap{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1373635428581923687L;
	public Porte(int chunkX, int chunkY, int chunkZ, int posX, int posY,
			int posZ) {
		super(chunkX, chunkY, chunkZ, posX, posY, posZ);
		setNom("Porte");
		setSizeX(12);
		setSizeY(2);
		setSizeZ(30);
		decalageX = 10;
		decalageY = 0;
		opacity = 1f;
		image = new ArrayList<ObjetImage>();
		image.add(new ObjetImage("ObjetMap/Porte/porte.png", 20, 46, 301, 702, 0, 0));
	}

	

	@Override
	protected ObjetMap newMe(int chunkX, int chunkY, int chunkZ, int posX,
			int posY, int posZ) {
		
		return null;
	}

}
