package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class ControllerDetection extends FComponent {

	public static final PImage xBox = new PImage("GUI/Controllers/xBoxAvailable.jpg");
	public static final PImage ps3 = new PImage("GUI/Controllers/ps3Available.jpg");
	
	public PImage actual;
	public ControllerDetection(GameContainer gc, Container parent) {
		super(parent);
		updateSize();
		if(ControllersManager.hasController(gc) && ControllersManager.isXBox(gc))
			actual = xBox;
		else if(ControllersManager.hasController(gc))
			actual = ps3;
	}
	@Override
	public void draw(Graphics g){
		g.translate(x, y);
		int actualX = 0;
		g.setColor(Color.black);
		g.setFont(FontRessources.getFonts().text);
		if(actual != null){
			g.drawImage(actual.getImg(), actualX,0);
			actualX += actual.getImg().getWidth() + 10;
			if(actual.equals(xBox))
				g.drawString("Manette XBOX disponible.", actualX, sizeY / 2 - g.getFont().getLineHeight() / 2);
			else
				g.drawString("Manette PS3 disponible.", actualX, sizeY / 2 - g.getFont().getLineHeight() / 2);
		}else
			g.drawString("Les exp�riences de jeu � la manette sont plus agr�ables.", actualX, sizeY / 2 - g.getFont().getLineHeight() / 2);
		g.translate(-x, -y);
		
	}
	@Override 
	public void update(GameContainer gc, int x, int y){
		super.update(gc, x, y);
		if(ControllersManager.hasController(gc) && ControllersManager.isXBox(gc))
			actual = xBox;
		else if(ControllersManager.hasController(gc))
			actual = ps3;
	}
	@Override
	public void updateSize() {
		this.sizeX = 600;
		this.sizeY = 50;
		this.y = this.getParent().getHeight() - sizeY;
		this.x = 0;
	}

}
