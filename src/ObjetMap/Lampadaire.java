package ObjetMap;

import gui.jeu.Jeu;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import Level.Camera;


public class Lampadaire extends ObjetMap {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6305598893979375800L;
	private long tempsPrecedent, tempsReste;
	public Lampadaire(int chunkX, int chunkY, int chunkZ, int posX, int posY,
			int posZ) {
		super(chunkX, chunkY, chunkZ, posX, posY, posZ);
		update = true;
		setNom("Lampadaire");
		setSizeX(10);
		setSizeY(10);
		setSizeZ(55);
		decalageX = -19;
		decalageY = 0;
		image = new ArrayList<ObjetImage>();
		image.add(new ObjetImage("ObjetMap/Lampadaire/lampadaire.png", 68, 70, 686, 700, 0, 0));
		tempsPrecedent = System.currentTimeMillis();
		tempsReste = 40;
	}
	public boolean isUpdate() {
		return true;
	}
	public void update(Jeu jeu){
		long tempsActuel = System.currentTimeMillis();
		if(tempsPrecedent + tempsReste <= tempsActuel){
			tempsPrecedent = tempsActuel;
			image.remove(0);
			int m = (int) (Math.random() * 3);
			switch(m){
				case 0:
					image.add(new ObjetImage("Lampadaire/lampadaire.png", 68, 70, 686, 700, 0, 0));
				break;
				case 2:
				case 3:
				case 1:
					image.add(new ObjetImage("Lampadaire/animation/lampadaire"+ m+".png", 68, 70, 686, 700, 0, 0));
				break;
	
			
			}
		}
	}
	public void paintComponent(Graphics g, Image img, ObjetImage c, Camera actualCam){
		ObjetMap o = this;
		int ombre = (o.getPosX() +  o.getPosY()) / 3;
		g.setColor(new Color(255,255,0,4));
		for(int i = 0; i < 40; i ++){
			g.fillOval((i) * actualCam.getZoom(), (45 + i) * actualCam.getZoom(), (70 - i - i) * actualCam.getZoom(), (35 - i - i) * actualCam.getZoom());
		}
		
		img.draw(0,0,
				c.getImageSizeInGameX() * actualCam.getZoom(),
				c.getImageSizeInGameY() * actualCam.getZoom());
		Color maskColor = o.getMaskColor();
		img.draw(0,0,
				c.getImageSizeInGameX() * actualCam.getZoom(),
				c.getImageSizeInGameY() * actualCam.getZoom(),
				maskColor);
		img.draw(0,0,
				c.getImageSizeInGameX() * actualCam.getZoom(),
				c.getImageSizeInGameY() * actualCam.getZoom(),
				new Color(0,0, 120, ombre));
	}
	

	@Override
	protected ObjetMap newMe(int chunkX, int chunkY, int chunkZ, int posX,
			int posY, int posZ) {
		
		return null;
	}

}
