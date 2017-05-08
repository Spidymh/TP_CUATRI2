package es.ucm.fdi.tp.launcher;

import es.ucm.fdi.tp.base.console.ConsolePlayer;
import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameError;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.RandomPlayer;
import es.ucm.fdi.tp.base.player.SmartPlayer;
import es.ucm.fdi.tp.ttt.TttAction;
import es.ucm.fdi.tp.ttt.TttState;
import es.ucm.fdi.tp.was.WasAction;
import es.ucm.fdi.tp.was.WasState;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainPr4 {
	
	private static final int NUM_JUGADORES = 2;
	private static final int SMART_AI_DEPTH = 7;

	/**
	 * Crear estado de un juego.
	 * Se le pasa el nombre del juego y crea un estado correspondiente
	 * @param gameName
	 * @return juego que coincide con el nombre
	 */
	public static GameState<?,?> createInitialState(String gameName){
		String nombre = gameName.toLowerCase();
		switch(nombre){
		case "ttt":{
			GameState<TttState, TttAction> game = new TttState(3);
			return game;
		}
		case "was":{
			GameState<WasState, WasAction> game = new WasState();
			return game;
		}
		}
		System.out.println("El juego introducido no esta disponible");
		return null;
	}
	
	/**
	 * Crear un jugador.
	 * Se le pasa un nombre y un tipo y crea un objeto gameplayer.
	 * @param gameName
	 * @param playerType
	 * @param playerName
	 * @return jugador
	 */
	public static GamePlayer createPlayer(String gameName,
			String playerType, String playerName){
		String jugador = playerType.toLowerCase();
		switch(jugador){
		case "smart":{
			SmartPlayer player = new SmartPlayer(playerName, SMART_AI_DEPTH);
			return player;
		}
		case "random":{
			RandomPlayer player = new RandomPlayer(playerName);
			return player;
		}
		case "console":{
			Scanner in = new Scanner(System.in);
			ConsolePlayer player = new ConsolePlayer(playerName, in);
			return player;
		}
		}
		System.out.println("El tipo de jugador introducido no es valido");
		return null;
	}
	
	/**
	 * Jugar una partida.
	 * Funcion copiada del paquete demo para jugar una partida.
	 * @param initialState
	 * @param players
	 * @return el estado ganador
	 */
	public static <S extends GameState<S, A>, A extends GameAction<S, A>> int playGame(GameState<S, A> initialState,
			List<GamePlayer> players) {
		int playerCount = 0;
		for (GamePlayer p : players) {
			p.join(playerCount++); // welcome each player, and assign
									// playerNumber
		}
		
		if(playerCount!=NUM_JUGADORES){
			throw new GameError("Numero de jugadores no coincide con el del juego");
		}
		
		@SuppressWarnings("unchecked")
		S currentState = (S) initialState;
		System.out.println("Initial State:\n" + currentState);
		while (!currentState.isFinished()) {
			// request move
			A action = players.get(currentState.getTurn()).requestAction(currentState);
			// apply move
			currentState = action.applyTo(currentState);
			System.out.println("After action:\n" + currentState);

			if (currentState.isFinished()) {
				// game over
				String endText = "The game ended: ";
				int winner = currentState.getWinner();
				if (winner == -1) {
					endText += "draw!";
				} else {
					endText += "player " + (winner + 1) + " (" + players.get(winner).getName() + ") won!";
				}
				System.out.println(endText);
			}
		}
		return currentState.getWinner();
	}
	
	/**
	 * Muestra del menu principal.
	 */
	private static void mostrarMenu(){
		System.out.println("MENU:");
		System.out.println("1. Jugar partida");
		System.out.println("2. Ayuda");
		System.out.println("0. Salir\n");
		System.out.print("> ");
	}
	
	/**
	 * Muestra del menu ayuda.
	 */
	private static void mostrarAyuda(){
		System.out.println("AYUDA:");
		System.out.println("Juegos disponibles:");
		System.out.println("1. Tic Tac Toe (ttt)");
		System.out.println("2. Wolf and Sheep (was)\n");
		System.out.println("Tipos de jugadores disponibles:");
		System.out.println("1. SmartPlayer");
		System.out.println("2. ConsolePlayer");
		System.out.println("3. RandomPlayer\n\n");
	}
	
	/**
	 * Creacion de lista de jugadores.
	 * Funcion auxiliar para simplificar el void main.
	 * @param nombrePartida
	 * @param cin
	 * @return lista de jugadores
	 */
	private static List<GamePlayer> generarListaJugadores (String nombrePartida, Scanner cin){
		List<GamePlayer> jugadores = new ArrayList<GamePlayer>();
		for (int i = 0; i < NUM_JUGADORES; ++i){
			System.out.println("Introduzca el nombre del " + (i+1) + " jugador:\n");
			System.out.print("> ");
			String nombreJugador = cin.nextLine();
			System.out.println("Introduzca el tipo del " + (i+1) + " jugador: (console, smart, random)\n");
			System.out.print("> ");
			String tipoJugador = cin.nextLine();
			System.out.println("");
			GamePlayer jugador = createPlayer(nombrePartida ,tipoJugador, nombreJugador);
			if(jugador!=null)jugadores.add(jugador);
			else {
				return null;
			}
		}
		return jugadores;
	}
	
	
	
	/**
	 * Main method.
	 * 
	 * @param args
	 */
	public static void main(String... args) {
		Scanner cin = new Scanner(System.in);
		int opcion = 0;
		mostrarMenu();
		opcion = cin.nextInt();
		cin.nextLine();
		while (opcion != 0){
			System.out.println("");
			switch (opcion){
			case 1:{
				System.out.println("Introduzca el nombre del juego: (was, ttt)\n");
				System.out.print("> ");
				String nombrePartida;
				nombrePartida = cin.nextLine();
				System.out.println("");
				GameState<?,?> partida = createInitialState(nombrePartida);
				if(partida==null){
					System.out.println("Finalizando ejecucion...");
					System.exit(0);
				}
				List<GamePlayer> jugadores = generarListaJugadores(nombrePartida, cin);
				if(jugadores==null){
					System.out.println("Finalizando ejecucion...");
					System.exit(0);
				}
				playGame(partida, jugadores);
			}break;
			case 2:{
				mostrarAyuda();
			}break;
			}
			mostrarMenu();
			opcion = cin.nextInt();
			cin.nextLine();
		}
		System.out.println("Finalizando ejecucion...");
		}
	}
