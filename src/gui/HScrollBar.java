package gui;

import org.newdawn.slick.Graphics;

public class HScrollBar extends Container{
	private static final long serialVersionUID = -860520415651633186L;
	protected PImage bardedef, curseur;
	protected float posX;
	protected float posY;
	protected float sizeX;
	protected float sizeY;
	protected static final float defaultSizeX = 54, defaultSizeY = 636;
	protected static final float beginSizeX = 54, beginSizeY = 60;
	protected static final float middleSizeX = 54, middleSizeY = 1;
	protected static final float endSizeX = 54, endSizeY = 60;
	protected float value, valueMax;
	protected float prop = 1;
	protected Container container;
	public HScrollBar(int posX, int posY, float prop, int sizeY, int sizeX,
			float valueMax, Container parent) {
		super(posX,posY,sizeX,sizeY, parent);
		this.value = 0;
		this.valueMax = valueMax;
		this.posX = posX;
		this.posY = posY;
		this.prop = prop;
		this.sizeY = sizeY - 120 * prop;
		this.sizeX = sizeX;
		bardedef = new PImage("GUI/scrollbar.png");
		curseur = new PImage("GUI/cursor.png");
	}

	public void draw(Graphics g){
		g.rotate(0, 0, -90);
			g.translate(getY() + sizeY, -getX() - sizeX);
			//Affichage de la ScrollBar
					//Affichage du haut
					bardedef.getImg().draw(posX, posY, posX + beginSizeX * prop , posY + beginSizeY  * prop , 0, 0, beginSizeX, beginSizeY);
					//Affichage du milieu
					bardedef.getImg().draw(posX, posY + (beginSizeY) * prop , posX + middleSizeX * prop, posY + (beginSizeY + sizeY / prop) * prop , 0, beginSizeY + 20, middleSizeX, beginSizeY + middleSizeY + 20);
					//Affichage du bas
					bardedef.getImg().draw(posX, posY + (sizeY / prop  + beginSizeY) * prop  , posX + endSizeX * prop, posY + (sizeY / prop + beginSizeY + endSizeY) * prop , 0, defaultSizeY - endSizeY, endSizeX, defaultSizeY);
				if(valueMax != 0){
					//Affichage du curseur
						//Affichage du haut
						curseur.getImg().draw(posX, posY + ((sizeY -(sizeY / (valueMax / sizeY)) - 35 * prop) / prop / valueMax * (value - 1)) * prop   + 38 * prop, posX + 54 * prop, posY + ((sizeY -(sizeY / (valueMax / sizeY)) - 35 * prop) / prop / valueMax * ((value    - 1 ))) * prop + (33) * prop  + 38 * prop, 0, 0, 54, 33);
						//Affichage du milieu
						curseur.getImg().draw(posX, posY + ((sizeY -(sizeY / (valueMax / sizeY)) - 35 * prop) / prop / valueMax * (value   - 1)) * prop + (33) * prop  + 38 * prop, posX + 54 * prop, posY + ((sizeY -(sizeY / (valueMax / sizeY)) - 35 * prop) / prop / valueMax * ((value   - 1))) * prop + (33 + ((sizeY / (valueMax / sizeY)) / prop + 40*prop)) * prop  + 38 * prop, 0, 34, 54, 35);
						//Affichage du bas
						curseur.getImg().draw(posX, posY + ((sizeY -(sizeY / (valueMax / sizeY)) - 35 * prop) / prop / valueMax * ((value  - 1) )) * prop + (((sizeY / (valueMax / sizeY)) / prop + 40*prop) + 33) * prop  + 38 * prop, posX + 54 * prop, posY + ((sizeY -(sizeY / (valueMax / sizeY)) - 35 * prop) / prop / valueMax * ((value   -  1))) * prop + (33 + ((sizeY / (valueMax / sizeY)) / prop + 40*prop) + 33) * prop  + 38 * prop, 0, 149, 54, 182);
	
				}
			g.translate(-getY() - sizeY, +getX() + sizeX);
		g.rotate(0, 0, 90);
	}
	/**
	 * @return the value
	 */
	public float getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(float value) {
		if(value >= valueMax){
			value = valueMax;
		}
		else if(value < 0){
			value = 0;
		}
		this.value = value;
	}

}
