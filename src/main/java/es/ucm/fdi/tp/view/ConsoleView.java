package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameObservable;
import es.ucm.fdi.tp.mvc.GameObserver;


/**
 * Clase que se encarga de mostrar el estado de la partida al usuario en modo consola.
 */
public class ConsoleView<S extends GameState<S,A>, A extends GameAction<S,A>> implements GameObserver<S,A> {
	
	public ConsoleView(GameObservable<S,A> GameObservable) {
		GameObservable.addObserver(this);
	}

	@Override
	public void notifyEvent(GameEvent<S, A> e) {
		switch(e.getType()){
		case Start:{
			System.out.println("Initial state");
			System.out.println("After action:\n" + e.getState());
		}break;
		case Change: {
			System.out.println("After action:\n" + e.getState());
			if(e.getState().isFinished()){
				String endText = "The game ended: ";
				int winner = e.getState().getWinner();
				if (winner == -1) {
					endText += "draw!";
				} else {
					endText += "player " + (winner + 1) + " won!";
				}
				System.out.println(endText);
			}
		}break;
		case Error:{
			System.out.println(e.toString());
		}break;
		case Stop:{
			System.out.println("Finishing game");
		}break;
		case Info:{
			System.out.println("Console mode active");
		}break;
		case Reset:{
			System.out.println("Restarting game");
		}break;
		}
	}

}
