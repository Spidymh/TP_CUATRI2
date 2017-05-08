package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;


public abstract class ModeloTablero <S extends GameState<S, A>, A extends GameAction<S, A>>{

	protected int row;
	protected int col;
	protected int turno;
	protected boolean piezasSeleccionables=false;
	protected boolean bloqueado=false;
	protected static Shape[] formasPorDefecto;
	public ModeloTablero(){}

	public int getTurno(){
		return this.turno;
	}
	
	/**
	 * Devuelve un Array de formas correspondientes a la representacion inicial del tablero,
	 * a diferencia de los colores, inicialmente estas seran iguales y no se generaran aleatoriamente.
	 * @param j identificador del jugador
	 * @return 
	 */
	public abstract Shape[] getFormasIniciales(int j);
	
	public abstract boolean getBloqueado();
	
	public abstract int getRow();
	
	public abstract int getCol();

	public abstract A generateAction(int posX, int posY);
	
}
