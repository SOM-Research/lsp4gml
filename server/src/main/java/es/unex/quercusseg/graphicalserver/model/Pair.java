package es.unex.quercusseg.graphicalserver.model;


public class Pair {

	
	private String name;
	private String value;
	
		
	public Pair(String _key, String _value) {
		
		this.name  = _key;
		this.value = _value;
		
	}


	public void setName(String _name) {
		
		this.name = _name;
		
	}
	
	
	public void setValue(String _value) {
		
		this.value = _value;
		
	}
	
	
	public String getName() {
		
		return this.name;
		
	}
	
	public String getValue() {
		
		return this.value;
		
	}

		
	public String toString() {
		
		String toString = "name: " + this.name + ", value: " + this.value;
		
		return toString;
		
	}
	
}
