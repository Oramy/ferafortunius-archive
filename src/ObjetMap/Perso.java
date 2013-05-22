package ObjetMap;


import java.util.ArrayList;

import Items.HumanEquipment;


public class Perso extends Entity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4369749988814525120L;
	public Perso(int chunkX, int chunkY, int chunkZ, int posX, int posY, int posZ){
		super(chunkX, chunkY, chunkZ, posX, posY, posZ);
		setNom("Perso");
		setSizeX(8);
		setSizeY(8);
		setSizeZ(24);
		decalageY = 0;
		id = -1;
		hp = maxHp = 100;
		mp = maxMp = 100;
		image = new ArrayList<ObjetImage>();
		image.add(new ObjetImage("ObjetMap/Personnage/personnage.png", 32, 32, 32, 32, 0, 0));
		getCollision().add(new CollisionBlock(0,0,0,8,8,24));
		equipment = new HumanEquipment(this);
		
	}
	@Override
	protected ObjetMap newMe(int chunkX, int chunkY, int chunkZ, int posX, int posY, int posZ){
		
		return new Perso( chunkX, chunkY,  chunkZ,  posX,  posY,  posZ);
	}
}
