package es.ucm.fdi.tp.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import es.ucm.fdi.tp.base.console.ConsolePlayer;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.player.RandomPlayer;
import es.ucm.fdi.tp.base.player.SmartPlayer;
import es.ucm.fdi.tp.mvc.GameTable;
import es.ucm.fdi.tp.ttt.TttAction;
import es.ucm.fdi.tp.ttt.TttState;
import es.ucm.fdi.tp.was.WasAction;
import es.ucm.fdi.tp.was.WasState;

public class VentanaInicio extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6656027518822525670L;
	private static final int SMART_AI_DEPTH = 7;

	public VentanaInicio(String name){
		super(name);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BoxLayout layout = new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS);
		
		setLayout(layout);
		
		insertarElementosGUI(this); 
		this.pack();
		this.setLocation(600, 300);
		setSize(300, 400);
		setVisible(true);
	}
	
	private static boolean seleccionarModo(JFrame frame){
		Object[] options = { "Modo Gráfico", "Modo Consola" };
		int n = JOptionPane.showOptionDialog(null, "Elija el modo que desea ejecutar.", "Practica 5 - G8",
		JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		return n == 0;
	}
	
	private void insertarElementosGUI(JFrame frame){
		
		boolean modoGrafico = seleccionarModo(frame);
		JLabel titulo1 = new JLabel("PRACTICA 5 - Grupo 8");
		JLabel titulo2 = new JLabel("Pablo Martín");
		JLabel titulo3 = new JLabel("José María López");
		
		String Opciones[] = {"manual", "smart", "random"};
		JComboBox<String> JugadorTipo1 = new JComboBox<String>(Opciones);
		JugadorTipo1.setMaximumSize(new Dimension(150,25));
		JComboBox<String> JugadorTipo2 = new JComboBox<String>(Opciones);
		JugadorTipo2.setMaximumSize(new Dimension(150,25));
		
		JButton was = new JButton("Wolf and Sheep");
		was.addActionListener(new ActionListener(){
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public void actionPerformed(ActionEvent e) {
				GameTable<WasState, WasAction> table = new GameTable<WasState, WasAction>(new WasState());
				table.start();
				if (modoGrafico){
				JugadorUI<WasState, WasAction> Jugador1 = new JugadorUI<WasState, WasAction>("jugador1", table, "was", 0);
				Jugador1.setLocation(400, 300);
				JugadorUI<WasState, WasAction> Jugador2 = new JugadorUI<WasState, WasAction>("jugador2", table, "was", 1);
				Jugador2.setLocation(300, 200);
				Jugador1.toFront();
			

				}
				else{
					Scanner cin = new Scanner(System.in);
					List<GamePlayer> players = new ArrayList<GamePlayer>();
					switch (JugadorTipo1.getSelectedIndex()){
					case 0:{
						players.add(new ConsolePlayer("Jugador 1", cin));
					}break;
					case 1:{
						players.add(new SmartPlayer("Jugador 1", SMART_AI_DEPTH));
					}break;
					case 2: {
						players.add(new RandomPlayer("Jugador 1"));
					}break;
					}
					switch (JugadorTipo2.getSelectedIndex()){
					case 0:{
						players.add(new ConsolePlayer("Jugador 2", cin));
					}break;
					case 1:{
						players.add(new SmartPlayer("Jugador 2", SMART_AI_DEPTH));
					}break;
					case 2: {
						players.add(new RandomPlayer("Jugador 2"));
					}break;
					}
					ConsoleController<WasState, WasAction> controladorJuego = new ConsoleController (players, table);
					new ConsoleView(table);
					frame.setVisible(false);
					frame.dispose();
					controladorJuego.run();
				}
				frame.setVisible(false);
				frame.dispose();
			}
		});
		JButton ttt = new JButton("Tic Tac Toe");
		ttt.addActionListener(new ActionListener(){
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public void actionPerformed(ActionEvent e) {
				GameTable<TttState, TttAction> table = new GameTable<TttState, TttAction>(new TttState(3));
				table.start();
				if(modoGrafico){
				JugadorUI<TttState, TttAction> Jugador1 = new JugadorUI<TttState, TttAction>("jugador1", table, "ttt", 0);
				Jugador1.setLocation(400, 300);
				JugadorUI<TttState, TttAction> Jugador2 = new JugadorUI<TttState, TttAction>("jugador2", table, "ttt", 1);
				Jugador2.setLocation(300, 200);
				Jugador1.toFront();
				}
				else{
					Scanner cin = new Scanner(System.in);
					List<GamePlayer> players = new ArrayList<GamePlayer>();
					switch (JugadorTipo1.getSelectedIndex()){
					case 0:{
						players.add(new ConsolePlayer("Jugador 1", cin));
					}break;
					case 1:{
						players.add(new SmartPlayer("Jugador 1", SMART_AI_DEPTH));
					}break;
					case 2: {
						players.add(new RandomPlayer("Jugador 1"));
					}break;}
					switch (JugadorTipo2.getSelectedIndex()){
					case 0:{
						players.add(new ConsolePlayer("Jugador 2", cin));
					}break;
					case 1:{
						players.add(new SmartPlayer("Jugador 2", SMART_AI_DEPTH));
					}break;
					case 2: {
						players.add(new RandomPlayer("Jugador 2"));
					}break;
					}
					ConsoleController<TttState, TttAction> controladorJuego = new ConsoleController (players, table);
					new ConsoleView(table);
					frame.setVisible(false);
					frame.dispose();
					controladorJuego.run();
				}
				frame.setVisible(false);
				frame.dispose();
			}
		});
		
		JButton exit= new JButton("Exit");
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		
		titulo1.setAlignmentX(CENTER_ALIGNMENT);
		titulo2.setAlignmentX(CENTER_ALIGNMENT);
		titulo3.setAlignmentX(CENTER_ALIGNMENT);
		was.setAlignmentX(CENTER_ALIGNMENT);
		ttt.setAlignmentX(CENTER_ALIGNMENT);
		exit.setAlignmentX(CENTER_ALIGNMENT);
		
		frame.getContentPane().add(Box.createRigidArea(new Dimension(0, 15)));
		frame.getContentPane().add(titulo1);
		frame.getContentPane().add(Box.createRigidArea(new Dimension(0, 15)));
		frame.getContentPane().add(titulo2);
		frame.getContentPane().add(Box.createRigidArea(new Dimension(0, 10)));
		frame.getContentPane().add(titulo3);
		frame.getContentPane().add(Box.createRigidArea(new Dimension(0, 40)));
		if (!modoGrafico){
			frame.getContentPane().add(JugadorTipo1);
			frame.getContentPane().add(Box.createRigidArea(new Dimension(0, 15)));
			frame.getContentPane().add(JugadorTipo2);
			}
		frame.getContentPane().add(Box.createRigidArea(new Dimension(0, 15)));
		frame.getContentPane().add(was);
		frame.getContentPane().add(Box.createRigidArea(new Dimension(0, 10)));
		frame.getContentPane().add(ttt);
		frame.getContentPane().add(Box.createRigidArea(new Dimension(0, 10)));
		frame.getContentPane().add(exit);
	}
	
	

	public static void main(String[] args) {
		new VentanaInicio ("Practica 5");
	}
	
}