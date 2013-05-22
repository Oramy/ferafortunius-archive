package bonus;

import gui.jeu.Jeu;

import java.io.Serializable;

import org.newdawn.slick.Graphics;

import Level.Camera;
import ObjetMap.Entity;


public abstract class Bonus implements Cloneable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Entity cible;
	private String text;
	public Bonus(Entity o){
		setCible(o);
	}
	public Bonus clone(){
		try {
			Bonus b = (Bonus) super.clone();
			b.setCible(cible);
			return b;
		} catch (CloneNotSupportedException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	public abstract void effect();
	public void paintComponent(Graphics g, Camera cam){
	}
	public void update(Jeu jeu){
		
	}
	public Entity getCible() {
		return cible;
	}
	public void setCible(Entity cible) {
		this.cible = cible;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
