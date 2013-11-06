package gui;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import org.newdawn.slick.GameContainer;

public class ControllersManager {
	private static ControllerManager firstController;
	private static PImage buttonXXbox = new PImage("GUI/Controllers/x.png"), buttonXPS3  = new PImage("GUI/Controllers/PS3_Square.png");
	private static PImage buttonAXbox = new PImage("GUI/Controllers/a.png"), buttonAPS3  = new PImage("GUI/Controllers/PS3_Square.png");

	public static PImage getButtonX(GameContainer gc){
		if(hasController(gc) && isXBox(gc))
			return buttonXXbox;
		return buttonXPS3;
	}
	public static PImage getButtonA(GameContainer gc){
		if(hasController(gc) && isXBox(gc))
			return buttonAXbox;
		return buttonAPS3;
	}
	public static boolean isXBox(GameContainer gc){
		Controller[] cons = ControllerEnvironment.getDefaultEnvironment().getControllers();
		for(Controller c : cons){
			if(c.getName().contains("XBOX 360"))
		    {
				return true;
		    }
		}
		return false;
	}
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
