package ObjetMap;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;

public class Light {
	protected ArrayList<Polygon> polygons;
	protected int quality;
	protected int radius;
	protected float intensity;
	protected Color color;
	protected Color beginColor;
	protected Color endColor;
	public void generate()
	{
	    polygons.clear();

	    polygons = new ArrayList<Polygon>();
	    
	    float buf=(float) ((Math.PI*2f)/(float)quality);

	    for(int i=0; i < quality; ++i)
	    {
	       addTriangle(new Vector2f((float)((float)radius*Math.cos((float)i*buf))
	                                ,(float)((float)radius*Math.sin((float)i*buf))) ,
	                   new Vector2f((float)((float)radius*Math.cos((float)(i+1)*buf))
	                                ,(float)((float)radius*Math.sin((float)(i+1)*buf))));
	    }
	}

	public void addTriangle(Vector2f pt1, Vector2f pt2){
		float farIntensity;
		Polygon shape = new Polygon();
		shape.addPoint(0, 0);
		 Color base = new Color((int)(intensity*color.r/255),
                 (int)(intensity*color.g/255),
                 (int)(intensity*color.b/255));
	    farIntensity = (float) (intensity-Math.sqrt(pt1.x*pt1.x + pt1.y*pt1.y)*intensity/radius);
	    shape.addPoint(pt1.x, pt1.y);  
	    Color far = new Color((int)(farIntensity*color.r/255),
	                                                     (int)(farIntensity*color.g/255),
	                                                     (int)(farIntensity*color.b/255));

	 /*   farIntensity = (float) (intensity-Math.sqrt(pt2.x*pt2.x + pt2.y*pt2.y)*intensity/radius);
	    m_shape.back().AddPoint(pt2.x, pt2.y,  sf::Color((int)(farIntensity*m_color.r/255),
	                                                     (int)(farIntensity*m_color.g/255),
	                                                     (int)(farIntensity*m_color.b/255)),sf::Color(255,255,255));

	    m_shape.back().SetBlendMode(sf::Blend::Add);
	    m_shape.back().SetPosition(m_position);*/
	}
}
