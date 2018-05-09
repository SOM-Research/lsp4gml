package es.unex.quercusseg.graphicalserver.model;


public class Diagram {

	
	private int x;
	private int y;
	private int z;
	

	public Diagram (int _x, int _y, int _z) {
		
		this.x = _x;
		this.y = _y;
		this.z = _z;
		
	}

	
	public void setX(int x) {
		
		this.x = x;
		
	}

	
	public void setY(int y) {
		
		this.y = y;
		
	}

	
	public void setZ(int z) {
		
		this.z = z;
		
	}

	public int getX() {
		
		return x;
		
	}

	public int getY() {
		
		return y;
		
	}


	public int getZ() {
		
		return z;
		
	}
	
	
	public String toString() {
		
		String diagram = "[x: " + this.x + ", y: " + this.y + ", z: " + this.z + "]";
		
		return diagram;
		
	}

}
