package ObjetMap;

import java.util.ArrayList;

public class PierreTerre extends ObjetMap {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4731654919643409489L;

	public PierreTerre(int chunkX, int chunkY, int chunkZ, int posX, int posY,
			int posZ) {
		super(chunkX, chunkY, chunkZ, posX, posY, posZ);
		setNom("Pierre");
		setSizeX(14);
		setSizeY(14);
		setSizeZ(4);
		decalageX = -0;
		decalageY = -0;
		image = new ArrayList<ObjetImage>();
		image.add(new ObjetImage("ObjetMap/Pierre/casepierreterre.png", 30, 20, 288, 206, 0, 0));
		getCollision().add(new CollisionBlock(0,0,0,14,14,4));
		
	}
	@Override
	protected ObjetMap newMe(int chunkX, int chunkY, int chunkZ, int posX,
			int posY, int posZ) {
		
		return null;
	}

}
