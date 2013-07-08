package gui.widgets;


import gui.Action;
import gui.Button;
import gui.CheckBox;
import gui.Container;
import gui.ContainerWithBords;
import gui.FComponent;
import gui.GridLayout;
import gui.IntLabel;
import gui.ScrollBar;
import gui.Text;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

import ObjetMap.CollisionBlock;
import ObjetMap.LosangeBlock;
import ObjetMap.ObjetMap;

public class WCollisionList extends Widget{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Container utilsBar;
	private ScrollBar collisionListSC;
	private Container collisionList;
	private CollisionBlock selectedCollision;
	private ObjetMap cible;
	private IntLabel lx, ly, lz, lsizeX, lsizeY, lsizeZ;
	private CheckBox losange;
	private Container collisionBar;
	private Button add;
	private IntLabel rapport;
	public WCollisionList(ObjetMap cible2, Container parent) {
		super(parent);
		this.setCible(cible2);
		this.resizablex = true;
		this.resizabley = true;
		selectedCollision = new CollisionBlock(0,0,0,0,0,0);
		
		
		//GUI
		collisionList = new ContainerWithBords(0,0,1,1, this);
		
		collisionListSC =  new ScrollBar(0,0,1,1,1,1, this);
		collisionListSC.setContainer(collisionList);
		this.addComponent(collisionListSC);
		
		//Utils bar
		utilsBar = new ContainerWithBords(0,0,1,1, this);
		utilsBar.setActualLayout(new GridLayout(1,2));
		this.addComponent(utilsBar);
		
		//Collision bar
		collisionBar = new ContainerWithBords(0,0,1,1, utilsBar);
		collisionBar.setActualLayout(new GridLayout(4,2));
		utilsBar.addComponent(collisionBar);
		//Créations des objets de modifications
		lx		= new IntLabel("X", collisionBar);
		ly		= new IntLabel("Y", collisionBar);
		lz		= new IntLabel("Z", collisionBar);
	    lsizeX	= new IntLabel("SX", collisionBar);
		lsizeY	= new IntLabel("SY", collisionBar);
		lsizeZ	= new IntLabel("SZ", collisionBar);
		
		losange = new CheckBox("Losange", collisionBar);
		losange.getAction().add(new Action(){
			public void actionPerformed(FComponent e)
			{
				
			}
		});
		rapport = new IntLabel("Rp", collisionBar);
		//Ajouts des composants de modification.
		collisionBar.addComponent(lx);
		collisionBar.addComponent(ly);
		collisionBar.addComponent(lz);
		collisionBar.addComponent(losange);
		collisionBar.addComponent(lsizeX);
		collisionBar.addComponent(lsizeY);
		collisionBar.addComponent(lsizeZ);
		collisionBar.addComponent(rapport);
		
		//Bouton d'ajout de collision
		add = new Button("Add a Collision Block", utilsBar);
		add.getAction().add(new Action(){
			public void actionPerformed(FComponent c){
				CollisionBlock newBlock = new CollisionBlock(0,0,0,0,0,0);
				getCible().getCollision().add(newBlock);
				selectedCollision = newBlock;
				updateCollisionList();
			}
		});
		utilsBar.addComponent(add);
		
	}
	public void updateCollisionList(){
		collisionList.getComponents().clear();
		if(getCible() != null){
			for(int i = 0, c = getCible().getCollision().size(); i < c; i++){
				CollisionBlock block = getCible().getCollision().get(i);
				
				//Infos générales
				Text t = new Text("X : " + block.getPosX() + "->" + (block.getPosX() + block.getSizeX()) + 
						"; Y : " + block.getPosY() + "->" + (block.getPosY() + block.getSizeY()) + 
						"; Z : " + block.getPosZ() + "->" + (block.getPosZ() + block.getSizeZ()), collisionList);
				if(block instanceof LosangeBlock)
				t.setText(t.getText() + "  Losange");
				t.setAutoEnter(true);
				t.setY(i * 40);
				t.setX(20);
				t.setSizeX(collisionList.getSizeX() - 100);
				collisionList.addComponent(t);
				
				//Button de selection
				Button select = new Button("S", collisionList);
					select.setY(i * 40 + 10);
					select.setSizeY(20);
					select.setX(collisionList.getSizeX() - 120);
				select.getAction().add(new Action(){

					@Override
					public void actionPerformed(FComponent c) {
						
						int id 				= (c.getY() - 10) / 40;
						selectedCollision 	= getCible().getCollision().get(id);
						lx.		setValue(selectedCollision.getPosX());
						ly.		setValue(selectedCollision.getPosY());
						lz.		setValue(selectedCollision.getPosZ());
						
						lsizeX.	setValue(selectedCollision.getSizeX());
						lsizeY.	setValue(selectedCollision.getSizeY());
						lsizeZ.	setValue(selectedCollision.getSizeZ());
						if(selectedCollision instanceof LosangeBlock)
							losange.setCheck(true);
						else
							losange.setCheck(false);
					}
					
				});
				collisionList.addComponent(select);
				
				//Button supprimer
				Button suppr = new Button("X", collisionList);
					suppr.setY(i * 40 + 10);
					suppr.setSizeY(20);
					suppr.setX(collisionList.getSizeX() - 80);
				suppr.getAction().add(new Action(){
	
					@Override
					public void actionPerformed(FComponent c) {
						
						int id 				= (c.getY() - 10) / 40;
						getCible().getCollision().remove(id);
					}
					
				});
				collisionList.addComponent(suppr);
				
				collisionList.setSizeY(i * 40);
				
			}
		}
	}
	public void update(GameContainer gc, int x, int y){
		super.update(gc, x, y);
		
		boolean added = false;
		if(cible.getCollision().contains(selectedCollision)){
			cible.getCollision().remove(selectedCollision);
			added = true;
		}
		if(losange.isCheck()){
			selectedCollision = new LosangeBlock(0,0,0,0,0,0);
		}
		else if(!losange.isCheck()){
			selectedCollision = new CollisionBlock(0,0,0,0,0,0);
		}
		if(added == true)
			cible.getCollision().add(selectedCollision);
		
		if(collisionList.getComponents().size() / 3 != getCible().getCollision().size()
				&& !gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
			updateCollisionList();
		
		selectedCollision.setBounds(
				lx.getValue(),
				ly.getValue(), 
				lz.getValue(), 
				lsizeX.getValue(),
				lsizeY.getValue(),
				lsizeZ.getValue());
		if(getCible().getCollision().contains(selectedCollision)){
			int id = getCible().getCollision().indexOf(selectedCollision) * 3;
			((Text)(collisionList.getComponents().get(id))).setText("X : " + selectedCollision.getPosX() + "->" + (selectedCollision.getPosX() + selectedCollision.getSizeX()) + 
							"; Y : " + selectedCollision.getPosY() + "->" + (selectedCollision.getPosY() + selectedCollision.getSizeY()) + 
							"; Z : " + selectedCollision.getPosZ() + "->" + (selectedCollision.getPosZ() + selectedCollision.getSizeZ()));
			if(selectedCollision instanceof LosangeBlock)
			((Text)(collisionList.getComponents().get(id))).setText(((Text)(collisionList.getComponents().get(id))).getText() + "  Losange");
		}
	}
	public void updateSize(){
		super.updateSize();
		collisionList.setSize(sizeX, sizeY - 180);
		collisionListSC.setSize(sizeX, sizeY - 180);
		utilsBar.setBounds(0, sizeY - 180, sizeX, 180);
	}
	/**
	 * @return the cible
	 */
	public ObjetMap getCible() {
		return cible;
	}
	/**
	 * @param cible the cible to set
	 */
	public void setCible(ObjetMap cible) {
		this.cible = cible;
	}
}
