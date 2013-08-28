package gui.editeurs.items;

import gui.ChooseLabel;
import gui.Chooser;
import gui.Container;
import gui.ContainerWithBords;
import gui.FComponent;
import gui.ImgComponent;
import gui.PImage;
import gui.Text;
import gui.buttons.Button;
import gui.buttons.CheckBox;
import gui.editeurs.Messages;
import gui.inputs.IntLabel;
import gui.inputs.Label;
import gui.inputs.TextArea;
import gui.layouts.GridLayout;

import observer.ActionListener;

import org.newdawn.slick.GameContainer;

import Items.Item;
import Items.ItemDescriptionRessources;
import Items.ItemType;
import Level.ItemLoader;
import Level.ObjetMapLoader;
import ObjetMap.ItemOnMap;
import ObjetMap.ObjetImage;

public class EditeurItemGeneral extends EditeurItemBasic{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Label nom;
	private Label path;
	private Button save; 
	private TextArea description;
	
	private CheckBox autoGet;
	private IntLabel autoRange;
	
	private IntLabel poids;
	private IntLabel maxStack;
	
	private Chooser type;
	
	private Label pathImg;
	private Text descText;

	private ContainerWithBords general;

	private Container part1;

	private ImgComponent apercu;

	private Container labelsASave;

	private Container range;

	public EditeurItemGeneral(Item editItem2, int x, int y, int sizeX, int sizeY,
			Container parent) {
		super(editItem2, x, y, sizeX, sizeY, parent);
		general = new ContainerWithBords(0, 0, this.sizeX, this.sizeY / 5, this);
		GridLayout gl = new GridLayout(3,1);
		gl.setHgap(5);
		gl.setVgap(5);
		general.setActualLayout(gl);
			//Partie 1
			part1 = new ContainerWithBords(0,0, 1,1, general);
			general.addComponent(part1);	
				apercu = new ImgComponent(Container.alpha, part1); //$NON-NLS-1$
				apercu.setBounds(0, 0, part1.getSizeX(), part1.getSizeY());
				apercu.setSizeChange(false);
				part1.addComponent(apercu);
			//Partie 2 : Labels and buttons
			labelsASave = new Container(0,0,1,1, general);
			general.addComponent(labelsASave);
				labelsASave.setBackground(Container.alpha);
				GridLayout gl2 = new GridLayout(1, 4);
				gl2.setVgap(10);
				labelsASave.setActualLayout(gl2);
					nom = new Label("",Messages.getString("EditeurItem.1"), labelsASave); //$NON-NLS-1$ //$NON-NLS-2$
				labelsASave.addComponent(nom);
				
					path = new Label("",Messages.getString("EditeurItem.3"), labelsASave); //$NON-NLS-1$ //$NON-NLS-2$
				labelsASave.addComponent(path);
				
					pathImg = new ChooseLabel("Images/Items/", "",Messages.getString("EditeurItem.5"), labelsASave); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				labelsASave.addComponent(pathImg);
				
				labelsASave.setBackground(Container.alpha);
				
					
					
					save = new Button(Messages.getString("EditeurItem.11"), 0,0, 1,1, labelsASave); //$NON-NLS-1$
					save.getAction().add(new ActionListener(){
						public void actionPerformed(FComponent e){
							editItem.setDescriptionPath(path.getInput().getContenu());
							ItemDescriptionRessources.createDesc(editItem.getName(), editItem.getDescription(), editItem.getDescriptionPath());
							ItemLoader.saveObject(editItem, "data/Items/"+path.getInput().getContenu()+".item"); //$NON-NLS-1$ //$NON-NLS-2$
						}
					});
					save.getAction().add(new ActionListener(){
						public void actionPerformed(FComponent e){
							ObjetMapLoader.saveObject(new ItemOnMap(0,0,0,0,0,0, editItem), "data/ObjetMap/item"+path.getInput().getContenu()+".obj"); //$NON-NLS-1$ //$NON-NLS-2$
						}
					});
					labelsASave.addComponent(save);
			//Partie 3 : Range
			range = new Container(0,0, 1,1, general);
			general.addComponent(range);
			range.setActualLayout(new GridLayout(1, 5));
			range.setBackground(Container.alpha); 
				autoGet = new CheckBox(Messages.getString("EditeurItem.17"), range); //$NON-NLS-1$
				range.addComponent(autoGet);
				
				autoRange = new IntLabel(0, 0, 1,1, Messages.getString("EditeurItemGeneral.2"), range); //$NON-NLS-1$
				autoRange.setValue(1000);
				range.addComponent(autoRange);
				
				poids = new IntLabel(0, 0, 1,1, Messages.getString("EditeurItemGeneral.3"), range); //$NON-NLS-1$
				poids.setValue(50);
				range.addComponent(poids);
				
				maxStack = new IntLabel(0, 0, 1,1, Messages.getString("EditeurItemGeneral.4"), range); //$NON-NLS-1$
				maxStack.setValue(50);
				range.addComponent(maxStack);
				
				type = new Chooser(0,0, range);
				for(int i = 0; i < ItemType.values().length; i++)
					type.addChoice(ItemType.values()[i].toString());
				type.setSelectedChoice(editItem.getType().toString());
				range.addComponent(type);
		this.addComponent(general);
		
		//Description
		descText = new Text(Messages.getString("EditeurItemGeneral.5"), false, this); //$NON-NLS-1$
		descText.setY(sizeY / 5 - 10);
		descText.setX(20);
		this.addComponent(descText);
		description = new TextArea(5, sizeY / 5 + 10, sizeX / 2, sizeY / 5 * 2 - 20, this);
		this.addComponent(description);
	}
	public void update(GameContainer gc, int x, int y){
		super.update(gc, x, y);
		editItem.setType(ItemType.valueOf(type.getSelectedChoice()));
		editItem.setName(nom.getInput().getContenu());
		
		editItem.setDescriptionPath(path.getInput().getContenu());
		editItem.setDescription(description.getInput().getContenu());
		
		editItem.setAutoGet(autoGet.isCheck());
		editItem.setAutoGetRange(autoRange.getValue());
		
		editItem.setWeight(poids.getValue());
		editItem.setMaxstackNumber(maxStack.getValue());
		
		pathImg.getInput().setContenu(pathImg.getInput().getContenu().replace("Images/", "")); //$NON-NLS-1$ //$NON-NLS-2$
		if(!editItem.getImg().getImage().equals(pathImg.getInput().getContenu()))
		{
			editItem.setImg(new ObjetImage(pathImg.getInput().getContenu()));
			apercu.setImg(new PImage(pathImg.getInput().getContenu()));
			System.out.println(pathImg.getInput().getContenu());
		}
		editItem.setAutoGet(true);
		editItem.setAutoGetRange(100);
	}
	@Override
	public void updateSize()
	{
		super.updateSize();
	}

	
}
