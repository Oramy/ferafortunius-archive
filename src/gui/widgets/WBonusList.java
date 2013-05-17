package gui.widgets;

import gui.Container;
import gui.ContainerWithBords;
import gui.GridLayout;
import gui.ScrollBar;

import java.util.ArrayList;

import bonus.Bonus;

public class WBonusList extends Widget {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Container utilsBar;
	private ScrollBar bonusListSC;
	private Container bonusList;
	//TODO Implement this class.
	/*private Bonus selectedBonus;
	private ArrayList<Bonus> bonus;
	private Chooser bonusType;*/
	public WBonusList(ArrayList<Bonus> bonus,Container parent) {
		super(parent);
		this.resizablex = true;
		this.resizabley = true;
		
		//this.selectedBonus = bonus.get(0);
		//GUI
		bonusList = new ContainerWithBords(0,0,1,1, this);
		
		bonusListSC =  new ScrollBar(0,0,1,1,1,1, this);
		bonusListSC.setContainer(bonusList);
		this.addComponent(bonusListSC);
		

		//Utils bar
		utilsBar = new ContainerWithBords(0,0,1,1, this);
		utilsBar.setActualLayout(new GridLayout(1,2));
		this.addComponent(utilsBar);
		
		//bonusType = new Chooser(0,0, this);
		//bonusType.addChoice(Bonus.class.)
	}
	public void updateSize(){
		super.updateSize();
		bonusList.setSize(sizeX, sizeY - 180);
		bonusListSC.setSize(sizeX, sizeY - 180);
		utilsBar.setBounds(0, sizeY - 180, sizeX, 180);
	}

}
