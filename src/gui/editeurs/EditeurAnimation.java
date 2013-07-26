package gui.editeurs;

import gui.Button;
import gui.Container;
import gui.ContainerWithBords;
import gui.FComponent;
import gui.PImage;
import gui.ScrollBar;

import observer.ActionListener;

import org.newdawn.slick.GameContainer;

import Level.Camera;
import Level.ChunkMap;
import ObjetMap.Animation;
import ObjetMap.ObjetMap;


public class EditeurAnimation extends Container{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ContainerWithBords apercuCont;
	private PanneauApercuAnimation apercu;
	
	private ScrollBar animExplorerscroll;
	private Container animExplorer;
	
	private BasicAnimationEditor basicAnimationEditor;
	private ObjetMap cible;
	
	private Button applyAnimation;
	public void update(GameContainer gc, int x, int y){
		super.update(gc, x, y);
		updateButtons();
		updateButtonsName();
	}
	public void updateButtonsName(){
		for(Animation anim : cible.getAnimations()){
			Button button = ((Button)animExplorer.getComponents().get(cible.getAnimations().indexOf(anim)));
			if(!button.getName().equals(anim.getNom())){
				button.setName(anim.getNom());
				button.setSizeX(anim.getNom().length() * 9 + 40);
			}
		}
	}
	public void updateButtons(){
		if(cible.getAnimations() != null){
			if(cible.getAnimations().size() > animExplorer.getComponents().size()){
				
				animExplorerscroll.setValueMax(60 +cible.getAnimations().size() * 45);
				animExplorer.setSizeY(60 +cible.getAnimations().size() * 45);
				for(int i = animExplorer.getComponents().size(); i < this.cible.getAnimations().size(); i++){
					Button modImg = null;
					modImg = new Button(cible.getAnimations().get(i).getNom(), animExplorer);
					modImg.getAction().add(new ActionListener(){
						public void actionPerformed(FComponent c){
							basicAnimationEditor.setAnimationToAdd(cible, cible.getAnimations().get((c.getY() - 5) / 45));
							((Button)c).setName(cible.getAnimations().get((c.getY() - 5) / 45).getNom());
							
						}
					});
					modImg.setX(5);
					modImg.setY(5 + 45 * (i));
					animExplorer.addComponent(modImg);
				}
			}
			else if(cible.getAnimations().size() < animExplorer.getComponents().size() && animExplorer.getComponents().size() > 0){
				animExplorer.getComponents().remove(animExplorer.getComponents().size() - 1);
				animExplorer.setSizeY(60 + cible.getAnimations().size() * 45);
				
			}
		}
	}
	public EditeurAnimation(ObjetMap o, int x, int y, int sizeX, int sizeY, Container parent) {
		super(x, y, sizeX, sizeY, parent);
		cible = o;
		apercuCont = new ContainerWithBords( 0,0, sizeX / 2, sizeY / 2, this);
			apercu = new PanneauApercuAnimation(new ChunkMap(1,1,1,1), cible, 10,10, apercuCont.getSizeX() - 20, apercuCont.getSizeY() - 20, apercuCont);
			apercu.setActualCam(new Camera(0,0, 1f, null));
			if(cible.getImage().size() != 0){
				apercu.setActualCam(new Camera(0, -cible.getImage().get(0).getImageSizeInGameY() / 2, 1f, null));
			}
			apercuCont.addComponent(apercu);
		this.addComponent(apercuCont);
		animExplorerscroll = new ScrollBar(0, sizeY / 2, sizeX / 2, sizeY / 2, 1, 0,this);
		animExplorer = new Container(0,0, sizeX / 4 - 10, sizeY / 4, animExplorerscroll);
		animExplorerscroll.setContainer(animExplorer);
		animExplorer.setBackground(new PImage("alpha.png")); //$NON-NLS-1$
		this.addComponent(animExplorerscroll);
		
		basicAnimationEditor = new BasicAnimationEditor(o, sizeX / 2, 0, sizeX / 2, sizeY, this);
		this.addComponent(basicAnimationEditor);
		
		applyAnimation = new Button(Messages.getString("EditeurAnimation.1"), 0,sizeY / 2 - sizeY / 16, sizeX / 2, sizeY / 16, this); //$NON-NLS-1$
		applyAnimation.getAction().add(new ActionListener(){
			public void actionPerformed(FComponent e){
				((Editeur)e.getRacine()).getEditeurObjet().getObjetCible().setAnimations(cible.getAnimations());
			}
		});
		this.addComponent(applyAnimation);
		
	}
	public void setObjetCible(ObjetMap o) {
		cible = o;
		apercu.setEditChoice(cible);
		basicAnimationEditor.setCible(o);
		
	}
}
