package ObjetMap;

import java.io.Serializable;
//This is the Rectangle collision block
public class CollisionBlock  implements Serializable, Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8066875143380284026L;
	private int posX, posY, posZ;
	private int sizeX, sizeY, sizeZ;
	private boolean mirror;
	public CollisionBlock(int posX, int posY, int posZ, int sizeX, int sizeY, int sizeZ){
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.sizeZ = sizeZ;
		setMirror(false);
	}
	public boolean accept(ObjetMap parent, ObjetMap collideObj, CollisionBlock isoBlock) {
		boolean accept = true;
		if(!acceptableX(parent, collideObj, isoBlock) && !acceptableY(parent, collideObj, isoBlock) && !acceptableZ(parent, collideObj, isoBlock)){
				accept = false;
		}
		return accept;
	}
	public boolean acceptableX(ObjetMap parent, ObjetMap collideObj, CollisionBlock block) {
		boolean accepted = true;
		if(collideObj.getPosX() + block.getPosX() >= parent.getPosX() + this.getPosX() 
				&& collideObj.getPosX() + block.getPosX() < parent.getPosX() + this.getPosX() + this.getSizeX())
			accepted = false;
		if( parent.getPosX() + this.getPosX() >= collideObj.getPosX() + block.getPosX() 
				&& parent.getPosX() + this.getPosX() < collideObj.getPosX() + block.getPosX() + block.getSizeX())
			accepted = false;
		return accepted;
	}
	public boolean acceptableY(ObjetMap parent, ObjetMap o, CollisionBlock co) {
		boolean accepted = true;
		if(o.getPosY() + co.getPosY() >=parent.getPosY() + this.getPosY() 
				&& o.getPosY() + co.getPosY() <parent.getPosY() + this.getPosY() + this.getSizeY())
			accepted = false;
		if(parent.getPosY() + this.getPosY() >= o.getPosY() + co.getPosY() 
				&&parent.getPosY() + this.getPosY() < o.getPosY() + co.getPosY() + co.getSizeY())
			accepted = false;
		return accepted;
	}
	public boolean acceptableZ(ObjetMap parent, ObjetMap o, CollisionBlock co) {
		boolean accepted = true;
		if(o.getPosZ() + co.getPosZ() >=parent.getPosZ() + this.getPosZ() 
				&& o.getPosZ() + co.getPosZ() <parent.getPosZ() + this.getPosZ() + this.getSizeZ())
			accepted = false;
		if(parent.getPosZ() + this.getPosZ() >= o.getPosZ() + co.getPosZ() 
				&&parent.getPosZ() + this.getPosZ() < o.getPosZ() + co.getPosZ() + co.getSizeZ())
			accepted = false;
		return accepted;
	}
	public CollisionBlock clone(){
		CollisionBlock o = null;
		try {
			o = (CollisionBlock)super.clone();
		} catch (CloneNotSupportedException e) {
			
			e.printStackTrace();
		}
		return o;
	}
	/**
	 * @return the posX
	 */
	public int getPosX() {
		return posX;
	}
	/**
	 * @param posX the posX to set
	 */
	public void setPosX(int posX) {
		this.posX = posX;
	}
	/**
	 * @return the posY
	 */
	public int getPosY() {
		return posY;
	}
	/**
	 * @param posY the posY to set
	 */
	public void setPosY(int posY) {
		this.posY = posY;
	}
	/**
	 * @return the posZ
	 */
	public int getPosZ() {
		return posZ;
	}
	/**
	 * @param posZ the posZ to set
	 */
	public void setPosZ(int posZ) {
		this.posZ = posZ;
	}
	/**
	 * @return the sizeX
	 */
	public int getSizeX() {
		return sizeX;
	}
	/**
	 * @param sizeX the sizeX to set
	 */
	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}
	/**
	 * @return the sizeY
	 */
	public int getSizeY() {
		return sizeY;
	}
	/**
	 * @param sizeY the sizeY to set
	 */
	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}
	/**
	 * @return the sizeZ
	 */
	public int getSizeZ() {
		return sizeZ;
	}
	/**
	 * @param sizeZ the sizeZ to set
	 */
	public void setSizeZ(int sizeZ) {
		this.sizeZ = sizeZ;
	}
	public void setBounds(int x, int y ,int z, int sizeX, int sizeY, int sizeZ){
		this.setPosX(x);
		this.setPosY(y);
		this.setPosZ(z);
		this.setSizeX(sizeX);
		this.setSizeY(sizeY);
		this.setSizeZ(sizeZ);
	}
	/**
	 * @return the mirror
	 */
	public boolean isMirror() {
		return mirror;
	}
	/**
	 * @param mirror the mirror to set
	 */
	public void setMirror(boolean mirror) {
		if(mirror != this.mirror){
			int a;
			a=this.sizeY;
			this.sizeY = this.sizeX;
			this.sizeX = a;
			
			a=this.posY;
			this.posY = this.posX;
			this.posX = a;
		}
		this.mirror = mirror;
		
	}


}
