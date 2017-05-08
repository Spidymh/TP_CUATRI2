 package es.ucm.fdi.tp.was;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.view.pieza;

/**
 * An action for TickTackToe.
 */
public class WasAction implements GameAction<WasState, WasAction> {

	private static final long serialVersionUID = -1889325373508412883L;
	
	private boolean movimientovacio;
	private int player;
    private int movimientoX;
    private int movimientoY;
    private int posPiezaX;//coordenadas de la ficha que qeuremos mover.
    private int posPiezaY;
    
    public boolean getVacia(){
    	return this.movimientovacio;
    }

    /**
     * Constructor que crea una accion vacia, en la que no se produce ningun movimiento.
     */
    public WasAction(){
    	this.movimientovacio=true;
    	this.movimientoX=0;
    	this.movimientoY=0;
    	this.posPiezaX=0;
    	this.posPiezaY=0;
    	this.player=1;
    }
    /**
     * 
     * @param player Turno
     * @param movimientoX FilaDestino
     * @param movimientoY ColumnaDestino
     * @param posFichaX FilaOrigen
     * @param posFichaY ColumnaOrigen
     */
    public WasAction(int player, int movimientoX, int movimientoY,int posFichaX, int posFichaY) {
        this.player = player;
        this.movimientoX = movimientoX;
        this.movimientoY = movimientoY;
        this.posPiezaX = posFichaX;
        this.posPiezaY = posFichaY;
        this.movimientovacio=false;
    }

    public int getPlayerNumber() {
        return player;
    }

    /**
     * Aplica una accion a un estado
     * @param state Estado anterior.
     * @return Proximo estado.
     */
    
    //ACABAR APPLYTO
    public WasState applyTo(WasState state) {
        if (player != state.getTurn()) {
            throw new IllegalArgumentException("Not the turn of this player");
        }

        // make move
        pieza[][] board = state.getBoard();
        if(!movimientovacio && board[posPiezaX][posPiezaY] != null && board[movimientoX][movimientoY] != null)
        	throw new IllegalArgumentException("Not valid movement");
        else {
        if( !this.movimientovacio && board[posPiezaX][posPiezaY].getJugador() != player)
        	throw new IllegalArgumentException("Not valid movement");
        else if(!movimientovacio){
        pieza aux = board[movimientoX][movimientoY];
        board[movimientoX][movimientoY] = board[posPiezaX][posPiezaY];
        board[posPiezaX][posPiezaY] = aux;
        	}
        }
        // update state
        WasState next = new WasState(state, board, false, -1);
        if (next.isWinner())next = new WasState(state, board, true, state.getTurn());
        

        //no hay empates.
        return next;
    }

    /**
     * 
     * @return FilaDestino
     */
    public int getmovimientoX() {
        return movimientoX;
    }

    /**
     * 
     * @return ColumnaDestino
     */
    public int getmovimientoY() {
        return movimientoY;
    }
    
    /**
     * 
     * @return FilaOrigen
     */
    public int getposFichaX() {
        return posPiezaX;
    }

    /**
     * 
     * @return ColumnaOrigen
     */
    public int getposFichaY() {
        return posPiezaY;
    }
    

    public String toString() {
    	StringBuilder sb=new StringBuilder();
    	if(!movimientovacio){
sb.append("Place ");
    			switch(player){
    			case 0:sb.append('O');break;
    			case 1:sb.append('X');break;
    			}
    			sb.append(" from (");
    			sb.append(this.posPiezaX +", "+this.posPiezaY+") to ");
        		sb.append("(" + movimientoX + ", " + movimientoY + ")");
        		return sb.toString();
        		}
    	else{
    		return "Pasar turno";
    	}
    }

}
