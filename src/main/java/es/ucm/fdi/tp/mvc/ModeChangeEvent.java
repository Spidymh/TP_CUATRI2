package es.ucm.fdi.tp.mvc;

import es.ucm.fdi.tp.view.JugadorUI.PlayerMode;

public class ModeChangeEvent {
	private PlayerMode modo;
	
	public ModeChangeEvent(String Modo){
		switch (Modo){
		case "Smart" :this.modo=PlayerMode.SMART;break;
		case "Random" :this.modo=PlayerMode.RANDOM;break;
		case "Manual" :this.modo=PlayerMode.MANUAL;break;
		}
	}
	
	public PlayerMode getModo(){
		return modo;
	}
}
