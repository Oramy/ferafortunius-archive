package gui;

import java.util.ArrayList;

import observer.ActionListener;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class Slider extends FComponent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4119891275627924428L;
	private int value, valueMax;
	private PImage slideZone;
	private PImage slider;
	private boolean clicked;
	private boolean dragged;
	private int pas;
	private ArrayList<ActionListener> action;
	public Slider(int valueMax, Container parent){
		super(parent);
		init();
		this.valueMax = valueMax;
		setPas(valueMax / 50);
		if(pas == 0)
			pas = 1;
		
	}
	public Slider(int valueMax, int value, Container parent){
		super(parent);
		init();
		this.valueMax = valueMax;
		this.value = value;
		this.setSizeX(valueMax);
		setPas(valueMax / 50);
		if(pas == 0)
			pas = 1;
		
	}
	public void init(){
		value = 0;
		valueMax = 0;
		setSizeX(100);
		setSizeY(58);
		slideZone = new PImage("GUI/slideZone.png");
		slider = new PImage("GUI/slider.png");
		action = new ArrayList<ActionListener>();
		clicked = false;
		dragged = false;
	}
	public void draw(Graphics g) {
		g.translate(x,y);
			slideZone.getImg().draw(0, 9,getSizeX(), slideZone.getImg().getHeight() / 2);
			slider.getImg().draw((float)(getSizeX() - slider.getImg().getWidth() / 2) / (float)(valueMax) * (float)(value),0,slider.getImg().getWidth() / 2, slider.getImg().getHeight() / 2);
		g.translate(-x,-y);
	}
	public void update(GameContainer gc, int x, int y){
		int mx = gc.getInput().getMouseX();
		@SuppressWarnings("unused")
		int my = gc.getInput().getMouseY();
		if(isInside(gc.getInput().getMouseX(),gc.getInput().getMouseY()) && gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && !clicked){
			clicked = true;
			dragged = true;
			
		}
		if(dragged){
			if(mx - x> this.getX() + (float)(getSizeX() - slider.getImg().getWidth() / 4) / (float)(valueMax) * (float)(value))
				setValue(getValue() + pas);
			if(mx - x < this.getX() + (float)(getSizeX() - slider.getImg().getWidth() / 4) / (float)(valueMax) * (float)(value))
				setValue(getValue()- pas);
			for(int i =0; i < action.size(); i++)
				action.get(i).actionPerformed(this);
		}
		if(gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && !clicked){
			clicked = true;
		}
		else if(!gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && clicked){
			clicked = false;
			dragged = false;
		}
	}
	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(int value){
		this.value = value;
		if(value >= valueMax)
			this.value = valueMax;
		if(value <= 0)
			this.value = 0;
		//System.out.println("Value = " + value);
		//System.out.println("ValueMax = " + valueMax);
	}
	/**
	 * @return the action
	 */
	public ArrayList<ActionListener> getAction() {
		return action;
	}
	/**
	 * @param action the action to set
	 */
	public void setAction(ArrayList<ActionListener> action) {
		this.action = action;
	}
	/**
	 * @return the valueMax
	 */
	public int getValueMax() {
		return valueMax;
	}
	/**
	 * @param valueMax the valueMax to set
	 */
	public void setValueMax(int valueMax) {
		this.valueMax = valueMax;
	}
	/**
	 * @return the pas
	 */
	public int getPas() {
		return pas;
	}
	/**
	 * @param pas the pas to set
	 */
	public void setPas(int pas) {
		this.pas = pas;
	}
	@Override
	public void updateSize() {
		// TODO Auto-generated method stub
		
	}
}
