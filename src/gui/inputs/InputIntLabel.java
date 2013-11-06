package gui.inputs;

import gui.Container;

import org.newdawn.slick.Input;

public class InputIntLabel extends InputLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7606510255118233135L;
	private int defaultIntContenu;
	public InputIntLabel(Container parent) {
		super(parent);
		
	}
	public int getDefaultIntContenu() {
		return defaultIntContenu;
	}
	public void setDefaultIntContenu(int defaultIntContenu) {
		this.defaultIntContenu = defaultIntContenu;
	}
	@Override
	public void keyPressed(int key, char c) {
		String c2 = "";
		c2 += c;
		if(Character.isDigit(c)
				|| key == Input.KEY_BACK
				|| key == Input.KEY_LEFT
				|| key == Input.KEY_RIGHT
				|| key == Input.KEY_DELETE){
			super.keyPressed(key, c);
		}
		else if(c == '-' && cursor == 0){
			contenu = contenu.substring(0, cursor) + c2 +  contenu.substring(cursor, contenu.length());
			cursor++;
		}
	}
}
