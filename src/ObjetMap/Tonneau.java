package ObjetMap;

import java.util.ArrayList;

public class Tonneau extends ObjetMap {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3749794098778037940L;

	public Tonneau(int chunkX, int chunkY, int chunkZ, int posX, int posY, int posZ){
		super(chunkX, chunkY, chunkZ, posX, posY, posZ);
		setNom("Tonneau");
		setSizeX(16);
		setSizeY(16);
		setSizeZ(34);
		decalageX = -2;
		decalageY = 0;
		image = new ArrayList<ObjetImage>();
		image.add(new ObjetImage("ObjetMap/Tonneau/tonneau.png", 34, 50, 179, 258, 0, 0));
		this.addCollisionBlock(new CollisionBlock(0,0,0,34,20,23));
	}
	
	@Override
	protected ObjetMap newMe(int chunkX, int chunkY, int chunkZ, int posX,
			int posY, int posZ) {
		
		return null;
	}

}
