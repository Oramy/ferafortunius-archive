package gui.jeu;

import gui.Action;
import gui.Container;
import gui.FComponent;
import gui.NextDialogButton;
import gui.PImage;
import gui.Text;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;


public class DialogBar extends Container{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected ArrayList<Text> dialogList;
	protected boolean enter;
	protected static PImage textButton = new PImage("GUI/textboxbutton.png"), textButtonHover = new PImage("GUI/textboxbuttonhover.png");
	protected  PImage actualTextButton;
	protected NextDialogButton next;
	protected boolean clicked;
	protected int oldx,oldy;
	public DialogBar(int x, int y, int sizeX, int sizeY,
			ArrayList<Text> dialogList2, PanneauJeuAmeliore panneauDuJeu) {
		super(x, y, sizeX, sizeY, panneauDuJeu);
		this.dialogList = dialogList2;
		enter = false;
		background.getImg().setAlpha(0.5f);
		actualTextButton = textButton;
		next = new NextDialogButton(this);
		next.setX(sizeX - 40);
		next.setY(sizeY - 51);
		next.getAction().add(new Action(){
			public void actionPerformed(FComponent c){
				nextDialog();
			}
		});
		this.addComponent(next);
		clicked = false;
		
	}
	public void draw(Graphics g){
		super.draw(g);
		g.drawImage(actualTextButton.getImg(), getX(),getY());
	}
	public void update(GameContainer gc, int x, int y){
		super.update(gc, x, y);
		if(gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && !clicked && actualTextButton == textButtonHover){
			clicked = true;	
			
		}
		if(clicked){
			this.setX(this.getX() + Mouse.getX() - oldx);
			this.setY(this.getY() - Mouse.getY() + oldy);
			oldx = Mouse.getX() - 15;
			oldy = Mouse.getY() - getSizeY() - 30;
			this.setY(this.getY() + (getSizeY() + 30));
			this.setX(this.getX() - 15);
		}
		if(!gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && clicked){
			clicked = false;
		}
		int mx = Mouse.getX();
		int my = this.getRacine().getSizeY() - Mouse.getY();
		if(mx > x+this.getX() && mx < x+this.getX() + actualTextButton.getImg().getWidth()
				&& my > y+this.getY() && my < y+this.getY() + actualTextButton.getImg().getHeight()){
			actualTextButton = textButtonHover;
		}
		else{
			actualTextButton = textButton;
		}
		next.update(gc, this.x + x, this.y + y);
		if(dialogList.size() <= 1){
			this.components.remove(next);
		}
		else{
			if(!this.components.contains(next)){
				this.addComponent(next);
			}
		}
		if(this.components.size() <= 1 && dialogList.size() > 0){
			this.background = new PImage("GUI/textBox.png");
			background.getImg().setAlpha(0.5f);
			this.addComponent(dialogList.get(0));
			dialogList.get(0).setY(20);
			dialogList.get(0).setMarginX(70);
			dialogList.get(0).setDisplay(((Jeu)(this.getRacine())).getGameTextDisplayMode());
		}
		else if(dialogList.size() == 0 && this.components.size() == 0){
			this.background = new PImage("alpha.png");
			
		}
		
		if(gc.getInput().isKeyDown(Input.KEY_ENTER)){
			enter = true;
		}
		else if(!gc.getInput().isKeyDown(Input.KEY_ENTER) && enter == true){
			enter = false;
			nextDialog();
		}
	}
	protected void nextDialog(){
		if(dialogList.size() > 0){
			if(dialogList.get(0).getDisplayInt() >= dialogList.get(0).getText().length() - 1){
				if(this.components.size() > 1)
					this.components.remove(1);
				if(dialogList.size() > 0)
					dialogList.remove(0);
				if(dialogList.size() == 0 && this.components.size() > 0)
					this.components.remove(0);
			}
			else{
				dialogList.get(0).setDisplayInt(dialogList.get(0).getText().length() - 1);
			}
		}
	}
}
