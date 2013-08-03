package gui.editeurs;

import ObjetMap.Entity;
import ObjetMap.ObjetMap;
import gui.Container;
import gui.ContainerWithBords;

public class EntityCaracteristicEditor extends ContainerWithBords{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Entity obj;
	
	public EntityCaracteristicEditor(ObjetMap obj,int x, int y, int sizeX, int sizeY,
			Container parent) {
		super(x, y, sizeX, sizeY, parent);
		background = Container.normalBackground;
		setObj(obj);
	}
	public Entity getObj() {
		return obj;
	}
	public void setObj(ObjetMap obj) {
		if(obj instanceof Entity){
			this.obj = (Entity)obj;
		}
		else
			this.obj = null;
	}
}
