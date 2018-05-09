package es.unex.quercusseg.graphicalserver.model;

import java.util.List;

public class Edge {

	
	private int     	id;
	private String  	type;
	private String 	    origin;
	private String 	    target;
	private List <Pair> abstractProperties;

	
	public void setId(int _id) {
		
		this.id = _id;
	
	}

	
	public void setType(String _type) {
		
		this.type = _type;
		
	}

	
	public void setOrigin(String origin) {
		
		this.origin = origin;
		
	}
	
	
	public void setTarget(String target) {
		
		this.target = target;
		
	}

	
	public int getId() {
		
		return id;
		
	}


	public String getType() {
		
		return type;
		
	}

	
	public String getOrigin() {
		
		return origin;
		
	}

	
	public String getTarget() {
		
		return target;
		
	}

	
	public void setAbstractProperties(List <Pair> abstractProperties) {
		
		this.abstractProperties = abstractProperties;
		
	}

	
	public List <Pair> getAbstractProperties() {
		
		return abstractProperties;
		
	}

	
	public String toString () {
		
		String toString = "";
		
		toString = toString + "Edge " 		 + this.id 						      + "\n";
		toString = toString	+ " id: "        + this.id   						  + "\n";
		toString = toString + " type: "      + this.type 						  + "\n";
		toString = toString + " origin: "    + this.origin 						  + "\n";
		toString = toString + " target: "    + this.target 						  + "\n";
		toString = toString + " abstract: (" + this.abstractProperties.toString() + ")\n";
	
		return toString;
		
	}

}
