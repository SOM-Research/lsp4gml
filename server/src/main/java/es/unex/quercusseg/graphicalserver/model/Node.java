package es.unex.quercusseg.graphicalserver.model;


import java.util.List;


public class Node {

	
	private int     	id;
	private String  	type;
	private List <Pair> abstractProperties;
	private Concrete    concrete;
	private Diagram     diagram;
	private Boolean     movable;
	private Boolean     editable;
	
	
	public Boolean getMovable() {
		
		return movable;
		
	}


	public void setMovable(Boolean movable) {
		
		this.movable = movable;
		
	}


	public Boolean getEditable() {
		
		return editable;
		
	}


	public void setEditable(Boolean editable) {
		
		this.editable = editable;
		
	}


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
	
	
	public void setConcrete(Concrete _concrete) {
		
		this.concrete = _concrete;
		
	}
	
	
	public Concrete getConcrete() {
		
		return this.concrete;
		
	}
	
	
	public String toString () {
		
		String toString = "";
		
		toString = toString + "Node " 		 + this.id 						      + "\n";
		toString = toString	+ " id: "        + this.id   						  + "\n";
		toString = toString + " type: "      + this.type 						  + "\n";
		toString = toString + " abstract: (" + this.abstractProperties.toString() + ")\n";
		toString = toString + " concrete: (" + this.concrete.toString()           + ")\n";
		toString = toString + " diagram:  (" + this.diagram.toString()            + ")\n";
		toString = toString + " movable: "   + this.movable 					  + "\n";
		toString = toString + " editable: "  + this.editable 					  + "\n";
		
		return toString;
		
	}

	
}
