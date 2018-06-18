package es.unex.quercusseg.graphicalserver.model;


public class Concrete {

	private int width;
	private int height;
	
	
	public Concrete(int _width, int _height) {
		
		this.width  = _width;
		this.height = _height;
		
	}
	
	
	public int getWidth() {
		
		return width;
		
	}
	
	public void setWidth(int width) {
		
		this.width = width;
		
	}
	
	public int getHeight() {
		
		return height;
		
	}
	
	public void setHeight(int height) {
		
		this.height = height;
		
	}
	
	public String toString() {
		
		String concrete = "[width: " + this.width + ", height: " + this.height + "]";
		
		return concrete;
		
	}

}
