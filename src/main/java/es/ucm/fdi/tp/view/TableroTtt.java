package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.ttt.TttAction;
import es.ucm.fdi.tp.ttt.TttState;


@SuppressWarnings("rawtypes")
public class TableroTtt <S extends GameState<S, A>, A extends GameAction<S, A>> extends ModeloTablero{

	private final int JUGADOR1 = 0;
	private final int JUGADOR2 = 1;
	private int row;
	private int col;
	private int dim;
	private TttState estado;
	
	public TableroTtt(TttState tttState, int d){
		this.dim = d;
		this.row = dim;
		this.col = dim;
		this.estado=tttState;
	}
	
	@Override
	public int getRow() {
		return row;
	}

	@Override
	public int getCol() {
		return col;
	}

	@Override
	public GameAction generateAction(int posX, int posY) {
		switch (estado.getTurn()){
		case JUGADOR1: {
			if (estado.at(posX, posY) == null) return new TttAction(JUGADOR1, posX, posY);
		}break;
		case JUGADOR2: {
			if (estado.at(posX, posY) == null) return new TttAction(JUGADOR2, posX, posY);
		}
		}
		return null;
	}

	@Override
	public boolean getBloqueado() {
		return this.estado.isFinished();
	}

	@Override
	public Shape[] getFormasIniciales(int j) {	
		return new Shape[]{Shape.CIRCLE};
	}
	
}