package ObjetMap;

import java.util.ArrayList;


public class PetiteCaisse extends Caisse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5375630397316051599L;

	public PetiteCaisse(int chunkX, int chunkY, int chunkZ, int posX, int posY,
			int posZ) {
		super(chunkX, chunkY, chunkZ, posX, posY, posZ);
		setNom("Petite Caisse");
		setSizeX(16);
		setSizeY(16);
		setSizeZ(17);
		decalageX = 0;
		decalageY = 0;
		image = new ArrayList<ObjetImage>();
		image.add(new ObjetImage("ObjetMap/Caisse/caisse.png", 30, 33, 600, 659, 0, 0));
		this.addCollisionBlock(new CollisionBlock(0,0,0,34,20,23));
	}

}
