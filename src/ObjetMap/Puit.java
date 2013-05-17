package ObjetMap;

import java.util.ArrayList;

public class Puit extends ObjetMap {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7005136144817654896L;

	public  Puit(int chunkX, int chunkY, int chunkZ, int posX, int posY,
			int posZ) {
		super(chunkX, chunkY, chunkZ, posX, posY, posZ);
		setNom("Puit");
		setSizeX(50);
		setSizeY(50);
		setSizeZ(140);
		decalageX = -40;
		decalageY = 0;
		image = new ArrayList<ObjetImage>();
		image.add(new ObjetImage("ObjetMap/Puit/puit.png", 120, 157, 1202, 1575, 0, 0));
		
	}


	@Override
	protected ObjetMap newMe(int chunkX, int chunkY, int chunkZ, int posX,
			int posY, int posZ) {
		
		return null;
	}

}
