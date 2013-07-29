package bonus;

import gui.FontRessources;
import gui.jeu.Jeu;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import Level.Camera;
import ObjetMap.Entity;

public abstract class SimpleBonus extends Bonus{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int amount;
	protected long time;
	protected int x,  y;
	protected Color color;
	
	public SimpleBonus(int amount,Entity o) {
		super(o);
		setText("");
		color = new Color(0,0,0,0);
		this.amount = amount;
	}
	public void paintComponent(Graphics g, Camera cam){
		super.paintComponent(g, cam);
		g.setFont(FontRessources.getFonts().gametext);
		
		g.scale(cam.getZoom(), cam.getZoom());
			g.setColor(color);
			g.drawString(getText(), - g.getFont().getWidth(getText()) / 2, y - (getCible().getSizeX() + getCible().getSizeY()) / 2 - getCible().getSizeZ() );
		g.scale(1 / (cam.getZoom()), 1 / (cam.getZoom()));
		
		g.setFont(FontRessources.getFonts().text);
	}
	public void update(Jeu jeu){
		if(time + 50 < System.currentTimeMillis()){
			y-=3;
			if(y < -255){
				getCible().getBonus().remove(this);
			}
			if(getCible().getBonus().size() > 6){
				getCible().getBonus().remove(this);
			}
			time = System.currentTimeMillis();
		}
	}
}
