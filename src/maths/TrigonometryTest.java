package maths;

import static org.junit.Assert.*;

import org.junit.Test;
import org.newdawn.slick.geom.Vector2f;

public class TrigonometryTest {

	@Test
	public void testGetXAxisAngle() {
		Vector2f a = new Vector2f(300,300);
		Vector2f b = new Vector2f(400,300);
		assertEquals("Fail : A(300;300), B(400;300)", 0, Trigonometry.getXAxisAngle(a,b), 0);
		
		a = new Vector2f(300,300);
		b = new Vector2f(300, 200);
		assertEquals("Fail : A(300;300), B(300;200)", 90, Trigonometry.getXAxisAngle(a,b), 0);
		
		a = new Vector2f(300,300);
		b = new Vector2f(300, 400);
		assertEquals("Fail : A(300;300), B(300;400)", 270, Trigonometry.getXAxisAngle(a,b), 0);
		
		a = new Vector2f(300,300);
		b = new Vector2f(200, 300);
		assertEquals("Fail : A(300;300), B(200;400)", 180, Trigonometry.getXAxisAngle(a,b), 0);
	}
	
	@Test
	public void testGetYAxisAngle() {
		Vector2f a = new Vector2f(300,300);
		Vector2f b = new Vector2f(300,400);
		assertEquals("Fail : A(300;300), B(300;400)", 0, Trigonometry.getYAxisAngle(a,b), 0);
		
		a = new Vector2f(300,300);
		b = new Vector2f(200, 300);
		assertEquals("Fail : A(300;300), B(200;300)", 90, Trigonometry.getYAxisAngle(a,b), 0);
		
		a = new Vector2f(300,300);
		b = new Vector2f(400, 300);
		assertEquals("Fail : A(300;300), B(400;300)", 270, Trigonometry.getYAxisAngle(a,b), 0);
		
		a = new Vector2f(300,300);
		b = new Vector2f(300, 200);
		assertEquals("Fail : A(300;300), B(300;200)", 180, Trigonometry.getYAxisAngle(a,b), 0);
	}
	

}
