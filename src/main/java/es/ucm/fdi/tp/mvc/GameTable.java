package es.ucm.fdi.tp.mvc;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameError;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent.EventType;

/**
 * An event-driven game engine.
 * Keeps a list of players and a state, and notifies observers
 * of any changes to the game.
 */
public class GameTable<S extends GameState<S, A>, A extends GameAction<S, A>> implements GameObservable<S, A> {

    private S estadoInicial;
    private S estadoActual;
    private List<GameObserver<S,A>> observadores;
    private boolean parado;

    public GameTable(S initState) {
        this.estadoInicial = initState;
        this.estadoActual = null;
        this.parado = false;
        this.observadores = new ArrayList<GameObserver<S,A>>();
    }

    public void reset(){
    	estadoActual=estadoInicial;
        notificarObservadores(new GameEvent<S,A>(EventType.Start, null, estadoInicial, null, "Se ha reiniciado la partida"));
    }
    
    public void start() {
        estadoActual = estadoInicial;
        notificarObservadores(new GameEvent<S,A>(EventType.Start, null, estadoInicial, null, "Comienza una partida"));
    }
    
    public void stop() throws GameError {
        if(parado && !this.estadoActual.isFinished()) throw new GameError("Juego ya parado");
        else parado = true;
        StringBuilder cadena=new StringBuilder();
        if(estadoActual.isFinished()){
        	cadena.append("El juego ha terminado\n");
        }
        else cadena.append("El juego se ha detenido\n");
        notificarObservadores(new GameEvent<S,A>(EventType.Stop, null, estadoActual, null, cadena.toString()));
    }
    
    public void execute(A action) {
    	this.estadoActual = action.applyTo(estadoActual);
    	this.notificarObservadores(new GameEvent<S,A>(EventType.Change, action, estadoActual, null, "cambio"));
    	if(estadoActual.isFinished())stop();
    	}
    
    public S getState() {
    	return this.estadoActual;
    }
    
    
    public void notificarObservadores(GameEvent<S, A> e){
    	for (int i = 0; i < observadores.size(); ++i)
    		observadores.get(i).notifyEvent(e);
    }

    public void addObserver(GameObserver<S, A> o) {
        observadores.add(o);
    }
    public void removeObserver(GameObserver<S, A> o) {
        observadores.remove(o);
    }
}
