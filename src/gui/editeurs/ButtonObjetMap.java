package gui.editeurs;

import java.util.ArrayList;

import ObjetMap.ObjetImage;
import ObjetMap.ObjetMap;
import gui.ButtonImage;
import gui.Container;

public class ButtonObjetMap extends ButtonImage{

	private ObjetMap objet;
	public ButtonObjetMap(ObjetMap o, int posX, int posY,
			int width, int height, Container parent) {
		super(o.getImage(), posX, posY, width, height, parent);
		
		if(o != null)
			this.setObjet(o);
	}
	public ObjetMap getObjet() {
		return objet;
	}
	public void setObjet(ObjetMap o) {
		this.objet = o;
	}

}
