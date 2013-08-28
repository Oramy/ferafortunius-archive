package gui.buttons;

import gui.Container;
import gui.ItemRessources;
import gui.PImage;
import gui.jeu.PanneauJeuAmeliore;

import java.util.ArrayList;

import observer.ActionListener;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import ObjetMap.ObjetImage;


public class ButtonImage extends Button {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<ObjetImage> img;
	private int xImg, yImg, hImg, wImg;
	private boolean proportions;
	private boolean drawButton;
	public ButtonImage(ArrayList<ObjetImage> image, int posX, int posY, int width, int height, Container parent){
		super("", parent);
		this.img = image;
		action = new ArrayList<ActionListener>();
		this.setBounds(posX, posY, (int)(width * prop), (int)(height * prop));
		enable = true;
		proportions = true;
		new Color(0,0,0,0);
		drawButton = true;
	}
	public ButtonImage(ObjetImage image, int posX, int posY, int width, int height, Container parent){
		super("", parent);
		this.img = new ArrayList<ObjetImage>();
		this.img.add(image);
		action = new ArrayList<ActionListener>();
		this.setBounds(posX, posY, (int)(width * prop), (int)(height * prop));
		enable = true;
		proportions = true;
		new Color(0,0,0,0);
		drawButton = true;
	}
	public ButtonImage(ArrayList<ObjetImage> image, Container parent){
		super("", parent);
		this.img = image;
		action = new ArrayList<ActionListener>();
		this.prop = 1f;
		this.setBounds(0,0,1,1);
		enable = true;
		proportions = true;
		drawButton = true;
		new Color(0,0,0,0);
	}
	public ButtonImage(ObjetImage image, Container parent){
		super("", parent);
		this.img = new ArrayList<ObjetImage>();
		this.img.add(image);
		action = new ArrayList<ActionListener>();
		this.prop = 1f;
		this.setBounds(0,0,1,1);
		enable = true;
		proportions = true;
		drawButton = true;
		new Color(0,0,0,0);
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.black);
		if(drawButton)
			drawButton(g);
		g.translate(this.getX() , this.getY());
			for (int k = 0; k < img.size(); k++) {
				boolean drawed = false;
				Image todraw = null;
				// Recherche de l'image
				for (int l = 0; l < PanneauJeuAmeliore.img.size(); l++) {
					if(img.get(k).getImage().equals(PanneauJeuAmeliore.img.get(l).getNom())){
						drawed = true;
						todraw =  PanneauJeuAmeliore.img.get(l).getImg();
						
					}
				}
				if(!drawed){
					for (int l = 0; l < ItemRessources.getItemRessource().size(); l++) {
						if(img.get(k).getImage().equals( ItemRessources.getItemRessource().get(l).getNom())){
							drawed = true;
							todraw = ItemRessources.getItemRessource().get(l).getImg();
						}
					}
				}
				if(!drawed){
					PImage toload = new PImage(img.get(k).getImage());
					todraw = toload.getImg();
					PanneauJeuAmeliore.img.add(toload);
				}
				//Dessin de l' image en proportion avec le bouton
				if(img.get(k).getSizeSpriteX() != 0 && img.get(k).getSizeSpriteY() != 0 && todraw != null){
					SpriteSheet sprite = new SpriteSheet(todraw, img.get(k).getSizeSpriteX(), img.get(k).getSizeSpriteY());
					Image img =  sprite.getSprite(this.img.get(k).getPosX(), this.img.get(k).getPosY());
					if(proportions){
						if(this.getBounds().width  > this.getBounds().height ){
							hImg = this.getBounds().height - 20;
							wImg  = (int)((float)hImg / (float)img.getHeight() * (float)img.getWidth());
							xImg = this.getWidth() / 2 - wImg / 2;
							yImg = 0;
						}
					}
					else{
						hImg = img.getHeight();
						wImg = img.getWidth();
						xImg = this.getWidth() / 2 - wImg / 2;
						yImg = this.getHeight() / 2 - hImg / 2;
					}
					img.draw(xImg, yImg, wImg, hImg);
				}
			}
		g.translate(-this.getX(), -this.getY());
	}
	/**
	 * @return the action
	 */
	public ArrayList<ActionListener> getAction() {
		return action;
	}
	/**
	 * @return the proportions
	 */
	public boolean isProportions() {
		return proportions;
	}
	/**
	 * @param proportions the proportions to set
	 */
	public void setProportions(boolean proportions) {
		this.proportions = proportions;
	}
	public boolean isDrawButton() {
		return drawButton;
	}
	public void setDrawButton(boolean drawButton) {
		this.drawButton = drawButton;
	}

}
