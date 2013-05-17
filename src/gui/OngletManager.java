package gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class OngletManager extends Container{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Onglet ongletActuel;
	private Container toDraw;
	public OngletManager(int x, int y, int sizeX, int sizeY, Container parent) {
		super(x, y, sizeX, sizeY, parent);
		setOngletActuel(null);
		this.setActualLayout(new OngletManagerLayout());
		this.background = new PImage("GUI/containerBackgroundwithoutBords.png");
		
	}
	public void update(GameContainer gc, int x, int y)
	{
		if(ongletActuel == null)
			ongletActuel = (Onglet) this.components.get(0);
		if(toDraw != null)
		{
			if(!toDraw.equals(ongletActuel.getContainer())){
				this.components.remove(toDraw);
				toDraw = ongletActuel.getContainer();
				this.components.add(toDraw);
			}
		}
		else{
			toDraw = ongletActuel.getContainer();
			this.components.add(toDraw);
		}
		super.update(gc, x, y);
		
	}
	public void draw(Graphics g) {
		g.translate(this.getX(), this.getY());
			if(background != null){
				getBackground().getImg().draw(0, 0, this.getWidth(), 50);
			}
		if(getComponents().size() != 0)
		{

			for(int i = 0; i < getComponents().size(); i++){
				
				getComponents().get(i).drawBegin(g);
				getComponents().get(i).draw(g);
				getComponents().get(i).drawEnd(g);
				
			}
				
		}
		g.translate(-this.getX(), -this.getY());
	}
	/**
	 * @return the ongletActuel
	 */
	public Onglet getOngletActuel() {
		return ongletActuel;
	}
	/**
	 * @param ongletActuel the ongletActuel to set
	 */
	public void setOngletActuel(Onglet ongletActuel) {
		this.ongletActuel = ongletActuel;
	}

}
