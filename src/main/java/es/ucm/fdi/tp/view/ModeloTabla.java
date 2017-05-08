package es.ucm.fdi.tp.view;

import java.awt.Color;

import javax.swing.table.AbstractTableModel;

public class ModeloTabla extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8954083002498800221L;
	private int numJugadores;
	private Color[] colores;

	public ModeloTabla(int numJug){
		this.numJugadores=numJug;
		this.colores=new Color[numJugadores];
		for(int i=0;i<numJugadores;i++){
			colores[i]=generarColorAleatorio();
		}
	}

	private static int generarNumeroAleatorio(){
		return (int) (Math.random()*255);
	}
	
	private static Color generarColorAleatorio(){
		return new Color(generarNumeroAleatorio(),generarNumeroAleatorio(),generarNumeroAleatorio());
	}
	
	@Override
	public int getColumnCount() {
		return 2;
	}
	
	public void setColor(int pos, Color nuevoColor){
		colores[pos]=nuevoColor;
	}
	
	@Override
	public int getRowCount() {
		return numJugadores;
	}

	public void refresh() {
		fireTableDataChanged();
	}
	
	public Color getColorenpos(int pos){
		return colores[pos];
	}
	
	@Override
	public Object getValueAt(int rowIndex, int colIndex) {
		return colIndex==0? "Jugador "+(rowIndex+1): 
			"";
		}
	
	public Color[] getColores(){
		return this.colores;
	}
	
	@Override
	public String getColumnName(int column){
		return column==0?"#Jugador ": "Color";
	}

}