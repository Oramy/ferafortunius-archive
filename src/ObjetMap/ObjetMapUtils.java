package ObjetMap;

public class ObjetMapUtils {
	public static synchronized boolean acceptableX(ObjetMap objet, ObjetMap objet2) {
		boolean accepted = true;
		if (objet.getPosX() >= objet2.getPosX() && objet.getPosX() <= objet2.getPosX() + objet2.getSizeX())
			accepted = false;
		if (objet2.getPosX() >= objet.getPosX() && objet2.getPosX() <= objet.getPosX() + objet.getSizeX())
			accepted = false;

		return accepted;
	}

	public static synchronized boolean acceptableY(ObjetMap objet, ObjetMap objet2) {
		boolean accepted = true;
		if (objet.getPosY() >= objet2.getPosY() && objet.getPosY() <= objet2.getPosY() + objet2.getSizeY())
			accepted = false;
		if (objet2.getPosY() >= objet.getPosY() && objet2.getPosY() <= objet.getPosY() + objet.getSizeY())
			accepted = false;
		return accepted;
	}

	public static synchronized boolean acceptableZ(ObjetMap objet, ObjetMap objet2) {
		boolean accepted = true;
		if (objet.getPosZ() >= objet2.getPosZ() && objet.getPosZ() <= objet2.getPosZ() + objet2.getSizeZ())
			accepted = false;
		if (objet2.getPosZ() >= objet.getPosZ() && objet2.getPosZ() <= objet.getPosZ() + objet.getSizeZ())
			accepted = false;

		return accepted;
	}
	public static synchronized boolean acceptableXYZ(ObjetMap objet, ObjetMap objet2) {
		if (!acceptableX(objet, objet2) && !acceptableY(objet, objet2) && !acceptableZ(objet, objet2))
			return true;
		return false;
	}
		

}
