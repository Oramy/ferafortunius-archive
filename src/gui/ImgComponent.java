package gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class ImgComponent extends FComponent{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2703403901934688410L;
	private PImage img;
	private int xImg, yImg, hImg, wImg;
	private boolean sizeChange;
	public ImgComponent(PImage img,Container parent) {
		super(parent);
		this.setBounds(0,0,60,60);
		this.setImg(img);
		sizeChange = true;
		
	}
	public void draw(Graphics g) {
		g.translate(this.getX(), this.getY());
			Image img = this.getImg().getImg().getSprite(0, 0);
			if(sizeChange){
				if(this.getBounds().width > this.getBounds().height){
					hImg = this.getBounds().height;
					wImg  = (int)((float)hImg / (float)img.getHeight() * (float)img.getWidth());
					xImg = this.getWidth() / 2 - wImg / 2;
					yImg = 0;
				}
			}
			else{
				hImg = img.getHeight();
				wImg = img.getWidth();
				xImg = this.getWidth() / 2 - wImg / 2;
				yImg = this.getHeight() / 2 - hImg / 2;;
			}
			img.draw(xImg, yImg, wImg, hImg);
		g.translate(-this.getX(), -this.getY());
	}
	@Override
	public void updateSize() {
	
	}
	public PImage getImg() {
		return img;
	}
	public void setImg(PImage img) {
		this.img = img;
	}
	public boolean isSizeChange() {
		return sizeChange;
	}
	public void setSizeChange(boolean sizeChange) {
		this.sizeChange = sizeChange;
	}
}
