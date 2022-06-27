package gui;

import net.java.games.input.ControllerEnvironment;

import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

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
		int count = Controllers.getControllerCount();
		
		for (int i = 0; i < count; i++) {
			Controller controller = Controllers.getController(i);

			if ((controller.getButtonCount() >= 3) && (controller.getButtonCount() < 100)) {
				if(controller.getName().contains("XBOX"))
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
		if(firstController == null) {
			int count = Controllers.getControllerCount();
			int id = -1;
			int j = -1;
			for (int i = 0; i < count; i++) {
				Controller controller = Controllers.getController(i);

				if ((controller.getButtonCount() >= 3) && (controller.getButtonCount() < 100)) {
					j++;
					
					if(controller.getName().contains("XBOX")
							|| controller.getName().contains("PS"))
					{
						id = j;	
						break;
					}
				}
			}
			firstController = new ControllerManager(id);
		}
		return firstController;
	}
	public static void update(GameContainer gc){
		if(hasController(gc)){
			getFirstController().update(gc);
		}
	}
}
