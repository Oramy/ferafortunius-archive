package observer;

import gui.jeu.Jeu;

public interface GameListener {
	/**
	 * Methode qui effectue l'action
	 * @param c le composant qui effectue l'action
	 */
	public abstract void actionPerformed(Jeu jeu);
}
