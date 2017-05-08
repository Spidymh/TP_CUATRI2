package es.ucm.fdi.tp.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class InfoMensajesUI extends JPanel{
	
	public TitledBorder titulo;
	public JScrollPane ventanaTexto;
	public JTextArea texto;

	private static final long serialVersionUID = 2589246569616571317L;
	
	InfoMensajesUI(){
		titulo = new TitledBorder("Informacion");
		texto = new JTextArea(80,80);
		texto.setEditable(false);
		ventanaTexto =  new JScrollPane(texto);
		ventanaTexto.setPreferredSize(new Dimension(200, 80));
		ventanaTexto.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		
		this.setLayout(new BorderLayout());
		Border b = BorderFactory.createLineBorder(Color.black, 1);
		this.setBorder(BorderFactory.createTitledBorder(b, "Información estado"));
		this.add(ventanaTexto);
	}
	
	public void insertarMensaje(String frase){
		texto.append("> " + frase + System.getProperty("line.separator"));
	}
	public void vaciar(){
		texto.setText("");
	}
	
	
}
