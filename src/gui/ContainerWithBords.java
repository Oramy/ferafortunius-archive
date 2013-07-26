package gui;

import org.newdawn.slick.Graphics;

public class ContainerWithBords extends Container{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3733964443317051686L;
	public ContainerWithBords(int x, int y, int sizeX, int sizeY,
			Container parent) {
		super(x, y, sizeX, sizeY, parent);
		background = Container.normalBackground;
		
	}
	public void draw(Graphics g) {
		g.translate(this.getX(), this.getY());
			if(background != null){
				getBackground().getImg().startUse();
				
				getBackground().getImg().drawEmbedded(0, 0, 10,10, 0,0,10,10);
				getBackground().getImg().drawEmbedded(10, 0, this.getWidth() - 10,10, 10,0,background.getImg().getWidth() - 10,10);
				getBackground().getImg().drawEmbedded(this.getWidth() - 10, 0, this.getWidth(),10, background.getImg().getWidth() - 10,0,background.getImg().getWidth(),10);
				//Gauche
				getBackground().getImg().drawEmbedded(0, 10, 10,this.getHeight() - 10, 0,10,10,background.getImg().getHeight() - 10);
				//Droite
				getBackground().getImg().drawEmbedded(this.getWidth() - 10,  10,this.getWidth(),this.getHeight() - 10, background.getImg().getWidth() - 10,10,background.getImg().getWidth(),background.getImg().getHeight() - 10);
				//Bas
				getBackground().getImg().drawEmbedded(0, this.getHeight() - 10, 10, this.getHeight(), 0,background.getImg().getHeight() - 10,10,background.getImg().getHeight());
				getBackground().getImg().drawEmbedded(10, this.getHeight() - 10, this.getWidth() - 10, this.getHeight(), 10,background.getImg().getHeight() - 10,background.getImg().getWidth() - 10,background.getImg().getHeight());
				getBackground().getImg().drawEmbedded(this.getWidth() - 10, this.getHeight() - 10, this.getWidth(), this.getHeight(), background.getImg().getWidth() - 10,background.getImg().getHeight() - 10,background.getImg().getWidth(),background.getImg().getHeight());
				//Milieu
				getBackground().getImg().drawEmbedded(10, 10, this.getWidth() - 10,this.getHeight() - 10, 10,10,background.getImg().getWidth() - 10,background.getImg().getHeight() - 10);
				
				getBackground().getImg().endUse();
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
}
