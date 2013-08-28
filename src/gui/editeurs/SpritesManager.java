package gui.editeurs;

import gui.Container;
import gui.ContainerWithBords;
import gui.FComponent;
import gui.buttons.Button;
import gui.buttons.CheckBox;
import gui.layouts.GridLayout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import observer.ActionListener;
import observer.Observable;
import observer.Observer;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ObjetMap.ObjetImage;

public class SpritesManager extends Container implements Observable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String pathIMG;
	
	private ArrayList<ObjetImage> spritesLoaded;
	
	private ArrayList<ObjetImage> spritesToAdd;

	private Container buttons;

	private ContainerWithBords imageChoice;
	
	
	//Observer mode
	
	private ArrayList<Observer> observers;
	
	public SpritesManager(int x, int y, int sizeX, int sizeY, Container parent, String pathIMG) {
		super(x, y, sizeX, sizeY, parent);
		
		this.pathIMG = pathIMG;
		
		this.observers = new ArrayList<Observer>();
		
		spritesLoaded = new ArrayList<ObjetImage>();
		
		spritesToAdd = new ArrayList<ObjetImage>();
		
		this.setActualLayout(new GridLayout(2,1));
		
		//Container des boutons
		buttons = new Container(0, 0, 1,1, this);
		this.addComponent(buttons);
		
		//Chargement par défaut
		Button loadDefault = new Button("Load Default", buttons);
		loadDefault.setSize(250, 50);
		loadDefault.getAction().add(new ActionListener(){
			public void actionPerformed(FComponent c){
				addSprites(loadSprites());
			}
		});
		buttons.addComponent(loadDefault);
		
		Button selecPath = new Button("Load XML File", 0, 55, 250, 50, buttons); //$NON-NLS-1$
		selecPath.getAction().add(new ActionListener(){
			public void actionPerformed(FComponent c){
				//Chargement du fichier
				File f = new File("Images/" + "ObjetMap/"); //$NON-NLS-1$ //$NON-NLS-2$
				
				//Chargement du chooser
				JFileChooser fc = new JFileChooser(f.getAbsolutePath());
				
				
				fc.setDialogTitle("Choisissez un xml à charger");
				
				//Filtreur de fichier
				FileFilter fileFilter = new FileNameExtensionFilter("XML Files", "xml");
				fc.setFileFilter(fileFilter);
				
				int returnVal = fc.showOpenDialog(fc);
				
				//Si on a validé
				if (returnVal == JFileChooser.APPROVE_OPTION) {
		          //On charge 
					File file = fc.getSelectedFile();
		          	String toLoad = file.getPath().replaceAll("\\\\", "/"); //$NON-NLS-1$
		          	toLoad = toLoad.substring(toLoad.indexOf("ObjetMap/"));
		          	System.out.println(toLoad);
		          	addSprites(loadSprites(toLoad));
		        }
			}
		});
		buttons.addComponent(selecPath);
		
		
		//Select ALL 
		Button selectAll = new Button("Select ALL", buttons);
		selectAll.setBounds(0, 110, 125, 50);
		selectAll.getAction().add(new ActionListener(){
			public void actionPerformed(FComponent c){
				for(FComponent comp : imageChoice.getComponents()){
					if(comp instanceof CheckBox){
						((CheckBox) comp).setCheck(true);
					}
				}
			}
		});
		buttons.addComponent(selectAll);
		
		//Deselect ALL 
				Button deselectAll = new Button("Deselect ALL", buttons);
				deselectAll.setBounds(125, 110, 125, 50);
				deselectAll.getAction().add(new ActionListener(){
					public void actionPerformed(FComponent c){
						for(FComponent comp : imageChoice.getComponents()){
							if(comp instanceof CheckBox){
								((CheckBox) comp).setCheck(false);
							}
						}
					}
				});
				buttons.addComponent(deselectAll);
		//OK
		Button ok = new Button("OK", buttons);
		ok.setBounds(0, buttons.getSizeY() - 70, 250, 70);
		ok.getAction().add(new ActionListener(){
			public void actionPerformed(FComponent c){
				notifyObservers();
			}
		});
		buttons.addComponent(ok);
		
		//Container de choix des images
		imageChoice = new ContainerWithBords(0,0, 1,1, this);
		
		this.addComponent(imageChoice);
	}


	protected void addSprites(ArrayList<ObjetImage> loadSprites) {
		this.spritesLoaded.addAll(loadSprites);
		addSpritesButton(loadSprites);
	}

	private void addSpritesButton(ArrayList<ObjetImage> loadSprites) {
		for(ObjetImage sprite : loadSprites){
			CheckBox toAdd = new CheckBox(sprite.getAlias(), imageChoice);
			toAdd.setBounds(0, (spritesLoaded.indexOf(sprite)) * 50, 150, 50);
			toAdd.getAction().add(new ActionListener(){

				@Override
				public void actionPerformed(FComponent c) {
					ObjetImage cible = spritesLoaded.get(c.getY() / 50);
					if(((CheckBox)c).isCheck()){
						if(!spritesToAdd.contains(cible)){
							spritesToAdd.add(cible);
						}
					}
					else{
						if(spritesToAdd.contains(cible)){
							spritesToAdd.remove(cible);
						}
					}
					
				}
				
			});
			imageChoice.addComponent(toAdd);
			
			Button delete = new Button("Del", imageChoice);
			delete.setBounds(150, (spritesLoaded.indexOf(sprite)) * 50, 100, 50);
			delete.getAction().add(new ActionListener(){

				@Override
				public void actionPerformed(FComponent c) {
					ObjetImage cible = spritesLoaded.get(c.getY() / 50);
					spritesToAdd.remove(cible);
					spritesLoaded.remove(cible);
					c.getParent().getComponents().clear();
					addSpritesButton(spritesLoaded);
				}
				
			});
			imageChoice.addComponent(delete);
		}
	}

	public ArrayList<ObjetImage> loadSprites(String pathXML){
		ArrayList<ObjetImage> result = new ArrayList<ObjetImage>();
		try {
			
			// Chemin du fichier à charger.
			pathXML = "Images/" + pathXML;
			
			//Chargement du fichier.
			File fXmlFile = new File(pathXML);
			
			//Si le fichier existe : 
			if(fXmlFile.exists())
			{
				
				//On charge le lecteur XML
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder;
				
					dBuilder = dbFactory.newDocumentBuilder();
				
				//On converti le document XML
				Document doc = dBuilder.parse(fXmlFile);
				//On compresse le document
				doc.getDocumentElement().normalize();
				
				
				//Obgenir le noeud des textures
				Node textureAtlas = doc.getFirstChild();
				
				//Obtenir chaque sprite.
				NodeList sprites = textureAtlas.getChildNodes();
				
				//Chargement de chaque sprite.
				for(int i = 0, c1 = sprites.getLength(); i < c1; i++){
					//Si c'est bien un sprite
					if(sprites.item(i).getNodeName().equals("SubTexture")){ //$NON-NLS-1$
						//On créé un accès rapide aux attributs.
						NamedNodeMap attributes = sprites.item(i).getAttributes();
						
						//On enregistre le nom.
						String nom = attributes.getNamedItem("name").getTextContent(); //$NON-NLS-1$
						
						String parent = "";
						//On enregistre l'alias parent
						if(attributes.getNamedItem("parent") != null)
						 parent = attributes.getNamedItem("parent").getTextContent(); //$NON-NLS-1$
						
						//On enregistre la position x
						int x = Integer.parseInt(attributes.getNamedItem("x").getTextContent()); //$NON-NLS-1$
						
						//On enregistre la pos Y
						int y = Integer.parseInt(sprites.item(i).getAttributes().getNamedItem("y").getTextContent()); //$NON-NLS-1$
						
						// Taille X
						int width = Integer.parseInt(sprites.item(i).getAttributes().getNamedItem("width").getTextContent()); //$NON-NLS-1$
						
						// Taille Y
						int height = Integer.parseInt(sprites.item(i).getAttributes().getNamedItem("height").getTextContent()); //$NON-NLS-1$
						
						//Création d'un nouvel objet avec les données récupérées.
						ObjetImage o = new ObjetImage(pathIMG, width , height , width, height, 0, 0);
						
						//Actualisation des données
						if(!nom.equals("")) //$NON-NLS-1$
							o.setAlias(nom);
						
						if(!parent.equals("")) //$NON-NLS-1$
							o.setParentAlias(parent);
						
						o.setPosSpriteSheetX(x);
						o.setPosSpriteSheetY(y);
						
						//Ajout de l'image a une liste provisoire.
						result.add(o);
					}
				}
			}else{
				System.err.print("Inexistant XML File");
			}
			
		} catch (ParserConfigurationException e1) {
			
			e1.printStackTrace();
		} catch (SAXException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return result;
	}
	public ArrayList<ObjetImage> loadSprites(){
		String path = pathIMG.substring(0, pathIMG.lastIndexOf(".")) + ".xml"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return loadSprites(path);
	}
	public String getPathIMG() {
		return pathIMG;
	}
	public void setPathIMG(String pathIMG) {
		this.pathIMG = pathIMG;
	}

	public ArrayList<ObjetImage> getSpritesToAdd() {
		return spritesToAdd;
	}

	public void setSpritesToAdd(ArrayList<ObjetImage> spritesToAdd) {
		this.spritesToAdd = spritesToAdd;
	}

	@Override
	public void addObserver(Observer obs) {
		this.observers.add(obs);
	}

	@Override
	public void remObserver(Observer obs) {
		this.observers.remove(obs);
	}

	@Override
	public void notifyObservers() {
		for(Observer obs : observers){
			ArrayList<ObjetImage> toSend = new ArrayList<ObjetImage>();
			for(ObjetImage img : spritesToAdd)
				toSend.add(img.clone());
			obs.update(this, toSend);
		}
	}

}
