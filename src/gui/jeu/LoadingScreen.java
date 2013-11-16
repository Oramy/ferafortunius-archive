package gui.jeu;

import gui.Container;
import gui.FontRessources;
import gui.GameMain;
import gui.ModeJeu;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import Level.MapLoader;

public class LoadingScreen extends Container {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int pointCount = 0;
	private GameMain gm;
	private int etape;
	protected String loading;
	public LoadingScreen(GameMain gm, int x, int y, int sizeX, int sizeY, Container parent) {
		super(x, y, sizeX, sizeY, parent);
		this.gm = gm;
		loading = Messages.getString("LoadingScreen.0"); //$NON-NLS-1$
	}
	public void init(GameContainer gc) {
		
	}
	public void update(GameContainer gc, int delta) {
		super.update(gc, 0,0);
		pointCount++;
		pointCount%=4;
		switch(etape){
			case 0:
				gm.setJeu(new Jeu(gm, gc));
				loading = Messages.getString("LoadingScreen.7"); //$NON-NLS-1$
			break;
			case 1:
				gm.getJeu().cleanGUI(); //$NON-NLS-1$
				loading = Messages.getString("LoadingScreen.1"); //$NON-NLS-1$
			break;
			case 2:
				gm.getJeu().firstInit(gm.getApp(), MapLoader.loadMap("data/Maps/SnapshotTestMap3.dat")); //$NON-NLS-1$
				loading = Messages.getString("LoadingScreen.3"); //$NON-NLS-1$
			break;
			case 3:
				gm.getJeu().initGUI();
				loading = Messages.getString("LoadingScreen.4"); //$NON-NLS-1$
			break;
			case 4:
				gm.getJeu().getPanneauDuJeu().loadMapImages();
				loading = Messages.getString("LoadingScreen.5"); //$NON-NLS-1$
			break;
			
			case 5:
				gm.getJeu().afterGUIInit(gc);
				loading = Messages.getString("LoadingScreen.6"); //$NON-NLS-1$
			break;
			case 6:
				gm.setMode(ModeJeu.Jeu);
			break;
		}
		etape++;
		
	}
	public void paintComponent(GameContainer gc, Graphics g){
		g.setFont(FontRessources.getFonts().gametitles);
		g.setColor(Color.white);
		g.drawString(loading, sizeX / 2 - g.getFont().getWidth(loading) / 2, sizeY / 2 - g.getFont().getLineHeight()); 
	}
}
