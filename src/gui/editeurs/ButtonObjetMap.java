package gui.editeurs;

import gui.ButtonImage;
import gui.Container;
import ObjetMap.ObjetMap;

public class ButtonObjetMap extends ButtonImage{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
