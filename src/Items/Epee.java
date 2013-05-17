package Items;

import ObjetMap.Entity;
import ObjetMap.ObjetImage;

public class Epee extends Arme {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6821542480292412328L;

	public Epee(Entity owner) {
		super(owner);
		this.weight = 25;
		setDescriptionPath("epee");
		this.img = new ObjetImage("Items/epeeFer.png");
		name = "Epée en fer";
		setDescription("Une épée qui suffira pour vaincre les premiers monstres.");
		
	}

}
