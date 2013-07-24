package gui;


import org.newdawn.slick.GameContainer;

public class ControllerManager {
	private boolean button1;
	private boolean button1Pressed, button1Released;
	
	private boolean button2;
	private boolean button2Pressed, button2Released;
	
	private boolean button3;
	private boolean button3Pressed, button3Released;
	
	private boolean button4;
	private boolean button4Pressed, button4Released;
	
	private boolean button5;
	private boolean button5Pressed, button5Released;
	
	private boolean up;
	private boolean upPressed, upReleased;
	
	private Container controllerContainer;
	
	private boolean down;
	private boolean downPressed, downReleased;
	
	private boolean left;
	private boolean leftPressed, leftReleased;
	
	private boolean right;
	private boolean rightPressed, rightReleased;
	
	private boolean start;
	private boolean startPressed, startReleased;
	
	private boolean select;
	private boolean selectPressed, selectReleased;
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
		if(gc.getInput().isButton2Pressed(id)){
			button2Pressed = true;
			if(!button2)
				button2 = true;
		}
		else if(!gc.getInput().isButton2Pressed(id)){
			button2Pressed = false;
			if(button2){
				button2 = false;
				button2Released = true;
			}
			else{
				button2Released = false;
			}
		}
		if(gc.getInput().isButton3Pressed(id)){
			button3Pressed = true;
			if(!button3)
				button3 = true;
		}
		else if(!gc.getInput().isButton3Pressed(id)){
			button3Pressed = false;
			if(button3){
				button3 = false;
				button3Released = true;
			}
			else{
				button3Released = false;
			}
		}
		if(gc.getInput().isButtonPressed(4, id)){
			button4Pressed = true;
			if(!button4)
				button4 = true;
		}
		else if(!gc.getInput().isButtonPressed(4, id)){
			button4Pressed = false;
			if(button4){
				button4 = false;
				button4Released = true;
			}
			else{
				button4Released = false;
			}
		}
		if(gc.getInput().isButtonPressed(5, id)){
			button5Pressed = true;
			if(!button5)
				button5 = true;
		}
		else if(!gc.getInput().isButtonPressed(5, id)){
			button5Pressed = false;
			if(button5){
				button5 = false;
				button5Released = true;
			}
			else{
				button5Released = false;
			}
		}
		
		
		if(gc.getInput().isButtonPressed(6, id)){
			selectPressed = true;
			if(!select)
				select = true;
		}
		else if(!gc.getInput().isButtonPressed(6, id)){
			selectPressed = false;
			if(select){
				select = false;
				selectReleased = true;
			}
			else{
				selectReleased = false;
			}
		}
		
		if(gc.getInput().isButtonPressed(7, id)){
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
		if(gc.getInput().isControllerLeft(id)){
			leftPressed = true;
			if(!left)
				left = true;
		}
		else if(!gc.getInput().isControllerLeft(id)){
			leftPressed = false;
			if(left){
				left = false;
				leftReleased = true;
			}
			else{
				leftReleased = false;
			}
		}
		if(gc.getInput().isControllerRight(id)){
			rightPressed = true;
			if(!right)
				right = true;
		}
		else if(!gc.getInput().isControllerRight(id)){
			rightPressed = false;
			if(right){
				right = false;
				rightReleased = true;
			}
			else{
				rightReleased = false;
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
		boolean toReturn = startReleased;
		startReleased = false;
		return toReturn;
	}
	public boolean isSelectPressed() {
		return selectPressed;
	}

	public boolean isSelectReleased() {
		boolean toReturn = selectReleased;
		selectReleased = false;
		return toReturn;
	}

	public boolean isButton2Pressed() {
		return button2Pressed;
	}

	public boolean isButton2Released() {
		return button2Released;
	}

	public boolean isLeftPressed() {
		return leftPressed;
	}

	public boolean isLeftReleased() {
		return leftReleased;
	}

	public boolean isRightPressed() {
		return rightPressed;
	}

	public boolean isRightReleased() {
		return rightReleased;
	}

	public boolean isButton3Pressed() {
		return button3Pressed;
	}

	public boolean isButton3Released() {
		boolean toReturn = button3Released;
		button3Released = false;
		return toReturn;
	}

	public boolean isButton4Pressed() {
		return button4Pressed;
	}

	public boolean isButton4Released() {
		return button4Released;
	}

	public boolean isButton5Pressed() {
		return button5Pressed;
	}

	public boolean isButton5Released() {
		return button5Released;
	}


}
