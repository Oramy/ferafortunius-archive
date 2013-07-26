package observer;

import gui.FComponent;

public interface ActionListener {
	/**
	 * Methode qui effectue l'action
	 * @param c le composant qui effectue l'action
	 */
	public abstract void actionPerformed(FComponent c);
}
