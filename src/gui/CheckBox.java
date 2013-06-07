package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;



public class CheckBox extends Button{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected boolean check;
	protected static final PImage normal = new PImage("GUI/checkBox.png");
	protected static final PImage active = new PImage("GUI/checkBoxActive.png");

	public CheckBox(String nom, Container parent) {
		super(nom, parent);
		boutonAct = normal;
		textColor = Color.black;
		check = false;
		setSizeY(25);
	}
	@Override
	public void normal(){
		if(!check)
			boutonAct = normal;
		if(check)
			boutonAct = active;
	}
	@Override
	public void hover(){

	}
	@Override
	public void clickReleased(){
		setCheck(!check);
		super.clickReleased();
	}
	public void draw(Graphics g) {

		//Vérification de la taille par rapport au texte
		if(this.getBounds().width - 40 < g.getFont().getWidth(getName())){
			this.setBounds(this.getBounds().x, this.getBounds().y, (int) g.getFont().getWidth(getName()) + 40, this.getBounds().height);
		}
		if(this.getBounds().height < g.getFont().getLineHeight()){
			this.setBounds(this.getBounds().x, this.getBounds().y, this.getBounds().width, (int) ((g.getFont().getLineHeight() * 2) * prop));
		}

		g.setColor(textColor);

		g.translate(this.getBounds().x, this.getBounds().y);
		//Dessin du bouton avec adaptation de l'image
		boutonAct.getImg().draw(5, getSizeY() / 2 - 10, 20,20);
		//Dessin du texte selon une proportion donnée.
		g.scale(prop, prop);

		g.drawString(getName(),  30,(this.getBounds().height) / 2 - ((g.getFont().getHeight(getName())/ 2)) - 2);

		g.scale(1/prop, 1/prop);

		g.translate(-this.getBounds().x, -this.getBounds().y);
	}
	/**
	 * @return the check
	 */
	public boolean isCheck() {
		return check;
	}

	/**
	 * @param check the check to set
	 */
	public void setCheck(boolean check) {
		this.check = check;
		super.clickReleased();
		if(!check)
			boutonAct = normal;
		if(check)
			boutonAct = active;
	}
}
