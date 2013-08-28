package gui.inputs;

import gui.Container;
import gui.ContainerWithBords;
import gui.PImage;

import org.newdawn.slick.GameContainer;

public class TextArea extends ContainerWithBords{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected InputTextArea input;
	
	protected static final PImage textFieldBackGround = new PImage("GUI/textField.png");
	public TextArea(int x, int y, int sizeX, int sizeY, Container parent) {
		super(x, y, sizeX, sizeY, parent);
		input = new InputTextArea(this);
		input.setX(20);
		input.setY(20);
		input.setSizeX(sizeX - 40);
		input.setSizeY(sizeY - 40);
		alwaysUpdateOnClick = true;
		this.addComponent(input);
		this.setBackground(TextArea.textFieldBackGround);
		
	}
	public void update(GameContainer gc, int x, int y){
		getInput().update(gc, x + this.getX(), y + this.getY());
	}
	public InputTextArea getInput(){
		return input;
	}
	public void setInput(InputTextArea input){
		this.input = input;
	}
}
