package es.ucm.fdi.tp.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;

import es.ucm.fdi.tp.mvc.ModeChangeEvent;
import es.ucm.fdi.tp.mvc.ModeListenable;
import es.ucm.fdi.tp.mvc.ModeListener;

public class ModoJugador extends JComboBox<String> implements ModeListenable, ActionListener{
	
	/**
	 * 
	 */
	
	private ArrayList<ModeListener> Observadores;
	private static final long serialVersionUID = 1L;
	static String OPCIONES[] = {"Manual", "Random", "Smart"};
	
	ModoJugador(){
		super(OPCIONES);
		this.Observadores=new ArrayList<ModeListener>();
		this.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
		        @SuppressWarnings("unchecked")
				JComboBox<String> cb = (JComboBox<String>)e.getSource();
		        String name = (String)cb.getSelectedItem();
		        	ModoJugador.this.notifyAll(new ModeChangeEvent(name));
		    }
			
		});
	}
	
	private void notifyAll(ModeChangeEvent e){
		for(int i=0;i<Observadores.size();i++)
			Observadores.get(i).notifyModeChangeEvent(e);
	}
	
	public String getModo(){
		return (String) this.getSelectedItem();
	}
	
	
	@Override
	public void addModeObserver(ModeListener listener) {
		this.Observadores.add(listener);
	}
	
}
