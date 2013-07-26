package gui.editeurs;

import gui.Button;
import gui.CheckBox;
import gui.Container;
import gui.ContainerWithBords;
import gui.FComponent;
import gui.IntLabel;
import gui.Label;
import gui.PImage;
import gui.ScrollBar;

import observer.ActionListener;

import org.newdawn.slick.GameContainer;

import ObjetMap.Animation;
import ObjetMap.ObjetMap;
import ObjetMap.TimedScript;


public class BasicAnimationEditor extends ContainerWithBords{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Animation animationToAdd;
	private ObjetMap cible;
	private IntLabel framesSize;
	private Label nom;
	private CheckBox repeat;
	private Button start;
	private Button stop;
	private ScrollBar timedSExplorerscroll;
	private Container timedSExplorer;
	private Button clone;
	private Button newAnim;
	private Button remove;
	private TimedScriptEditor timedSEditor;
	private Button monter;
	private Button descendre;
	public BasicAnimationEditor(ObjetMap o, int x, int y, int sizeX, int sizeY,
			Container parent) {
		super(x, y, sizeX, sizeY, parent);
		setCible(o);
		init();
		
	}
	public BasicAnimationEditor(ObjetMap o,Animation anim, int x, int y, int sizeX, int sizeY,
			Container parent) {
		super(x, y, sizeX, sizeY, parent);
		setCible(o);
		init();
		
		setAnimationToAdd(o, anim);
		
	}
	public void init(){
		
		animationToAdd = new Animation("", 0); //$NON-NLS-1$
		
		//Nom de l'animation
		nom = new Label(10, 10, getSizeX() / 2 - 10, 40, "",Messages.getString("BasicAnimationEditor.2"), this); //$NON-NLS-1$ //$NON-NLS-2$
		this.addComponent(nom);
		
		
		//Nombre de frame
		framesSize = new IntLabel(10, 50, getSizeX() / 2 - 10, 40, Messages.getString("BasicAnimationEditor.3"), this); //$NON-NLS-1$
		this.addComponent(framesSize);
		
		
		//Est ce que c'est répété
		repeat = new CheckBox(Messages.getString("BasicAnimationEditor.4"),  this); //$NON-NLS-1$
		repeat.setBounds(getSizeX() / 2, 10, getSizeX() / 6, 40);
		this.addComponent(repeat);
		
		
		//Commencer l'animation
		start = new Button(Messages.getString("BasicAnimationEditor.5"),  this); //$NON-NLS-1$
		start.setBounds(getSizeX() / 2 + getSizeX() / 6, 10, getSizeX() / 6, 40);
		start.getAction().add(new ActionListener(){
			public void actionPerformed(FComponent e){
				animationToAdd.start();
				stop.enable();
				start.disable();
			}
		});
		this.addComponent(start);
		
		
		//Stopper l'animation
		stop = new Button(Messages.getString("BasicAnimationEditor.6"),  this); //$NON-NLS-1$
		stop.setBounds(getSizeX() / 2 + getSizeX() / 3, 10, getSizeX() / 6, 40);
		stop.getAction().add(new ActionListener(){
			public void actionPerformed(FComponent e){
				animationToAdd.stop();
				start.enable();
				stop.disable();
			}
		});
		this.addComponent(stop);
		
		
		//Ajouter une animation
		clone = new Button(Messages.getString("BasicAnimationEditor.7"),  this); //$NON-NLS-1$
		clone.setBounds(getSizeX() / 2 + getSizeX() / 6, 50, getSizeX() / 6, 40);
		clone.getAction().add(new ActionListener(){
			public void actionPerformed(FComponent e){
				animationToAdd = animationToAdd.clone();
				getCible().getAnimations().add(animationToAdd);
				timedSEditor.setAnim(animationToAdd);
			}
		});
		this.addComponent(clone);
		
		
		//Supprimer l'animation.
		remove = new Button(Messages.getString("BasicAnimationEditor.8"),  this); //$NON-NLS-1$
		remove.setBounds(getSizeX() / 2 + getSizeX() / 3, 50, getSizeX() / 6, 40);
		remove.getAction().add(new ActionListener(){
			public void actionPerformed(FComponent e){
				getCible().getAnimations().remove(animationToAdd);
			}
		});
		this.addComponent(remove);
		
		//Ajouter une animation
		newAnim = new Button(Messages.getString("BasicAnimationEditor.10"),  this); //$NON-NLS-1$
		newAnim.setBounds(getSizeX() / 2 + getSizeX() / 6, 90, getSizeX() / 6, 40);
		newAnim.getAction().add(new ActionListener(){
			public void actionPerformed(FComponent e){
				animationToAdd = new Animation("", 0);
				setAnimationToAdd(cible, animationToAdd);
				getCible().getAnimations().add(animationToAdd);
				timedSEditor.setAnim(animationToAdd);
			}
		});
		this.addComponent(newAnim);
		
		
		//Monter
		monter = new Button(Messages.getString("BasicAnimationEditor.11"),  this); //$NON-NLS-1$
		monter.setBounds(getSizeX() / 2 + getSizeX() / 6, 130, getSizeX() / 6, 40);
		monter.getAction().add(new ActionListener(){
			public void actionPerformed(FComponent e){
				int index = getCible().getAnimations().indexOf(animationToAdd);
				if(index > 0){
					getCible().getAnimations().remove(animationToAdd);
					getCible().getAnimations().add(index - 1, animationToAdd);
				}
			}
		});
		this.addComponent(monter);
		
		//Descendre
		descendre = new Button(Messages.getString("BasicAnimationEditor.12"),  this); //$NON-NLS-1$
		descendre.setBounds(getSizeX() / 2 + getSizeX() / 3, 130, getSizeX() / 6, 40);
		descendre.getAction().add(new ActionListener(){
			public void actionPerformed(FComponent e){
				int index = getCible().getAnimations().indexOf(animationToAdd);
				if(index < getCible().getAnimations().size()){
					getCible().getAnimations().remove(animationToAdd);
					getCible().getAnimations().add(index + 1, animationToAdd);
				}
			}
		});
		this.addComponent(descendre);
				
		timedSExplorerscroll = new ScrollBar(10 , 90, getSizeX() / 2 - 10, getSizeY() / 2 - 100, 1, 0,this);
		timedSExplorer = new Container(0,0, getSizeX() / 2 - 10, getSizeY() / 2 - 100, timedSExplorerscroll);
		timedSExplorerscroll.setContainer(timedSExplorer);
		timedSExplorer.setBackground(Container.alpha); //$NON-NLS-1$
		this.addComponent(timedSExplorerscroll);
		
		timedSEditor =  new TimedScriptEditor(animationToAdd, 0, getSizeY()  / 2, getSizeX(), getSizeY() / 2, this);
		this.addComponent(timedSEditor);
		
	}
	public void update(GameContainer gc, int x, int y){
		super.update(gc, x, y);
		updateScripts();
		updateButtonsName();
		
	}
	/**
	 * Permet de mettre a jour les scripts
	 */
	public void updateScripts(){
		if(animationToAdd != null){
			if(animationToAdd.getScripts() != null){
				if(animationToAdd.getScripts().size() > timedSExplorer.getComponents().size()){
					
					timedSExplorerscroll.setValueMax(60 +animationToAdd.getScripts().size() * 45);
					timedSExplorer.setHeight(60 +animationToAdd.getScripts().size() * 45);
					for(int i = timedSExplorer.getComponents().size(); i < this.animationToAdd.getScripts().size(); i++){
						Button modImg = null;
						modImg = new Button(animationToAdd.getScript(i).getName(), timedSExplorer);
						modImg.getAction().add(new ActionListener(){
							public void actionPerformed(FComponent c){
								((Button)c).setName(animationToAdd.getScript((c.getY() - 5) / 45).getName());
								timedSEditor.setTsCible(animationToAdd, animationToAdd.getScript((c.getY() - 5) / 45));
							}
						});
						modImg.setX(5);
						modImg.setY(5 + 45 * (i));
						timedSExplorer.addComponent(modImg);
					}
				}
				else if(animationToAdd.getScripts().size() < timedSExplorer.getComponents().size() && timedSExplorer.getComponents().size() > 0){
					timedSExplorer.getComponents().remove(timedSExplorer.getComponents().size() - 1);
					timedSExplorer.setSizeY(60 + animationToAdd.getScripts().size() * 45);
					
				}
			}
			animationToAdd.setNom(nom.getInput().getContenu());
			animationToAdd.setFramesSize(framesSize.getValue()); //$NON-NLS-1$
			animationToAdd.setRepeat(repeat.isCheck());
			if(animationToAdd.isStarted()){
				start.disable();
				stop.enable();
			}
			else{
				stop.disable();
				start.enable();
			}
		}
	}
	/**
	 * Permet de mettre a jour le nom des boutons.
	 */
	public void updateButtonsName(){
		for(TimedScript script : animationToAdd.getScripts()){
			Button button = ((Button)timedSExplorer.getComponents().get(animationToAdd.getScripts().indexOf(script)));
			if(!button.getName().equals(script.getName())){
				button.setName(script.getName());
				button.setSizeX(script.getName().length() * 9 + 40);
			}
		}
	}
	/**
	 * @return the animationToAdd
	 */
	public Animation getAnimationToAdd() {
		return animationToAdd;
	}
	/**
	 * @param animationToAdd the animationToAdd to set
	 */
	public void setAnimationToAdd(ObjetMap o,Animation animationToAdd) {
		this.animationToAdd = animationToAdd;
		setCible(o);
		nom.getInput().setContenu(animationToAdd.getNom());
		framesSize.getInput().setContenu(animationToAdd.getFramesSize() + ""); //$NON-NLS-1$
		repeat.setCheck(animationToAdd.isRepeat());
		
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
