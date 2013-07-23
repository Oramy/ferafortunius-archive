package gui;

import org.newdawn.slick.GameContainer;

public class ControllersManager {
	private static ControllerManager firstController;
	public static boolean hasController(GameContainer gc){
		if(gc.getInput().getControllerCount() > 0)
			return true;
		return false;
	}
	public static ControllerManager getFirstController(){
		if(firstController == null)
			firstController = new ControllerManager(0);
		return firstController;
	}
	public static void update(GameContainer gc){
		if(hasController(gc)){
			getFirstController().update(gc);
		}
	}
}
