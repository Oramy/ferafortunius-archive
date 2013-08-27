package ObjetMap;

/**
 * A class who make a move in a period
 * @author Oramy
 *
 */
public class MoveAnimation implements Runnable {

	protected float x, y;
	protected long time;
	protected ObjetImage cible; 
	
	public MoveAnimation(float x, float y, long time, ObjetImage cible){
		this.x = x;
		this.y = y;
		this.time = time;
		this.cible = cible;
	}
	@Override
	public void run() {
		long firstTime = System.currentTimeMillis();
		
		long cursor = System.currentTimeMillis();
		long max = firstTime + time;
		while(cursor < max){
			float coefX = (x - cible.getDecalageX()) / (max - cursor);
			float coefY = (y - cible.getDecalageY()) / (max - cursor);
			float finalX = (x) * coefX;
			float finalY = (y) * coefY; 
			cible.setDecalageX((int) (cible.getDecalageX() + finalX));
			cible.setDecalageY((int) (cible.getDecalageY() + finalY));
			cursor = System.currentTimeMillis();
			
		}
	}

}
