package gui;

import gui.jeu.PanneauJeuAmeliore;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import ObjetMap.ObjetMap;


public class ScrollImage extends FComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5683608443380097937L;
	private ArrayList<ObjetMap> tochoose;
	private int choice;
	private int xImg, yImg, hImg, wImg;
	private boolean down;
	private boolean up;
	private int xBef, yBef, sizeYBef;
	private int xAft, yAft, sizeYAft;
	private int xChoice, yChoice, sizeYChoice;
	public ScrollImage(int x, int y, int sizeX, int sizeY,ArrayList<ObjetMap> tochoose,Container parent) {
		super(parent);
		init();
		this.setX(x);
		this.setY(y);
		this.setSizeX(sizeX);
		this.setSizeY(sizeY);
		this.setTochoose(tochoose);
		choice = 0;
		
	}
	public void update(GameContainer gc, int x, int y){
		if(!down && !up){
			if(gc.getInput().getMouseX() >= this.getX() + x
					&& gc.getInput().getMouseX() <= this.getX() + x + getSizeX()
					&& gc.getInput().getMouseY() >= this.getY() + y
					&& gc.getInput().getMouseY() <= this.getY() + y + getSizeY() && gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
				int mouse = Mouse.getDWheel();
				if(mouse < 0){
					if(choice - 1 >= 0){
						down = true;
					}
				}
				if(mouse > 0){
					if(choice +1 < tochoose.size()){
						up = true;
					}
				}
			}
		}
		if(down){
			sizeYBef++;
			if(sizeYBef % 3 == 0){
				yBef++;
			}
			yChoice++;
			sizeYChoice--;
			sizeYAft--;
			if(sizeYBef >= 30){
				down = false;
				choice--;
				sizeYBef = 0;
				yBef = 0;
				sizeYAft = 0;
				yChoice = 0;
				sizeYChoice = 0;
			}
		}
		if(up){
			sizeYAft++;
			if(sizeYAft % 3 == 0)
				yAft--;
			sizeYChoice--;
			sizeYBef--;
			if(sizeYAft >= 30){
				up = false;
				choice++;
				sizeYBef = 0;
				yAft = 0;
				sizeYAft = 0;
				yChoice = 0;
				sizeYChoice = 0;
			}
		}
	}
	public void draw(Graphics g){
		g.translate(getX(),getY());
			//Before
			//long tempsPrec = System.currentTimeMillis();
			if(choice - 1 >= 0){
				for (int l = 0; l < PanneauJeuAmeliore.img.size(); l++) {
					if(PanneauJeuAmeliore.img.get(l).getNom().equals(tochoose.get(choice - 1).getImage().get(0).getImage())){
					PImage p = PanneauJeuAmeliore.img.get(l);
					Image img = p.getImg().getSprite(0, 0);
					if(this.getBounds().width > this.getBounds().height){
						hImg = this.getBounds().height - 50  +sizeYBef;
						wImg  = (int)((float)hImg / (float)img.getHeight() * (float)img.getWidth());
						xImg = this.getWidth() / 2 - wImg / 2;
						yImg = 0;
					}
					img.draw(xImg+xBef, yImg+yBef, wImg, hImg);
					l = PanneauJeuAmeliore.img.size();
					}
				}
			}
			//System.out.println(System.currentTimeMillis() - tempsPrec);
			//tempsPrec = System.currentTimeMillis();
			//After
			if(choice +1 < tochoose.size()){
				for (int l = 0; l < PanneauJeuAmeliore.img.size(); l++) {
					if(PanneauJeuAmeliore.img.get(l).getNom().equals(tochoose.get(choice + 1).getImage().get(0).getImage())){
						PImage p = PanneauJeuAmeliore.img.get(l);
						Image img = p.getImg().getSprite(0, 0);
						if(this.getBounds().width > this.getBounds().height){
							hImg = this.getBounds().height - 50 + sizeYAft;
							wImg  = (int)((float)hImg / (float)img.getHeight() * (float)img.getWidth());
							xImg = this.getWidth() / 2 - wImg / 2;
							yImg = this.getSizeY() - hImg;
						}
						img.draw(xImg +xAft, yImg + yAft, wImg, hImg);
						l = PanneauJeuAmeliore.img.size();
					}
				}
			}
			//System.out.println(System.currentTimeMillis() - tempsPrec);
			//Choice
			for (int l = 0; l < PanneauJeuAmeliore.img.size(); l++) {
				if(PanneauJeuAmeliore.img.get(l).getNom().equals(tochoose.get(choice).getImage().get(0).getImage())){
					PImage p = PanneauJeuAmeliore.img.get(l);
					Image img = p.getImg().getSprite(0, 0);
					if(this.getBounds().width > this.getBounds().height){
						hImg = this.getBounds().height - 20 + sizeYChoice;
						wImg  = (int)((float)hImg / (float)img.getHeight() * (float)img.getWidth());
						xImg = this.getWidth() / 2 - wImg / 2;
						yImg = 10;
					}
					img.draw(xImg + xChoice, yImg + yChoice, wImg, hImg);
					l = PanneauJeuAmeliore.img.size();
				}
			}
		
		g.translate(-getX(),-getY());
	}
	public void init(){
		setChoice(0);
		setTochoose(new ArrayList<ObjetMap>());
	}
	/**
	 * @return the tochoose
	 */
	public ArrayList<ObjetMap> getTochoose() {
		return tochoose;
	}
	/**
	 * @param tochoose the tochoose to set
	 */
	public void setTochoose(ArrayList<ObjetMap> tochoose) {
		this.tochoose = tochoose;
	}
	/**
	 * @return the choice
	 */
	public int getChoice() {
		return choice;
	}
	/**
	 * @param choice the choice to set
	 */
	public void setChoice(int choice) {
		this.choice = choice;
	}
	@Override
	public void updateSize() {
		// TODO Auto-generated method stub
		
	}
}
