package gui.jeu;

import java.awt.Point;

public class Isometric {
	public static Point screenToWorld(Point p, int width, int height){
		Point p2 = new Point();
		
		p2.y = (+ p.y  + p.x) / 2;
		p2.x = - (p.y - p.x);
		
		p2.x -= width / 2;
		p2.y -= height / 2;
		return p2;
	}
	public static Point worldToScreen(Point p){
		Point p2 = new Point();
		p2.x = p.x - p.y;
		p2.y = (- p.x - p.y) / 2;
		return p2;
	}
}
