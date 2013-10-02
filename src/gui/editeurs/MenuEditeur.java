package gui.editeurs;

import observer.ActionListener;

import org.newdawn.slick.GameContainer;

import gui.Container;
import gui.FComponent;
import gui.InternalFrame;
import gui.buttons.Button;

public class MenuEditeur extends Container{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EditeurMap editeur;
	private MenuEditeurBar barre;
	private Button newmap;
	private Button supprimer;
	public MenuEditeur(int x, int y, int sizeX, int sizeY, Container parent) {
		super(x, y, sizeX, sizeY, parent);
		editeur = null;
		if(((Editeur)(getRacine())) instanceof Editeur)
			editeur = ((Editeur)(getRacine())).getEditeurMap();
		setBarre(new MenuEditeurBar(0,0, sizeX, sizeY / 2, this));
		this.addComponent(getBarre());
		newmap = new Button(Messages.getString("MenuEditeur.0"), this); //$NON-NLS-1$
		newmap.setY(sizeY / 2);
		newmap.setSizeX(sizeX / 8);
		newmap.setSizeY(sizeY/2);
		newmap.getAction().add(new ActionListener(){
			public void actionPerformed(FComponent e){
				InternalFrame i = new InternalFrame(0, 0, 400, 200, Messages.getString("MenuEditeur.1"), editeur); //$NON-NLS-1$
				i.getContainer().addComponent(new NewMapContainer(0, 0,400,200, i.getContainer()));
				editeur.addComponent(i);
			}
		});
		this.addComponent(newmap);
		supprimer = new Button(Messages.getString("MenuEditeur.3"), this); //$NON-NLS-1$
		supprimer.setY(sizeY / 2);
		supprimer.setX(sizeX/8);
		supprimer.setSizeY(sizeY/2);
		supprimer.setSizeX(sizeX / 8);
		supprimer.getAction().add(new ActionListener(){
			public void actionPerformed(FComponent e){
				Button himself = (Button) e;
				editeur.setEditeurMode(EditeurMode.values()[(editeur.getEditeurMode().ordinal() + 1) % EditeurMode.values().length]);
			}
		});
		this.addComponent(supprimer);
		
	}
	@Override
	public void update(GameContainer gc, int x, int y){
		super.update(gc, x, y);
		if(editeur.getEditeurMode() == EditeurMode.Placer){
			supprimer.setName(Messages.getString("MenuEditeur.3")); //$NON-NLS-1$
		}
		else if(editeur.getEditeurMode() == EditeurMode.Ensemble)
		{
			supprimer.setName(Messages.getString("MenuEditeur.5")); //$NON-NLS-1$
		}
		else if(editeur.getEditeurMode() == EditeurMode.Supprimer)
		{
			supprimer.setName(Messages.getString("MenuEditeur.4")); //$NON-NLS-1$
		}
		else if(editeur.getEditeurMode() == EditeurMode.Selection)
		{
			supprimer.setName(Messages.getString("MenuEditeur.6")); //$NON-NLS-1$
		}
	}
	/**
	 * @return the barre
	 */
	public MenuEditeurBar getBarre() {
		return barre;
	}
	/**
	 * @param barre the barre to set
	 */
	public void setBarre(MenuEditeurBar barre) {
		this.barre = barre;
	}

}
