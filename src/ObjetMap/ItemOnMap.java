package ObjetMap;

import gui.jeu.Jeu;
import gui.jeu.PanneauJeuAmeliore;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import Items.Item;
import Level.Camera;


public class ItemOnMap extends ObjetMap {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4075030050866438123L;
	
	protected Item item;
	protected boolean gettingDown;
	protected long tempsPrec, tempsReq;
	public ItemOnMap(int chunkX, int chunkY, int chunkZ, int posX, int posY,
			int posZ, Item item) {
		super(chunkX, chunkY, chunkZ, posX, posY, posZ);
		this.item = item;
		this.sizeX = 1;
		this.sizeY = 1;
		this.sizeZ = 1;
		this.nom = item.getName();
		gettingDown = false;
		image = new ArrayList<ObjetImage>();
		ObjetImage clone = item.getImg().clone();
		clone.setImageSizeInGameX(item.getImg().getImageSizeInGameX());
		clone.setImageSizeInGameY(item.getImg().getImageSizeInGameY());
		
		image.add(clone);
		getCollision().add(new CollisionBlock(0,0,0, 30, 30, 30));
		update = true;
		invisible = true;
		tempsReq = 40;
		
	}
	public ObjetMap clone(){
		ItemOnMap clone = (ItemOnMap) super.clone();
		clone.item = item.clone();
		return clone;
		
	}
	public void paintComponent(PanneauJeuAmeliore pan,Graphics g, Image img, int posX, int posY, ObjetImage c, Camera actualCam){
		g.translate(0, -(decalageY * actualCam.getZoom() + decalageY * actualCam.getZoom() / 10));
			super.paintComponent(pan, g, img, posX, posY, c, actualCam);
			if(pan.getSurlignObject() != null){
				if(pan.getSurlignObject().equals(this)){
					g.drawString(nom,  - g.getFont().getWidth(nom) / 2 + c.getImageSizeInGameY() / 2 * actualCam.getZoom(), -g.getFont().getLineHeight());
				}
			}
		g.translate(0, (decalageY * actualCam.getZoom() + decalageY * actualCam.getZoom() / 10));
	}
	public void update(Jeu jeu){
		super.update(jeu);
		long temps = System.currentTimeMillis();
		if(temps - tempsReq > tempsPrec){
			tempsPrec = temps;
			if(image.get(0).getDecalageY() == image.get(0).getImageSizeInGameY() / 2)
				gettingDown = true;
			else if(image.get(0).getDecalageY()  == 0){
				gettingDown = false;
			}
			if(gettingDown == false && image.get(0).getDecalageY() < image.get(0).getImageSizeInGameY() / 2){
				image.get(0).setDecalageY(image.get(0).getDecalageY() + 2);
			}
			else if(gettingDown == true && image.get(0).getDecalageY() > 0)
				image.get(0).setDecalageY(image.get(0).getDecalageY() - 2);
			
			
		}
		if(item.isAutoGet()){
			Entity p = jeu.getPlayer();
			int centerpx = (p.posX + p.sizeX / 2);
			int centerpy = (p.posY + p.sizeY / 2);
			int centerpz = (p.posZ + p.sizeZ / 2);
			
			int centerx = (posX + sizeX / 2);
			int centery = (posY + sizeY / 2);
			int centerz = (posZ + sizeZ / 2);
			if(p.chunkX == chunkX 
				&& p.chunkY == chunkY
				&& p.chunkZ == chunkZ
				&&  centerpx >= centerx - item.getAutoGetRange() && centerpx <= centerx + item.getAutoGetRange()
				&&  centerpy >= centery - item.getAutoGetRange() && centerpy <= centery + item.getAutoGetRange()
				&&  centerpz >= centerz - item.getAutoGetRange() && centerpz <= centerz + item.getAutoGetRange()){
				int difx = centerpx - centerx;
				if(difx < 0)
					difx = -difx;
				int dify = centerpy - centery;
				if(dify < 0)
					dify = -dify;
				int difz = centerpz - centerz;
				if(difz < 0)
					difz = -difz;
				int bigger = difx;
				if(bigger < dify)
					bigger = dify;
				if(bigger < difz)
					bigger = difz;
				int stepx = Math.round((float)difx / (float)bigger);
				int stepy =  Math.round((float)dify / (float)bigger);
				int stepz =  Math.round((float)difz / (float)bigger);
				if((p.posX + p.sizeX / 2) > (posX + sizeX / 2)){
					posX+= stepx;
				}
				else if((p.posX + p.sizeX / 2) < (posX + sizeX / 2)){
					posX-= stepx;
				}
				if((p.posY + p.sizeY / 2) > (posY + sizeY / 2)){
					posY+= stepy;
				}
				else if((p.posY + p.sizeY / 2) < (posY + sizeY / 2)){
					posY-= stepy;
				}
				if((p.posZ + p.sizeZ / 2) > (posZ + sizeZ / 2)){
					posZ+= stepz;
				}
				else if((p.posZ + p.sizeZ / 2) < (posZ + sizeZ / 2)){
					posZ-= stepz;
				}
				if((p.posX + p.sizeX / 2) == (posX + sizeX / 2) && (p.posY + p.sizeY / 2) == (posY + sizeY / 2) && (p.posZ + p.sizeZ / 2) == (posZ + sizeZ / 2)){
					item.setOwner(jeu.getPlayer());
					jeu.getPlayer().getInventaire().addContent(item);
					jeu.getCarte().getChunks()[chunkX][chunkY][chunkZ].remove(this);
				}
			}
		}
		PanneauJeuAmeliore pan = jeu.getPanneauDuJeu();
		if(pan.getSurlignObject() != null){
			if(pan.getSurlignObject().equals(this) && jeu.getGm().getApp().getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
				item.setOwner(jeu.getPlayer());
				
				jeu.getPlayer().getInventaire().addContent(item);
				jeu.getCarte().getChunks()[chunkX][chunkY][chunkZ].remove(this);
			}
		}
		
	}
	
	@Override
	protected ObjetMap newMe(int chunkX, int chunkY, int chunkZ, int posX,
			int posY, int posZ) {
		
		return null;
	}

}
