package texts;

import java.util.ArrayList;

import observer.GameListener;

public class Balise  {
	
	protected int beginning, end;
	
	protected String name;
	
	protected GameListener action;
	
	protected ArrayList<Attribute> attributes;
	
	public Balise(String name, boolean alone){
		this.name = name;
		attributes = new ArrayList<Attribute>();
	}
	
	public Balise(String name, ArrayList<Attribute> attributes){
		this.name = name;
		this.attributes = attributes;
	}
	
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
	
}
