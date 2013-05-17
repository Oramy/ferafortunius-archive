package gui.editeurs;

import gui.Action;
import gui.Button;
import gui.Container;
import gui.FComponent;
import gui.InternalFrame;

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
		setBarre(new MenuEditeurBar(0,0, sizeX, sizeY / 8, this));
		this.addComponent(getBarre());
		newmap = new Button(Messages.getString("MenuEditeur.0"), this); //$NON-NLS-1$
		newmap.setY(sizeY / 8);
		newmap.setSizeX(sizeX / 8);
		newmap.getAction().add(new Action(){
			public void actionPerformed(FComponent e){
				InternalFrame i = new InternalFrame(0, 0, 400, 200, Messages.getString("MenuEditeur.1"), editeur); //$NON-NLS-1$
				i.getContainer().addComponent(new NewMapContainer(0, 0,400,200, i.getContainer()));
				editeur.addComponent(i);
			}
		});
		this.addComponent(newmap);
		supprimer = new Button(Messages.getString("MenuEditeur.3"), this); //$NON-NLS-1$
		supprimer.setY(sizeY / 8);
		supprimer.setX(sizeX/8);
		supprimer.setSizeX(sizeX / 8);
		supprimer.getAction().add(new Action(){
			public void actionPerformed(FComponent e){
				Button himself = (Button) e;
				if(editeur.getEditeurMode() == EditeurMode.Placer){
					himself.setName(Messages.getString("MenuEditeur.5")); //$NON-NLS-1$
					editeur.setEditeurMode(EditeurMode.Ensemble);
				}
				else if(editeur.getEditeurMode() == EditeurMode.Ensemble)
				{
					himself.setName(Messages.getString("MenuEditeur.4")); //$NON-NLS-1$
					editeur.setEditeurMode(EditeurMode.Supprimer);
				}
				else if(editeur.getEditeurMode() == EditeurMode.Supprimer)
				{
					himself.setName(Messages.getString("MenuEditeur.3")); //$NON-NLS-1$
					editeur.setEditeurMode(EditeurMode.Placer);
				}
			}
		});
		this.addComponent(supprimer);
		
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
