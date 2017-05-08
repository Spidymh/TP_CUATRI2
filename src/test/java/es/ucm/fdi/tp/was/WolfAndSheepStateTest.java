package es.ucm.fdi.tp.was;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;


public class WolfAndSheepStateTest {

    private static final int DIM=8;
	
    /**
     * Prueba para comprobar que los movimientos iniciales de las ovejas son correctos.
     */
    @Test
    public void TestInitialSheep(){
    	WasState state=new WasState(); 
    	ArrayList<WasAction> accionesoveja=(ArrayList<WasAction>) state.validActions(1);
    	boolean fichasCorrectas = true;
    	for (int i = 0; i < 7; ++i){ // 7 es el numero de acciones validas de las ovejas en el turno 1.
    		switch(accionesoveja.get(i).getposFichaY()){
    		case 1: {
    			if(i != 0 && i !=1) fichasCorrectas = false;
    		}break;
    		case 3:{
    			if(i != 2 && i != 3) fichasCorrectas = false;
    		}break;
    		case 5:{
    			if(i != 4 && i != 5) fichasCorrectas = false;
    		}break;
    		case 7:{
    			if(i != 6) fichasCorrectas = false;
    		}break;
    		}
    	}
    	assertTrue(fichasCorrectas);
    }
    
    /**
     * Prueba para ver que si las ovejas no pueden mover se genera u movimiento que no afecta al tablero.
     */
    @Test
    public void SheepnoMovs(){/*
    	WasState state=new WasState();
    	int[][] tablero=state.getBoard();
    	int[][] tablerosig = null;
    	for (int ctrl=0;ctrl<tablero[1].length;ctrl++)tablero[1][ctrl]=0;
    	state=new WasState(state,tablero,false,state.getWinner());
		state=new WasState(state, state.getBoard(),state.isFinished(),state.getWinner());
    	ArrayList<WasAction> accionesdelaoveja=(ArrayList<WasAction>) state.validActions(1);
    	assertTrue(accionesdelaoveja.size()==1);
		state=new WasState(state, state.getBoard(),state.isFinished(),state.getWinner());
    	for(WasAction a:accionesdelaoveja)
    		tablerosig = a.applyTo(state).getBoard();
    		for(int i=0;i<tablero.length;i++)
    			for(int j=0;j<tablero[i].length;j++)
    			assertTrue(tablero[i][j]==tablerosig[i][j]);
    			*/
    }
    
    /**
     * Prueba para ver que si el lobo esta rodeado entonces las ovejas ganan.
     */
    @Test
	public void TestSurroundedWolf(){
		WasState state=new WasState();
		for(int LoboX=0;LoboX<DIM;LoboX++){
			for(int LoboY=(LoboX+1)%2;LoboY < DIM;LoboY+=2){
				state=new WasState(LoboX,LoboY);
				assertTrue(state.isWinner());
			}
		}
	}
	
    /**
     * Prueba para comprobar que los movimientos iniciales del lobo son correctos.
     */
	@Test
	public void TestInitialWolf(){
		WasState state=new WasState();
		ArrayList<WasAction> accionesdellobo=(ArrayList<WasAction>) state.validActions(0);
		assertTrue(accionesdellobo.size()==1);
		for(WasAction a:accionesdellobo)state=a.applyTo(state);
		accionesdellobo=(ArrayList<WasAction>) state.validActions(0);
		assertTrue(accionesdellobo.size()==4);
	}
	
    /**
     * Prueba para comprobar que el lobo sale ganador cuando llega final del tablero.
     */
	@Test
	public void TestWolfWins(){
		final int[][] sol={
		{7,0}, {6, 1},
		{6,1}, {5,0},
		{5,0}, {4,1},
		{4,1}, {3,0},
		{3,0}, {2,1},
		{2,1}, {1,0},
		{1,0}, {0,1}};
		//WasState state=gana(sol);
		//state=new WasState(state, state.getBoard(),false,-1);
		//assertTrue(state.isWinner());
	}
	
	/**
	 * Funcion auxiliar usada para hacer movimientos en pruebas unitarias.
	 * @param movs lista de movimientos a comprobar
	 * @return el estado despues de mover.
	 */
	private static WasState gana(int[]...movs){
		WasState state=new WasState();
		for(int ctrl=0;ctrl<movs.length;ctrl+=2){
			WasAction wa=new WasAction(state.getTurn(),movs[ctrl+1][0],
					movs[ctrl+1][1],movs[ctrl][0],movs[ctrl][1]);
			state=wa.applyTo(state);
		//	state=new WasState(state, state.getBoard(),state.isFinished(),state.getWinner());//Cambia el turno
		}
		return state;
	}
}

