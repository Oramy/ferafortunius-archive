package gui.inputs;

import gui.Container;
import gui.FComponent;
import gui.buttons.ButtonImage;
import observer.ActionListener;
import ObjetMap.ObjetImage;

public class IntLabel extends Label{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7600451572618949939L;
	
	private ButtonImage up;
	private ButtonImage down;
	public IntLabel(int x, int y , int sizeX, int sizeY,Container parent) {
		super(x, y, sizeX, sizeY, parent);
		init();
	}
	public IntLabel(int x, int y , int sizeX, int sizeY,String intitule, Container parent){
		super(x, y, sizeX, sizeY, parent);
		init();
		this.setIntitule(intitule);		
	}
	public IntLabel(int x, int y , int sizeX, int sizeY,String intitule, String contenu, Container parent){
		super(x, y, sizeX, sizeY, parent);
		init();
		this.setIntitule(intitule);		
		getInput().setDefaultContenu(contenu);
	}
	public IntLabel(Container parent) {
		super(0,0,1,1, parent);
		init();
	}
	public IntLabel(String intitule, Container parent){
		super(0,0,1,1, parent);
		init();
		this.setIntitule(intitule);		
	}
	public IntLabel(String intitule, String contenu, Container parent){
		super(0,0,1,1, parent);
		init();
		this.setIntitule(intitule);		
		getInput().setDefaultContenu(contenu);
	}
	public void init(){
		super.init();
		input = new InputIntLabel(this);
		up = new ButtonImage(new ObjetImage("GUI/up.png"), sizeX - 30 - sizeX / 30, 0, 	10, sizeY / 2, this);
		up.setProportions(false);
		up.setActionWhilePressed(true);
		up.setDrawButton(false);
		up.getAction().add(new ActionListener(){
			public void actionPerformed(FComponent c){
				up();
			}
		});
		this.addComponent(up);
		down = new ButtonImage(new ObjetImage("GUI/down.png"), sizeX - 30 - sizeX / 30,sizeY / 2, 10, sizeY / 2,this);
		down.setProportions(false);
		down.setActionWhilePressed(true);
		down.setDrawButton(false);
		down.getAction().add(new ActionListener(){
			public void actionPerformed(FComponent c){
				down();
			}
		});
		this.addComponent(down);
	}
	public void updateSize(){
		up.setBounds(sizeX - 30 - sizeX / 30, 0, 10, sizeY / 2);
		down.setBounds(sizeX - 30 - sizeX / 30, sizeY / 2, 10, sizeY / 2);
	}
	public int getValue(){
		if(!getInput().getContenu().equals("")&& !getInput().getContenu().equals("-"))
		return Integer.parseInt(getInput().getContenu());
		
		return 0;
	}
	public void up(){
		setValue(getValue()+ 1);
	}
	public void down(){
		setValue(getValue()- 1);
	}
	public void setValue(int newVal){
		getInput().setContenu(newVal+"");
	}
}
