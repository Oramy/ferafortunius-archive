package ObjetMap;

import gui.PImage;

import java.io.Serializable;

import com.ferafortunius.animations.MoveToAnimation;


public class ObjetImage implements Serializable, Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2927160650901251737L;
	private String image;
	private String alias;
	private String parentAlias;
	private int posX, posY;
	private int posSpriteSheetX, posSpriteSheetY;
	private int decalageX, decalageY;
	private int sizeX;
	private int sizeY;
	private int imageSizeInGameX, imageSizeInGameY;
	private int rotationCenterX, rotationCenterY;
	private int rotation;
	private int detailRotation;
	private boolean mirror;
	
	protected float ratio = 1f;
	private transient int imageID;
	public ObjetImage clone(){
		ObjetImage clone = null;
		try {
			clone = (ObjetImage) super.clone();
		} catch (CloneNotSupportedException e) {
			
			e.printStackTrace();
		}
		clone.decalageX = decalageX;
		clone.decalageY = decalageY;
		clone.image = image;
		clone.alias = alias;
		clone.parentAlias = parentAlias;
		return clone;
	}
	public ObjetImage(String image, int imageSizeInGameX, int imageSizeInGameY,
			int sizeSpriteX, int sizeSpriteY, 
			int posSpriteX, int posSpriteY, boolean b){
		this.mirror = b;
		this.setImage(image);
		this.setImageSizeInGameX(imageSizeInGameX);
		this.setImageSizeInGameY(imageSizeInGameY);
		this.setSizeSpriteX(sizeSpriteX);
		this.setSizeSpriteY(sizeSpriteY);
		rotationCenterX = imageSizeInGameX / 2;
		rotationCenterY = imageSizeInGameY / 2;
		this.posX = posSpriteX;
		this.posY = posSpriteY;
		setDecalageX(0);
		setDecalageY(0);
	}
	public ObjetImage(String image, int imageSizeInGameX, int imageSizeInGameY,
			int sizeSpriteX, int sizeSpriteY, 
			int posSpriteX, int posSpriteY){
		this.mirror = false;
		this.setImage(image);
		this.setImageSizeInGameX(imageSizeInGameX);
		this.setImageSizeInGameY(imageSizeInGameY);
		this.setSizeSpriteX(sizeSpriteX);
		this.setSizeSpriteY(sizeSpriteY);
		rotationCenterX = imageSizeInGameX / 2;
		rotationCenterY = imageSizeInGameY / 2;
		this.posX = posSpriteX;
		this.posY = posSpriteY;
		setDecalageX(0);
		setDecalageY(0);
	}
	public ObjetImage(String image, int sizeSpriteX, int sizeSpriteY){
		this.mirror = false;
		this.setImage(image);
		this.setImageSizeInGameX(sizeSpriteX);
		this.setImageSizeInGameY(sizeSpriteY);
		rotationCenterX = imageSizeInGameX / 2;
		rotationCenterY = imageSizeInGameY / 2;
		this.setSizeSpriteX(sizeSpriteX);
		this.setSizeSpriteY(sizeSpriteY);
		setDecalageX(0);
		setDecalageY(0);

	}
	public ObjetImage(String image){
		this.mirror = false;
		this.setImage(image);
		PImage p = new PImage(image);
		if(p.getImg() != null){
			int sizeX = p.getImg().getWidth();
			int sizeY = p.getImg().getHeight();
			this.setImageSizeInGameX(sizeX);
			this.setImageSizeInGameY(sizeY);
			rotationCenterX = imageSizeInGameX / 2;
			rotationCenterY = imageSizeInGameY / 2;
			this.setSizeSpriteX(sizeX);
			this.setSizeSpriteY(sizeY);
		}
		p = null;

	}
	public void rotate(float rotation){
		this.rotation += (int)rotation;
		if(rotation > 0)
			this.detailRotation += (rotation - (int)rotation) * 1000;
		else
			this.detailRotation -= (Math.abs(rotation) - (int)Math.abs(rotation)) * 1000;
	}
	public void extend(float prop){
		imageSizeInGameX = (int)((float)imageSizeInGameX * prop + 0.5f);
		imageSizeInGameY = (int)((float)imageSizeInGameY * prop + 0.5f);
		rotationCenterX = (int)((float)rotationCenterX * prop + 0.5f);
		rotationCenterY = (int)((float)rotationCenterY * prop + 0.5f);
	}
	public void reduce(float prop){
		imageSizeInGameX = (int)((float)imageSizeInGameX / prop + 0.5f);
		imageSizeInGameY = (int)((float)imageSizeInGameY / prop + 0.5f);
		rotationCenterX = (int)((float)rotationCenterX / prop + 0.5f);
		rotationCenterY = (int)((float)rotationCenterY / prop + 0.5f);
	}
	public void move(int x, int y){
		if(ratio == 0f )
			ratio = 1f;
		decalageX += x;
		decalageY += y;
	}
	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * @return the posX
	 */
	public int getDecalageX() {
		return (int) (decalageX * getRatio());
	}
	/**
	 * @param posX the posX to set
	 */
	public void setDecalageX(int posX) {
		this.decalageX = posX;
	}
	/**
	 * @return the sizeX
	 */
	public int getSizeSpriteX() {
		return sizeX;
	}
	/**
	 * @param sizeX the sizeX to set
	 */
	public void setSizeSpriteX(int sizeX) {
		this.sizeX = sizeX;
	}
	/**
	 * @return the sizeY
	 */
	public int getSizeSpriteY() {
		return sizeY;
	}
	/**
	 * @param sizeY the sizeY to set
	 */
	public void setSizeSpriteY(int sizeY) {
		this.sizeY = sizeY;
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
	 * @return the imageSizeInGameX
	 */
	public int getImageSizeInGameX() {
		return (int) (imageSizeInGameX * ratio);
	}
	/**
	 * @param imageSizeInGameX the imageSizeInGameX to set
	 */
	public void setImageSizeInGameX(int imageSizeInGameX) {
		this.imageSizeInGameX = imageSizeInGameX;
	}
	/**
	 * @return the imageSizeInGameY
	 */
	public int getImageSizeInGameY() {
		return (int) (imageSizeInGameY * ratio);
	}
	/**
	 * @param imageSizeInGameY the imageSizeInGameY to set
	 */
	public void setImageSizeInGameY(int imageSizeInGameY) {
		this.imageSizeInGameY = imageSizeInGameY;
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
		this.mirror = mirror;
	}

	/**
	 * @return the rotation
	 */
	public float getRotation() {
		return (float)rotation + ((float)detailRotation) / 1000;
	}
	/**
	 * @param f the rotation to set
	 */
	public void setRotation(int f) {
		this.rotation = f;
	}
	/**
	 * @return the posSpriteSheetX
	 */
	public int getPosSpriteSheetX() {
		return posSpriteSheetX;
	}
	/**
	 * @param posSpriteSheetX the posSpriteSheetX to set
	 */
	public void setPosSpriteSheetX(int posSpriteSheetX) {
		this.posSpriteSheetX = posSpriteSheetX;
	}
	/**
	 * @return the posSpriteSheetY
	 */
	public int getPosSpriteSheetY() {
		return posSpriteSheetY;
	}
	/**
	 * @param posSpriteSheetY the posSpriteSheetY to set
	 */
	public void setPosSpriteSheetY(int posSpriteSheetY) {
		this.posSpriteSheetY = posSpriteSheetY;
	}
	/**
	 * @return the posY
	 */
	public int getDecalageY() {
		return (int) (decalageY * getRatio());
	}
	/**
	 * @param posY the posY to set
	 */
	public void setDecalageY(int posY) {
		this.decalageY = posY;
	}
	/**
	 * @return the rotationCenterX
	 */
	public int getRotationCenterX() {
		return  (int) (rotationCenterX * getRatio());
	}
	/**
	 * @param rotationCenterX the rotationCenterX to set
	 */
	public void setRotationCenterX(int rotationCenterX) {
		this.rotationCenterX = rotationCenterX;
	}
	/**
	 * @return the rotationCenterY
	 */
	public int getRotationCenterY() {
		return  (int) (rotationCenterY * getRatio());
	}
	/**
	 * @param rotationCenterY the rotationCenterY to set
	 */
	public void setRotationCenterY(int rotationCenterY) {
		this.rotationCenterY = rotationCenterY;
	}
	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}
	/**
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}
	/**
	 * @return the parentAlias
	 */
	public String getParentAlias() {
		return parentAlias;
	}
	/**
	 * @param parentAlias the parentAlias to set
	 */
	public void setParentAlias(String parentAlias) {
		this.parentAlias = parentAlias;
	}
	public int getDetailRotation() {
		return detailRotation;
	}
	public void setDetailRotation(int detailRotation) {
		this.detailRotation = detailRotation;
	}
	public int getImageID() {
		return imageID;
	}
	public void setImageID(int imageID) {
		this.imageID = imageID;
	}
	public float getRatio() {
		if(ratio <= 0.0f)
			ratio = 1f;
		return ratio;
	}
	public void setRatio(float ratio) {
		this.ratio = ratio;
	}
	public void moveIn(float x, float y, float rotation, long time) {
		Thread t = new Thread(new MoveToAnimation(x, y, rotation, time, this));
		t.start();
	}
}
