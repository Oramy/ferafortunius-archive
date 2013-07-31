package texts;

import java.util.ArrayList;

import observer.GameListener;

public class Balise  {
	
	protected String name;
	
	protected GameListener action;
	
	protected ArrayList<Attribute> attributes;
	
	//Builders
	public Balise(String name, boolean alone){
		this.name = name;
		attributes = new ArrayList<Attribute>();
	}
	
	public Balise(String name, ArrayList<Attribute> attributes){
		this.name = name;
		this.attributes = attributes;
	}
	
	/**
	 * 
	 * @param name name of the attribute
	 * @param value value of the attribute
	 */
	public void addAttribute(String name, String value){
		attributes.add(new Attribute(name, value));
	}
	public ArrayList<Attribute> getAttributes(){
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();
		for(Attribute att : this.attributes)
			attributes.add(att.clone());
		return attributes;
	}
	public String getName(){
		return name;
	}

	public Attribute getAttribute(String name) {
		for(Attribute a : attributes)
		{
			if(a.getName().equals(name))
				return a;
		}
		return null;
	}
	
}
