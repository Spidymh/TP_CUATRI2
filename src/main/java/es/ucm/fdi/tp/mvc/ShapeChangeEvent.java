package es.ucm.fdi.tp.mvc;

import es.ucm.fdi.tp.view.Shape;

public class ShapeChangeEvent {
	/**
	 * La forma a la que se quiere cambiar
	 */
	Shape nuevaForma;
	/**
	 * El jugador al que afectara el cambio
	 */
	int jug;
	/**
	 * Hemos considerado que hay juegos en los que un jugador tiene piezas con varias formas, como el ajedrez
	 */
	int idDeForma;
	boolean solocirculo;
	public ShapeChangeEvent(Shape forma, int id1, int id2, boolean soloCirculo){
		nuevaForma=forma;
		jug=id1;
		idDeForma=id2;
		solocirculo=soloCirculo;
	}
	
	public Shape getNuevaForma(){
		return this.nuevaForma;
	}
	
	public int getIdDeForma(){
		return this.idDeForma;
	}
	
	public int getJug(){
		return this.jug;
	}
}
