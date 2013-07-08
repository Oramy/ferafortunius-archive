package ObjetMap;

import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

public class LosangeBlock extends CollisionBlock{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public LosangeBlock(int posX, int posY, int posZ, int sizeX, int sizeY,
			int sizeZ) {
		super(posX, posY, posZ, sizeX, sizeY, sizeZ);
	}
	//Teste les quatres points d'un carré avec un losange.
	//TODO Collision rectangles à 45°
	
	public Shape getShape(ObjetMap parent){
		Polygon p2 = new Polygon();
		p2.addPoint(parent.getX() + this.getPosX() + this.getSizeX() / 2,
				parent.getY() + this.getPosY());
		
		p2.addPoint(parent.getX() + this.getPosX() + this.getSizeX(),
				parent.getY() + this.getPosY() + this.getSizeY() / 2);
		p2.addPoint(parent.getX() + this.getPosX() + this.getSizeX() / 2,
				parent.getY() + this.getPosY() + this.getSizeY());
		p2.addPoint(parent.getX() + this.getPosX(),
				parent.getY() + this.getPosY() + this.getSizeY() / 2);
		return p2;
	}
}
