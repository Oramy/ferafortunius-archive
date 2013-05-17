package gui;

import org.newdawn.slick.Graphics;

public class BasicButtonImage extends Button{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PImage image;
	public BasicButtonImage(String path, Container parent) {
		super("", parent);
		image = new PImage(path);
		sizeX = image.getImg().getWidth();
		sizeY = image.getImg().getHeight();
		prop = 0.9f;
		
	}
	public BasicButtonImage(int sizeX, int sizeY, String path, Container parent) {
		super("", parent);
		image = new PImage(path);
		sizeX = this.sizeX;
		sizeY = this.sizeY;
		
	}
	public BasicButtonImage(int x, int y,int sizeX, int sizeY, String path, Container parent) {
		super("", x,y,sizeX,sizeY, parent);
		image = new PImage(path);
		
	}
	public void hover(){
		boutonAct = boutonHover;
		prop = 1f;
	}
	public void normal(){
		boutonAct = bouton;
		prop = 0.9f;
	}
	public void draw(Graphics g){
		g.translate(this.getBounds().x, this.getBounds().y);
			g.drawImage(image.getImg().getScaledCopy((int)(sizeX * prop), (int)(sizeY * prop)), 
					(int)((sizeX - (sizeX * prop)) / 2), (int)((sizeY - (sizeY * prop)) / 2));
		g.translate(-this.getBounds().x, -this.getBounds().y);
	}

}
