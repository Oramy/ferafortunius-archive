package Level;

import ObjetMap.ObjetMap;

public class Camera {
	private float zoom;
	
	private float maxzoom = 2f;
	private float minzoom = 0.5f;
	private int x; 
	private	int y;
	private long camSpeed;
	private long lastMove;
	/**
	 * l'objet à suivre
	 */
	private ObjetMap followHim;
	private ChunkMap carte;
	private CameraStatus status;
	private int destX;
	private int destY;
	private long timeToDest, beginTime;
	public Camera(int x, int y, float zoom, ChunkMap carte){
		this.carte = carte;
		this.zoom = zoom;
		this.x = x;
		this.y = y;
		setStatus(CameraStatus.Stopped);
		setCamSpeed(1);
		lastMove = System.currentTimeMillis();
	}
	public Camera(int x, int y){
		this.x = x;
		this.y = y;
		setStatus(CameraStatus.Stopped);
		setCamSpeed(1);
		lastMove = System.currentTimeMillis();
	}
	public void move(float x, float  y){
		this.x += x;
		this.y += y;
	}
	public int getMovesNumber(int destX, int destY){
		int movesX, movesY;
		movesX = (int) (x - destX);
		if(movesX < 0)
			movesX = -movesX;
		movesY = (int) (y - destY);
		if(movesY < 0)
			movesY = -movesY;
		if(movesX > movesY)
			return movesX;
		return movesY;
	}
	public void moveToObject(int posX, int posY, int posZ, int moveSize){
		int x = (posX - posY);
		int y = -(posX + posY) / 2;
		float difx = this.x - x;
		if(difx < 0)
			difx = -difx;
		float dify = this.y - y;
		if(dify < 0)
			dify = -dify;
		float bigger = difx;
		if(difx < dify)
			bigger = dify;
		if(this.x != x){
			if(this.x > x){
				this.x-= difx / bigger * moveSize;
				if(this.x < x)
					this.x = x;				
			}
			else
			{
				this.x+= difx / bigger *moveSize;
				if(this.x > x)
					this.x = x;
			}
		}
		if(this.y != y){
			if(this.y > y){
				this.y-= dify / bigger * moveSize;
				if(this.y < y)
					this.y = y;
			}
			else
			{
				this.y+= dify / bigger * moveSize;
				if(this.y > y)
					this.y = y;
			}
		}
		lastMove = System.currentTimeMillis();
	}
	public void moveToObject(ObjetMap o, int moveSize){
		if(carte == null)
		moveToObject(o.getPosX(), o.getPosY(), o.getPosZ(), moveSize);
		else
			moveToObject(o.getAbsPosX(carte.getChunksSize()), o.getAbsPosY(carte.getChunksSize()), o.getAbsPosZ(carte.getChunksSize()), moveSize);
	}
	public void teleportToObject(int posX, int posY, int posZ){
		this.x = (posX - posY);
		this.y = -(posX + posY) / 2 - posZ;
	}
	public void teleportToObject(ObjetMap o){
		if(carte == null)
			teleportToObject(o.getPosX(), o.getPosY(), o.getPosZ());
		else
			teleportToObject(o.getAbsPosX(carte.getChunksSize()), o.getAbsPosY(carte.getChunksSize()), o.getAbsPosZ(carte.getChunksSize()));
	}
	public void moveAtObject(int x1, int y1, int z1, int time){
		setStatus(CameraStatus.Move);
		destX = x1 - y1;
		destY = -(x1 + y1) / 2 - z1;
		timeToDest = time;
		beginTime = System.currentTimeMillis() + 1;
	}
	public void moveAtObject(ObjetMap o, int time){
		if(carte == null)
			moveAtObject(o.getPosX() + o.getSizeX() / 2, o.getPosY() + o.getSizeY() / 2, o.getPosZ() + o.getSizeZ() / 2, time);
		else
			moveAtObject(o.getAbsPosX(carte.getChunksSize()) + o.getSizeX() / 2, o.getAbsPosY(carte.getChunksSize()) + o.getSizeY() / 2, o.getAbsPosZ(carte.getChunksSize()) + o.getSizeZ() / 2, time);
			
	}
	// TODO fonction de la caméra plus sophistiqués.
	/**
	 * 
	 * @param x position x de la caméra voulue
	 * @param y position y de la caméra voulue
	 * @param time
	 */
	public void moveAt(int x, int y, int time){
		setStatus(CameraStatus.Move);
		destX = x;
		destY = y;
		timeToDest = time;
		/*while(this.x != x || this.y != y)
		{
			if(this.x != x){
				if(this.x > x){
					this.x--;
				}
				else
				{
					this.x++;
				}
			}
			if(this.y != y){
				if(this.y > y){
					this.y--;
				}
				else
				{
					this.y++;
				}
			}
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}*/
	}
	/**
	 * @return the zoom
	 */
	public float getZoom() {
		return zoom;
	}
	public void update(){
		if(getStatus() == CameraStatus.Follow){
			if((System.currentTimeMillis() - lastMove) / getCamSpeed() >= 1)
			moveToObject(followHim, (int) ((System.currentTimeMillis() - lastMove) / getCamSpeed()));
		}
		else if(getStatus() == CameraStatus.Move){
			if((this.x == destX && this.y == destY) || ((beginTime + timeToDest - System.currentTimeMillis()) / getMovesNumber(destX, destY)) <= 0)
				setStatus(CameraStatus.Stopped);
			else
			moveToObject((int)destX,
					(int)destY,
					0,
					(int) ((System.currentTimeMillis() - beginTime) / ((beginTime + timeToDest - System.currentTimeMillis()) / getMovesNumber(destX, destY))));
		}
		else if(getStatus() == CameraStatus.Show){
			
		}
	}
	/**
	 * @param zoom the zoom to set
	 */
	public void setZoom(float zoom) {
		
		this.zoom = zoom;
		if(this.zoom < minzoom){
			this.zoom = minzoom;
		}
		if(this.zoom > maxzoom){
			this.zoom = maxzoom;
		}
	}
	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * @return the followHim
	 */
	public ObjetMap getFollowHim() {
		return followHim;
	}
	/**
	 * @param followHim the followHim to set
	 */
	public void setFollowHim(ObjetMap followHim) {
		this.followHim = followHim;
		if(followHim != null)
			setStatus(CameraStatus.Follow);
	}
	public CameraStatus getStatus() {
		return status;
	}
	public void setStatus(CameraStatus status) {
		this.status = status;
	}
	public long getCamSpeed() {
		return camSpeed;
	}
	public void setCamSpeed(long camSpeed) {
		this.camSpeed = camSpeed;
	}
	public float getMaxzoom() {
		return maxzoom;
	}
	public void setMaxzoom(float maxzoom) {
		this.maxzoom = maxzoom;
	}
	public float getMinzoom() {
		return minzoom;
	}
	public void setMinzoom(float minzoom) {
		this.minzoom = minzoom;
	}
}
