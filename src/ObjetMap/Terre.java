package ObjetMap;

import java.util.ArrayList;

public class Terre extends ObjetMap {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6125346206581719428L;

	public Terre(int chunkX, int chunkY, int chunkZ, int posX, int posY, int posZ){
		super(chunkX, chunkY, chunkZ, posX, posY, posZ);
		setNom("Terre");
		setSizeX(100);
		setSizeY(100);
		setSizeZ(100);
		decalageX = 0;
		decalageY = 0;
		image = new ArrayList<ObjetImage>();
		image.add(new ObjetImage("ObjetMap/Terre/terre.png", 30, 20, 288, 206, 0, 0));
		getCollision().add(new LosangeBlock(0,0,0,100,100,100));
	}

	@Override
	protected ObjetMap newMe(int chunkX, int chunkY, int chunkZ, int posX,
			int posY, int posZ) {
		
		return null;
	}

}
