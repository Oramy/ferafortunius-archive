package ObjetMap;

import org.newdawn.slick.geom.*;

public class CylinderBlock extends CollisionBlock{

	public CylinderBlock(int posX, int posY, int posZ, int sizeX, int sizeY,
			int sizeZ) {
		super(posX, posY, posZ, sizeX, sizeY, sizeZ);
		// TODO Auto-generated constructor stub
	}
	public Shape getShape(ObjetMap parent){
		Shape p2 = new Ellipse(parent.getPosX() + posX  + sizeX / 2, parent.getPosY() + posY + sizeY / 2, sizeX / 2, sizeY / 2);
		return p2;
	}
	
}
