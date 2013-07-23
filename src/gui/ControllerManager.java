package gui;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.command.ControllerButtonControl;

public class ControllerManager {
	private boolean button1;
	private boolean button1Pressed, button1Released;
	
	private boolean up;
	private boolean upPressed, upReleased;
	
	private Container controllerContainer;
	
	private boolean down;
	private boolean downPressed, downReleased;
	
	private boolean start;
	private boolean startPressed, startReleased;
	private int id;
	public ControllerManager(int id) {
		this.id = id;
	}

	public void update(GameContainer gc) {
		if(gc.getInput().isButton1Pressed(id)){
			button1Pressed = true;
			if(!button1)
				button1 = true;
		}
		else if(!gc.getInput().isButton1Pressed(id)){
			button1Pressed = false;
			if(button1){
				button1 = false;
				button1Released = true;
			}
			else{
				button1Released = false;
			}
		}
		if(gc.getInput().isButtonPressed(7, id)){
		System.out.println("Start");
			startPressed = true;
			if(!start)
				start = true;
		}
		else if(!gc.getInput().isButtonPressed(7, id)){
			startPressed = false;
			if(start){
				start = false;
				startReleased = true;
			}
			else{
				startReleased = false;
			}
		}
		
		if(gc.getInput().isControllerUp(id)){
			upPressed = true;
			if(!up)
				up = true;
		}
		else if(!gc.getInput().isControllerUp(id)){
			upPressed = false;
			if(up){
				up = false;
				upReleased = true;
			}
			else{
				upReleased = false;
			}
		}
		
		if(gc.getInput().isControllerDown(id)){
			downPressed = true;
			if(!down)
				down = true;
		}
		else if(!gc.getInput().isControllerDown(id)){
			downPressed = false;
			if(down){
				down = false;
				downReleased = true;
			}
			else{
				downReleased = false;
			}
		}
	}
	public boolean isButton1Pressed() {
		return button1Pressed;
	}
	public boolean isButton1Released() {
		return button1Released;
	}

	public Container getControllerContainer() {
		return controllerContainer;
	}

	public void setControllerContainer(Container controllerContainer) {
		this.controllerContainer = controllerContainer;
	}
	public boolean isUpPressed() {
		return upPressed;
	}

	public boolean isUpReleased() {
		return upReleased;
	}

	public boolean isDownPressed() {
		return downPressed;
	}

	public boolean isDownReleased() {
		return downReleased;
	}

	public boolean isStartPressed() {
		return startPressed;
	}

	public boolean isStartReleased() {
		return startReleased;
	}


}
