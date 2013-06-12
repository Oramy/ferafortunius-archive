package gui.editeurs;

import gui.Action;
import gui.Button;
import gui.CheckBox;
import gui.Chooser;
import gui.Container;
import gui.ContainerWithBords;
import gui.FComponent;
import gui.IntLabel;
import gui.InternalFrame;
import gui.Label;
import gui.PImage;
import gui.ScrollBar;
import gui.Slider;
import gui.Text;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.newdawn.slick.GameContainer;

import ObjetMap.Direction;
import ObjetMap.ObjetImage;
import ObjetMap.ObjetImageList;
import ObjetMap.ObjetMap;


public class ImgEditor extends ContainerWithBords implements Observer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private IntLabel sizeInGameX;
	private IntLabel sizeInGameY;
	
	private IntLabel spriteSizeX;
	private IntLabel spriteSizeY;
	private IntLabel spriteX;
	private IntLabel spriteY;
	
	private IntLabel rotationCenterX;
	private IntLabel rotationCenterY;
	private Slider rotation;
	
	private IntLabel spriteSheetX;
	private IntLabel spriteSheetY;
	
	private Button upLayer;
	private Button downLayer;
	
	private Label alias;
	private Label parentalias;
	
	private CheckBox mirror;
	
	private ObjetMap obj;
	
	private Container pathContainer;
		private Button selecPath;
		private Label imgPath;
	
	private Button loadImage;
	private Button addSprites;
	private Button addImage;
	private Button remImage;
	
	private ObjetImage imgToAdd;
	private PImage apercImg;
	
	private ScrollBar imgExplorerscroll;
	private Container imgExplorer;
	
	private Direction direction;
	private Chooser directionChoice;

	private Button addImageList;

	private Label imageListName;
	
	
	
	public void updateImageData(){
		//Actualisation des données de l'image
		getImgToAdd().setImageSizeInGameX(sizeInGameX.getValue());
		getImgToAdd().setImageSizeInGameY(sizeInGameY.getValue());
		
		if(spriteSizeX.getValue() > 0)
			getImgToAdd().setSizeSpriteX(spriteSizeX.getValue());
		else
			getImgToAdd().setSizeSpriteX(1);
		
		
		if(spriteSizeY.getValue() > 0)
			getImgToAdd().setSizeSpriteY(spriteSizeY.getValue());
		else
			getImgToAdd().setSizeSpriteY(1);
		
		
		getImgToAdd().setPosX(spriteX.getValue());
		getImgToAdd().setPosY(spriteY.getValue());
		
		getImgToAdd().setRotationCenterX(rotationCenterX.getValue());
		getImgToAdd().setRotationCenterY(rotationCenterY.getValue());
		
		getImgToAdd().setPosSpriteSheetX(spriteSheetX.getValue());
		getImgToAdd().setPosSpriteSheetY(spriteSheetY.getValue());
		
		getImgToAdd().setRotation(rotation.getValue());
		getImgToAdd().setAlias(alias.getInput().getContenu());
		
		getImgToAdd().setParentAlias(parentalias.getInput().getContenu());
		getImgToAdd().setMirror(mirror.isCheck());
	}
	/**
	 * Permet d'actualiser la liste des boutons pour la liste d'images actuelle.
	 * @param gc l'environnement
	 * @param x position de la souris
	 * @param y position de la souris
	 */
	public void updateButtons(GameContainer gc, int x, int y)
	{
		//Si la liste n'est pas vide
		if(getObj().getImageList(directionChoice.getSelectedChoice()) != null){
			//Si ça n'est pas deja actualisé
			if(getObj().getImageList(directionChoice.getSelectedChoice()).getList().size() > imgExplorer.getComponents().size()){
				
				// On change la taille de notre barre de scroll
				imgExplorerscroll.setValueMax(60 + getObj().getImageList(directionChoice.getSelectedChoice()).getList().size() * 45);
				
				//On change la taille de notre container
				imgExplorer.setSizeY(60 + getObj().getImageList(directionChoice.getSelectedChoice()).getList().size() * 45);
				
				//On rajoute un bouton pour chaque image
				for(int i = imgExplorer.getComponents().size(); i < this.getObj().getImageList(directionChoice.getSelectedChoice()).getList().size(); i++){
					
					//Création du bouton
					Button modImg = null;
					
					//Si l'alias du bouton est inexistant, on donne le nom de l'image au bouton.
					if(getObj().getImageList(directionChoice.getSelectedChoice()).getList().get(i).getAlias() == null || getObj().getImageList(directionChoice.getSelectedChoice()).getList().get(i).getAlias() =="" ) //$NON-NLS-1$
						modImg = new Button(getObj().getImageList(directionChoice.getSelectedChoice()).getList().get(i).getImage().substring(getObj().getImageList(directionChoice.getSelectedChoice()).getList().get(i).getImage().lastIndexOf("/")), imgExplorer); //$NON-NLS-1$
					
					//Sinon, on prend l'alias
					else
						modImg = new Button(getObj().getImageList(directionChoice.getSelectedChoice()).getList().get(i).getAlias(), imgExplorer);
					
					//Ajout de l'action de séléction au bouton
					modImg.getAction().add(new Action(){
						public void actionPerformed(FComponent c){
							setObj(obj);
							getImgPath().getInput().setContenu(getObj().getImageList(directionChoice.getSelectedChoice()).getList().get((c.getY() - 5) / 45).getImage());
							getLoadImage().getAction().get(0).actionPerformed(getLoadImage());
							setImgToAdd(getObj().getImageList(directionChoice.getSelectedChoice()).getList().get((c.getY() - 5) / 45));
						}
					});
					
					//Positionnement
					modImg.setX(5);
					modImg.setY(5 + 45 * (i));
					
					//Ajout au container
					imgExplorer.addComponent(modImg);
				}
			}
			//Sinon, si une image a été enlevée
			else if(getObj().getImageList(directionChoice.getSelectedChoice()).getList().size() < imgExplorer.getComponents().size() && imgExplorer.getComponents().size() > 0){
				
				//On enlève le bouton correspondant.
				imgExplorer.getComponents().remove(imgExplorer.getComponents().size() - 1);
				imgExplorer.setSizeY(60 + getObj().getImageList(directionChoice.getSelectedChoice()).getList().size() * 45);
				
			}
		}
	}
	public void update(GameContainer gc, int x, int y){
		super.update(gc, x, y);
		
		//Actualisation des données de l'image actuelle
		updateImageData();
		
		//Actualisation des boutons
		updateButtons(gc, x, y);
		
		//Actualisation de la direction.
		updateDirection();
	}
	public void updateDirection(){
		String selectedChoice = directionChoice.getSelectedChoice();
		
		//Actualise la liste d'image et le pointeur.
		obj.setCurrentImageList(selectedChoice);
		obj.setImageList(selectedChoice);
	}
	
	public ImgEditor(int x, int y, int sizeX, int sizeY, ObjetMap objet, Container parent) {
		super(x, y, sizeX, sizeY, parent);
		this.setObj(objet);
		direction = Direction.S;
		pathContainer = new Container(20,5, (sizeX / 2) / 6 * 5 - 40,40, this);
		pathContainer.setBackground(new PImage("alpha.png")); //$NON-NLS-1$
		setImgPath(new Label(0,0, pathContainer.getSizeX(), 40, "", Messages.getString("ImgEditor.4"), pathContainer)); //$NON-NLS-1$ //$NON-NLS-2$
		pathContainer.addComponent(getImgPath());
		selecPath = new Button(Messages.getString("ImgEditor.5"), pathContainer.getSizeX() + pathContainer.getX(), 10, sizeX / 2 - (pathContainer.getSizeX() + pathContainer.getX()) - 20, 30, this); //$NON-NLS-1$
		selecPath.getAction().add(new Action(){
			public void actionPerformed(FComponent c){
				//Chargement du fichier
				File f = new File("Images/" + "ObjetMap/"); //$NON-NLS-1$ //$NON-NLS-2$
				
				//Chargement du chooser
				JFileChooser fc = new JFileChooser(f.getAbsolutePath());
				
				
				fc.setDialogTitle("Choisissez une image à charger");
				
				//Filtreur de fichier
				FileFilter fileFilter = new FileNameExtensionFilter("PNG Files", "png");
				fc.setFileFilter(fileFilter);
				int returnVal = fc.showOpenDialog(fc);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            getImgPath().getInput().setContenu(file.getPath().substring(file.getPath().lastIndexOf("ObjetMap"))); //$NON-NLS-1$
		            getImgPath().getInput().setContenu(getImgPath().getInput().getContenu().replaceAll("\\\\", "/")); //$NON-NLS-1$ //$NON-NLS-2$
		        }
			}
		});
		this.addComponent(selecPath);
		this.addComponent(pathContainer);
		setLoadImage(new Button(Messages.getString("ImgEditor.10"), 20, 50, sizeX / 2 - 40,30,this)); //$NON-NLS-1$
		getLoadImage().getAction().add(new Action(){
			public void actionPerformed(FComponent c){
				if(!getImgPath().getInput().getContenu().equals("")){ //$NON-NLS-1$
					apercImg = new PImage(getImgPath().getInput().getContenu());
					sizeInGameX.getInput().setContenu(apercImg.getImg().getWidth()+ ""); //$NON-NLS-1$
					sizeInGameY.getInput().setContenu(apercImg.getImg().getHeight()+""); //$NON-NLS-1$
					spriteSizeX.getInput().setContenu(apercImg.getImg().getWidth()+ ""); //$NON-NLS-1$
					spriteSizeY.getInput().setContenu(apercImg.getImg().getHeight()+ ""); //$NON-NLS-1$
					if(apercImg.getImg() != null)
						setImgToAdd(new ObjetImage(getImgPath().getInput().getContenu(), apercImg.getImg().getWidth() ,apercImg.getImg().getHeight() ,apercImg.getImg().getWidth(),apercImg.getImg().getHeight(),0,0));
					else{
						InternalFrame error = new InternalFrame(100, 100, 450, 50, Messages.getString("ImgEditor.16"), (Container) c.getRacine()); //$NON-NLS-1$
						error.getContainer().addComponent(new Text(Messages.getString("ImgEditor.17"), error.getContainer())); //$NON-NLS-1$
						((Container) c.getRacine()).addComponent(error);
					}
					
				}
			}
		});
		this.addComponent(getLoadImage());
		sizeInGameX = new IntLabel(20, 80, sizeX / 4 - 20, 40,"", Messages.getString("ImgEditor.19"), this); //$NON-NLS-1$ //$NON-NLS-2$
		this.addComponent(sizeInGameX);
		sizeInGameY = new IntLabel(sizeX / 4, 80, sizeX / 4 - 20, 40,"", Messages.getString("ImgEditor.21"), this); //$NON-NLS-1$ //$NON-NLS-2$
		this.addComponent(sizeInGameY);
		
		spriteSizeX = new IntLabel(20, 120, sizeX / 4 - 20, 40,Messages.getString("ImgEditor.22"),"", this); //$NON-NLS-1$ //$NON-NLS-2$
		this.addComponent(spriteSizeX);
		spriteSizeY = new IntLabel(sizeX / 4, 120, sizeX / 4 - 20, 40,Messages.getString("ImgEditor.24"), "", this); //$NON-NLS-1$ //$NON-NLS-2$
		this.addComponent(spriteSizeY);
		
		spriteX = new IntLabel(20, 160, sizeX / 4 - 20, 40,Messages.getString("ImgEditor.26"), "", this); //$NON-NLS-1$ //$NON-NLS-2$
		this.addComponent(spriteX);
		spriteY = new IntLabel(sizeX / 4, 160, sizeX / 4 - 20, 40,Messages.getString("ImgEditor.28"), "", this); //$NON-NLS-1$ //$NON-NLS-2$
		this.addComponent(spriteY);
		
		rotationCenterX = new IntLabel(20, 200, sizeX / 4 - 20, 40,Messages.getString("ImgEditor.30"), "", this); //$NON-NLS-1$ //$NON-NLS-2$
		this.addComponent(rotationCenterX);
		rotationCenterY = new IntLabel(sizeX / 4, 200, sizeX / 4 - 20, 40,Messages.getString("ImgEditor.32"), "", this); //$NON-NLS-1$ //$NON-NLS-2$
		this.addComponent(rotationCenterY);
		
		rotation = new Slider(360, 360, this);
		rotation.setPas(2);
		rotation.setSizeX(sizeX / 2 - 40);
		rotation.setX(20);
		rotation.setY(240);
		this.addComponent(rotation);

		spriteSheetX = new IntLabel(20, 270, sizeX / 4 - 20, 40,Messages.getString("ImgEditor.34"), "", this); //$NON-NLS-1$ //$NON-NLS-2$
		this.addComponent(spriteSheetX);
		
		spriteSheetY = new IntLabel(sizeX / 4, 270, sizeX / 4 - 20, 40,Messages.getString("ImgEditor.36"), "", this); //$NON-NLS-1$ //$NON-NLS-2$
		this.addComponent(spriteSheetY);
		
		upLayer = new Button(Messages.getString("ImgEditor.38"), this); //$NON-NLS-1$
		upLayer.getAction().add(new Action(){
			public void actionPerformed(FComponent e){
				int pos = getObj().getImageList(directionChoice.getSelectedChoice()).getList().indexOf(imgToAdd);
				if(pos < getObj().getImageList(directionChoice.getSelectedChoice()).getList().size() - 1 && pos != -1){
					getObj().getImageList(directionChoice.getSelectedChoice()).getList().remove(imgToAdd);
					getObj().getImageList(directionChoice.getSelectedChoice()).getList().add(pos + 1, imgToAdd);
					reorganizeButton();
				}
			}
		});
		upLayer.setY(310);
		upLayer.setSizeX(sizeX / 4 - 20);
		upLayer.setX(20);
		
		this.addComponent(upLayer);
		downLayer = new Button(Messages.getString("ImgEditor.39"), this); //$NON-NLS-1$
		downLayer.getAction().add(new Action(){
			public void actionPerformed(FComponent e){
				int pos = getObj().getImageList(directionChoice.getSelectedChoice()).getList().indexOf(imgToAdd);
				if(pos > 0){
					getObj().getImageList(directionChoice.getSelectedChoice()).getList().remove(imgToAdd);
					getObj().getImageList(directionChoice.getSelectedChoice()).getList().add(pos - 1, imgToAdd);
					reorganizeButton();
				}
			}
		});
		downLayer.setY(310);
		downLayer.setSizeX(sizeX/4 - 20);
		downLayer.setX(sizeX/4);
		this.addComponent(downLayer);
		
		alias = new Label(20,350, sizeX / 4 - 20, 40, Messages.getString("ImgEditor.40"), this); //$NON-NLS-1$
		this.addComponent(alias);
		
		
		parentalias = new Label(sizeX / 4,350, sizeX / 4 - 20, 40, Messages.getString("ImgEditor.41"), this); //$NON-NLS-1$
		this.addComponent(parentalias);
		
		mirror = new CheckBox(Messages.getString("ImgEditor.42"), this); //$NON-NLS-1$
		mirror.setY(390);
		mirror.setX(20);
		this.addComponent(mirror);
		
		setImgToAdd(new ObjetImage("", 0,0,0,0,0,0)); //$NON-NLS-1$
		addImage = new Button(Messages.getString("ImgEditor.44"), 20, sizeY - 80, sizeX / 2 - 40,30,this); //$NON-NLS-1$
		addImage.getAction().add(new Action(){
			public void actionPerformed(FComponent c){
				if(getObj().getImageList(directionChoice.getSelectedChoice()) != null)
					getObj().getImageList(directionChoice.getSelectedChoice()).getList().add(getImgToAdd());
				else{
					System.out.println("Liste inexistante");
				}
			}
		});
		this.addComponent(addImage);
		addSprites = new Button(Messages.getString("ImgEditor.45"), 20, sizeY - 110, sizeX / 2 - 40,30,this); //$NON-NLS-1$
		addSprites.getAction().add(new Action(){
			public void actionPerformed(FComponent c){
				openSpritesManager();
			}
		});
		this.addComponent(addSprites);
		remImage = new Button(Messages.getString("ImgEditor.62"), 20, sizeY - 50, sizeX / 2 - 40,30,this); //$NON-NLS-1$
		remImage.getAction().add(new Action(){
			public void actionPerformed(FComponent c){
				if(getObj().getImageList(directionChoice.getSelectedChoice()).getList().contains(getImgToAdd())){
					imgExplorer.getComponents().remove(getObj().getImageList(directionChoice.getSelectedChoice()).getList().indexOf(getImgToAdd()));
					getObj().getImageList(directionChoice.getSelectedChoice()).getList().remove(getImgToAdd());
					imgExplorer.setSizeY(60 + getObj().getImageList(directionChoice.getSelectedChoice()).getList().size() * 45);
					reorganizeButton();
				}
			}
		});
		this.addComponent(remImage);
		imgExplorerscroll = new ScrollBar(sizeX / 2 + 20,10, sizeX / 2 - 40, sizeY - 20, 1, 0,this);
		imgExplorer = new Container(0,0, sizeX / 2 - 40, sizeY - 20, imgExplorerscroll);
		imgExplorerscroll.setContainer(imgExplorer);
		imgExplorer.setBackground(new PImage(Messages.getString("alpha"))); //$NON-NLS-1$
		this.addComponent(imgExplorerscroll);
		
		
		addImageList = new Button("Add Image List", 10, sizeY - 200, 150, 30, this);
			addImageList.getAction().add(new Action(){
				public void actionPerformed(FComponent c){
					obj.getImagesLists().add(new ObjetImageList(imageListName.getInput().getContenu()));
					directionChoice.addChoice(obj.getImagesLists().get(obj.getImagesLists().size() - 1).getAlias());
				}
			});
		this.addComponent(addImageList);
		
		imageListName = new Label(100, sizeY - 250, 300, 30, "Image List Name", this);
		
		this.addComponent(imageListName);
		
		directionChoice = new Chooser(10, sizeY - 160, this);
		for(ObjetImageList list : obj.getImagesLists())
			directionChoice.addChoice(list.getAlias());
		if(!obj.getCurrentImageList().equals(""))
			directionChoice.setSelectedChoice(obj.getCurrentImageList());
		this.addComponent(directionChoice);
		
		
	}
	/**
	 * Ouvre le sprite manager
	 */
	protected void openSpritesManager() {
		
		
		//Création de la fenêtre
		InternalFrame frameSpritesManager = new InternalFrame(this.getRacine().getSizeX()/2 - 250, 
				this.getRacine().getSizeY()/2 - 300,
				500,
				600,
				"Gestion des sprites", (Container) this.getRacine());
		
		//Création du container principal
		SpritesManager spritesManager = new SpritesManager(0, 20, 500, 580, frameSpritesManager, this.getImgPath().getInput().getContenu());
		frameSpritesManager.getComponents().remove(frameSpritesManager.getContainer());
		frameSpritesManager.setContainer(spritesManager);
		frameSpritesManager.addComponent(spritesManager);
		
		//Ajout de this comme obsevateur.
		spritesManager.addObserver(this);
		//Ajout de la fenêtre.
		((Container) this.getRacine()).addComponent(frameSpritesManager);
	}
	protected void reorganizeButton() {
		imgExplorer.getComponents().clear();
	}
	/**
	 * @return the obj
	 */
	public ObjetMap getObj() {
		return obj;
	}
	/**
	 * @param obj the obj to set
	 */
	public void setObj(ObjetMap obj) {
		this.obj = obj;
	}
	/**
	 * @return the imgPath
	 */
	public Label getImgPath() {
		return imgPath;
	}
	/**
	 * @param imgPath the imgPath to set
	 */
	public void setImgPath(Label imgPath) {
		this.imgPath = imgPath;
	}
	/**
	 * @return the imgToAdd
	 */
	public ObjetImage getImgToAdd() {
		return imgToAdd;
	}
	/**
	 * @param imgToAdd the imgToAdd to set
	 */
	public void setImgToAdd(ObjetImage imgToAdd) {
		this.imgToAdd = imgToAdd;
		//Actualisation des données de l'éditeur.
		getImgPath().getInput().setContenu(imgToAdd.getImage());
		
		
		sizeInGameX.getInput().setContenu(imgToAdd.getImageSizeInGameX() + ""); //$NON-NLS-1$
		sizeInGameY.getInput().setContenu(imgToAdd.getImageSizeInGameY() + ""); //$NON-NLS-1$
		
		spriteSizeX.getInput().setContenu(imgToAdd.getSizeSpriteX() + ""); //$NON-NLS-1$
		spriteSizeY.getInput().setContenu(imgToAdd.getSizeSpriteY() + ""); //$NON-NLS-1$
		
		spriteX.getInput().setContenu(imgToAdd.getPosX() + ""); //$NON-NLS-1$
		spriteY.getInput().setContenu(imgToAdd.getPosY() + ""); //$NON-NLS-1$
		
		rotationCenterX.getInput().setContenu(imgToAdd.getRotationCenterX() + ""); //$NON-NLS-1$
		rotationCenterY.getInput().setContenu(imgToAdd.getRotationCenterY() + ""); //$NON-NLS-1$
		
		spriteSheetX.getInput().setContenu(imgToAdd.getPosSpriteSheetX() + ""); //$NON-NLS-1$
		spriteSheetY.getInput().setContenu(imgToAdd.getPosSpriteSheetY() + ""); //$NON-NLS-1$
		
		rotation.setValue((int) (imgToAdd.getRotation()));
		
		alias.getInput().setContenu(imgToAdd.getAlias());
		
		parentalias.getInput().setContenu(imgToAdd.getParentAlias());
		
		mirror.setCheck(imgToAdd.isMirror());
	}
	/**
	 * @return the loadImage
	 */
	public Button getLoadImage() {
		return loadImage;
	}
	/**
	 * @param loadImage the loadImage to set
	 */
	public void setLoadImage(Button loadImage) {
		this.loadImage = loadImage;
	}
	/**
	 * @return the direction
	 */
	public Direction getDirection() {
		return direction;
	}
	/**
	 * @param direction the direction to set
	 */
	public void setDirection(Direction direction) {
		if(this.direction != direction)
			imgExplorer.getComponents().clear();
		this.direction = direction;
	}
	@Override
	public void update(Observable obs, Object imageList) {
		//Si l'élémenet reçu est bien le bon :
		if(imageList instanceof ArrayList)
		{
			this.obj.getImageList(this.obj.getCurrentImageList()).getList().addAll((ArrayList<ObjetImage>) imageList);
		}
	}

}
