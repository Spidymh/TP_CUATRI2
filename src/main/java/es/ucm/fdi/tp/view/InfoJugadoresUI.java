package es.ucm.fdi.tp.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;

import es.ucm.fdi.tp.mvc.ColorChangeEvent;
import es.ucm.fdi.tp.mvc.ColorChangeListenable;
import es.ucm.fdi.tp.mvc.ColorChangeListener;

public class InfoJugadoresUI extends JPanel implements ColorChangeListenable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tabla;
	private ModeloTabla modelo;
	private ArrayList<ColorChangeListener> observadores;
	
	
	private void notify(ColorChangeEvent e){
		for(int i=0;i<observadores.size();i++)observadores.get(i).notifyColorChangeEvent(e);
		repaint();
	}
	
	InfoJugadoresUI(int numJug){
		this.observadores=new ArrayList<ColorChangeListener>();
		this.setLayout(new FlowLayout());
		this.modelo=new ModeloTabla(2);
		this.tabla=new JTable(modelo){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
				Component comp = super.prepareRenderer(renderer, row, col);
				if (col == 1)
					comp.setBackground(modelo.getColorenpos(row));
				else
					comp.setBackground(Color.WHITE);
				return comp;
			}
		};

		tabla.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent evento){
				int row = tabla.rowAtPoint(evento.getPoint());
				int col = tabla.columnAtPoint(evento.getPoint());
				if (row >= 0 && col >= 0) {
					changeColor(row);
				}
			}
		});
		
		tabla.setSize(tabla.getAutoResizeMode(), tabla.getAutoResizeMode());
		tabla.setAutoResizeMode(WHEN_IN_FOCUSED_WINDOW);
		JScrollPane scroll=new JScrollPane(tabla);
		this.add(scroll);
		//this.setSize(new Dimension(275, 275));
		Border b = BorderFactory.createLineBorder(Color.black, 1);
		this.setBorder(BorderFactory.createTitledBorder(b, "Información jugadores"));
	}

	private void changeColor(int row) {
		Color c=JColorChooser.showDialog(null, "Selecciona otro color", Color.white);
		
		modelo.setColor(row, c);
		this.notify(new ColorChangeEvent(row,c));
	}

	public Color[] getColor(){
		return this.modelo.getColores();
	}
	
	public static void main(String ... args) {
		JFrame jf = new JFrame("prueba");
		JPanel jp=new JPanel();
		jp.add(new InfoJugadoresUI(2));
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jf.setContentPane(jp);
		jf.setSize(400, 400);
		jf.setVisible(true);
	}

	@Override
	public void addColorChangeObserver(ColorChangeListener listener) {
		this.observadores.add(listener);
	}
	
}