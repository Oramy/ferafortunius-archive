package texts;

public class Attribute implements Cloneable {
	protected String name;
	
	protected String value;
	
	public Attribute clone(){
		Attribute clone = new Attribute(this.name, this.value);
		return clone;
	}
	public Attribute(String name, String value){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

}
