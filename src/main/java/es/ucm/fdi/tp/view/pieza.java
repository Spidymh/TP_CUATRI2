package es.ucm.fdi.tp.view;

public class pieza {
	private Shape forma;
	private int jugador;
	private boolean selected;
	private boolean soloCirculo=false;
	
	public pieza (Shape f, int j){
		this.forma = f;
		this.jugador = j;
	}
	
	public Shape getShape(){
		return this.forma;
	}
	
	public boolean getSoloCirculo(){
		return this.soloCirculo;
	}
	
	public void setSelected(){
		this.selected = !this.selected;
	}
	
	public boolean getSelected(){
		return this.selected;
	}
	
	public int getJugador(){
		return this.jugador;
	}
}
