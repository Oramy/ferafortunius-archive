package gui;

import gui.buttons.Button;

import java.util.ArrayList;

import observer.ActionListener;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class Chooser extends Container{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<String> choices;
	private int selectedChoice;
	private Button show;
	private boolean showed;
	private int lineheight;
	public Chooser(int x, int y, Container parent) {
		super(x, y, 120, 30, parent);
		lineheight = 0;
		choices = new ArrayList<String>();
		setShowed(false);
		selectedChoice = -1;
		background = Container.backGroundUnbords;
		show = new Button("^", sizeX - 32, 0, 30, 30, this);
		show.setDefineSize(false);
		show.getAction().add(new ActionListener(){
			public void actionPerformed(FComponent e){
				show();
			}
		});
		this.addComponent(show);
		
	}
	public void show(){
		setShowed(!isShowed());
		if(isShowed()){
			sizeY = 200;
			this.y -= sizeY / 2;
		}
		else{
			this.y += sizeY / 2;
			sizeY = 30;
		}
		show.y = sizeY / 2 - show.sizeY / 2;
	}
	public void update(GameContainer gc, int x, int y){
		super.update(gc, x, y);
		
		int mx = gc.getInput().getMouseX();
		int my = gc.getInput().getMouseY();
		
		if(isInside(mx, my)){
			if(isShowed())
			{
				if(gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)){
					int id  = (int) ((my - this.getYOnScreen() - sizeY / 2) / lineheight + selectedChoice);
					if(id < choices.size() && id >= 0)
					setSelectedChoice(id);
				}
			}
		}
	}
	public void draw(Graphics g){
		super.draw(g);
		if(lineheight == 0)
			lineheight = g.getFont().getLineHeight();
		g.translate(this.getBounds().x, this.getBounds().y);
			
			g.setColor(Color.black);
			g.drawString(choices.get(selectedChoice), 10, sizeY / 2 - lineheight / 2 );
			if(isShowed()){
				int pos = selectedChoice;
				if(pos == -1)
				{
					selectedChoice = 0;
					pos =0;
				}
				for(int i = pos; i >= 0; i--){
					g.drawString(choices.get(i), 10, 
							sizeY / 2 - lineheight / 2 - lineheight * (pos - i));
				}
				for(int i = pos; i < choices.size(); i++){
					g.drawString(choices.get(i), 10, 
							sizeY / 2 - lineheight / 2 + lineheight * (i - pos));
				}
			}
		g.translate(-this.getBounds().x, -this.getBounds().y);
		
	}
	public void addChoice(String choice){
		choices.add(choice);
	}
	public void removeChoice(String choice){
		choices.remove(choice);
	}
	public void setSelectedChoice(int choice){
		selectedChoice = choice;
	}
	public void setSelectedChoice(String choice){
		selectedChoice = choices.indexOf(choice);
	}
	public String getSelectedChoice(){
		return choices.get(selectedChoice);
	}
	public String getChoice(int id){
		return choices.get(id);
	}
	public void clear(){
		choices.clear();
		selectedChoice = 0;
	}
	public boolean isShowed() {
		return showed;
	}
	public void setShowed(boolean showed) {
		this.showed = showed;
	}
	public int getSelectedChoiceID() {
		return selectedChoice;
	}
}
