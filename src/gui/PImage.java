package gui;

import java.io.File;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


public class PImage{
	private SpriteSheet img;
	private String nom = "";
	public static final PImage alpha = new PImage("alpha.png");
	public PImage(String nom, int sizeX, int sizeY, int spacing, int margin){
		try {
			this.img = new SpriteSheet(new Image("Images/" + nom), sizeX, sizeY, spacing, margin);
		} catch (SlickException e) {
			
			e.printStackTrace();
		}
		this.nom = nom;
	}
	public PImage(String nom){
		File f = new File("Images/" + nom);
		if(f.exists() && !f.isDirectory()){
			try {
				Image img = new Image("Images/" + nom);
				this.img = new SpriteSheet(img , img.getWidth(),  img.getHeight());
			} catch (SlickException e) {
				
				e.printStackTrace();
			}
			this.nom = nom;
		}
	}
	public SpriteSheet getImg() {
		return img;
	}
	public void setImg(SpriteSheet img) {
		this.img = img;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
}
