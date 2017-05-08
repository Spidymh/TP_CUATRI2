package es.ucm.fdi.tp.mvc;

public class BoardEvent {

	private int puntoX;
	private int puntoY;
	private int mouseButton;

	
	public BoardEvent (int x, int y, int mb){
		puntoX = x;
		puntoY = y;
		mouseButton = mb;
	}
	
	public int getX(){
		return puntoX;
	}
	
	public int getY(){
		return puntoY;
	}
	
	public int getMouseButton(){
		return mouseButton;
	}
	
}
