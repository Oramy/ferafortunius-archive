package gui.editeurs.animations;

import gui.Container;
import gui.jeu.PanneauJeuAmeliore;

import org.newdawn.slick.Graphics;

import Level.ChunkMap;
import ObjetMap.Animation;
import ObjetMap.ObjetImage;
import ObjetMap.ObjetMap;

public class PanneauAnimationKey extends PanneauJeuAmeliore{
	private ObjetMap workOn;
	
	protected ObjetImage image;
	
	protected Animation animation;
	
	//TODO AnimationKeys Editor
	public PanneauAnimationKey(ObjetMap workOn, int x, int y, int sizeX, int sizeY,
			Container parent) {
		super(new ChunkMap(1,1,1,1), x, y, sizeX, sizeY, parent);
		this.setWorkOn(workOn);
	}
	@Override
	public void draw(Graphics g){
		g.translate(sizeX / 2, sizeY / 2);
		this.drawObject(g, getWorkOn(), false, false);
		g.translate(-sizeX / 2, -sizeY / 2);
	}
	public ObjetMap getWorkOn() {
		return workOn;
	}
	public void setWorkOn(ObjetMap workOn) {
		this.workOn = workOn;
	}
}
