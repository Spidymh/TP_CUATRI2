package es.ucm.fdi.tp.was;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.view.Shape;
import es.ucm.fdi.tp.view.pieza;
import es.ucm.fdi.tp.view.punto;

/**
 * A TickTackToe state.
 * Describes a board of TickTackToe that is either being
 * played or is already finished.
 */
public class WasState extends GameState<WasState, WasAction> {

	private static final long serialVersionUID = 3763370013431494216L;
	
	private final int turn;
    private final boolean finished;
    private final pieza[][] board;
    private final int winner;
    
    //0 para el jugador del lobo, 1 para el jugador de las ovejas
    private static final int LOBO = 0;
    private static final int OVEJA = 1;
    private static final int DIM=8;
    private static final int NUM_DIREC = 4;
    private static final int dirs[][] ={{-1,-1},{-1, 1},{1,1},{1,-1}};
   

/**
 * Constructor por defecto, que crea el estado inicial del juego.
 */
    public WasState() {    	
        super(2); //2 es el numero de jugadores

        board = new pieza[DIM][];
        for (int i=0; i<DIM; i++) {
            board[i] = new pieza[DIM];
            for (int j=0; j<DIM; j++) board[i][j] = null;
        }
        board[7][0] = new pieza(Shape.BKNIGHT, LOBO);
        board[0][1] = new pieza(Shape.WKNIGHT, OVEJA);
        board[0][3] = new pieza(Shape.WKNIGHT, OVEJA);
        board[0][5] = new pieza(Shape.WKNIGHT, OVEJA);
        board[0][7] = new pieza(Shape.WKNIGHT, OVEJA);
        
        this.turn = LOBO;
        this.winner = -1;
        this.finished = false;
    }
        
    /**
     * Dada una fila yuna columna, rodea al lobo de ovejas.
     * Usamos este constructor e las pruebas.
     * @param loboX Fila del lobo
     * @param loboY Columna del lobo
     */
    public WasState(int loboX, int loboY){
    	super(2);
    	this.finished=false;
    	this.winner=-1;
    	this.turn=0;
    	pieza[][] board=new pieza[DIM][DIM];
    	for(int i=0;i<DIM;i++){
    		for(int j=0;j<DIM;j++){
    			board[i][j]=null;
    		}
    	}
		board[loboX][loboY] = new pieza(Shape.BKNIGHT, LOBO);
    	for(int ctrl=0;ctrl<dirs.length;ctrl++){
    		if(inRange(loboX+dirs[ctrl][0],loboY+dirs[ctrl][1]))
    			board[loboX+dirs[ctrl][0]][loboY+dirs[ctrl][1]] = new pieza(Shape.WKNIGHT, OVEJA);
    	}
    	this.board=board;
    	}
    
    /**
     * Actualiza el estado del tablero tras realizarse una jugada.
     * @param prev Estado anterior
     * @param board Tablero al que queremos actualizar.
     * @param finished 
     * @param winner
     */
    public WasState(WasState prev, pieza[][] board, boolean finished, int winner) {
    	super(2);
        this.board = board;
        this.turn = (prev.turn + 1) % 2;
        this.finished = finished;
        this.winner = winner;
    }    

    

    public WasState(pieza[][] board, boolean finished, int winner, int turn) {
    	super(2);
        this.board = board;
        this.turn=turn;
        this.finished = finished;
        this.winner = winner;
    }  
    
    /**
     * Comprueba que la casilla de destino de una accion sea valida.
     * @param action 
     * @param direccion 0: NE; 1: NW; 2: SW; 3:SE
     * @return True si el destino es valido, False en otro caso
     */
    private boolean DestinoValido(WasAction action, int direccion){
		return(action.getmovimientoX() == action.getposFichaX() + dirs[direccion][0]
 			   && action.getmovimientoY() == action.getposFichaY() + dirs[direccion][1]
 			   && this.at(action.getmovimientoX(), action.getmovimientoY()) == null
 			   && action.getmovimientoX()< DIM && action.getmovimientoY()<DIM
 			   && action.getmovimientoX()>= 0 && action.getmovimientoY() >= 0);
    }
    
    
    /**
     * Comprueba si una accion es valida
     * @param action
     * @return True si es valida, False en otro caso.
     */
    public boolean isValid(WasAction action) {//Comprueba si la accion es valida
        if (isFinished()) {//Si el juego ha terminado no hay nuinguna accion valida
            return false;
        }
        else{
        	if(this.at(action.getposFichaX(), action.getposFichaY()) == null) return false;
        	else{
        		switch(this.at(action.getposFichaX(), action.getposFichaY()).getJugador()){
        		case LOBO:{
        			for(int direccion = 0; direccion < NUM_DIREC; ++direccion){
        				if (DestinoValido(action, direccion)) return true;
        			}
        			return false;
        		}
        		case OVEJA:{
        			for(int direccion = 2; direccion < NUM_DIREC; ++direccion){
        				if (DestinoValido(action, direccion)) return true;
        			}
        		return false;
        		}
        		}
        		return false;
        	}
        }
    }

    /**
     * Obtiene una lista con todos los posibles movimientos del jugador al que le toca.
     * @param playerNumber 0:LOBO; 1:OVEJA
     * @return La lista de acciones validas del jugador.
     */
    public List<WasAction> validActions(int playerNumber) {
        ArrayList<WasAction> valid = new ArrayList<>();
        if (finished) {
            return valid;
        }

        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
            	switch(playerNumber){
            	case LOBO://Es el lobo
            	{
            		if(this.at(i,j) != null && this.at(i, j).getJugador() == LOBO){
            			for (int k = 0; k < NUM_DIREC; ++k){
            				WasAction act = new WasAction(0,i+dirs[k][0], j + dirs[k][1], i,j);
            				if(isValid(act)) valid.add(act);
            			}
            		}
            	}break;
            	case OVEJA: //Ovejas
            	{
            		if(this.at(i,j) != null && this.at(i, j).getJugador() == OVEJA){
            			for (int k = 2; k < NUM_DIREC; ++k){
            				WasAction act = new WasAction(1,i+dirs[k][0], j + dirs[k][1], i,j);
            				if(isValid(act)) valid.add(act);
            			}
            		}
            	}break;
            	}
            }
        }
        if(valid.size()==0 && playerNumber==OVEJA){
        	valid.add(new WasAction());//accion vacia
        }
        return valid;
    }

    /**
     * Nos Permite consultar si el juego tiene ganador.
     * 
     * Si acaba de mover el lobo y es el turno de la oveja se comprobara si el lobo
     * ha llegado a la fila de arriba si acaban de mover las ovejas se comprueba si el lobo
     * esta rodeado tras el ultimo movimiento
     * @return True si el juego ha terminado, False en otro caso.
     */
    public boolean isWinner() {
    	switch (turn){
    	case OVEJA:{//OVEJA
    		for(int i = 0; i < DIM/2; ++i)
    			if(board[0][2*i+1] != null && board[0][2*i+1].getJugador() == LOBO) return true;
    	}break;
    	case LOBO:{//LOBO
             List<WasAction> accionesdellobo=validActions(0);
             return accionesdellobo.size()==0;
    	}
    	}
    	return false;
    }

    /**
     * Dadas unas coordenadas comprueba que se pueden meter en el tablero.
     * 
     * @param x Fila
     * @param y Columna
     * @return True si la casilla esta en rango, False en otro caso.
     */
    private static boolean inRange(int x, int y){
    	return(x>=0 && y>=0 && x<DIM && y<DIM);
    }

    /**
     * Dadas unas coordenadas, comprueba lo que hay en ellas.
     * @param movimientoX Fila
     * @param movimientoY Columna
     * @return El contenido del tablero en las coordenadas indicadas.
     */
    public pieza at(int movimientoX, int movimientoY) {
    	if(inRange(movimientoX,movimientoY)) return board[movimientoX][movimientoY];
    	else return null;//devolvemos null sino esta en rango.
    }

    /**
     * Nos permite obtener de quien es el turno.
     * @return 1 si es el turno de la oveja, 0 si es el del lobo
     */
    public int getTurn() {
        return turn;
    }

    /**
     * Nos premite saber si la partida ha terminado.
     * @return True si el juego ha terminado, False en otro caso.
     */
    public boolean isFinished() {
        return finished;
    }
    
    /**
     * Si el juego ha terminado, nos permite saber el ganador.
     * @return el jugador que ha ganado, segun el turno.
     */
    public int getWinner() {
        return winner;
    }
    
    public punto buscaLobo(){
		for (int i = 0; i < DIM; ++i)
			for(int j = 0; j < DIM; ++j)
				if(board[i][j] != null && board[i][j].getJugador() == LOBO)
					return new punto(i, j);
		return null;
			
    }
    
    public ArrayList<punto> buscaOvejas(){
    	ArrayList<punto> lista = new ArrayList<punto>();
		for (int i = 0; i < DIM; ++i)
			for(int j = 0; j < DIM; ++j)
				if(board[i][j] != null && board[i][j].getJugador() == OVEJA){
					lista.add(new punto(i,j));
				}
		return lista;
    }


    /**
     * @return a copy of the board
     */
    public pieza[][] getBoard() {
        pieza[][] copy = new pieza[board.length][];
       //System.arraycopy(board, srcPos, dest, destPos, length);
        for (int i=0; i<board.length; i++) copy[i] = board[i].clone();
        return copy;
    }
    
	@Override
	public pieza getPiezaAt(int x, int y) {
		return this.at(x,y);
		
	}
	
    /**
     * Esto se podria hacer mejor si las piezas fuesen enteros a secas
     * @param player
     * @param nuevaPieza
     */
	public WasState cambiarPiezas(int player, Shape nuevaPieza){
		pieza [][]nuevoTablero=new pieza[DIM][DIM];
		for(int i=0;i<DIM;i++)
			for(int j=0;j<DIM;j++){
				if(board[i][j].getJugador() == player)	
					nuevoTablero[i][j]=new pieza(nuevaPieza, board[i][j].getJugador());
				else if(board[i][j]==null)
					nuevoTablero[i][j]=null;
				else 
					board[i][j]=nuevoTablero[i][j];
			}
		return new WasState(nuevoTablero, finished, winner, turn);
	}
	
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("  ");
        for(int ctrl=0;ctrl<board.length;ctrl++){
        	sb.append("  "+ ctrl +" ");
        }
        sb.append('\n');
        sb.append("  ");
        for(int j=0;j<board.length;j++){
        	sb.append(".---");
        }
        sb.append(".\n");
        for (int i=0; i<board.length; i++) {
            sb.append(i + " |");
            for (int j=0; j<board.length; j++) {
                sb.append(board[i][j] == null ? "   |" : board[i][j].getJugador() == LOBO ? " O |" : " X |");
            }
            sb.append("\n");
            sb.append("  ");
            for(int j=0;j<board.length;j++){
            	sb.append(".---");
            }
            sb.append(".\n");
        }
        return sb.toString();
    }


}
