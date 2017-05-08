package es.ucm.fdi.tp.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import es.ucm.fdi.tp.base.console.ConsolePlayer;
import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.RandomPlayer;
import es.ucm.fdi.tp.mvc.GameTable;
import es.ucm.fdi.tp.was.WasAction;
import es.ucm.fdi.tp.was.WasState;

/**
 * 
 * @author Pablo Martin Huertas y Jose Maria Lopez Morales
 * Clase encargada de controlar el flujo del juego en modo consola
 * @param <S> es un estado
 * @param <A> es una accion
 */
public class ConsoleController <S extends GameState<S,A>, A extends GameAction<S,A>> implements Runnable {

	private List<GamePlayer> jugadores;
	private GameTable<S,A> juego;
	
	
	public ConsoleController(List<GamePlayer> players, GameTable<S,A> game) {
			this.jugadores = players;
			this.juego = game;
	}
	
	@Override
	public void run() {
		int playerCount = 0;
		for (GamePlayer p : jugadores) {
			p.join(playerCount++); // welcome each player, and assign
									// playerNumber
		}
		this.juego.start();

		while (!juego.getState().isFinished()) {
			// request move
			A action = jugadores.get(juego.getState().getTurn()).requestAction(juego.getState());
			// apply move
			juego.execute(action);
		}	
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String... args) {
		
		try (Scanner s = new Scanner(System.in)) {
			List<GamePlayer> players = new ArrayList<GamePlayer>();
			GameState<WasState, WasAction> estadoJuego = new WasState();
			GameTable<WasState, WasAction> game = new GameTable(estadoJuego);
			players.add(new ConsolePlayer("Alice", s));
			players.add(new RandomPlayer("AiBob"));
			ConsoleController<WasState, WasAction> controladorJuego = new ConsoleController (players, game);
			new ConsoleView(game);
			controladorJuego.run();
		} 
	}

	
}
