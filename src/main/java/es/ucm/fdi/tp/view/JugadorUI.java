package es.ucm.fdi.tp.view;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.BoardEvent;
import es.ucm.fdi.tp.mvc.BoardListener;
import es.ucm.fdi.tp.mvc.ColorChangeEvent;
import es.ucm.fdi.tp.mvc.ColorChangeListener;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameObserver;
import es.ucm.fdi.tp.mvc.GameTable;
import es.ucm.fdi.tp.mvc.ModeChangeEvent;
import es.ucm.fdi.tp.mvc.ModeListener;
import es.ucm.fdi.tp.ttt.TttState;
import es.ucm.fdi.tp.was.WasAction;
import es.ucm.fdi.tp.was.WasState;

public class JugadorUI<S extends GameState<S,A>, A extends GameAction<S,A>> extends JFrame 
	implements ColorChangeListener, BoardListener, GameObserver<S,A>, ModeListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6696978050638543432L;
	
	public enum PlayerMode{
		RANDOM, SMART, MANUAL
	}
	
	private static final int SMART_AI_DEPTH = 10;
	private BotoneraUI botonera;
	private InfoMensajesUI mensajes;
	private InfoJugadoresUI infoJugadores;
	private TableroUI<S,A> tablero;
	private GameTable<S,A> juego;
	private int ident;
	private PlayerMode modo=PlayerMode.MANUAL;
	
	private boolean tieneElTurno(){
		return this.juego.getState().getTurn()==this.ident;
	}
	
	
	JugadorUI(String name, GameTable<S,A> juego, int id){
		super(name);
		this.juego = juego;
		this.ident=id;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		setLayout(new BorderLayout());
		insertarElementosGUI(this.getContentPane()); 
		juego.addObserver(this);
		this.tablero.setPuedeInteractuar(this.tieneElTurno());
		this.botonera.setPuedeInteractuar(this.tieneElTurno());
		this.pack();
		setSize(800, 600);
		setVisible(true); 
	}
	public JugadorUI(String name, GameTable<S, A> juego,
			String gameName, int id) {
			super(name);
			this.ident=id;
			this.juego = juego;
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
			setLayout(new BorderLayout());
			insertarElementosGUI(this.getContentPane(), gameName); 
			this.tablero.setPuedeInteractuar(getTurno()==this.ident);
			this.botonera.setPuedeInteractuar(this.getTurno()==this.ident);
			juego.addObserver(this);
			this.pack();
			setSize(800, 600);
			setVisible(true); 
		}
	
	@SuppressWarnings("unchecked")
	private void insertarElementosGUI(Container pane, String gameName) {
		this.botonera =  new BotoneraUI(new ButtonsUIListener(){
		@Override
		public void generateRandom() {
			juego.execute((A)tablero.generateRandom());
			repaint();
		}
		@Override
		public void generateSmart() {
			juego.execute((A)tablero.generateSmart(SMART_AI_DEPTH));
		}
		@Override
		public void reset() {
			juego.reset();
			repaint();
		}
		@Override
		public void apagar() {
			System.exit(0);
		}
	});
	pane.add(botonera, BorderLayout.NORTH);
	
	JPanel este = new JPanel();
	este.setLayout(new BoxLayout(este, BoxLayout.Y_AXIS));
	este.setPreferredSize(new Dimension(275, 600));
	este.setMaximumSize(new Dimension(275,600));
	
	this.mensajes = new InfoMensajesUI();
	mensajes.insertarMensaje("Ventana que muestra los eventos del juego");
	mensajes.setPreferredSize(new Dimension(275, 700));
	este.add(mensajes);
	
	este.add(Box.createRigidArea(new Dimension(0, 10)));
	
	this.infoJugadores = new InfoJugadoresUI(2);
	this.infoJugadores.addColorChangeObserver(this);
	infoJugadores.setPreferredSize(new Dimension(275, 150));
	este.add(this.infoJugadores);
	pane.add(este, BorderLayout.EAST);
	switch(gameName){
	case "was":{
		this.tablero = new TableroUI<S,A>(
				new TableroWas<S,A>((WasState) juego.getState()), juego.getState(), infoJugadores.getColor());
	}break;
	case "ttt": {
		this.tablero = new TableroUI<S,A>(
				new TableroTtt<S,A>((TttState)juego.getState(), 3), juego.getState(), infoJugadores.getColor());
	}break;
	}

	tablero.setPreferredSize(new Dimension (500,500));
	tablero.setMinimumSize(new Dimension (500,500));
	pane.add(tablero, BorderLayout.CENTER);
	tablero.addObserver(this);
	this.botonera.AddModeObserver(this);
}
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void insertarElementosGUI(Container pane){
		
		this.botonera =  new BotoneraUI(new ButtonsUIListener(){
			@Override
			public void generateRandom() {
				juego.execute((A)tablero.generateRandom());
				repaint();
			}
			@Override
			public void generateSmart() {
				juego.execute((A)tablero.generateSmart(SMART_AI_DEPTH));
			}
			@Override
			public void reset() {
				juego.reset();
				repaint();
			}
			@Override
			public void apagar() {
				System.exit(0);
			}
		});
		pane.add(botonera, BorderLayout.NORTH);
		
		JPanel este = new JPanel();
		este.setLayout(new BoxLayout(este, BoxLayout.Y_AXIS));
		este.setPreferredSize(new Dimension(275, 600));
		este.setMaximumSize(new Dimension(275,600));
		this.botonera.AddModeObserver(this);
		
		this.mensajes = new InfoMensajesUI();
		mensajes.insertarMensaje("Ventana que muestra los eventos del juego");
		mensajes.setPreferredSize(new Dimension(275, 700));
		este.add(mensajes);
		
		este.add(Box.createRigidArea(new Dimension(0, 10)));
		
		this.infoJugadores = new InfoJugadoresUI(2);
		this.infoJugadores.addColorChangeObserver(this);
		infoJugadores.setPreferredSize(new Dimension(275, 150));
		este.add(this.infoJugadores);
		pane.add(este, BorderLayout.EAST);
		
		this.tablero = new TableroUI<S,A>(new TableroWas((WasState) juego.getState())
		, juego.getState(), this.infoJugadores.getColor());
		repaint();
		tablero.setPreferredSize(new Dimension (500,500));
		tablero.setMinimumSize(new Dimension (500,500));
		pane.add(tablero, BorderLayout.CENTER);
		tablero.addObserver(this);
	}

	@Override
	public void notifyBoardEvent(BoardEvent e) {
		A accion = tablero.generateAct(e.getX(), e.getY());
		if(accion != null) {
			if(tablero.isFinished())mensajes.insertarMensaje("La partida ha terminado,"
					+ "no puedes realizar mas movimientos\n");
			else{
				juego.execute(accion);
			}
		}
		tablero.repaint();
	}

    private int getWinner(){
    	return juego.getState().getWinner();
    }
    
    private boolean isWinner(int i){
    	return i==juego.getState().getWinner();
    }
    
    private boolean thereIsWinner(){
    	return juego.getState().getWinner()!=-1;
    }
    

    public int getTurno(){
    	return juego.getState().getTurn();
    }
    
	@SuppressWarnings("unchecked")
	@Override
	public void notifyEvent(GameEvent<S,A> e) {
		switch(e.getType()){
		case Change: {
			mensajes.insertarMensaje(e.getAction().toString());
			this.tablero.setPuedeInteractuar(this.tieneElTurno());
			this.botonera.setPuedeInteractuar(this.tieneElTurno());
			tablero.setEstado(e.getState());
			tablero.repaint();
			if(this.getTurno()==this.ident){
			switch(this.modo){
			case RANDOM:{
				if(!tablero.isFinished()){
				this.juego.execute((A)this.tablero.generateRandom());
				this.tablero.setPuedeInteractuar(this.tieneElTurno());
				this.botonera.setPuedeInteractuar(this.tieneElTurno());
				tablero.repaint();
				}
					}break;
			case SMART:{
				if(!tablero.isFinished()){
				this.juego.execute((A)this.tablero.generateSmart(SMART_AI_DEPTH));
				this.tablero.setPuedeInteractuar(this.tieneElTurno());
				this.botonera.setPuedeInteractuar(this.tieneElTurno());
				tablero.repaint();
				}
			}break;
			case MANUAL:{
				if(!tablero.isFinished()&&tablero.blocked()){
					this.juego.execute((A)this.tablero.generateRandom());
					this.tablero.setPuedeInteractuar(this.tieneElTurno());
					this.botonera.setPuedeInteractuar(this.tieneElTurno());
					repaint();
					}
				break;
			}
				}
			}
		}break;
		case Start: {
			mensajes.insertarMensaje(e.toString());
			this.tablero.setPuedeInteractuar(this.tieneElTurno());
			this.botonera.setPuedeInteractuar(this.tieneElTurno());
			repaint();
		}break;
		case Stop:{
			if(this.tablero.isFinished()){
				mensajes.insertarMensaje(e.toString());
				if(isWinner(ident))
					mensajes.insertarMensaje("Enhorabuena, has ganado!!!");
				else{
					if(thereIsWinner())mensajes.insertarMensaje("El ganador es el Jugador " + getWinner());
					else mensajes.insertarMensaje("Habeis empatado");
				}
			}
		}break;
		case Error:{
			System.out.println(e.toString());
		}break;
		case Info:{
			System.out.println("Console mode active");
		}break;
		case Reset:{
			System.out.println("Restarting game");
		}break;
		}
	}


	@Override
	public void notifyColorChangeEvent(ColorChangeEvent e) {
		this.tablero.notifyColorChanges(e);
	}
	
	@SuppressWarnings({ "incomplete-switch", "unchecked" })
	@Override
	public void notifyModeChangeEvent(ModeChangeEvent e) {
		this.modo=e.getModo();
		if(this.tieneElTurno()){
			if(e.getModo()!=PlayerMode.MANUAL){
				switch(e.getModo()){
				case SMART: this.juego.execute((A)this.tablero.generateSmart(SMART_AI_DEPTH));break;
				case RANDOM: this.juego.execute((A)this.tablero.generateRandom());break;
				}
			}
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		 GameTable<WasState, WasAction> table = new GameTable<WasState, WasAction>(new WasState());
		 table.start();
		 JugadorUI<WasState, WasAction> Jugador1 = new JugadorUI<WasState, WasAction>("jugador1", table, "was", 0);
		 JugadorUI<WasState, WasAction> Jugador2 = new JugadorUI<WasState, WasAction>("jugador2", table, "was", 1);
	}
	


}
