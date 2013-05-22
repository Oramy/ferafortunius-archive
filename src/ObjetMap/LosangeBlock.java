package ObjetMap;

import java.awt.Point;

public class LosangeBlock extends CollisionBlock{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float rapport;
	public LosangeBlock(int posX, int posY, int posZ, int sizeX, int sizeY,
			int sizeZ) {
		super(posX, posY, posZ, sizeX, sizeY, sizeZ);
		setRapport(0.5f);
	}
	//Teste les quatres points d'un carré avec un losange.
	//TODO Collision rectangles à 45°
	public boolean accept(ObjetMap parent, ObjetMap collideObj, CollisionBlock isoBlock) {
		boolean accept = true;
		//Si il est dans le carré où est inscrit le losange, alors on teste le losange
		if(!super.accept(parent, collideObj, isoBlock)){
			if(!acceptable(parent, new Point(collideObj.getX() + isoBlock.getPosX(), 
					collideObj.getY() + isoBlock.getPosY())) 
				|| !acceptable(parent, new Point(collideObj.getX() + isoBlock.getPosX()+ isoBlock.getSizeX(),
						collideObj.getY() + isoBlock.getPosY()))
				|| !acceptable(parent, new Point(collideObj.getX() + isoBlock.getPosX(),
						collideObj.getY() + isoBlock.getPosY() + isoBlock.getSizeY()))
				|| !acceptable(parent, new Point(collideObj.getX() + isoBlock.getPosX() + isoBlock.getSizeX(),
						collideObj.getY() + isoBlock.getPosY() + isoBlock.getSizeY()))){
				if(isoBlock instanceof LosangeBlock){
					if(!accept(parent, collideObj, (LosangeBlock)isoBlock))
						accept = false;
				}
				else
					accept = false;
			}
			
		}
		
		return accept;
	}
	public boolean accept(ObjetMap parent, ObjetMap collideObj, LosangeBlock isoBlock) {
		boolean accept = true;
		//Si il est dans le carré où est inscrit le losange, alors on teste le losange
		if(!acceptable(parent, new Point(collideObj.getX() + isoBlock.getPosX(), 
					collideObj.getY() + isoBlock.getPosY() + isoBlock.getSizeY() / 2)) 
				|| !acceptable(parent, new Point(collideObj.getX() + isoBlock.getPosX()+ isoBlock.getSizeX(),
						collideObj.getY() + isoBlock.getPosY() + isoBlock.getSizeY() / 2))
				|| !acceptable(parent, new Point(collideObj.getX() + isoBlock.getPosX()  + isoBlock.getSizeX() / 2,
						collideObj.getY() + isoBlock.getPosY() + isoBlock.getSizeY()))
				|| !acceptable(parent, new Point(collideObj.getX() + isoBlock.getPosX() + isoBlock.getSizeX() / 2,
						collideObj.getY() + isoBlock.getPosY()))){
				accept = false;
		}
		return accept;
	}
	//Vérifie qu'un point est dans le losange.
	public boolean acceptable(ObjetMap parent, Point point){
		float xl =  parent.getX() + this.getPosX() + this.getSizeX() / 2;
		float yl =  parent.getY() + this.getPosY() + this.getSizeY() / 2;
		float dx = (float)getSizeX() / 2;
		float dy = (float)getSizeY() / 2;
		float x = (float) point.getX() + dx;
		float y  = (float) point.getY() + dy;
		if(Math.abs(xl-x) / dx + Math.abs(yl - y)/ dy <= 1)
			return false;
		return true;
					
	}
	public float getRapport() {
		return rapport;
	}
	public void setRapport(float rapport) {
		this.rapport = rapport;
	}
}
