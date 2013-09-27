package maths;

import org.newdawn.slick.geom.Vector2f;

public class Trigonometry {
	public static float getXAxisAngle(Vector2f point1, Vector2f point2){
		float distance = point1.distance(point2);
		
		double xDistance = point2.x - point1.x;
		double yDistance = point2.y - point1.y;
		
		
		float angle = (float) (Math.acos(xDistance / distance) * 180 / Math.PI);
		
		if(yDistance > 0)
			angle = 360 - angle;
			
		return angle;
	}
	public static float getXAxisAngle(float x1, float y1, float x2, float y2){
			
		return getXAxisAngle(new Vector2f(x1, y1), new Vector2f(x2, y2));
	}
	
	public static float getYAxisAngle(Vector2f point1, Vector2f point2){
		float distance = point1.distance(point2);
		
		double xDistance = point2.x - point1.x;
		double yDistance = point2.y - point1.y;
		
		
		float angle = (float) (Math.acos(yDistance / distance) * 180 / Math.PI);
		
		if(xDistance > 0)
			angle = 360 - angle;
			
		return angle;
	}
}
