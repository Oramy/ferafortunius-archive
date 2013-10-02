package gui.jeu.utils;

import gui.jeu.PanneauJeuAmeliore;

import java.awt.Point;

import Level.Camera;

public class Isometric {
	private static float getXOnScreen(float isometricX, float isometricY, PanneauJeuAmeliore pan, Camera cam){
		float xcam =  (cam.getX() * cam.getZoom());
		
		float x = ((isometricX - isometricY) * cam.getZoom());
		x  += pan.getX();
		x  -= xcam;
		x  += pan.getSizeX() / 2;
		
		return x;
		
	}
	private static float getYOnScreen(float isometricX, float isometricY,  float isometricZ, PanneauJeuAmeliore pan, Camera cam){
		float ycam =  (cam.getY() * cam.getZoom());
		
		float y = (int) ((-isometricX - isometricY) * cam.getZoom() * 0.5f);
		y -= isometricZ * cam.getZoom();
		y -= pan.getY();
		y -= ycam;
		y += pan.getSizeY() / 2;
		
		return y;
		
	}
	public static Point worldToScreen(Point p, float z, PanneauJeuAmeliore pan, Camera cam){
		Point p2 = new Point();
		p2.x = (int) getXOnScreen(p.x, p.y, pan, cam);
		p2.y =  (int)getYOnScreen(p.x, p.y, z,pan, cam);
		return p2;
	}
}
