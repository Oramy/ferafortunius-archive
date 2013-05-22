package ObjetMap;

import java.util.ArrayList;

public class Banc extends ObjetMap {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1270873702546492459L;

	public Banc(int chunkX, int chunkY, int chunkZ, int posX, int posY, int posZ) {
		super(chunkX, chunkY, chunkZ, posX, posY, posZ);
		update = false;
		setNom("Banc");
		setSizeX(30);
		setSizeY(20);
		setSizeZ(30);
		image = new ArrayList<ObjetImage>();
		image.add(new ObjetImage("ObjetMap/Banc/banc2.png", 55, 57, 547, 572, 0, 0));
		this.addCollisionBlock(new CollisionBlock(0,15,0,30,5,30));
		this.addCollisionBlock(new CollisionBlock(0,0,0,30,15,15));
	}

	@Override
	protected ObjetMap newMe(int chunkX, int chunkY, int chunkZ, int posX,
			int posY, int posZ) {
		
		return null;
	}

}
