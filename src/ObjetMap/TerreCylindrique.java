package ObjetMap;

import java.util.ArrayList;

public class TerreCylindrique extends ObjetMap {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2522395006905483656L;

	public TerreCylindrique(int chunkX, int chunkY, int chunkZ, int posX, int posY,
			int posZ) {
		super(chunkX, chunkY, chunkZ, posX, posY, posZ);
		setNom("Terre cylindrique");
		setSizeX(4);
		setSizeY(4);
		setSizeZ(5);
		decalageX = -5;
		image = new ArrayList<ObjetImage>();
		image.add(new ObjetImage("ObjetMap/Terre Cylindrique/terre_cylindre.png", 10, 10, 100, 100, 0, 0));
		
	}

	@Override
	protected ObjetMap newMe(int chunkX, int chunkY, int chunkZ, int posX,
			int posY, int posZ) {
		
		return null;
	}

}
