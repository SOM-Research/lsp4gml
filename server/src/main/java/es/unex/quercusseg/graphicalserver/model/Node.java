package es.unex.quercusseg.graphicalserver.model;


import java.util.List;


public class Node {

	
	private int     	id;
	private String  	type;
	private List <Pair> abstractProperties;
	private Diagram     diagram;
	
	
	public void setId(int _id) {
		
		this.id = _id;
	
	}

	
	public void setType(String _type) {
		
		this.type = _type;
		
	}

	
	public int getId() {
		
		return id;
		
	}


	public String getType() {
		
		return type;
		
	}

	
	public void setAbstractProperties(List <Pair> abstractProperties) {
		
		this.abstractProperties = abstractProperties;
		
	}

	
	public List <Pair> getAbstractProperties() {
		
		return abstractProperties;
		
	}


	public void setDiagram(Diagram _diagram) {
		
		this.diagram = _diagram;
		
	}
	
	
	public Diagram getDiagram() {
		
		return this.diagram;
		
	}
	
	
	public String toString () {
		
		String toString = "";
		
		toString = toString + "Node " 		 + this.id 						      + "\n";
		toString = toString	+ " id: "        + this.id   						  + "\n";
		toString = toString + " type: "      + this.type 						  + "\n";
		toString = toString + " abstract: (" + this.abstractProperties.toString() + ")\n";
		toString = toString + " diagram:  (" + this.diagram.toString()            + ")\n";
		
		return toString;
		
	}

	
}
