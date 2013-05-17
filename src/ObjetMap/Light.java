package ObjetMap;


import gui.jeu.PanneauJeuAmeliore;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import Level.Camera;


public class Light extends ObjetMap{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1181934310176395367L;
	protected Color lightColor;
	protected int portee;
	protected float intensity;
	public Light(int chunkX, int chunkY, int chunkZ, int posX, int posY,
			int posZ) {
		super(chunkX, chunkY, chunkZ, posX, posY, posZ);
		setNom("Lumière");
		setInvisible(true);
		sizeX = 1;
		sizeZ = 1;
		sizeY = 1;
		portee = 200;
		intensity = 0.01f;
		lightColor = new Color(255,255,255);
		
	}
	@Override
	public void paintComponent(PanneauJeuAmeliore pan, Graphics g, Image img,int posX, int posY, ObjetImage c, Camera actualCam){
		for(int i = portee; i > 0; i--){
			Color color = lightColor;
			color.a = intensity;
			g.setColor(color);
			g.scale(actualCam.getZoom(), actualCam.getZoom());
			g.fillOval(-(i / 2),  - (i / 4),  
					+ i , + i / 2);
			g.scale(1 /actualCam.getZoom(),  1 / actualCam.getZoom());
		}
	}
	@Override
	protected ObjetMap newMe(int chunkX, int chunkY, int chunkZ, int posX,
			int posY, int posZ) {
		
		return null;
	}

}
