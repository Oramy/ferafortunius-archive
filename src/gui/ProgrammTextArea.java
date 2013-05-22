package gui;

public class ProgrammTextArea extends TextArea{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProgrammTextArea(int x, int y, int sizeX, int sizeY, Container parent) {
		super(x, y, sizeX, sizeY, parent);
		this.components.remove(input);
		input = new InputProgrammTextArea(this);
		input.setX(20);
		input.setY(20);
		input.setSizeX(sizeX - 40);
		input.setSizeY(sizeY - 40);
		alwaysUpdateOnClick = true;
		this.addComponent(input);
		this.setBackground(new PImage("GUI/textField.png"));
		
	}

}
