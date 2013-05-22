package gui.jeu;

import gui.Action;
import gui.Container;
import gui.FComponent;
import gui.HiddenButton;
import gui.ModeJeu;
import gui.PImage;

public class MenuJeuContainer extends Container{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3433903612615297834L;

	public MenuJeuContainer(int x, int y, int sizeX, int sizeY, Container parent) {
		super(x, y, sizeX, sizeY, parent);
		background = new PImage("alpha.png"); //$NON-NLS-1$
		HiddenButton inventaire = new HiddenButton(Messages.getString("MenuJeuContainer.1"), "GUI/Icon/inventaire.png", this); //$NON-NLS-1$ //$NON-NLS-2$
		inventaire.setY(0);
		//inventaire.disable();
		this.addComponent(inventaire);
		inventaire.getAction().add(new Action(){
			public void actionPerformed(FComponent c){
				Jeu jeu = (Jeu) c.getRacine();
				jeu.inverseInventory();
			}
		});
		HiddenButton equipement = new HiddenButton(Messages.getString("MenuJeuContainer.3"), "GUI/Icon/equipement.png", this); //$NON-NLS-1$ //$NON-NLS-2$
		equipement.setY(inventaire.getSizeY());
		equipement.disable();
		this.addComponent(equipement);
		HiddenButton addiction = new HiddenButton(Messages.getString("MenuJeuContainer.5"), "GUI/Icon/addiction.png", this); //$NON-NLS-1$ //$NON-NLS-2$
		addiction.setY(inventaire.getSizeY() * 2);
		addiction.disable();
		this.addComponent(addiction);
		HiddenButton boutique = new HiddenButton(Messages.getString("MenuJeuContainer.7"), "GUI/Icon/boutique.png", this); //$NON-NLS-1$ //$NON-NLS-2$
		boutique.setY(inventaire.getSizeY() * 3);
		boutique.disable();
		this.addComponent(boutique);
		HiddenButton options = new HiddenButton(Messages.getString("MenuJeuContainer.9"), "GUI/Icon/options.png", this); //$NON-NLS-1$ //$NON-NLS-2$
		options.setY(inventaire.getSizeY() * 4);
		this.addComponent(options);
		options.getAction().add(new Action(){
			public void actionPerformed(FComponent c){
				Jeu jeu = (Jeu) c.getRacine();
				jeu.addComponent(jeu.getOptionsFen());
			}
		});
		HiddenButton sauvegarder = new HiddenButton(Messages.getString("MenuJeuContainer.11"), "GUI/Icon/sauvegarder.png", this); //$NON-NLS-1$ //$NON-NLS-2$
		sauvegarder.setY(inventaire.getSizeY() * 5);
		sauvegarder.disable();
		this.addComponent(sauvegarder);
		HiddenButton quitter = new HiddenButton(Messages.getString("MenuJeuContainer.13"), "GUI/Icon/quitter.png", this); //$NON-NLS-1$ //$NON-NLS-2$
		quitter.setY(inventaire.getSizeY() * 6);
		//quitter.disable();
		quitter.getAction().add(new Action(){
			public void actionPerformed(FComponent c){
				Jeu jeu = (Jeu)getRacine();
				jeu.getGm().setMode(ModeJeu.Menu, jeu.getGm().getApp());
			}
		});
		this.addComponent(quitter);
	}

}
