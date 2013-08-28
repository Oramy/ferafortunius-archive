package gui.widgets;

import gui.Container;
import gui.ContainerWithBords;
import gui.FComponent;
import gui.ScrollBar;
import gui.inputs.IntLabel;
import gui.layouts.MinHeightLayout;

import org.newdawn.slick.GameContainer;

import ObjetMap.Entity;
import ObjetMap.ObjetMap;

public class WEntityCaracs extends Widget{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Entity obj;
	
	private ScrollBar scrollbar;
	private Container caracCont;
	
	//Components
	private IntLabel hp, mp;
	private IntLabel hpMax, mpMax;
	private IntLabel hpGain, mpGain;
	private IntLabel speed;
	
	public WEntityCaracs(ObjetMap obj, Container parent) {
		super(parent);
		setObj(obj);
		if(obj != null)
			initComponents();
	}
	
	public void initComponents(){
		//Containers
		caracCont = new ContainerWithBords(0,0,200,1000, this);
		caracCont.setActualLayout(new MinHeightLayout());
		scrollbar =  new ScrollBar(0,0,1,1,1000,1, this);
		scrollbar.setContainer(caracCont);
		
		this.addComponent(scrollbar);
		
		//Hp
		hp = new IntLabel(20,0, 1,50,"HP", caracCont);
		caracCont.addComponent(hp);
		
		//Mp
		mp = new IntLabel(20,0, 1,50,"MP", caracCont);
		caracCont.addComponent(mp);
		
		//Hp max
		hpMax = new IntLabel(20,0, 1,50,"HP Max", caracCont);
		caracCont.addComponent(hpMax);
		
		//Mp Max
		mpMax = new IntLabel(20,0, 1,50,"MP Max", caracCont);
		caracCont.addComponent(mpMax);
		
		//Hp Gain
		hpGain = new IntLabel(20,0, 1,50,"HP Gain", caracCont);
		caracCont.addComponent(hpGain);
		
		//Mp Gain
		mpGain = new IntLabel(20,0, 1,50,"MP Gain", caracCont);
		caracCont.addComponent(mpGain);
		
		//Mp Gain
		speed = new IntLabel(20,0, 1,50,"Speed", caracCont);
		caracCont.addComponent(speed);
	}
	@Override
	public void update(GameContainer gc, int x, int y){
		super.update(gc, x, y);
		updateEntity();
	}
	
	@Override
	public void updateSize(){
		super.updateSize();
		scrollbar.setSize(sizeX, sizeY);
		
		//Caracs
		for(FComponent c : caracCont.getComponents()){
			c.setX(20);
			c.setSizeX(sizeX - 40);
		}
	}
	public void updateCaracsComponent(){
		if(obj != null){
			hp.setValue(obj.getHp());
			mp.setValue(obj.getMp());
			hpMax.setValue(obj.getMaxHp());
			mpMax.setValue(obj.getMaxMp());
			
			hpGain.setValue(obj.getHpgain());
			mpGain.setValue(obj.getMpgain());
			
			speed.setValue(obj.getSpeed());
		}
	}
	public void updateEntity(){
		if(obj != null){
			obj.setHp(hp.getValue());
			obj.setMp(mp.getValue());
			
			obj.setMaxHp(hpMax.getValue());
			obj.setMaxMp(mpMax.getValue());
			
			obj.setHpgain(hpGain.getValue());
			obj.setMpgain(mpGain.getValue());
			
			obj.setSpeed(speed.getValue());
		}
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
		
		updateCaracsComponent();
	}
}
