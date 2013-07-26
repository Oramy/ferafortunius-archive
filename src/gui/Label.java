package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Label extends Container{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1530076009079149287L;
	protected String intitule;
	protected InputLabel input;
	private static final PImage backgroundLabel = new PImage("GUI/label.png");;
	private Color textColor;
	public Label(int x, int y , int sizeX, int sizeY,Container parent) {
		super(x, y, sizeX, sizeY, parent);
		init();
	}
	public Label(int x, int y , int sizeX, int sizeY,String intitule, Container parent){
		super(x, y, sizeX, sizeY, parent);
		init();
		this.setIntitule(intitule);		
	}
	public Label(int x, int y , int sizeX, int sizeY,String intitule, String contenu, Container parent){
		super(x, y, sizeX, sizeY, parent);
		init();
		this.setIntitule(intitule);		
		getInput().setDefaultContenu(contenu);
	}
	public Label(Container parent) {
		super(0,0,1,1, parent);
		init();
	}
	public Label(String intitule, Container parent){
		super(0,0,1,1, parent);
		init();
		this.setIntitule(intitule);		
	}
	public Label(String intitule, String contenu, Container parent){
		super(0,0,1,1, parent);
		init();
		this.setIntitule(intitule);		
		getInput().setDefaultContenu(contenu);
	}
	public void init(){
		setIntitule("");
		setInput(new InputLabel(this));
		background = Container.alpha;
		alwaysUpdateOnClick = true;
		setTextColor(new Color(0,0,0));
	}
	public void draw(Graphics g){
		if(getInput().getSizeX() < g.getFont().getWidth(intitule)){
			getInput().setX(g.getFont().getWidth(intitule) + 5);
			getInput().setSizeX(getSizeX() - g.getFont().getWidth(intitule) - 15);
			getInput().setSizeY(sizeY);
		}
		if(intitule.equals("")){
			getInput().setX(0);
			getInput().setSizeX(sizeX);
			getInput().setSizeY(sizeY);
		}
		g.translate(getX(), getY());
			g.setColor(getTextColor());
			g.drawString(intitule, 0, getSizeY() / 2 - g.getFont().getLineHeight() / 2);
			//Affichage de l'input label
			g.translate(getInput().getX(), getInput().getY());
				backgroundLabel.getImg().draw(0, 0, getInput().getSizeX(), getInput().getSizeY());
			g.translate(-getInput().getX(), -getInput().getY());
			getInput().drawBegin(g);
			getInput().draw(g);
			getInput().drawEnd(g);
		g.translate(-getX(), -getY());
		super.draw(g);
	}
	public void updateSize(){
		
	}
	public void update(GameContainer gc, int x, int y){
		super.update(gc, x, y);
		getInput().update(gc, x + this.getX(), y + this.getY());
	}
	/**
	 * @return the intitule
	 */
	public String getIntitule() {
		return intitule;
	}
	/**
	 * @param intitule the intitule to set
	 */
	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}
	/**
	 * @return the input
	 */
	public InputLabel getInput() {
		return input;
	}
	/**
	 * @param input the input to set
	 */
	public void setInput(InputLabel input) {
		this.input = input;
	}
	/**
	 * @return the textColor
	 */
	public Color getTextColor() {
		return textColor;
	}
	/**
	 * @param textColor the textColor to set
	 */
	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}
}
