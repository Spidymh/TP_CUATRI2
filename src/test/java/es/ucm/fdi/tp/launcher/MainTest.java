package es.ucm.fdi.tp.launcher;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.junit.Test;

import es.ucm.fdi.tp.base.console.ConsolePlayer;
import es.ucm.fdi.tp.base.model.GameError;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.RandomPlayer;
import es.ucm.fdi.tp.base.player.SmartPlayer;
import es.ucm.fdi.tp.was.WasState;

public class MainTest {
	@Test
	public void ExcesoNumArgs(){
		String mensaje=null;
		try{
		SmartPlayer player1=new SmartPlayer("Pepito",1);
		RandomPlayer player2=new RandomPlayer("Zulano");
		ConsolePlayer player3=new ConsolePlayer("Peter", new Scanner(System.in));
		List<GamePlayer>jugadores=new ArrayList<GamePlayer>();
		jugadores.add(player1);
		jugadores.add(player2);
		jugadores.add(player3);
		WasState state=new WasState();
		MainPr4.playGame(state,jugadores);
		}
		catch(GameError exc){
			mensaje=exc.toString();
			}
		assertNotNull(mensaje);
	}
	
	@Test
	public void NosufiNumArgs(){
		String mensaje=null;
		try{
		SmartPlayer player1=new SmartPlayer("Pepito",1);
		List<GamePlayer>jugadores=new ArrayList<GamePlayer>();
		jugadores.add(player1);
		WasState state=new WasState();
		MainPr4.playGame(state,jugadores);
		}
		catch(GameError exc){
			mensaje=exc.toString();
			}
		assertNotNull(mensaje);
	}
	
	/**
	 * Generamos 10 nombres aleatorios y comprobamos que no son validos.
	 * La probabilidad de que lo sean es despreciable
	 */
	@Test
	public void notValidGame(){
		int longitud;
		char letra;
		String juego="";
		boolean fallo=true;
		Random r = new Random(42); 
		r.nextInt(27); // "__
		for(int ctrl=0;ctrl<10;ctrl++){
			longitud=(int)(Math.random()*10+1);
			for(int ctrl2=0;ctrl2<longitud;ctrl2++){
				letra= (char) (((int)(Math.random()*27)) + (int)'a');
				juego+=letra;
			}
			GameState<?,?> partida=MainPr4.createInitialState(juego);
			if(partida!=null)fallo=false;
			juego="";
		}
		assertTrue(fallo);
	}
}
