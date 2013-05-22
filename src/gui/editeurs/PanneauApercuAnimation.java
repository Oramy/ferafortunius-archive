package gui.editeurs;

import gui.Container;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import Level.CameraStatus;
import Level.Chunk;
import Level.ChunkMap;
import ObjetMap.ObjetMap;


public class PanneauApercuAnimation extends PanneauApercu{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/*
	 * Suivre notre objet ?
	 */
	private boolean follow;
	
	public PanneauApercuAnimation(ChunkMap c, ObjetMap obj, int x, int y,
			int sizeX, int sizeY, Container parent) {
		super(obj, x, y, sizeX, sizeY, parent);
		moveImage = false;
		follow = true;
		translate = true;
		editChoice.setPosition(0,0,0, 1000, 1000, 100);
		if(follow)
			this.actualCam.moveToObject(2300, 2300,100, 100000000);

		this.carte.getChunk(0, 0, 0).setModeActuel(Chunk.GOD_MOD);

	}
	public void update(GameContainer gc, int x, int y){
		super.update(gc, x, y);
		this.editChoice.updateAlone(null, this.carte);
		//Si F3, on change la valeur de follow
		if(gc.getInput().isKeyPressed(Input.KEY_F3)){
			follow = !follow;
			editChoice.setPosition(0,0,0, 1000, 1000, 100);
			if(follow)
				this.actualCam.moveToObject(2300, 2300,100, 100000000);
		}
		//Update des positions du follow
		if(follow)
			editChoice.setPosition(0,0,0, 1000, 1000, 100);
	}
	public void draw(Graphics g){
		g.translate(getX(),getY());

		//Affichage de l'objet
		if(getEditChoice() != null){
			
			//Translations
			g.translate(this.getWidth()/2, this.getHeight()/2);
			
			g.translate(-actualCam.getX() * actualCam.getZoom(), -actualCam.getY() * actualCam.getZoom());

			this.translateToObject(g, editChoice);
			
			//Affichages
			this.drawObject(g, editChoice, debugMode, false);
			
			//Untranstlations
			this.untranslateToObject(g, editChoice);

			g.translate(actualCam.getX() * actualCam.getZoom(), actualCam.getY() * actualCam.getZoom());		
			
			g.translate(-this.getWidth()/2, -this.getHeight()/2);
		}
		g.translate(-getX(),-getY());	


		//Affichage du statut du follow
		g.setColor(Color.black);
		if(follow)
			g.drawString("Follow", 0,0);
	}


}
