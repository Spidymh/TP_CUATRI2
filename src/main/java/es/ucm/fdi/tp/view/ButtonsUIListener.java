package es.ucm.fdi.tp.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public abstract class ButtonsUIListener implements ActionListener {
	
	private boolean puedeInteractuar;

	protected abstract void generateRandom();
	protected abstract void generateSmart();
	protected abstract void reset();
	protected abstract void apagar();

	
	
	public void setPuedeInteractuar(boolean nuevoValor){
		this.puedeInteractuar=nuevoValor;
	}	
	
	public boolean getPuedeInteractuar(){
		return this.puedeInteractuar;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		case "Random": {
			if(puedeInteractuar)generateRandom();
		}break;
		case "Smart":{
			if(puedeInteractuar)generateSmart();
		}break;
		case "Reiniciar":{
			if(puedeInteractuar)reset();
		}break;
		case "Apagar": {
			apagar();
		}break;
		}

	}
}
