package ObjetMap;

import java.util.ArrayList;

public class Panneau extends ObjetMap {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4666369304276604408L;

	public Panneau(int chunkX, int chunkY, int chunkZ, int posX, int posY, int posZ){
		super(chunkX, chunkY, chunkZ, posX, posY, posZ);
		setNom("Panneau");
		setSizeX(22);
		setSizeY(5);
		setSizeZ(22);
		decalageX = 4;
		decalageY = -3;
		image = new ArrayList<ObjetImage>();
		image.add(new ObjetImage("ObjetMap/Panneau/panneau.png", 38, 46, 194, 231, 60, 0));
		getCollision().add(new CollisionBlock(0,0,0,2,22,22));
	}
	
	@Override
	protected ObjetMap newMe(int chunkX, int chunkY, int chunkZ, int posX,
			int posY, int posZ) {
		
		return null;
	}

}
