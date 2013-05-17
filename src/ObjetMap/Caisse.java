package ObjetMap;

import java.util.ArrayList;

public class Caisse extends ObjetMap {
	/**
	 * 
	 */
	private static final long serialVersionUID = 791986065755268397L;

	public Caisse(int chunkX, int chunkY, int chunkZ, int posX, int posY, int posZ) {
		super(chunkX, chunkY, chunkZ, posX, posY, posZ);
		update = false;
		setNom("Caisse");
		setSizeX(31);
		setSizeY(31);
		setSizeZ(34);
		decalageX = -14;
		decalageY = -16;
		image = new ArrayList<ObjetImage>();
		image.add(new ObjetImage("ObjetMap/Caisse/caisse.png", 60, 66, 600, 659, 0, 0));
		this.addCollisionBlock(new CollisionBlock(0,0,0,34,20,23));
	}
	
	@Override
	protected ObjetMap newMe(int chunkX, int chunkY, int chunkZ, int posX,
			int posY, int posZ) {
		
		return null;
	}

}
