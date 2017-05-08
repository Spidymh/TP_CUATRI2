package es.ucm.fdi.tp.view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.RandomPlayer;
import es.ucm.fdi.tp.base.player.SmartPlayer;
import es.ucm.fdi.tp.mvc.BoardEvent;
import es.ucm.fdi.tp.mvc.BoardListenable;
import es.ucm.fdi.tp.mvc.BoardListener;
import es.ucm.fdi.tp.mvc.ColorChangeEvent;
import es.ucm.fdi.tp.mvc.GameTable;
import es.ucm.fdi.tp.mvc.ShapeChangeEvent;
import es.ucm.fdi.tp.was.WasAction;
import es.ucm.fdi.tp.was.WasState;

public class TableroUI<S extends GameState<S, A>, A extends GameAction<S, A>> extends JBoard implements BoardListenable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4433603032523198502L;

	
	private ModeloTablero<S,A> model;
	private GameState state;
	private List<BoardListener> observadores;
	private Color[] colores;
	private boolean puedeInteractuar;
	
	public void setPuedeInteractuar(boolean nuevoValor){
		this.puedeInteractuar=nuevoValor;
	}	

	public boolean getPuedeInteractuar(){
		return this.puedeInteractuar;
	}
	
    public boolean isFinished(){
    	return state.isFinished();
    }
    
    public void setEstado(S state){
    	this.state=state;
    }
	
    
	public TableroUI(ModeloTablero<S,A> model, GameState<S, A> estado, Color[] colores){
		super();
		this.colores=colores;
		this.model = model;	
		this.state=estado;
		this.observadores = new ArrayList<BoardListener>();
		this.puedeInteractuar=false;
	}

	@Override
	protected void keyTyped(int keyCode) {
		// TODO Auto-generated method stub
	}

	public boolean blocked(){
		return this.model.getBloqueado();
	}
    
    public GameAction generateSmart(int depth){
    	return new SmartPlayer("Peter", depth).requestAction(state);
    }
    
	public GameAction generateRandom(){
    	RandomPlayer ghost=new RandomPlayer("tom");
    	ghost.join(this.state.getTurn());
    	return ghost.requestAction(this.state);
    }
    
	
	@Override
	protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {
		//System.out.println("Mouse: " + clickCount + "clicks at position (" + row + "," + col + ") with Button "	+ mouseButton);
		if(this.puedeInteractuar){
		for (int i = 0; i < observadores.size(); ++i)
    		observadores.get(i).notifyBoardEvent(new BoardEvent(row, col, mouseButton));
		}
	}
	
	public A generateAct(int x, int y){
		return model.generateAction(x, y);
	}
	
	@Override
	protected Shape getShape(int player) {
		return player == 0 ? Shape.RECTANGLE : Shape.CIRCLE;/**/
	}

	@Override
	protected Color getColor(pieza p) {
		return this.colores[p.getJugador()];
	}

	@Override
	protected Color getBackground(int row, int col) {
		return (row+col) % 2 == 0 ? new Color(240, 217, 181) : new Color(181, 136, 99); //Marron claro y oscuro
	}

	@Override
	protected int getNumRows() {
		return this.model.getRow();
	}

	@Override
	protected int getNumCols() {
		return this.model.getCol();
	}
	
	protected int getSepPixels() {
		return 0; 
	}

	@Override
	protected pieza getPieza(int row, int col) {
		return state.getPiezaAt(row, col);
	}


	@Override
	public void addObserver(BoardListener o) {
		observadores.add(o);
	}
	
	public void notifyColorChanges(ColorChangeEvent e){
		this.colores[e.getRow()] = e.getNuevoColor();
		repaint();
	}
	/**
	 * Marca una pieza
	 */
	@Override
	protected void seleccionar(int row, int col) {
		if(this.puedeInteractuar && getPieza(row,col)!=null && getPieza(row,col).getJugador()==state.getTurn()){
			if(this.model.piezasSeleccionables)getPieza(row, col).setSelected();
			}
	}

	
	public static void main (String ... args){
		JFrame jf = new JFrame("titulo");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GameTable<WasState, WasAction> nuevo=new GameTable<WasState, WasAction>(new WasState());
		TableroWas<WasState, WasAction> otro=new TableroWas<WasState, WasAction>(nuevo.getState());
		@SuppressWarnings("unchecked")
		TableroUI<WasState, WasAction> prueba = new TableroUI<WasState, WasAction>(otro, nuevo.getState(), new Color[]{Color.white, Color.black});
		prueba.setPreferredSize(new Dimension (400,400));
		prueba.setMaximumSize(new Dimension(400, 400));
		jf.setContentPane(prueba);
		jf.setSize(400, 400);
		jf.setVisible(true);
	}


	

}