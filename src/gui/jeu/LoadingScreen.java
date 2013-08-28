package gui.jeu;

import gui.Container;
import gui.GameMain;
import gui.ModeJeu;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class LoadingScreen extends Container {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int pointCount = 0;
	private GameMain gm;
	public LoadingScreen(GameMain gm, int x, int y, int sizeX, int sizeY, Container parent) {
		super(x, y, sizeX, sizeY, parent);
		this.gm = gm;
	}
	public void init(GameContainer gc) {
		
	}
	public void update(GameContainer gc, int delta) {
		super.update(gc, 0,0);
		pointCount++;
		pointCount%=4;
	}
	public void paintComponent(GameContainer gc, Graphics g){
		String loading = "Loading";
		for(int i = 0; i < pointCount; i++)
			loading += ".";
		g.setColor(Color.white);
		g.drawString(loading, sizeX / 2 - g.getFont().getWidth("Loading") / 2, sizeY / 2 - g.getFont().getLineHeight()); 
		gm.setMode(ModeJeu.Jeu, gc);
	}
}
