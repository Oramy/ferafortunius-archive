package gui.editeurs.objetmaps;

import gui.Container;
import gui.editeurs.PanneauApercu;
import gui.layouts.BorderLayout;
import gui.widgets.WCollisionList;
import ObjetMap.ObjetMap;

public class EditeurImageRender extends Container{

	private ImgEditor imgEditor;
	private PanneauApercu panneauAp;
	public EditeurImageRender(ObjetMap workedObj, int x, int y, int sizeX, int sizeY, Container parent) {
		super(x, y, sizeX, sizeY, parent);
		
		this.actualLayout = new BorderLayout();
		imgEditor = new ImgEditor(0, 0, sizeX / 2, sizeY, workedObj, this);
		this.addComponent(BorderLayout.WEST, imgEditor);
		panneauAp = new PanneauApercu(imgEditor, workedObj, 0,0, sizeX / 2, sizeY, this);
		panneauAp.setMoveImage(true);
		this.addComponent(BorderLayout.EAST, panneauAp);
		
	}
	public void setObj(ObjetMap obj) {
		imgEditor.setObj(obj);
		panneauAp.setEditChoice(obj);
	}

}
