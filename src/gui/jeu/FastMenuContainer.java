package gui.jeu;

import gui.Align;
import gui.Container;
import gui.PImage;
import gui.Text;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;


public class FastMenuContainer extends Container{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7505571169771491184L;
	private int natY;
	private LifeBar life;
	private ManaBar mana;
	private ExpBar exp;
	public FastMenuContainer(int x, int y, int sizeX, int sizeY,
			Jeu jeu) {
		super(x, y, sizeX, sizeY, jeu);
		background =  new PImage("alpha.png");
		natY = this.getY();
		life = new LifeBar(jeu.getPlayer(), this.getWidth() / 2 - 88, this);
		life.setColor(new Color(0,255,0,100));
		life.setSizeY(20);
		life.setX(0);
		life.setY(66 - life.getSizeY());
		life.setTextAlign(Align.Center);
		mana = new ManaBar(jeu.getPlayer(), this.getWidth() / 2 - 88, this);
		mana.setColor(new Color(0,0,255,100));
		mana.setSizeY(20);
		mana.setY(66 - mana.getSizeY());
		mana.setX(this.getWidth() - mana.getSizeX());
		mana.setAlign(Align.Right);
		mana.setTextAlign(Align.Center);
		exp = new ExpBar(jeu.getPlayer(), this.getWidth(), this);
		exp.setColor(new Color(0,0, 0,100));
		exp.setSizeY(24);
		exp.setY(86 - exp.getSizeY());
		exp.setX(0);
		exp.setTextAlign(Align.Center);
		Container cont = new Container(this.getWidth() / 2 - 121, 5, 243, 70, this);
		Text nomPerso = new Text(jeu.getPlayer().getNom(), cont);
		nomPerso.setX(cont.getSizeX() / 2 - jeu.getGm().getApp().getDefaultFont().getWidth(nomPerso.getText()) / 2);
		nomPerso.setY(20);
		cont.addComponent(nomPerso);
		cont.setBackground(new PImage("GUI/statusBackground.png"));
		Container infos = new Container(0, 86, this.getWidth(), this.getHeight() - 86, this);
		this.addComponent(infos);
		this.addComponent(life);
		this.addComponent(mana);
		this.addComponent(cont);
		this.addComponent(exp);
		
	}
	public void update(GameContainer gc, int x, int y){
		super.update(gc, x, y);
		if(gc.getInput().getMouseX() >= this.getX() + x
				&& gc.getInput().getMouseX() <= this.getX() + x + getSizeX()
				&& gc.getInput().getMouseY() >= this.getY() + y
				&& gc.getInput().getMouseY() <= this.getY() + y + getSizeY()){
			if(this.getY() > natY - getSizeY() +90)
				this.setY(this.getY() - getSizeY() / 40);
			if(this.getY() < natY - getSizeY() +90)
				y = natY - getSizeY() +90;
			if(getzIndex() != parent.getzMax()){
				parent.setzMax(parent.getzMax() + 1);
				setzIndex(parent.getzMax());
			}
		}
		else if( this.getY() < natY){
			this.setY(this.getY() + getSizeY() / 40);
		}
	}
}
