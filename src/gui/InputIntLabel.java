package gui;

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
	public void keyPressed(int key, char c) {
		String c2 = "";
		c2 += c;
		if(Character.isDigit(c)){
			contenu = contenu.substring(0, cursor) + c2 +  contenu.substring(cursor, contenu.length());
			cursor++;
		}
		else if(key == Input.KEY_BACK){
			if(cursor != 0){
				cursor--;
				contenu = contenu.substring(0, cursor)  +  contenu.substring(cursor + 1, contenu.length());
			}
		}
		else if(key == Input.KEY_LEFT){
			if(cursor != 0)
				cursor--;
		}
		else if(key == Input.KEY_RIGHT){
			if(cursor != contenu.length())
				cursor++;
		}
		else if(key == Input.KEY_DELETE){
			if(cursor != contenu.length()){
				contenu = contenu.substring(0, cursor)  +  contenu.substring(cursor + 1, contenu.length());
			}
		}
		else if(c == '-' && cursor == 0){
			contenu = contenu.substring(0, cursor) + c2 +  contenu.substring(cursor, contenu.length());
			cursor++;
		}
	}
}
