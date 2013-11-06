package gui.editeurs.items;

import gui.Container;
import gui.ContainerWithBords;
import gui.FComponent;
import gui.ScrollBar;
import gui.Text;
import gui.inputs.IntLabel;
import gui.layouts.MinHeightLayout;

import org.newdawn.slick.GameContainer;

import Items.EquipmentItem;

public class EditeurCaracEquipment extends Container{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private EquipmentItem equip;
	private ScrollBar scrollbar;
	private Container caracCont;
	
	//Components
	private IntLabel hp, mp;
	private IntLabel mpgain;
	private IntLabel hpgain;
	
	public EditeurCaracEquipment(int x, int y, int sizeX, int sizeY,
			Container parent) {
		super(x, y, sizeX, sizeY, parent);
		//Containers
		caracCont = new ContainerWithBords(0,0,200,1000, this);
		caracCont.setActualLayout(new MinHeightLayout());
		scrollbar =  new ScrollBar(0,0,1,1,1000,1, this);
		scrollbar.setContainer(caracCont);
		
		this.addComponent(scrollbar);
		
		//Hp
		hp = new IntLabel(20,0, 1,50,"HP Max", caracCont);
		caracCont.addComponent(hp);
		
		//Hp
		hpgain = new IntLabel(20,0, 1,50,"HP Gain", caracCont);
		caracCont.addComponent(hpgain);
		
		//Hp
		mp = new IntLabel(20,0, 1,50,"MP", caracCont);
		caracCont.addComponent(mp);
		
		//Hp
		mpgain = new IntLabel(20,0, 1,50,"MP Gain", caracCont);
		caracCont.addComponent(mpgain);
		
			
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
		if(equip != null){
			hp.setValue(equip.getMaxHpBonus());
			hpgain.setValue(equip.getHpGainBonus());
			mp.setValue(equip.getMaxMpBonus());
			mpgain.setValue(equip.getMpGainBonus());
		}
	}
	public void updateEntity(){
		if(equip != null){
			equip.setMaxHpBonus(hp.getValue());
			equip.setHpGainBonus(hpgain.getValue());
			equip.setMaxMpBonus(mp.getValue());
			equip.setMpGainBonus(mpgain.getValue());
		}
	}
	public EquipmentItem getEquip() {
		return equip;
	}
	public void setEquip(EquipmentItem equip) {
		this.equip = equip;
		updateCaracsComponent();
	}
}
