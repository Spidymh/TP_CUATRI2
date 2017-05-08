package es.ucm.fdi.tp.launcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import es.ucm.fdi.tp.base.console.ConsolePlayer;
import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.RandomPlayer;
import es.ucm.fdi.tp.base.player.SmartPlayer;
import es.ucm.fdi.tp.mvc.GameTable;
import es.ucm.fdi.tp.ttt.TttAction;
import es.ucm.fdi.tp.ttt.TttState;
import es.ucm.fdi.tp.view.ConsoleController;
import es.ucm.fdi.tp.view.ConsoleView;
import es.ucm.fdi.tp.view.JugadorUI;
import es.ucm.fdi.tp.was.WasAction;
import es.ucm.fdi.tp.was.WasState;

public class Main {

	private static final int SMART_AI_DEPTH = 7;
	
	private static GameTable<?, ?> createGame(String gType) {
		GameTable<?, ?> game = null;;
		switch(gType){
		case "was":{
			game =  new GameTable<WasState, WasAction>(new WasState());
			game.start();
			return game;
		}
		case "ttt":{
			game = new GameTable<TttState, TttAction>(new TttState(3));
			game.start();
			return game;
		}
		}
		return game;
	}
	
	private static boolean tipoJugadorCorrecto(String nombre){
		return nombre.equals("manual") || nombre.equals("smart")|| nombre.equals("random");
	}
	
	private static <S extends GameState<S, A>, A extends GameAction<S, A>>
		void startConsoleMode(GameTable<S, A> game, String playerModes[]) {
		
		for(int i = 0; i < playerModes.length; ++i){
			if(!tipoJugadorCorrecto(playerModes[i])){
				System.err.println("Invalid type of player");
				System.exit(1);
			}
		}
		Scanner cin = new Scanner(System.in);
		List<GamePlayer> players = new ArrayList<GamePlayer>();
		for(int i = 0; i < playerModes.length; ++i){
			switch(playerModes[i]){
			case "manual":{
				players.add(new ConsolePlayer("Jugador" + (i+1), cin));
			}break;
			case "smart":{
				players.add(new SmartPlayer("Jugador" + (i+1), SMART_AI_DEPTH));
			}break;
			case "random":{
				players.add(new RandomPlayer("Jugador" +(i+1)));
			}break;
			}
		}
		game.start();
		ConsoleController<?, ?> controladorJuego = new ConsoleController (players, game);
		new ConsoleView(game);
		controladorJuego.run();
	}
	
	@SuppressWarnings("unchecked")
	private static <S extends GameState<S, A>, A extends GameAction<S, A>>
		void startGUIMode(String gType, GameTable<S, A> game) {
		switch (gType){
		case "was":{
			JugadorUI<WasState, WasAction> Jugador1 = new JugadorUI<WasState, WasAction>("jugador1", (GameTable<WasState, WasAction>) game, "was", 0);
			Jugador1.setLocation(400, 300);
			JugadorUI<WasState, WasAction> Jugador2 = new JugadorUI<WasState, WasAction>("jugador2", (GameTable<WasState, WasAction>) game, "was", 1);
			Jugador2.setLocation(300, 200);
			Jugador1.toFront();
		}break;
		case "ttt":{
			JugadorUI<TttState, TttAction> Jugador1 = new JugadorUI<TttState, TttAction>("jugador1", (GameTable<TttState, TttAction>) game, "ttt", 0);
			Jugador1.setLocation(400, 300);
			JugadorUI<TttState, TttAction> Jugador2 = new JugadorUI<TttState, TttAction>("jugador2", (GameTable<TttState, TttAction>) game, "ttt", 1);
			Jugador2.setLocation(300, 200);
			Jugador1.toFront();
		}break;
		}
		
	}
	
	private static void usage() {
	System.out.println("El args debe contener:");
	System.out.println("game: ttt (para Tres-en-Raya) ó was (para Wolf and Sheep)");
	System.out.println("mode: gui (para usar Swing) ó console (para usar la interfaz de consola");
	System.out.println("playeri: manual (para un jugador manual player), random (para uno aleatorio) ó smart(para uno inteligente)");
	}
	
	public static void main(String ... args) {
	if (args.length < 2) {
	usage();
	System.exit(1);
	}
	GameTable<?, ?> game = createGame(args[0]);
	if (game == null) {
	System.err.println("Invalid game");
	System.exit(1);
	}
	String[] playerModes = Arrays.copyOfRange(args, 2, args.length);
	if (game.getState().getPlayerCount() != playerModes.length ) {
	System.err.println("Invalid number of players");
	System.exit(1);
	}
	switch (args[1]) {
	case "console":
	startConsoleMode(game, playerModes);
	break;
	case "gui":
	startGUIMode(args[0], game);
	break;
	default:
	System.err.println("Invalid view mode: "+args[1]);
	usage();
	System.exit(1);
	}
	}
}
