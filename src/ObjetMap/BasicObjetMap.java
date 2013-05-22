package ObjetMap;

public class BasicObjetMap extends ObjetMap {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BasicObjetMap(int chunkX, int chunkY, int chunkZ, int posX,
			int posY, int posZ) {
		super(chunkX, chunkY, chunkZ, posX, posY, posZ);
	}

	@Override
	protected ObjetMap newMe(int chunkX, int chunkY, int chunkZ, int posX,
			int posY, int posZ) {
		return null;
	}

}
